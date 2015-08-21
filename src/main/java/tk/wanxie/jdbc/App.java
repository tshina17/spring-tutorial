package tk.wanxie.jdbc;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tk.wanxie.jdbc.dao.HibernateDaoImpl;
import tk.wanxie.jdbc.model.*;

import java.util.Date;
import java.util.List;

public class App {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/jdbc.xml");
        //JdbcDaoImpl dao = context.getBean("jdbcDaoImpl", JdbcDaoImpl.class);
        //NamedParameterJdbcDaoImpl dao = context.getBean("namedParameterJdbcDao", NamedParameterJdbcDaoImpl.class);
        HibernateDaoImpl dao = context.getBean("hibernateDaoImpl", HibernateDaoImpl.class);


        /**
         * A Transient object is an object created outside a Session.
         * The object becomes persistent when the session save() it throughout the entire session, which keep track any changes to the object.
         * Once the session is closed, the object become detached.
         *
         * new() -> Transient -> session.save() -> Persistent -> session.close() -> Detached
         *
         * Transient <- session.delete() <- Persistent -> session.close() -> Detached
         */


        // all relational mapping should commit within one session, separate sessions could cause error
        Session session = dao.getSessionFactory().openSession();
        session.beginTransaction();

        //dao.insertCircle(new Circle(1, "First circle"));
        //System.out.println(dao.getCircleCount());

        Address addr = new Address("123 main street", "San Jose", "CA", "94223");
        Address addr2 = new Address("123 abc street", "San Francisco", "CA", "94203");

        User user1 = new User("First user", addr2, addr, new Date(), "The first person walk on the moon.");
        User user2 = new User("Second user", addr, addr2, new Date(), "The second person create the model.");

        session.persist(user1);
        session.save(user2);


        // Emails
        Email email1 = new Email("abc@abc.com");
        Email email2 = new Email("def@def.com");
        Email email3 = new Email("zxv@zxv.com");

        user1.getEmails().add(email1);
        user1.getEmails().add(email2);
        user1.getEmails().add(email3);

        user2.getEmails().add(email1);
        user2.getEmails().add(email2);
        user2.getEmails().add(email3);


        // Vehicles
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName("Car");

        TwoWheeler bike = new TwoWheeler();
        bike.setVehicleName("Bike");
        bike.setSteeringHandle("Bike Steering Handle");

        FourWheeler car = new FourWheeler();
        car.setVehicleName("Honda X2");
        car.setSteeringWheel("Honda Steering Wheel");

        user1.setVehicle(vehicle);

        session.save(vehicle);
        session.save(bike);
        session.save(car);


        // Credit Cards
        CreditCard card1 = new CreditCard();
        card1.setCardNumber("1234-5678-9012-3456");

        CreditCard card2 = new CreditCard();
        card2.setCardNumber("0000-1111-2222-3333");

        CreditCard card3 = new CreditCard();
        card3.setCardNumber("XXXX-XXXX-XXXX-XXXX");

        card3.setUser(user1);
        user1.getCreditCard().add(card1);           // cannot add card1 to user2 since already added to user1 as it is oneToMany mapping

        card1.setUser(user2);
        card2.setUser(user2);

        user2.getCreditCard().add(card1);
        user2.getCreditCard().add(card2);

        session.persist(card1);         // persist() might not execute an INSERT statement if outside of the transaction
        //session.save(card1);          // a long session is better to use persist() than save()
        session.save(card2);
        session.save(card3);


        // Workspaces
        Workspace workspace1 = new Workspace();
        workspace1.setWorkspaceName("Palo Alto");

        Workspace workspace2 = new Workspace();
        workspace2.setWorkspaceName("San Francisco");

        workspace1.getUsers().add(user1);
        workspace1.getUsers().add(user2);

        workspace2.getUsers().add(user1);
        workspace2.getUsers().add(user2);

        user1.getWorkspaces().add(workspace1);
        user1.getWorkspaces().add(workspace2);

        user2.getWorkspaces().add(workspace1);
        user2.getWorkspaces().add(workspace2);

        session.save(workspace1);
        session.save(workspace2);

        user1.getWorkspaces().remove(workspace1);
        user2.getWorkspaces().remove(workspace1);
        session.delete(workspace1);                         // must remove from user1 and user2 before can delete workspace1

        user1.setUsername("Update First user name");

        session.getTransaction().commit();
        session.close();

        System.out.println("");
        dao.getUser(1).getWorkspaces().forEach(s -> System.out.println(s));
        System.out.println("");

        user1.setUsername("changed username");



        Session session2 = dao.getSessionFactory().openSession();
        session2.beginTransaction();

        session2.update(user1);                 // do a select than update because of @SelectBeforeUpdate

        Query query = session2.createQuery("from User where userId > :user_id");    // HQL, User is the class name, userId is the field name
        query.setInteger("user_id", 0);                                             // bind the parameter, better to bind with name than index
        query.setFirstResult(1);                                                    // similar to mysql offset, used for pagination
        query.setMaxResults(1);                                                     // similar to mysql limit
        List<User> users = query.list();

        users.forEach(s -> Hibernate.initialize(s.getWorkspaces()));                // lazy loading all workspaces for each user

        Query query2 = session2.createQuery("select username from User");           // this will create a list of username only
        List<String> usernames = query2.list();

        Query query3 = session2.createQuery("select userId, username from User where userId = :user_id");           // this will create a list object array
        query3.setParameter("user_id", 2);
        List<Object[]> username_id = query3.list();

        Query query4 = session2.createQuery("select new Vehicle(username) from User");           // this will create a list of vehicle with the appropriate constructor
        List<Vehicle> vehicles = query4.list();

        Query query5 = session2.getNamedQuery("CreditCard.byId").setInteger("card_id", 2);      // using named query defined in the Entity class
        List<CreditCard> cards = query5.list();

        session2.getTransaction().commit();
        session2.close();

        users.forEach(s -> {
            System.out.println(s);
            s.getWorkspaces().forEach(d -> System.out.println(d));
        });

        usernames.forEach(s -> System.out.println(s));
        username_id.forEach(s -> System.out.println(s[0] + " - " + s[1]));
        vehicles.forEach(s -> System.out.println(s));
        cards.forEach(s -> System.out.println(s));
    }

}

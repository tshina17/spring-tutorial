package tk.wanxie.jdbc.dao;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import tk.wanxie.jdbc.model.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository                         // is a specialization of the @Component with similar use and functionality, in addition to importing the DAOs into the DI container
public class HibernateDaoImpl {

    @Resource
    private SessionFactory sessionFactory;

    public int getCircleCount() {
        String hql = "SELECT COUNT(*) FROM circles";                     // Circle here is the @Entity (model class) name
        Query query = sessionFactory.openSession().createQuery(hql);
        return ((Long) query.uniqueResult()).intValue();
    }

    public void insertCircle(Circle circle) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(circle);                                           // always better to use persist() instead of save()
        session.getTransaction().commit();                              // mapping Entity will not be save with save()
        session.close();
    }

    public void insertUserDetails(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void insertVehicle(Vehicle vehicle) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(vehicle);
        session.getTransaction().commit();
        session.close();
    }

    public void insertObject(Object object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        session.close();
    }

    public int getUserCount() {
        String hql = "SELECT COUNT(*) FROM User";
        Query query = sessionFactory.openSession().createQuery(hql);
        return ((Long) query.uniqueResult()).intValue();
    }

    public CreditCard getCreditCard(int id) {
        CreditCard creditCard = null;
        Session session = sessionFactory.openSession();
        creditCard = (CreditCard) session.get(CreditCard.class, id);
        session.close();
        return creditCard;
    }

    public Workspace getWorkspace(int id) {
        Workspace workspace = null;
        Session session = sessionFactory.openSession();
        workspace = (Workspace) session.get(Workspace.class, id);
        session.close();
        return workspace;
    }

    public User getUser(int userId) {
        User user = null;
        Session session = sessionFactory.openSession();
        user = (User) session.get(User.class, userId);
        Hibernate.initialize(user.getWorkspaces());             // use Hibernate.initialize on the Collection if using FetchType.LAZY
        session.close();                                        // cannot lazy load emails if session is close, use @ElementCollection(fetch=FetchType.EAGER)
        return user;                                            // will work if session closed but slow
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User");
        users = query.list();
        return users;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}

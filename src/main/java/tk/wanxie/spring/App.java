package tk.wanxie.spring;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

        //ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/beans/beans.xml");
        //ApplicationContext context = new ClassPathXmlApplicationContext("beans/beans.xml");
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("beans/beans.xml");

        // desktop application need to close the context manually, while web app auto close
        context.registerShutdownHook();

        // get static messages from a property file
        System.out.println(context.getMessage("greeting", null, "Default greeting", null));

        // if using interface, don't need to change anything here and only class type in config file will change to a different object
        Creature creature = (Creature) context.getBean("creature");
        creature.speak();
        creature.eat();
        System.out.println(creature);

        Address address = (Address) context.getBean("address");
        System.out.println(address);

        Person man = (Person) context.getBean("man");
        System.out.println(man);

        System.out.println("");

        Classroom classroom = (Classroom) context.getBean("classroom");
        classroom.attendent();
    }

}

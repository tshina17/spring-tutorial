package tk.wanxie.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Person implements Creature, ApplicationContextAware, BeanNameAware {     // implement ApplicationContextAware if want to use the ApplicationContext in this class
                                                                            // BeanNameAware gives the name of this class defined in the XML config file

    private int id;
    private String name;
    private int taxId;
    private Address address;
    private Address address2;

    private ApplicationContext context;

    public Person() {
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void speak() {
        System.out.println("Hello! I'm a person.");
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAddress2(Address address2) {
        this.address2 = address2;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", taxId=" + taxId +
                ", address=" + address +
                ", address2=" + address2 +
                '}';
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    @Override
    public void setBeanName(String s) {
        System.out.println(s);
    }
}

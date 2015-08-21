package tk.wanxie.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

public class Animal implements Creature {

    @Autowired
    @Qualifier("cat")                                   // can also annotate in setter method instead here
    private String name;

                                                        // default @Resource is the same as @Resource(name="type")
    @Resource(name="horse")                             // is the same as using @Autowired and @Qualifier together, not from spring framework
    private String type;

    @Value("3325 Abc Street")                           // set the default value just like using property tag inside bean in config file
    protected String location;

    @Override
    public void speak() {
        System.out.println("WoooWW.....");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    @Required                                           // if annotation is used, auto detect for error before compile, not from spring framework
    public void setLocation(String location) {
        this.location = location;
    }

    @PostConstruct                                      // the same as using init-method in bean config file, not from spring framework
    public void init() {
        System.out.println("Initializing Animal...................");
    }

    @PreDestroy                                         // the same as using destroy-method in bean config file, not from spring framework
    public void destroy() {
        System.out.println(this);
        System.out.println("Destroying Animal........................");
    }

    @Override
    public String toString() {
        return "Animal{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

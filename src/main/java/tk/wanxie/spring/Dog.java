package tk.wanxie.spring;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component                          // define this class as spring bean, so don't have to create the bean in the config file if it is the only instance defined
public class Dog extends Animal implements ApplicationEventPublisherAware {

    @Resource
    private MessageSource messageSource;            // using getMessage within a class, not from application context

    private ApplicationEventPublisher publisher;    // must implement ApplicationEventPublisherAware for it to work

    @Override
    @Resource
    public void setLocation(String location) {
        this.location = location;
    }

    @PostConstruct
    public void init() {
        FightEvent event = new FightEvent(this);
        publisher.publishEvent(event);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("From Dog class: " + messageSource.getMessage("greeting", null, "Default Greeting", null));

        // passing arguments to getMessage
        System.out.println(messageSource.getMessage("dog.location", new Object[] {this.getType(), this.location}, "No location", null));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
}

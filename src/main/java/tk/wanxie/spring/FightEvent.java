package tk.wanxie.spring;

import org.springframework.context.ApplicationEvent;

public class FightEvent extends ApplicationEvent {

    private Object source;

    public FightEvent(Object source) {
        super(source);
        this.source = source;
    }

    @Override
    public String toString() {
        return "Fight Event for " + source.getClass().getSimpleName() + " occured!";
    }
}

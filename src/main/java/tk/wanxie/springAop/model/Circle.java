package tk.wanxie.springAop.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Circle {

    @Value("Circle Name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String setNameAndReturn(String name) {
        this.name = name;
        return "A new name is setted.";
    }

    @Override
    public String toString() {
        return "Circle{" +
                "name='" + name + '\'' +
                '}';
    }
}

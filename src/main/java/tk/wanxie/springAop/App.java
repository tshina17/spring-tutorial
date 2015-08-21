package tk.wanxie.springAop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tk.wanxie.springAop.service.ShapeService;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop/aop.xml");

        ShapeService service = context.getBean("shapeService", ShapeService.class);
        //service.setCircle((Circle) context.getBean("circle"));
        service.getCircle().setNameAndReturn("My Circle");
        System.out.println(service.getTriangle());
    }

}

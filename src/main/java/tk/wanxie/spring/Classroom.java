package tk.wanxie.spring;

/*
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
*/

import java.util.List;

public class Classroom /* implements InitializingBean, DisposableBean */ {

    private List<Person> students;

    public List<Person> getStudents() {
        return students;
    }

    public void setStudents(List<Person> students) {
        this.students = students;
    }

    public void attendent() {
        students.forEach(s -> System.out.println(s));
    }

    /**
     * It is better to use the config file to define init and destroy of this class
     *
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Initializing bean for Classroom.");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Destroying bean for Classroom.");
    }
    */

    public void init() {
        System.out.println("Init with config file.");
    }

    public void cleanUp() {
        System.out.println("Calling method after destroying class.");
    }
}

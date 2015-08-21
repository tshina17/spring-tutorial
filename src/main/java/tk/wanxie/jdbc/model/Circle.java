package tk.wanxie.jdbc.model;

import javax.persistence.*;

                                                    // Better to use @Entity and @Table(name="circles")
@Entity(name="circles")                             // same as @Entity and @Table(name="circles") together, but also change the Circle class name to circles instead of Circle
public class Circle {

    private int id;

    private String name;

    public Circle() {

    }

    public Circle(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id                                             // required for @Entity, if annotation used for getter, the annotation of other fields must also be in getter, not work with setter
    @Column(name="circle_id")                       // use this if the column name is different
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="name")                            // annotation in getter can modify the field before store in database
    public String getName() {
        return name + " from getter";
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

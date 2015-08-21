package tk.wanxie.jdbc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
//@DiscriminatorValue("Car")                                    // use Car as the vehicle type instead of FourWheeler, only for strategy=InheritanceType.SINGLE_TABLE
@Table(name="four_wheeler")                                     // not required for strategy=InheritanceType.SINGLE_TABLE
public class FourWheeler extends Vehicle {

    @Column(name="steering_wheel")
    private String steeringWheel;

    public String getSteeringWheel() {
        return steeringWheel;
    }

    public void setSteeringWheel(String steeringWheel) {
        this.steeringWheel = steeringWheel;
    }
}

package tk.wanxie.jdbc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
//@DiscriminatorValue("Bike")                             // instead of using TwoWheeler as the vehicle type (@discriminatorColumn), we can use something else, only for InheritanceType.SINGLE_TABLE
@Table(name="two_wheeler")                                // not required for strategy=InheritanceType.SINGLE_TABLE
public class TwoWheeler extends Vehicle {

    @Column(name="steering_handle")
    private String steeringHandle;

    public String getSteeringHandle() {
        return steeringHandle;
    }

    public void setSteeringHandle(String steeringHandle) {
        this.steeringHandle = steeringHandle;
    }
}

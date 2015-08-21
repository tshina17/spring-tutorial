package tk.wanxie.jdbc.model;

import javax.persistence.*;

@Entity
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)               // whether all inheritance use the vehicle table to store data or separate,
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)            // each Entity and subclass will have a table of its own duplicating parent properties as well
@Inheritance(strategy=InheritanceType.JOINED)                       // this will normally be the type of strategy to use, where subclass table will be referencing the parent table
/*@DiscriminatorColumn(                                             // configure the column name for storing the inheritance class name, only needed for InheritanceType.SINGLE_TABLE
    name="vehicle_type",
    discriminatorType=DiscriminatorType.STRING
)*/
@Table(name="vehicle")
public class Vehicle {

    @Id
    @GeneratedValue
    //@GeneratedValue(strategy=GenerationType.TABLE)                // use GenerationType.TABLE if using InheritanceType.TABLE_PER_CLASS to prevent id conflict
    @Column(name="vehicle_id")
    private int vehicleId;

    @Column(name="vehicle_name")
    private String vehicleName;

    public Vehicle() {
    }

    public Vehicle(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", vehicleName='" + vehicleName + '\'' +
                '}';
    }
}

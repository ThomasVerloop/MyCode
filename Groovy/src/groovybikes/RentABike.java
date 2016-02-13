package groovybikes;
import java.util.List;
import groovybikes.Bike;

public interface RentABike {
   List<Bike> getBikes();
   Bike getBike(String serialNo);
   void setStoreName(String name);
   String getStoreName();
}
 


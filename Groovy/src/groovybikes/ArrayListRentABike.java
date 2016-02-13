package groovybikes;
import groovybikes.Bike;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class ArrayListRentABike implements RentABike {
   private String storeName;
   final List<Bike> bikes = new ArrayList<Bike>();

   public void setStoreName(String name) {
      this.storeName = name;
   }

   public String getStoreName() {
      return storeName;
   }

   public ArrayListRentABike(String storeName) {
      this.storeName = storeName;
      bikes.add(new Bike("Shimano", "Roadmaster", 20, "11111", 15, "Fair"));
      bikes.add(new Bike("Cannondale", "F2000 XTR", 18, "22222",12, "Excellent"));
      bikes.add(new Bike("Trek","6000", 19, "33333", 12.4,"Fair"));
      bikes.add(new Bike("Gazelle","automatic", 17, "44444", 12.7,"Fair"));
   }

   public String toString() { return "com.springbook.RentABike: " + storeName; }

   public List<Bike> getBikes() { return bikes; }

   public Bike getBike(String serialNo) {
      for(Bike bike: bikes) {
         if(serialNo.equals(bike.getSerialNo())) return bike;
      }
      return null;
   }
	@Test(groups = {"old","real","new"})
	final void testSomething(){
//		assertTrue(true)
		System.out.println( "Tested something using TestNG");
		System.out.println( "Tested something else using TestNG");
	}
}

package groovybikes



/**
 * @author Thomas
 *
 */
class Bike{
	  String manufacturer
	  String model
	  Integer frame
	  String serialNo
	  Double weight
	  String status
	  BigDecimal cost
	  
	  // Constructors for normal Java use
	  public Bike(){}
	  
	  public Bike(String manufacturer, String model, int frame, 
	     String serialNo, double weight, String status)
	  {
	      this.manufacturer = manufacturer;
	      this.model = model;
	      this.frame = frame;
	      this.serialNo = serialNo;
	      this.weight = weight;
	      this.status = status;
	   }

	  public void setCost(BigDecimal newCost) {
	    cost = newCost.setScale(3, BigDecimal.ROUND_HALF_UP)
	  }
	  
	  
	  public String toString() {
	    return """Bike:
	         manufacturer --  ${manufacturer} 
	         model -- ${model}
	         frame -- ${frame} 
	         serialNo -- ${serialNo}
	         cost  -- ${cost}
	      """
	  }	
}

import org.testng.annotations.*
import org.testng.TestNG
import org.testng.TestListenerAdapter
import static org.testng.AssertJUnit.*;

/**
* (ThV)
* The TestNg plugin generates an xml file based on the settings in the TestNg (eclipse)run configuration.
* however, because the plugin isn't aware of Groovy it doesn't see Groovy classes and specified groups and don't runs them
* To still run them edit the xml file AND make it read only and run the Groovy script as TestNg.
* The xml file can be found at: Users/Thomas/AppData/Local/Temp/testng-customsuite.xml
*/
public class BikeTest {

	/**
	* Main entry point to run <code>BikeTest</code> as
	* simple Groovy class
	*/
	public static void main(String[] args){
		def testng = new TestNG()
		testng.setTestClasses(BikeTest)
		testng.addListener(new TestListenerAdapter())
		testng.run()
	}
	
	@Test(groups = ["real"])
	final void testBike() {
	    // Groovy way to initialize a new object
	    def b = new groovybikes.Bike(manufacturer:"Shimano", model:"Roadmaster")
	  
	    // explicitly call the default accessors
	    assert b.getManufacturer() == "Shimano"
	    assert b.getModel() == "Roadmaster"
	  
	    // Groovier way to invoke accessors
	    assert b.model == "Roadmaster"
	    assert b.manufacturer == "Shimano"
	    
	    println b
	}

	@Test(groups = ["what","new"])
	final void testSome(){
		assertTrue(true)
		println "Tested something using TestNG"
	}

}
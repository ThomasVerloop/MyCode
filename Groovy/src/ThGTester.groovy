import thgen.*;
import thgen.een.*;
import org.testng.annotations.Test;
import org.testng.Assert;

// need to init the ThG classes...
Apple.GOSPELinit()
Crate.GOSPELinit()

Apple.Make 10, 200, "GroovyAppeltje"
Apple.Make 13, 250, "GrannySmith"

test = new AppleTest()
//test.basicFunctionality()
test.basicFunctionality_3()

Apple.For_All_Apples(new MyGroovyAppleAction(), null);


class MyGroovyAppleAction implements ActionClass{
	public void doit(Object inst, Object arg){
		Apple apple = (Apple) inst;
		System.out.println("Groovy Appeltje :" + apple.getmyName() + 
			", weight = " + apple.getweigth());
	}
}
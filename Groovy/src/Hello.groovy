println "Hello world ----------- this is groovy:"
def cBase = {println "hoi: " + it.toString()}
def cRich = {println "senior: " + it.toString() + " earns: " + it.salary}
def cCommon = {println "employee: " + it.toString() + " earns: " + it.salary}

names = ["Thomas", "Jeroen", "Boomie", "Monique", "Ylva"]
names.each(cBase)

 
class Employee {
	Employee(){}
	Employee(Employee e){name = e.name; salary = e.salary;}
    def salary
    def name
    String toStringImpl(){return name;}
    String toString(){return toStringImpl();}
}

 def highPaid(List<Employee> emps) {
	def threshold = 150
	return emps.findAll{ e -> e.salary > threshold }
}

int i = 0
def setName = {emp -> emp.name = names[i++]}
def emps = [180.2, 281/2, 160, 150, 170].collect{ val -> 
		emp = new Employee(salary:val); 
		emp.name = names[i++]; 
		emp
	}

println 'employee count = ' + emps.size()           // prints 5
def seniors = highPaid(emps)
println 'Expert count = ' + seniors.size()          // prints 3

seniors.each(cBase)
println ""

seniors.each(cRich)
println ""

emps.each(cCommon)

// def crash = highPaid(names)

// -------------------------------------------------------------------------------
// Decorator tricks
//
// examples ExpandoMeta Class: (ThV note: need 1.6 Groovy libraries)
// http://www.ibm.com/developerworks/java/library/j-eaed8.html?S_TACT=105AGX54&S_CMP=C0903&ca=dnw-1033&ca=dth-j&open&cm_mmc=5878-_-n-_-vrm_newsletter-_-10731_128952&cmibm_em=dm:0:16536323

ArrayList.metaClass.getFirst {
  delegate.size > 0 ? get(0) : null
}

ArrayList.metaClass.getLast {
  delegate.size > 0 ? get(delegate.size - 1) : null
}

ArrayList l = new ArrayList()
l << 1 << 2 << 3
l.with { // this with() causes all statements to be interpreted in the context of the ArrayList l:
		 // and because of the previous addition of getFirst() and getLast() getters, 
		 // it looks if any ArrayList has a first and last properties 
	println "first = " + first
	println "last = " + last
}

ArrayList emptyList = new ArrayList()
println "first = " + emptyList.first
println "last = " + emptyList.last

class Logger {
    def log(String m) {
        println m
    }
}

def logger = new Logger()
logger.log "this log message brought to you in original case"

logger.metaClass.log = { String m -> println m.toUpperCase()}
logger.log "this log message brought to you in upper case"



//The GenericLowerDecorator class acts as a universal decorator to force all
//string-based parameters into lowercase. It does so by using a hook method. 
//When you invoke this decorator, you wrap it around any instance. 
//The invokeMethod() method intercepts all method calls to this class, 
//allowing you to perform whatever actions you like. In this case, 
//I intercept each method call and go through all the method parameters. 
//If any of the parameters are of type String, I add the lowercase version of it 
//to a new arguments list, and leave the other arguments intact. 
//At the end of the hook method, I invoke the original method call on the decorated
//object using my new argument list. This decorator converts all string parameters to lowercase, 
//regardless of the method or its parameters.

class GenericLowerDecorator {
    private delegate

    GenericLowerDecorator(delegate) {
        this.delegate = delegate
    }

    def invokeMethod(String name, args) {
    	def newargs
    	if(args.size() == 0){
    		newargs = args
    	} else{    		
    		newargs = args.collect{ arg ->
            	if (arg instanceof String) return arg.toLowerCase()
            	else return arg
    		}
    	}
        delegate.invokeMethod(name, newargs)
    }
}
 
class TimeStampingLogger extends Logger {
    private Logger logger

    TimeStampingLogger(logger) {
        this.logger = logger
    }

    def log(String message) {
        def now = Calendar.instance
        logger.log("$now.time: $message")
    }
}

tlog = new GenericLowerDecorator(
    new TimeStampingLogger(
        new Logger()))

tlog.log('IMPORTANT Message')
 
class MyString {
	String s = ""; //  properties aren't taken over by the generic decorator
	void add(String m){s += m;}
	String getResult(String txt){return txt + s;}
}

mys = new MyString()
myds = new GenericLowerDecorator(mys)
myds.add("HALLO Thomas")      // decorator forces lower cased parameter
mys.add(" have a GOOD day")   // original add() parameter used

println myds.getS() // ThV note: must use getS() because ms.s property doesn't exist for decorator
println mys.s       // ThV note: here it works,   its a bit tricky !!!!!!!!!

// Ant use

ant = new AntBuilder()
ant.echo(message:"mapping it via attribute!")		 
ant.echo("Hello World!")

//ant = new AntBuilder()
ant.mkdir(dir:"c:/dev/projects/groovy/binaries/")

try{

    ant.javac(srcdir:"c:/dev/projects/groovy/src", 
       destdir:"c:/dev/projects/groovy/binaries/" )

}catch(Throwable thr){
    ant.mail(mailhost:"smtp.telfort.nl", subject:"build failure"){
       from(address:"thomas.verloop@telfort.nl", name:"buildmaster")
       to(address:"thomas.verloop@telfort.nl", name:"Development Team")
       message("Unable to compile ighr's source.")
    }
}


public class CommandLineView {
  def myName = "Thomas"
  def rentaBike  = new groovybikes.ArrayListRentABike("${myName} bikes store");

  def printAllBikes() {
    println rentaBike
    rentaBike.getBike("22222").setCost(new BigDecimal(0.1005))
    rentaBike.bikes.each{ println it}  // no iterators or casting
  }
}

new CommandLineView().printAllBikes()

println "ipconfig /all".execute().text

InetAddress.getAllByName("www.google.com" ).each{println it}


paths = ['/usr/local/include/mail.jar', '/usr/local/include/activation.jar', "crap", "more...."]
println(paths.join(';C:'))

static String checksum( String input ) {
	def digest = java.security.MessageDigest.getInstance("SHA-256")
	digest.update( input.bytes )
	new BigInteger(1,digest.digest()).toString(16).padLeft(32, '0')
}

println "SHA-256 checksum of 'hello world'" + " = " + checksum("hello world")
println "SHA-256 checksum of 'jello world'" + " = " + checksum("jello world")

Collection.metaClass.partition = { show, size ->
	def rslt = delegate.inject( [ [] ] ) { ret, elem ->
	  ( ret.last() << elem ).size() >= size ? ret << [] : ret
	  if(show) println ret
	  ret
	}
	rslt.last() ? rslt : rslt[ 0..-2 ]
  }
	  
  def origList = [1, 2, 3, 4, 5, 6]
  
  println origList
  println origList[0..0]
  println origList[0..-1]
  println origList[0..-2]
  println origList[0..-3]
  
  println "Start of partitions.............\n"
  
  assert [ [1], [2], [3], [4], [5], [6] ] == origList.partition(true, 1 )
  assert [ [1, 2], [3, 4], [5, 6] ]       == origList.partition(true, 2 )
  assert [ [1, 2, 3], [4, 5, 6] ]         == origList.partition(true, 3 )
  assert [ [1, 2, 3, 4], [5, 6] ]         == origList.partition(true, 4 )
  assert [ [1, 2, 3, 4, 5], [6] ]         == origList.partition(true, 5 )
  assert [ [1, 2, 3, 4, 5, 6] ]           == origList.partition(true, 6 )
  
  println "" + origList + " --> partition(4) --> " + origList.partition( false,4 )
  
  def list2 = origList.partition(false, 2 )
  println "partition(2) of " + list2 + " = " + list2.partition(false,2)
	  
  assert [ [emps[0], emps[1], emps[2], emps[3]], [emps[4]] ] == emps.partition(true, 4 )

  Employee.metaClass.toString = {-> "employee: " + name + " earns: " + salary }
  println "--------------< first meta change >-------------"

  Employee e = new Employee(name:"newEmp",salary:1.1)
  println "newEmp = " + e + " NOTE: this uses NOT the new Groovy metaClass defined toStringImpl()"
  println "newEmp.toString() = " + e.toString() + " NOTE: this uses the new Groovy metaClass defined toStringImpl()"

  Employee.metaClass.toString = {-> name.toUpperCase() }
  println "--------------< second meta change >-------------"
  
  println "(2) newEmp = " + e + " NOTE: this uses NOT the FIRST new Groovy metaClass defined toStringImpl()"
  println "(2) newEmp.toString() = " + e.toString() + " NOTE: this uses the SECOND new Groovy metaClass defined toStringImpl()"

  println "emps[2] = " + emps[2] + " NOTE: this code uses the original toStringImpl() and not the Groovy one !!"
  println "emps[2].toString() = " + emps[2].toString() + " NOTE: this code uses the original toStringImpl() and not the Groovy one !!"
  
  Employee e2 = new Employee(emps[2])

  println "new e2 = " + e2 + " NOTE: this uses NOT the SECOND new Groovy metaClass defined toStringImpl()"
  println "new e2.toString() = " + e2.toString() + " NOTE: this uses the SECOND new Groovy metaClass defined toStringImpl()"
  
  list2 = emps.partition(false, 2 )
  println "partition(2) of " + list2 + " = " + list2.partition(false,2)
  
  println "\n--------------< Methodcalls with map params >-------------"
  // see: http://groovy.codehaus.org/Extended+Guide+to+Method+Signatures
  def foo(x,y,z) {[x,y,z]}
	assert foo(a:1,b:2,3,4) == [[a:1, b:2], 3, 4]
	assert foo(a:1,3,b:2,4) == [[a:1, b:2], 3, 4]
	assert foo(3,4,a:1,b:2) == [[a:1, b:2], 3, 4]
	
	assert foo(4,3,b:2,a:1) == [[b:2, a:1], 4, 3]
println foo(4,3,b:2,a:1)
println "end"

def myList = [1,3,5,7,4,2,3]  
println myList// .nub() this is from Functional Java Lists (removes duplicates)


  class Class1 {
	def closure = {
	  println this.class.name
	  println delegate.class.name
	  def nestedClos = {
		println owner.class.name
	  }
	  nestedClos()
	}
  }
  def clos = new Class1().closure
  clos.delegate = this
  clos()
  /*  prints:
   Class1
   Script1
   Class1$_closure1  */

  
  def list = ['a','b','c','d']
  def newList = []
  
  list.collect( newList ) { it.toUpperCase() }
  println newList           //  ["A", "B", "C", "D"]

  println "\n--------------< Hidden class member access within closure >-------------"
  class HiddenMember {
	  private String name;
	
	  def getClosure (String name)
	  {
		return { println ("Argument: ${name}, Object: ${owner.name}")}
	  }
  }
  def hm = new HiddenMember(name:"ThV")
  def myClosure =  hm.getClosure ("Hoi")
  
  myClosure()
  
  println "\n--------------< Curried closure >-------------"
  def c = { arg1, arg2-> println "${arg1} ${arg2}" }
  def d = c.curry("foo")
  d("bar")

  // Note: to see doc about this: http://docs.codehaus.org/display/GROOVY/Closures+-+Formal+Definition  
  println "\n--------------< Methods with closure param (Category and 'use' directive) >-------------"
  public class ClassWithEachDirMethod {
	  public static void eachDir(File self, Closure closure) {
		  File[] files = self.listFiles();
		  if(files == null) return
		  for (int i = 0; i < files.length; i++) {
			  if (files[i].isDirectory()) {
				  closure.call(files[i]);
				  eachDir(files[i],closure)
			  }
		  }
	  }
  }
  
  dir = new File("c:/users/Gast/Documents")
  use(ClassWithEachDirMethod.class) {
	dir.eachDir {
	  println it
	}
  }
  
  println "\n--------------< Category and 'use' examples >-------------"
  @Category(Integer)
  class IntegerOps {
	  def triple() {
		  this * 3
	  }
  }
 
  use (IntegerOps) {
	  assert 25.triple() == 75
  }
//  Or, using the @Mixin flavor for compile-time "mixing in" of the methods:
  @Category(List)
  class Shuffler {
	  def shuffle() {
		  def result = new ArrayList(this)
		  Collections.shuffle(result)
		  result
	  }
  }
 
  @Mixin(Shuffler)
  class Sentence extends ArrayList {
	  Sentence(Collection initial) { super(initial) }
  }
 
  def words = ["The", "quick", "brown", "fox"]
  println new Sentence(words).shuffle()
  // => [quick, fox, The, brown]       (order will vary)
//  Or, instead of using @Mixin, try "mixing in" your methods at runtime:
  // ... as before ...
 
  class Sentence2 extends ArrayList {
	  Sentence2(Collection initial) { super(initial) }
  }
  Sentence2.mixin Shuffler
 
  def words2 = ["Thomas", "and", "Ylva", "Verloop"]
  println new Sentence(words2).shuffle()
  // => [quick, fox, The, brown]       (order will vary)
  
  import groovy.xml.*
  
  def html = DOMBuilder.newInstance().html {
	head {
	  title (class:'mytitle', 'Test')
	}
	body {
	  p (class:'mystyle', 'This is a test.')
	}
  }
  
  use (groovy.xml.dom.DOMCategory) {
	assert html.head.title.text() == 'Test'
	assert html.body.p.text() == 'This is a test.'
	assert html.find{ it.tagName == 'body' }.tagName == 'body'
	assert html.getElementsByTagName('*').grep{ it.'@class' }.size() == 2
  }
  
  try {
	  use (groovy.xml.dom.DOMCategory){
		println html.head.title
	  }
  } catch(MissingPropertyException mpe) {
	println mpe
	println "Categories wear off"
  }
  
  class Pouncer {
	  static pounces( Integer self ){
		 (0..<self).inject("") { s, n ->
			s += "boing! "
		 }
	  }
   }
   
   use( Pouncer ) {
	 assert 3.pounces() == "boing! boing! boing! "
   }
   
  println "\n--------------< Use standard external classes as category examples >-------------"
   import org.apache.commons.lang.ClassUtils
   import org.apache.commons.lang.StringUtils
   import org.apache.commons.lang.WordUtils
   
   use( ClassUtils, StringUtils, WordUtils ) {
	  assert "java.lang.Class".packageName == "java.lang"
	  assert "java.lang.Class".shortClassName == "Class"
	  assert Map.Entry.isInnerClass()
   
	  assert "CamelCase".swapCase() == "cAMELcASE"
	  assert "ALL CAPS".uncapitalize() == "aLL cAPS"
	  assert "GROOVY JAVA".initials() == "GJ"
   
	  assert "FOO_BAR_BAZ".split("_").collect { word ->
		 word.toLowerCase().capitalize()
	  }.join("") == "FooBarBaz"
   }

   println "\n--------------< mixin examples >-------------"
   // see: ExpandoMetaClass jdoc
class Student {
     List schedule = []
     def addLecture(String lecture) { schedule << lecture }
 }
 
 class Worker {
     List schedule = []
     def addMeeting(String meeting) { schedule << meeting }
 }
// We can mimic a form of multiple inheritance as follows: 
 class CollegeStudent {
     static { mixin Student, Worker }
 }
 new CollegeStudent().with {
     addMeeting('Performance review with Boss')
     addLecture('Learn about Groovy Mixins')
     println schedule
     println mixedIn[Student].schedule
     println mixedIn[Worker].schedule
 }

 // We can also be a little more dynamic and not require the CollegeStudent class to be defined at all, e.g.:
 
  def cs = new Object()
  cs.metaClass {
	  mixin Student, Worker
	  getSchedule {
		  mixedIn[Student].schedule + mixedIn[Worker].schedule
	  }
  }
  cs.with {
	  addMeeting('Performance review with ThV')
	  addLecture('Learn Groovy Mixins')
	  println schedule
  }
 
  // We can also define a no dup queue by mixing in some Queue and Set functionality as follows:
  def ndq = new Object()
  ndq.metaClass {
	  mixin ArrayDeque
	  mixin HashSet
	  leftShift = { Object o ->
		  if (!mixedIn[Set].contains(o)) {
			  mixedIn[Queue].push(o)
			  mixedIn[Set].add(o)
			  println "queued AND added to set: " + o
		  }
	  }
  }
  ndq << 1
  ndq << 2
  ndq << 1
  assert ndq.size() == 2
  
  class CustomComparator implements Comparator {
	  int compare(Object a, b) { return a.size() - b.size() }
  }
  interface Closeable{
	  void close();
  }
  class CustomCloseable implements Closeable {
	  void close() { println 'Lights out - I am closing' }
  }
  
  void closeQuietly(Closeable clos) {clos.close()}
  
  import static java.util.Collections.sort
  def o = new Object()
  o.metaClass.mixin CustomComparator, CustomCloseable
  def items = ['a', 'bbb', 'cc']
  sort(items, o as Comparator)
  println items                // => [a, cc, bbb]
  closeQuietly(o as Closeable) // => Lights out - I am closing

  // Using the Delegating Meta class
  // see: http://groovy.codehaus.org/Using+the+Delegating+Meta+Class
  import org.codehaus.groovy.runtime.InvokerHelper
  
  class DelegatingMetaClassInvokeHelperTest extends GroovyTestCase
  {
	  void testReplaceMetaClass()
	  {
		  /*
		   * Constructing first instance before meta class replacment
		   * is made.
		   */
		  def firstInstance = "first"
		  assertEquals "first", firstInstance.toString()
  
		  def myMetaClass = new MyDelegatingMetaClass(String.class)
		  InvokerHelper.metaRegistry.setMetaClass(String.class, myMetaClass)
  
		  /*
		   * Constructing second instance after meta class replacment
		   * is made.
		   */
		  def secondInstance = "second"
  
		  /*
		   * Since we are replacing a meta class at the class level
		   * we are changing the behavior of the first and second
		   * instance of the string.
		   */
		  assertEquals "changed first", firstInstance.toString()
		  assertEquals "changed second", secondInstance.toString()
	  }
  }
  
  class MyDelegatingMetaClass extends groovy.lang.DelegatingMetaClass
  {
	  MyDelegatingMetaClass(final Class aclass)
	  {
		  super(aclass);
		  initialize()
	  }
  
	  public Object invokeMethod(Object a_object, String a_methodName, Object[] a_arguments)
	  {
		  return "changed ${super.invokeMethod(a_object, a_methodName, a_arguments)}"
	  }
  }
   println "\n--------------< ProxyMetaClass example >-------------"
   // see http://groovy.codehaus.org/Using+the+Proxy+Meta+Class
  import org.codehaus.groovy.runtime.InvokerHelper
  
  class ProxyMetaClassTest extends GroovyTestCase
  {
	  void testProxyMetaClass()
	  {
		  def proxy = ProxyMetaClass.getInstance(String.class);
		  proxy.interceptor = new MyInterceptor()
  
		  def text = "hello world"
  
		  assertEquals "hello world", text.toString()
  
		  proxy.use {
			  assertEquals "changed hello world", text.toString()
		  }
  
		  assertEquals "hello world", text.toString()
		  text = "changed hello world";
		  print "new text is " + text
	  }
  }
  
  class MyInterceptor implements groovy.lang.Interceptor
  {
	  Object beforeInvoke(Object a_object, String a_methodName, Object[] a_arguments)
	  {
		  print a_object
	  }
  
	  boolean doInvoke()
	  {
		  return true
	  }
  
	  Object afterInvoke(Object a_object, String a_methodName, Object[] a_arguments, Object a_result)
	  {
		   return "changed ${a_result}"
	  }
  }
  def pm = new ProxyMetaClassTest();
  pm.testProxyMetaClass();
  
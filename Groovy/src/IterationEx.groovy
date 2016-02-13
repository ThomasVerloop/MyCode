// --------------------elementary---------------------------------
def x = [1,2,3]

println x.class
println x[2]
println x
println '' // just a newline
println "--------------< Ranges and List and closure >-------------"
square = { n -> n * n } // closure to square something

range = (1..15) + [30,81,301]

squareRow = range.collect(square)

println range
println squareRow
println "Their classes: " + range.class + "  " + squareRow.class

range.eachWithIndex{it, idx -> println it + " squared = " + squareRow[idx]}

println "\n--------------< Fibonacci using the .times {} loop construct with a closure >-------------"
current = 1
next    = 1
10.times { // loop 10 times
   print current + ' '
   newCurrent = next
   next = next + current
   current = newCurrent
}
println ''



println "--------------< number list test using assert >-------------"
def numbers = [ 5, 7, 9, 12 ]
assert numbers.any { it % 2 == 0 }                        //returns true since 12 is even
assert numbers.every { it > 4 }                           //returns true since all #s are > 4
assert numbers.findAll { it in 6..10 } == [7,9]           //returns all #s > 5 and < 11
assert numbers.collect { ++it } == [6, 8, 10, 13]         //returns a new list with each # incremented
numbers.eachWithIndex{ num, idx -> println "$idx: $num" } //prints each index and number

println "\n--------------< map / list tests >-------------"
printMapClosure = { key, value -> println key + "=" + value }
[ "yue" : "wu", "lane" : "burks", "sudha" : "saseethiaseeleethialeselan" ].each(printMapClosure)

def stringList = [ "java", "perl", "python", "ruby", "c#", "cobol",
                   "groovy", "jython", "smalltalk", "prolog", "m", "yacc" ];
stringList << "A68"; // append

def stringMap = [ "Su" : "Sunday", "Mo" : "Monday", "Tu" : "Tuesday",
                  "We" : "Wednesday", "Th" : "Thursday", "Fr" : "Friday",
                  "Sa" : "Saturday" ];
stringMap["WW"] = "WeekEnd" //add to map
 

stringList.each() { print " ${it}" }; println "";
// java perl python ruby c# cobol groovy jython smalltalk prolog m yacc

stringMap.each() { key, value -> println "${key} == ${value}" };
// Su == Sunday
// We == Wednesday
// Mo == Monday
// Sa == Saturday
// Th == Thursday
// Tu == Tuesday
// Fr == Friday

def me // used for getting the last MapEntry reference (it is not a copy !!!!)
stringMap.each() { obj -> me = obj; println "${obj} ==> ${obj.class}" };
me.setValue("Hahahaha")// (ThV) Note: this setValue() writes through to Map
try{
    me.setKey("Ha") // Keys can't be set (it influences position is hashMap)
} catch(Exception e){
	println "expected Exception = "
	println e
}
//stringMap << me // (ThV) not needed, it still is in the map: see above

stringList.eachWithIndex() { obj, i -> println " ${i}: ${obj}" };
// 0: java
// 1: perl
// 2: python
// 3: ruby
// 4: c#
// 5: cobol
// 6: groovy
// 7: jython
// 8: smalltalk
// 9: prolog
// 10: m
// 11: yacc

stringMap.eachWithIndex() { obj, i -> println " ${i}: ${obj.key} -- ${obj} (${obj.class})" };
// 0: Su=Sunday
// 1: We=Wednesday
// 2: Mo=Monday
// 3: Sa=Saturday
// 4: Th=Thursday
// 5: Tu=Tuesday
// 6: Fr=Friday

println "\n--------------< List injection iter >-------------"
def value = [1, 2, 3].inject(10, { count, item -> count + item })
assert value == 16
def value2 = (1..4).inject(20) { count, item -> count + item }
assert value2 == 30
println "value2 is of ${value2.class} and its value = ${value2}"

println "\n--------------< variable arg list (code doesn't work anymore)>-------------"
/*int sum(int... someInts) {
	def total = 0
	for (int i = 0; i < someInts.size(); i++)
		total += someInts[i]
	return total
}
 assert sum(1)       == 1
 assert sum(1, 2)    == 3
 assert sum(1, 2, 3) == 6
 println "sum(1,2,3,4) = " + sum(1,2,3,4)
 */
 
 println "\n--------------< closure with parameter >-------------"
 // Note: to see doc about this: http://docs.codehaus.org/display/GROOVY/Closures+-+Formal+Definition
 //  also read about scoping and hiding further down that web page  
 public class A {
    private int member = 20;
    private String method() {return "hello";}
	
	// define a closure with a string param
	def publicMethod (String name_) {
		def localVar = member + 5;
		def localVar2 = "Parameter: ${name_}";
		return {
			println "${member} ${name_} ${localVar} ${localVar2} ${method()}"
		}
	}
 }
 A sample = new A();
 def closureVar = sample.publicMethod("Xavier");
 closureVar();

 println "\n--------------< closure with closure param and head() and tail() operators >-------------"
 // Note: to see doc about this: Functional thinking: Functional features in Groovy, Part 1 
 //(http://www.ibm.com/developerworks/java/library/j-ft7/index.html) 
 def filter(list, p) {
	 if (list.size() == 0) return list
	 if (p(list.head()))
	   [] + list.head() + filter(list.tail(), p)
	 else
	   filter(list.tail(), p)
   }
   
l = filter(1..20, {n-> n % 2 == 0})
println "filtered list:" + l

 
 
 
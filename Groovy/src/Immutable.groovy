import junit.framework.*;
def im = new ImmutableImp()
try{
	im.address_primitives_immutability()
} catch (ReadOnlyPropertyException e){
	println "catched expected: " + e
}
try{
	im.address_list_references()
} catch (UnsupportedOperationException e){
	println "catched expected: " + e
}

class ImmutableImp extends GroovyTestCase {
//	@Test (expected = ReadOnlyPropertyException.class)
	void address_primitives_immutability() {
		Address a = new Address(
			["201 E Randolph St", "25th Floor"], "Chicago", "IL", "60601")
		assertEquals "Chicago", a.city
		a.city = "New York"
	}

//	@Test (expected=UnsupportedOperationException.class)
	void address_list_references() {
		Address a = new Address(
			["201 E Randolph St", "25th Floor"], "Chicago", "IL", "60601")
		assertEquals "201 E Randolph St", a.streets[0]
		assertEquals "25th Floor", a.streets[1]
		a.streets[0] = "404 W Randoph St"
	}

}


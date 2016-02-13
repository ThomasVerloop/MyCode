import swing.Search;

class SearchTest extends GroovyTestCase {
	void testSearchByKeyword(){
		def results = Search.byKeyword("thirstyhead")
		results.each{
			assertTrue it.content.toLowerCase().contains("thirstyhead") ||
			it.author.toLowerCase().contains("thirstyhead")
		}    
	}
}
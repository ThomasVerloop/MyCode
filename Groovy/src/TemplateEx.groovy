import groovy.text.Template
import groovy.text.SimpleTemplateEngine

def fle = new File("src/unit_test.tmpl")
def coll = ["testBinding", "testToString", "testAdd", "testRunner","testBike", 'testJeroenfunc']
def binding = [test_suite:"ThTemplateTest", test_cases:coll]
def engine = new SimpleTemplateEngine()
def template = engine.createTemplate(fle).make(binding)
println template.toString()

/**
 * @author Thomas
 *
 */

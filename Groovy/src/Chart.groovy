import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.data.DefaultPieDataset
import groovy.swing.SwingBuilder
import java.awt.*
import javax.swing.WindowConstants as WC

def piedataset = new DefaultPieDataset()
println "hello, do you like pie?"

// (ThV) Note this special initialiser closure
piedataset.with {
    setValue "Jan", 20
    setValue "Apr", 10
    setValue "May", 30
    setValue "June", 40
}

def options = [true, true, true]

// (ThV) Note how the options array is used to specify the last 3 boolean parameters
def chart = ChartFactory.createPieChart("Pie Chart Sample", piedataset, *options)

// (ThV) set property in the groovy way
chart.backgroundPaint = Color.gray

def swingBld = new SwingBuilder()
swingBld.frame(title:"Groovy PieChart", defaultCloseOperation:WC.EXIT_ON_CLOSE, 
		size:[400,500], show:true) {
			panel(id:'canvas') { widget(new ChartPanel(chart)) }
}

		/* (ThV) the old groovy way (ThV)
		def frame = swingBld.frame(title:'Groovy PieChart',
			   defaultCloseOperation:WindowConstants.EXIT_ON_CLOSE) {
		   panel(id:'canvas') { widget(new ChartPanel(chart)) }
		}
		frame.pack()
		frame.show()
		*/
		
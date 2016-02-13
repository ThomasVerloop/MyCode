package swing;

import groovy.swing.SwingBuilder
import javax.swing.*
import java.awt.*

class Gwitter{   
	def searchField
	def resultsList
	
	static void main(String[] args){
		println args
		def gwitter = new Gwitter()
		gwitter.show()
	}
	
	void show(){
		def swingBld = new SwingBuilder()  
		
		def customMenuBar = {
			swingBld.menuBar{
				menu(text: "File", mnemonic: 'F') {
					menuItem(text: "Exit", mnemonic: 'X', actionPerformed: { dispose() })
				}
			}  
		}    
		
		def searchPanel = {
			swingBld.panel(constraints: BorderLayout.NORTH){
				searchField = textField(columns:15)
				button(text:"Search", actionPerformed:{ // use separate thread: doOutside()....
					doOutside{resultsList.listData = Search.byKeyword(searchField.text) }
					}
				)
			}
		}
		
		def resultsPanel = {
			swingBld.scrollPane(constraints: BorderLayout.CENTER){
				resultsList = list(fixedCellWidth: 380, fixedCellHeight: 75, cellRenderer:new StripeRenderer())
			}
		}    
		
		swingBld.frame(title:"Gwitter", 
				defaultCloseOperation:JFrame.EXIT_ON_CLOSE, 
				size:[400,500],
				show:true) {
					customMenuBar()
					searchPanel()
					resultsPanel()
				}    
	}  
}

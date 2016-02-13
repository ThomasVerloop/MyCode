package swing

import java.io.File;
import javax.swing.*;
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter
import groovy.swing.SwingBuilder
def swing = new SwingBuilder()
def openExcelDialog  = swing.fileChooser(dialogTitle:"Choose an excel file",
	                id:"openExcelDialog", fileSelectionMode : JFileChooser.FILES_ONLY,
			//the file filter must show also directories, in order to be able to look into them
					fileFilter: [getDescription: {-> "*.xls"}, accept:{File file-> file ==~ /.*?\.xls/ ||
					file.isDirectory() }] as FileFilter) {}
//later, in the controller
def JFileChooser fc = openExcelDialog
if(fc.showOpenDialog() != JFileChooser.APPROVE_OPTION) return
 //user cancelled
println "" + fc.selectedFile
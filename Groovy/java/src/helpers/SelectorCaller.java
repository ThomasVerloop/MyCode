package helpers;

import java.io.File;

import javax.swing.JFrame;

import components.FileChooserDemo2;
import components.GroovyFilter;
import org.apache.log4j.Logger;

public class SelectorCaller implements FileSelectionHandler{
	FileChooserDemo2 fc = createFileChooser();

	@Override
	public void doCanceled() {
		// do nothing
	}

	@Override
	public void doSelection(File file) {
        Logger cat = Logger.getLogger(this.getClass());
        cat.info("Running: " + file);
		GroovyCaller.runGroovyScriptFile(file);
	}

    public void selectFile() {
        fc.showFileSelector(null, "run", this);
    }

    public static FileChooserDemo2 createFileChooser() {
        //Add content to the window.
        FileChooserDemo2 fileChooserEx = new FileChooserDemo2();
        // configure FileChooser
        fileChooserEx.createFileSelector();
        fileChooserEx.addFileFilter(new GroovyFilter());
        return fileChooserEx;
    }

}

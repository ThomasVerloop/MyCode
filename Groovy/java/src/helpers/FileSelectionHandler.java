package helpers;

import java.io.File;

public interface FileSelectionHandler {

	void doCanceled();

	void doSelection(File file);
}

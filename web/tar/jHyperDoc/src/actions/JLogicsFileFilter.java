/*
 * Created on Jan 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package actions;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class JLogicsFileFilter extends FileFilter {
	String ext;

	public JLogicsFileFilter(String ext) {
		this.ext = ext;
	}

	public boolean accept(File f) {
		if (f.getName().endsWith(ext) || f.isDirectory())
			return true;

		return false;
	}

	public String getDescription() {
		return "JLogics File (*." + ext + ") files ";
	}
}
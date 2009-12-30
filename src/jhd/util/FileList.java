package jhd.util;

import java.io.File;
import java.util.Vector;

public class FileList {
	String dirName, filePattern;

	public FileList() {
		dirName = new String("."); // default to current directory
		filePattern = new String("*.*"); // default to all files
	}
	
	public Vector getAllFiles(String s){
		File f  = new File(s);
		Vector v = new Vector();
		File parent = new File(f.getParent());
		getAllFiles(parent,v);
		return v;
	}
	
	private void getAllFiles(File s,Vector v){
		File[] files = s.listFiles();
		
		for(int i = 0; i < files.length; i++){
			if(files[i].isDirectory()){
				getAllFiles(files[i],v);
			}
		}
		v.addAll(getFileList(s.getPath()));
	}

	public Vector getFileList(String s) {
		Vector list = new Vector();
		int i;

		s = s.replace('\\', '/');

		i = s.lastIndexOf('/');

		if (i >= 0) {
			dirName = s.substring(0, i);
			if (i < s.length() - 1) {
				filePattern = s.substring(i + 1);
			}
		} else {
			filePattern = s;
		}
		File f = new File(dirName);
		File[] theFiles = f.listFiles(new PatternFilter(filePattern));
		for (i = 0; i < theFiles.length; i++) {
			try {
				list.add(theFiles[i].getCanonicalPath());
			} catch (Exception e) {
			}
		}
		return list;
	}
}
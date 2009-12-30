/*
 * Created on Dec 16, 2004
 *
 */
package util;

import java.awt.Component;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.JTextComponent;

/**
 * @author mahesh
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GlobalUI {

	public static final String ENGLISH = "english.xml";
	public static final String TAMIL = "tamil.xml";

	public static String FONT_NAME = "Arial Unicode MS";
	//private static final String FONT_NAME = "Lucida Console";
	//private static final String FONT_NAME = "TSCu_Paranar";
	//private static final String FONT_NAME = "TSCu_Comic";
	//private static final String FONT_NAME = "TSCu_Paranar Bold";
	//private static final String FONT_NAME = "TSCu_Times";
	//private static final String FONT_NAME = "SooriyanDotCom";
	//private static final String FONT_NAME = "TSCuthamba";
	//private static final String FONT_NAME = "aVarangal";
	//private static final String FONT_NAME = "TSCu_RAvaNan";
	//private static final String FONT_NAME = "TSCu_sivAji";

	private static int fontSize = 12;

	static Vector labelList, controls;
	static Properties p;

	static {

		labelList = new Vector();
		controls = new Vector();
		p = new Properties();
		loadLanguage(ENGLISH);
	}

	public static void add(JComponent c, String label) {
		controls.add(c);
		labelList.add(label);
	}

	public static String getText(String key) {
		return p.getProperty(key);
	}

	public static void loadLanguage(String lang) {
		loadLanguage(lang, null);
	}

	public static void loadLanguage(String lang, Component f) {
		/*
		 * load the properties file according to the langauge
		 */
		try {
			FileInputStream fin = new FileInputStream(lang);
			p.loadFromXML(fin);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * change the font according to the language
		 */

		FONT_NAME = p.getProperty("FONT");
		fontSize = Integer.parseInt(p.getProperty("SIZE"));

		if (lang == ENGLISH) {
			changeFont(FONT_NAME, fontSize);
		} else if (lang == TAMIL) {
			changeFont(FONT_NAME, fontSize);
		}

		if (f != null)
			SwingUtilities.updateComponentTreeUI(f);
	}

	public static void update() {
		int len = controls.size();

		for (int i = 0; i < len; i++) {
			JComponent c = (JComponent) controls.get(i);
			String text = labelList.get(i).toString();
			setText(c, text);
		}
	}

	static void setText(JComponent cp, String name) {
		if (cp instanceof AbstractButton) {
			((AbstractButton) cp).setText(p.getProperty(name));
		} else if (cp instanceof JLabel) {
			((JLabel) cp).setText(p.getProperty(name));
		} else if (cp instanceof JTextComponent) {
			((JTextComponent) cp).setText(p.getProperty(name));
		}
	}

	static void changeFont(String FONT_NAME, int fontSize) {
		Enumeration e = UIManager.getDefaults().keys();
		FontUIResource f = new FontUIResource(FONT_NAME, Font.PLAIN, fontSize);
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			Object value = UIManager.get(key);

			if (value instanceof FontUIResource) {
				UIManager.put(key, f);
			}
		}
	}
}
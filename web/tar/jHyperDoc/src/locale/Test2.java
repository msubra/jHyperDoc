/*
 * Created on Apr 12, 2004
 */
package locale;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author maheshexp
 */
class LocalLabel extends JLabel implements LocaleChangeListener {
	private String ID;

	public LocalLabel(String key) {
		ID = key;
	}

	public void localeChanged(LocaleChangeEvent event) {
		Font f = new Font(event.getValue("font"), Font.BOLD, 15);
		this.setFont(f); //set the corresponding font
		this.setText(event.getValue(ID));//set corresponding message
	}
}

class LocalMenu extends JMenuItem implements LocaleChangeListener {
	private String ID;

	public LocalMenu(String key) {
		ID = key;
	}

	public void localeChanged(LocaleChangeEvent event) {
		Font f = new Font(event.getValue("font"), Font.BOLD, 15);
		this.setFont(f); //set the corresponding font
		this.setText(event.getValue(ID));//set corresponding message
	}
}

class LocalMenuBar extends JMenuBar implements LocaleChangeListener {
	public void localeChanged(LocaleChangeEvent event) {
		Font f = new Font(event.getValue("font"), Font.BOLD, 15);
		this.setFont(f); //set the corresponding font

		for (int i = 0; i < this.getMenuCount(); i++) {

		}
	}
}

class Window2 extends JFrame {
	Container pane;
	LocaleChooser combo = new LocaleChooser();
	LocalLabel l = new LocalLabel("name1"), l1 = new LocalLabel("name2");

	public Window2(String s) {
		super(s);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 300);
		this.setVisible(true);
		init();
	}

	private void init() {
		pane = this.getContentPane();

		pane.add(l, BorderLayout.NORTH);
		pane.add(l1, BorderLayout.CENTER);

		combo.addItem("Tamil");
		combo.addItem("English");
		pane.add(combo, BorderLayout.SOUTH);

		LocalMenuBar menubar = new LocalMenuBar();

		JMenu menu = new JMenu("File");
		LocalMenu item1 = new LocalMenu("name1");
		LocalMenu item2 = new LocalMenu("name2");

		menu.add(item1);
		menu.add(item2);

		menubar.add(menu);
		this.setJMenuBar(menubar);

		this.validate();
		combo.addLocaleChangeListener(l);
		combo.addLocaleChangeListener(l1);

		//combo.addLocaleChangeListener(item1);
		//combo.addLocaleChangeListener(item2);

		combo.addLocaleChangeListener(menubar);

	}
}

public class Test2 {
	public static void main(String[] args) {
		new Window2("Dynamic Locale");
	}
}
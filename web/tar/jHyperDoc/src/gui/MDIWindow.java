/*
 * Created on May 16, 2004
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;

import menus.JLogicsMenu;
import toolbar.JLogicsToolBar;

/**
 * @author maheshexp
 */
public class MDIWindow extends JFrame {
	
//	public final static JDesktopPane desktop = new JDesktopPane();
//	public final static JDesktopPane desktop1 = new JDesktopPane();
//	JSplitPane parent;
//
//	public final static PropertiesWindow properties = new PropertiesWindow();
//	public final static ModuleListWindow mod_list = new ModuleListWindow();
//
//	Container contentPane = this.getContentPane();

	public static JDesktopPane desktop;
	public static JDesktopPane desktop1;
	public static PropertiesWindow properties;
	public static ModuleListWindow mod_list;
	public static DigitalWindow currentWindow;

	Container contentPane;

	public MDIWindow() {
		super("jLogics - Digital Logical Circuit Simulator");
		//long t1 = System.currentTimeMillis();

		
		Splash sp = new Splash(this);
		Splash.setMessage("Starting jLogics");

		desktop = new JDesktopPane();
		desktop1 = new JDesktopPane();
		

		contentPane = this.getContentPane();

		
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		Splash.setMessage("Intializaing Toolbars");
		initToolbars();
		
		Splash.setMessage("Intializaing Digital Desktop");
		initDesktops();

		Splash.setMessage("Intializaing Property Window");
		properties = new PropertiesWindow();
		addWindow(properties);
		
		Splash.setMessage("Intializaing Module Window");
		mod_list = new ModuleListWindow();
		addWindow(mod_list);

		//		try {
		//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//
		//		SwingUtilities.updateComponentTreeUI(this);

		JSplitPane split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				properties, mod_list);
		JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split1,
				desktop);

		contentPane.add(split2);
		
		Splash.setMessage("Finalizing");
		this.setVisible(true);
		
		sp.dispose();
		sp = null;
		//System.out.println(System.currentTimeMillis() - t1);
	}

	private void initToolbars() {
		JLogicsToolBar toolbar = new JLogicsToolBar();
		toolbar.setFloatable(true);
		contentPane.add(toolbar, BorderLayout.NORTH);
		this.setJMenuBar(new JLogicsMenu());
	}

	public static DrawingPad getDrawingPad() {
		return currentWindow != null ? currentWindow.getDrawingPad() : null;
	}

	private void initDesktops() {
		desktop.setVisible(true);
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		contentPane.add(desktop);
	}

	public static void addWindow(JInternalFrame f) {
		desktop.add(f);

		if (f instanceof DigitalWindow)
			currentWindow = (DigitalWindow) f;

		f.show();
		f.moveToFront();
	}

	public static void removeWindow(JInternalFrame f) {
		desktop.remove(f);
		f.dispose();
		desktop.repaint();
	}

	public static PropertiesWindow getPropertyWindow() {
		return properties;
	}

	public static ModuleListWindow getModuleWindow() {
		return mod_list;
	}

}
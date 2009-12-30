/*
 * Created on May 16, 2004
 */
package menus;

import gui.DigitalWindow;
import gui.DrawingPad;
import gui.MDIWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import main.Main;
import parser.EquationCircuit;
import truthtable.TruthTableFrame;
import util.GlobalUI;
import util.MessageBox;
import util.Util;
import actions.FileOpenAction;
import actions.FileSaveAction;
import circuit.DigitalCircuit;
import circuit.Module;

/**
 * @author maheshexp
 */
public class JLogicsMenu extends JMenuBar {

	public JLogicsMenu() {
		initFileMenus();
		initLanguageMenus();
		initToolsMenu();
	}

	void initFileMenus() {
		JMenuItem open, save, newfile, exit, pic;
		JMenu file_menu;

		/* create SAVE menu */
		save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		save.addActionListener(new FileSaveAction(MDIWindow.currentWindow,
				"cir"));

		/* create OPEN menu */
		open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke("control O"));
		open.addActionListener(new FileOpenAction(MDIWindow.currentWindow,
				"cir"));

		/* create NEWFILE menu */
		newfile = new JMenuItem("New Circuit");
		newfile.setAccelerator(KeyStroke.getKeyStroke("control N"));
		newfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MDIWindow.addWindow(new DigitalWindow());
			}
		});

		/* create NEWFILE menu */
		exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke("control W"));
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int x = MessageBox.ask("Exit?", GlobalUI.getText("want_exit"));
				if (x == MessageBox.YES) {
					System.exit(0);
				}
			}
		});

		pic = new JMenuItem("Convert to Picture");
		pic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Util.convertToImage();
			}
		});

		/* add all menus */
		file_menu = new JMenu("File");
		file_menu.add(newfile);
		file_menu.add(open);
		file_menu.add(save);
		file_menu.add(pic);
		file_menu.add(exit);
		this.add(file_menu);//add to menubar

		/* update for localization */
		GlobalUI.add(newfile, "new");
		GlobalUI.add(open, "open");
		GlobalUI.add(save, "save");
		GlobalUI.add(exit, "exit");
		GlobalUI.add(file_menu, "file");

	}

	void initLanguageMenus() {
		JMenuItem tamil;
		JMenuItem eng;
		JMenu lang_menu;

		tamil = new JMenuItem("Tamil");
		eng = new JMenuItem("English");

		/* create ENGLISH menu */
		eng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeLang(GlobalUI.ENGLISH);
			}
		});

		/* create TAMIL menu */
		tamil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeLang(GlobalUI.TAMIL);
			}
		});

		/* add all menus */
		lang_menu = new JMenu("Lanaguage");
		lang_menu.add(eng);
		lang_menu.add(tamil);

		this.add(lang_menu);//add to menubar

		/* update for localization */
		GlobalUI.add(tamil, "tamil");
		GlobalUI.add(eng, "english");
		GlobalUI.add(lang_menu, "language");
	}

	void initToolsMenu() {
		JMenuItem conv;
		JMenuItem align;
		JMenuItem module;
		JMenuItem truth;
		JMenu tools_menu;

		/* create EQUATION TO CIRCUIT CONVERSTION menu */
		conv = new JMenuItem("Equation -> Circuit");
		conv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Util.convertEquationToCircuit();
				DigitalCircuit cir = MDIWindow.getDrawingPad().getCircuit();
				EquationCircuit.align(cir);
			}
		});

		/* create ALIGN CIRCUIT menu */
		align = new JMenuItem("Align Circuit");
		align.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DigitalCircuit cir = MDIWindow.getDrawingPad().getCircuit();
				EquationCircuit.align(cir);
			}
		});

		/* create MODULE CONVERSTION menu */
		module = new JMenuItem("Convert To Module");

		module.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DrawingPad pad = MDIWindow.getDrawingPad();
				if (pad == null) {
					MessageBox.showError("No Circuit available");
					return;
				}
				DigitalCircuit circuit = pad.getCircuit();
				Module m = new Module(circuit);
				m.toModule();

				FileSaveAction save_mod = new FileSaveAction(
						MDIWindow.currentWindow, "mod");
				save_mod.save(m);

				DigitalWindow win = new DigitalWindow();
				win.getDrawingPad().getCircuit().add(m);
				MDIWindow.addWindow(win);
				win.show();
			}
		});

		/* create MODULE CONVERSTION menu */
		truth = new JMenuItem("Generate Truth Table");
		truth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (MDIWindow.currentWindow != null) {
					TruthTableFrame ttf = new TruthTableFrame();
					MDIWindow.addWindow(ttf);
					ttf.show();
				} else {
					MessageBox.showError("ERR002");
				}
			}
		});

		/* add ALL MENUS */
		tools_menu = new JMenu("Tools");
		tools_menu.add(conv);
		tools_menu.add(align);
		tools_menu.add(truth);
		tools_menu.add(module);
		this.add(tools_menu);//add to menubar

		/* update for localization */
		GlobalUI.add(tools_menu, "tools");
		GlobalUI.add(conv, "equ_to_cir");
		GlobalUI.add(align, "align_cir");
		GlobalUI.add(truth, "gen_truth_table");
		GlobalUI.add(module, "conv_mod");

	}

	void changeLang(String langName) {
		GlobalUI.loadLanguage(langName, Main.win);
		GlobalUI.update();
	}
}
/*
 * Created on Jul 28, 2004
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import util.MessageBox;
import actions.FileOpenAction;
import circuit.Module;

public class ModuleListWindow extends JInternalFrame {

	JButton add, remove;
	Container c;
	Box p2;

	ModuleItem selected = null;

	class ModuleItem extends JButton implements ActionListener {
		Module module;
		File fileName;

		public ModuleItem(Module module) {
			super(module.getLabel());
			this.module = module;
			this.addActionListener(this);
		}

		public void setFileName(File fileName) {
			this.fileName = fileName;
		}

		public File getFileName() {
			return fileName;
		}

		public void actionPerformed(ActionEvent e) {

			if (MDIWindow.getDrawingPad() != null) {
				MDIWindow.getDrawingPad().setGateType(module.getID());
			}

			selected = (ModuleItem) e.getSource();

		}

		public Module getModule() {
			return module;
		}

	}

	public ModuleListWindow() {
		super("Module List");

		c = this.getContentPane();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(100, 200);
		c.setLayout(new BorderLayout());

		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(add = new JButton(new ImageIcon("images/add_green.png")));
		p1.add(remove = new JButton(new ImageIcon("images/remove_green.png")));

		c.add(p1, "North");
		p2 = Box.createVerticalBox();

		c.add(new JScrollPane(p2));

		add.addActionListener(new FileOpenAction(MDIWindow.currentWindow,
						"mod"));

		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (selected == null) {
					MessageBox.showError("No Module selected");
					return;
				}

				int ret = JOptionPane.showConfirmDialog(ModuleListWindow.this,
						"Do you want to remove the module from the list",
						"Confirm?", JOptionPane.YES_NO_OPTION);
				if (ret == JOptionPane.OK_OPTION) {
					p2.remove(selected);
				}
				c.validate();
			}
		});

		this.setVisible(true);
	}

	public Module getModule() {
		return selected.getModule();
	}

	public ModuleItem getModuleItem() {
		return selected;
	}

	public void addModule(Module g, File f) {
		ModuleItem b = new ModuleItem(g);
		b.setFileName(f);
		p2.add(b);
		ModuleListWindow.this.validate();
	}

	public static void main(String[] args) {
		new ModuleListWindow();
	}
}
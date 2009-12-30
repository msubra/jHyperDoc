/*
 * Created on May 30, 2004
 */
package jhd.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jhd.util.FileList;

/**
 * @author maheshexp
 */
public class JHDFrame extends JFrame {
	JPanel panel1, panel2, panel3, panel4;
	JTextField list;
	JButton load;
	JList files1,files2;
	Container c;
	
	public JHDFrame(){
		super("jHyperDoc");
		this.setSize(400,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = this.getContentPane();
		
		initPanel1();
		this.validate();
		this.show();
	}
	
	private void initPanel1(){
		panel1 = new JPanel(new BorderLayout());
		panel1.add(list = new JTextField());
		panel1.add(load = new JButton("Load"),"West");
		files1 = new JList();
		
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileList flist = new FileList();
				Vector files = flist.getAllFiles((String)list.getText());
				files1.setListData(files);
			}
		});
		
		panel2  = new JPanel(new BorderLayout());
		panel2.add(files1);
		
		c.add(panel1,"North");
		c.add(panel2);
	}

}

package util;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * Created on Dec 15, 2004
 *
 */

/**
 * @author mahesh
 *  
 */
public class MessageBox extends JFrame {
	/*
	 * type=1 INFORMATION type=0 ERROR
	 *  
	 */
	public static final int ERROR = JOptionPane.ERROR_MESSAGE;
	public static final int INFO = JOptionPane.INFORMATION_MESSAGE;
	public static final int YES = JOptionPane.YES_OPTION;
	public static final int NO = JOptionPane.NO_OPTION;
	public static final int CANCEL = JOptionPane.CANCEL_OPTION;

	public static void show(String title, String message, int type) {
		String mesg = GlobalUI.getText(message);
		if (mesg == null || mesg == "")
			mesg = message;

		JLabel msg = new JLabel(mesg);
		msg.setFont(new Font(GlobalUI.FONT_NAME, Font.PLAIN, 12));
		JOptionPane.showMessageDialog(null, msg, title, type);
	}

	public static void showError(String message) {
		show("Error", message, ERROR);
	}

	public static void showInformation(String message) {
		show("Information!", message, INFO);
	}

	public static int ask(String title, String message) {
		return JOptionPane.showConfirmDialog(null, message, title,
				JOptionPane.YES_NO_OPTION, INFO);
	}

}
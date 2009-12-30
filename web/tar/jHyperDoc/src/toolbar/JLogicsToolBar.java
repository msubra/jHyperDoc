/*
 * Created on May 5, 2004
 */
package toolbar;

import javax.swing.JComponent;
import javax.swing.JToolBar;

import util.GlobalUI;

/**
 * @author maheshexp
 */
public class JLogicsToolBar extends JToolBar {

	public JLogicsToolBar() {
		super();

		init();
	}

	private void init() {
		add(new NormalButton(), "normal");
		add(new SimulateButton(this), "simulate");
		add(new JToolBar.Separator());

		add(new AndButton(), "andgate");
		add(new OrButton(), "orgate");
		add(new NotButton(), "notgate");
		add(new NorButton(), "notgate");
		add(new NandButton(), "nandgate");
		add(new XorButton(), "xorgate");
		add(new ToggleGateButton());
		add(new JToolBar.Separator());

		add(new DCButton(), "dc");
		add(new ClockButton(), "clock");
		add(new JToolBar.Separator());

		add(new LEDButton(), "led");
		add(new JToolBar.Separator());
	}

	void add(JComponent x, String label) {
		this.add(x);
		GlobalUI.add(x, label);
	}
}
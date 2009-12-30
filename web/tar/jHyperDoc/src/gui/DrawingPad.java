package gui;
import gates.And;
import gates.ByPassGate;
import gates.Clock;
import gates.DC;
import gates.Flip1;
import gates.Gate;
import gates.LED;
import gates.Nand;
import gates.Nor;
import gates.Not;
import gates.Or;
import gates.ToggleSwitch;
import gates.Xor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;

import shape.DigitalShape;
import actions.FileOpenAction;
import actions.MouseSensorAction;
import actions.PropertyUpdateAction;
import circuit.DigitalCircuit;

public class DrawingPad extends JPanel {
	public final static int NORMAL = 0;
	public final static int MOVING = 1;
	public final static int CONNECTING = 2;
	public final static int ADDING = 3;
	public final static int SIMULATING = 4;

	MouseSensorAction mouse;
	int mode;
	long gateType;

	public transient DigitalShape current = null;

	Font fnt = new Font("Courier", Font.BOLD, 12);
	public transient BufferedImage bimg = null;
	DigitalCircuit circuit;

	public DrawingPad() {
		super(true);

		//Add the mouse sensor
		mouse = new MouseSensorAction(this);

		//add the properties update listeners
		this.addMouseListener(new PropertyUpdateAction());

		//start the painter thread
		new Painter().start();

		//other intializations
		circuit = new DigitalCircuit();
		this.setCursor(DigitalShape.DEFAULT_CURSOR);
		this.setBackground(Color.WHITE);
		circuit.setDrawingPad(this);
	}

	public void add(DigitalShape s) {
		circuit.setDrawingPad(this);
		circuit.add(s);
	}

	synchronized public void paint(Graphics g) {
		if (bimg == null) {
			bimg = (BufferedImage) this.createImage(this.getWidth(), this
					.getHeight());

			g.drawImage(bimg, 0, 0, null);
		}

		super.paint(g);
		getCircuit().draw((Graphics2D) g);

		if (isAddingMode()) {
			g.setFont(fnt);
			String label = getName(gateType);
			g.drawString(label, mouse.x1, mouse.y1);
		}

		if (isConnectingMode()) {
			g.drawLine(mouse.x1, mouse.y1, mouse.x2, mouse.y2);
		}
	}

	public void resetMode() {
		setMode(NORMAL);
	}

	public void setMode(int mode) {
		this.mode = mode;
		repaint();
	}

	public boolean isConnectingMode() {
		return mode == CONNECTING;
	}

	public boolean isMovingMode() {
		return mode == MOVING;
	}

	public boolean isNormalMode() {
		return mode == MOVING;
	}

	public boolean isSimulatingMode() {
		return mode == SIMULATING;
	}

	public boolean isAddingMode() {
		return mode == ADDING;
	}

	/**
	 * @return
	 */
	public DigitalCircuit getCircuit() {
		return circuit;
	}

	public void setCircuit(DigitalCircuit circuit) {
		this.circuit = circuit;
		circuit.setDrawingPad(this);
		repaint();
	}
	/**
	 * @param l
	 */
	public void setGateType(long type) {
		setMode(ADDING);
		gateType = type;
	}

	public long getGateType() {
		return gateType;
	}

	public static Gate getGate(long id) {
		if (id == Gate.AND) {
			return new And();
		}
		if (id == Gate.OR) {
			return new Or();
		}
		if (id == Gate.NOT) {
			return new Not();
		}
		if (id == Gate.NAND) {
			return new Nand();
		}
		if (id == Gate.XOR) {
			return new Xor();
		}
		if (id == Gate.NOR) {
			return new Nor();
		}
		if (id == Gate.DC) {
			return new DC();
		}
		if (id == Gate.CLOCK) {
			return new Clock();
		}
		if (id == Gate.LED) {
			return new LED();
		}
		if (id == Gate.FLIP1) {
			return new Flip1();
		}
		if (id == Gate.BYPASS) {
			return new ByPassGate();
		}
		if(id == Gate.TOGGLE) {
			return new ToggleSwitch();
		}

		if (id == MDIWindow.getModuleWindow().getModule().getID()) {
			File f = MDIWindow.getModuleWindow().getModuleItem().getFileName();
			return FileOpenAction.open(f);
		}

		return null;
	}

	public static String getName(long id) {
		if (id == Gate.AND) {
			return "AND";
		}
		if (id == Gate.OR) {
			return "OR";
		}
		if (id == Gate.NOT) {
			return "NOT";
		}
		if (id == Gate.NAND) {
			return "NAND";
		}
		if (id == Gate.XOR) {
			return "XOR";
		}
		if (id == Gate.NOR) {
			return "NOR";
		}
		if (id == Gate.DC) {
			return "DC";
		}
		if (id == Gate.CLOCK) {
			return "CLOCK";
		}
		if (id == Gate.LED) {
			return "LED";
		}

		return "<null>";
	}

	public BufferedImage getImage() {
		return bimg;
	}

}
/*
 * Created on May 5, 2004
 */
package gates;

import gui.PropertiesWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import node.Node;
import properties.Properties;
import shape.DigitalObject;
import signal.Signal;

/**
 * @author maheshexp
 */
public abstract class Gate extends DigitalObject implements Properties {

	public static final long AND = 1;
	public static final long OR = 2;
	public static final long NAND = 3;
	public static final long NOR = 4;
	public static final long XOR = 5;
	public static final long NOT = 6;
	public static final long DC = 7;
	public static final long CLOCK = 8;
	public static final long LED = 9;
	public static final long NODE = 10;

	public static final long FLIP1 = 11;
	public static final long BYPASS = 12;
	public static final long TOGGLE = 13;

	public int MAX_INPUTS, MAX_OUTPUTS, MIN_INPUTS, MIN_OUTPUTS;

	String label;
	Font fnt;
	final String name;
	public final long id;

	/**
	 * @param p
	 * @param inputCount
	 * @param outputCount
	 */

	public Gate(String name, long id) {
		/*
		 * Minimum 2 Inputs, 1 Output Maximum 16 Inputs, 1 Output
		 */
		this(name, 2, 1, 16, 1, id);
	}

	public Gate(String name, int inputs, int outputs, long id) {
		/*
		 * Minimum n Inputs, n Output Maximum 16 Inputs, 16 Output
		 */
		this(name, inputs, outputs, 16, 16, id);
	}

	public Gate(String name, int minInputs, int minOutputs, int maxInputs,
			int maxOutputs, long id) {

		super(minInputs, minOutputs);

		this.MIN_INPUTS = minInputs;
		this.MIN_OUTPUTS = minOutputs;
		this.MAX_INPUTS = maxInputs;
		this.MAX_OUTPUTS = maxOutputs;

		this.name = name;
		this.id = id;

		fnt = new Font("Courier", Font.PLAIN, 10);
	}

	public Node getInputNode(int node) {
		return (Node) inputs.get(node);
	}

	public Node getOutputNode(int node) {
		return (Node) outputs.get(node);
	}

	public Signal getInputSignal(int node) {
		if (inputs != null && inputs.size() > 0) {
			Node n = (Node) inputs.get(node);
			return n.getSignal();
		}
		return null;
	}

	public Signal getOutputSignal(int node) {
		if (outputs != null && outputs.size() > 0) {
			Node n = (Node) outputs.get(node);
			return n.getSignal();
		}
		return null;
	}

	/**
	 * @return Label of the Gate
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets a Label to the Gate
	 * 
	 * @param string
	 */
	public void setLabel(String string) {
		label = string;
	}

	public String toString() {
		return "<" + getName() + ">";
	}

	/**
	 * @return Type of the Gate
	 */
	public String getName() {
		return name;
	}

	synchronized public void draw(Graphics2D g) {
		super.draw(g);
		g.setColor(Color.BLACK);
		g.setFont(fnt);

		if ( this.getLabel() != null)
			g.drawString(this.getLabel(), x - 5, y - 5);

		if(PropertiesWindow.isShowBit()) {
			g.drawString(this.getSignal().getBit(), x + width, y + height + 5);
		}
		
		drawShape(g);
	}

	protected abstract void drawShape(Graphics2D g);

	/*
	 * (non-Javadoc)
	 * 
	 * @see signal.Properties#getID()
	 */
	public long getID() {
		return id;
	}

	public void connect(int outNodePos, Gate dest, int inNodePos) {
		/*
		 * connect this nodes output to the input of dest node , to the destNode
		 * location
		 */

		Node n1 = getOutputNode(outNodePos);
		Node n2 = dest.getInputNode(inNodePos);
		n1.connect(n2);
	}

	public abstract void simulate();
}
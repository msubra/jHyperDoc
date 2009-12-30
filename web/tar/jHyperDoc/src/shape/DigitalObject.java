/*
 * Created on Apr 15, 2004
 */
package shape;

import gui.MDIWindow;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import node.InputNode;
import node.Node;
import node.OutputNode;

/**
 * @author maheshexp
 */
public class DigitalObject extends DigitalShape {
	/**
	 * declares inputs and outputs in ArrayLists and in integers
	 */
	protected ArrayList inputs, outputs;
	protected int inputCount, outputCount;
	/**
	 * initialise the canmove variable to false
	 */
	boolean canMove = false;
	int x1, y1, x2, y2;

	public DigitalObject() {
		/* By default 0 inputs and 0 output */
		this(0, 0);
	}

	public DigitalObject(int inputCount, int outputCount) {
		super(20, 20);
		pad = MDIWindow.getDrawingPad();
		this.inputCount = inputCount;
		this.outputCount = outputCount;

		inputs = new ArrayList();
		outputs = new ArrayList();

		setBorderColor(Color.BLUE);
		checkSize();
		init();
	}

	/* intialize to default inputs and outputs */
	private void init() {
		for (int i = 0; i < inputCount; i++) {
			InputNode n = new InputNode(this, i);
			n.reposition();
			inputs.add(n);
		}
		for (int i = 0; i < outputCount; i++) {
			OutputNode n = new OutputNode(this, i);
			n.reposition();
			outputs.add(n);
			n.setSignal(signal);
		}
	}

	/**
	 * This function will find any object is avaiable in the given Point pad
	 * 
	 * @return Returns a DigitalShape if found any or NULL if nothing found
	 */
	public DigitalShape find(Point p) {
		//if the POINT is in the GATE
		if (this.contains(p))
			return this;

		Vector temp = new Vector(inputs);
		temp.addAll(outputs);

		//search for the POINT in InputNodes and OutputNodes
		for (int i = 0; i < temp.size(); i++) {
			Node n = (Node) temp.get(i);
			if (n.contains(p))
				return n;

		}
		//if not found
		return null;
	}

	/**
	 * This function will draw the body of the object, inputs and output nodes
	 */
	synchronized public void draw(Graphics2D g) {
		super.draw(g);

		checkSize(); //check for size each time u draw;
		Node n;
		for (int i = 0; i < inputs.size(); i++) {
			n = (Node) inputs.get(i);
			n.reposition();
			n.draw(g);
		}

		for (int i = 0; i < outputs.size(); i++) {
			n = (Node) outputs.get(i);
			n.reposition();
			n.draw(g);
		}
	}

	/*
	 * move the object to the location where mouse moves This will function only
	 * if canMove flag is set
	 */
	public void mouseMoved(MouseEvent e) {
		if (canMove) {
			x2 = x1 + e.getX();
			y2 = y1 + e.getY();
			reposition(); //repostion the object
		}
	}

	public void reposition() {
		this.setLocation(x2, y2);
	}

	/*
	 * store the location where the mouse is pressed canMove falg will be TRUE
	 * for one click and FALSE for another click
	 */

	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		canMove = !canMove;
		x1 = x - e.getX();
		y1 = y - e.getY();
	}

	public void delete() {
		Vector temp = new Vector(inputs);
		temp.addAll(outputs);
		for (int i = 0; i < temp.size(); i++) {
			Node n = (Node) temp.get(i);
			n.delete();
			n = null;
		}
		inputs = null;
		outputs = null;
		if (pad != null && pad.getCircuit() != null)
			pad.getCircuit().remove(this);
	}

	/**
	 * @return No.of inputs to this Digital Object
	 */
	public int getInputCount() {
		if (inputs == null)
			inputs = new ArrayList();
		return inputs.size();
	}

	/**
	 * @return No.of outputs to this Digital Object
	 */
	public int getOutputCount() {
		if (outputs == null)
			outputs = new ArrayList();
		return outputs.size();
	}

	/*
	 * this function will resize this DigitalObject according to the no.of
	 * inputs and outputs for this gate
	 */
	private void checkSize() {
		int size = getInputCount() >= getOutputCount()
				? getInputCount()
				: getOutputCount();
		int calcH = (Node.HEIGHT + Node.VGAP) * size - 1;

		if (calcH < 20)
			height = 20;
		else
			height = calcH;
	}

	public String toString() {
		return "Gate";
	}

	public void setInputCount(int count) {
		if (count > inputs.size()) { //increase no.of inputs
			for (int i = inputs.size(); i < count; i++) {
				InputNode n = new InputNode(this, i);
				n.reposition();
				inputs.add(n);
			}
		} else if (count < inputs.size()) { //decrease no.of inputs
			for (int i = count; i < inputs.size(); i++) {
				Node n = (Node) inputs.get(i);
				n.delete();
				inputs.remove(n);
			}
		}
		inputCount = inputs.size();
	}

	public void setOutputCount(int count) {
		if (count > outputs.size()) { //increase no.of inputs
			for (int i = outputs.size(); i < count; i++) {
				OutputNode n = new OutputNode(this, i);
				n.reposition();
				outputs.add(n);
			}
		} else if (count < outputs.size()) { //decrease no.of inputs
			for (int i = count; i < outputs.size(); i++) {
				Node n = (Node) outputs.get(i);
				n.delete();
				outputs.remove(n);
			}
		}
		outputCount = outputs.size();
	}

	public ArrayList getInputComponents() {
		return inputs;
	}

	public ArrayList getOutputComponents() {
		return outputs;
	}

}
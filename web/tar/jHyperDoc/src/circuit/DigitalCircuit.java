package circuit;

import gates.Clock;
import gates.DC;
import gates.Gate;
import gates.LED;
import gui.DrawingPad;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

import shape.DigitalShape;

public class DigitalCircuit extends Gate implements Cloneable {
	ArrayList components;
	transient ArrayList clocks;
	transient DrawingPad pad;

	public DigitalCircuit() {
		super("CIRCUIT", 0, 0, System.currentTimeMillis());
		components = new ArrayList();
		clocks = new ArrayList();
	}

	public DigitalCircuit(int inputs, int outputs) {
		super("Module", inputs, outputs, 123123);
	}

	public DigitalCircuit(DigitalCircuit circuit) {
		this();
		this.components = (ArrayList) circuit.components.clone();
		this.clocks = (ArrayList) circuit.clocks.clone();
		this.inputs = (ArrayList) circuit.inputs.clone();
		this.outputs = (ArrayList) circuit.outputs.clone();
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < inputs.size(); i++) {
			DigitalShape s = (DigitalShape) inputs.get(i);
			s.draw(g);
		}

		for (int i = 0; i < outputs.size(); i++) {
			DigitalShape s = (DigitalShape) outputs.get(i);
			s.draw(g);
		}

		for (int i = 0; i < components.size(); i++) {
			DigitalShape s = (DigitalShape) components.get(i);
			s.draw(g);
		}
	}

	synchronized public void simulate() {
		for (int i = 0; i < inputs.size(); i++) {
			Gate s = (Gate) inputs.get(i);
			s.simulate();
		}

		for (int i = 0; i < components.size(); i++) {
			Gate s = (Gate) components.get(i);
			s.simulate();
		}

		for (int i = 0; i < outputs.size(); i++) {
			Gate s = (Gate) outputs.get(i);
			s.simulate();
		}
	}

	/*
	 * DC Components are added to inputs LED Components are added to outputs
	 * Other Components are added to components
	 */
	public void add(DigitalShape c) {

		if (c instanceof LED)
			outputs.add(c);
		else if (c instanceof DC)
			inputs.add(c);
		else
			components.add(c);

		c.setCircuit(this);
	}

	public void remove(DigitalShape c) {
		inputs.remove(c);
		outputs.remove(c);
		components.remove(c);
	}

	//If the Point lies in any of the object in the circuit
	public DigitalShape find(Point p) {
		Vector temp = new Vector(inputs);
		temp.addAll(outputs);
		temp.addAll(components);

		for (int i = 0; i < temp.size(); i++) {
			DigitalShape s = (DigitalShape) temp.get(i);
			DigitalShape s1 = s.find(p);
			if (s1 != null)
				return s1;
		}

		return null;
	}

	public String toString() {
		return "Circuit";
	}

	public void stop() {
		for (int i = 0; i < inputs.size(); i++) {
			DC dc = (DC) inputs.get(i);
			if (dc.getSignal().isOn())
				dc.switchOff();
		}

		if (clocks != null) {
			for (int i = 0; i < clocks.size(); i++) {
				Clock c = (Clock) clocks.get(i);
				c.stop();
				c = null;
			}
			clocks.clear();
		}
	}

	public void start() {
		for (int i = 0; i < inputs.size(); i++) {
			DigitalShape s = (DigitalShape) inputs.get(i);
			if (s instanceof Clock) {
				Clock c = (Clock) s;
				c.start();
				clocks.add(c);
			}
		}
	}

	public int getInputCount() {
		int count = 0;
		for (int i = 0; i < inputs.size(); i++) {
			Gate g = (Gate) inputs.get(i);
			if (g.id == Gate.DC)
				count++;
		}
		return count;
	}

	/**
	 * Returns all the components present in the circuit
	 * 
	 * @return
	 */
	public Vector getComponents() {
		Vector temp = new Vector(inputs);
		temp.addAll(outputs);
		temp.addAll(components);
		return temp;
	}

	//	public DigitalCircuit toModule() {
	//		DigitalCircuit cir = new DigitalCircuit(this);
	//
	//		for (int i = 0; i < cir.inputs.size(); i++) {
	//			DC dc = (DC) cir.inputs.get(i);
	//			dc.MIN_INPUTS = 1;
	//			dc.changeInput(1);
	//		}
	//
	//		for (int i = 0; i < cir.outputs.size(); i++) {
	//			LED led = (LED) cir.outputs.get(i);
	//			led.MAX_INPUTS = 1;
	//			led.changeOutput(1);
	//		}
	//
	//		return cir;
	//	}

	/**
	 * sets drawingpad to the circuit on which to be drawn
	 */
	public void setDrawingPad(DrawingPad pad) {
		this.pad = pad;
	}

	/**
	 * @return DrawingPad
	 */
	public DrawingPad getDrawingPad() {
		return pad;
	}

	protected void drawShape(Graphics2D g) {
	}

}
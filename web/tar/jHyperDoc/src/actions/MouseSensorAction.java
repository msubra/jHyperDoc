package actions;

import gates.Gate;
import gui.DrawingPad;

import java.awt.Point;
import java.awt.event.MouseEvent;

import node.ByPassNode;
import node.InputNode;
import node.Node;
import node.OutputNode;
import shape.DigitalShape;
import util.GlobalUI;
import util.MessageBox;
import util.Util;

public class MouseSensorAction implements MouseSensor {

	/**
	 * Referes to which DrawingPad this listener is added
	 */
	DrawingPad pad;

	/**
	 * referes to currently selected object
	 */
	DigitalShape selected;

	/**
	 * From and To Node while connecting two nodes
	 */
	Node from, to;

	/**
	 * entered -> used to sense wheather the mouse entered into an object for
	 * the first time
	 */
	public boolean entered = false;

	public int x1, y1, x2, y2;

	/**
	 * @param pad
	 *            for which this listener should be added
	 */
	public MouseSensorAction(DrawingPad pad) {
		this.pad = pad;
		pad.addMouseListener(this);
		pad.addMouseMotionListener(this);
	}

	/**
	 * Define all events that should happen when the mouse is pressed on a
	 * Digital Object
	 * 
	 * 1. Get the object at the clicked location 2. update the details about the
	 * selected object 3. if the circuit is in the adding mode then add
	 * components to the circuit 4. if selected object is GATE then make it work
	 * under SIMULATION mode 5. if selected Object is NODE then if Not in
	 * Connecting Mode and clicked in the output node make ready for conection.
	 * if already in connecting mode then checkRules for making connection. if
	 * rules satisfied then make connection, else discard connection 6.set the
	 * correct mode occordingly 7. if selected is none of the objects above, and
	 * it is under connection mode then make BYPASS nodes for connections
	 * 
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		DigitalShape c = Util.getComp(p);

		/*
		 * Perform gate addition activities in the circuit area
		 */
		if (pad.isAddingMode()) {
			Gate gate = DrawingPad.getGate(pad.getGateType());
			gate.setLocation(e.getPoint());
			pad.add(gate);
			return;
		}

		if (c instanceof Gate) {
			/*
			 * Since it is a GATE, it works only in Simulating Mode
			 */
			c.mousePressed(e);
			return;
		} else if (c instanceof Node) {
			selected = c;
			if (!pad.isConnectingMode()) {
				pad.setMode(DrawingPad.CONNECTING);
				from = (Node) c;
			} else {//in connecting mode
				to = (Node) c;
				if (to.isConnected()) {
					MessageBox.showError(GlobalUI.getText("ERR005"));
				} else {
					if (checkRule(from, to)) {
						from.connect(to);
						if (to instanceof InputNode) {
							//connected to target, clear the mode
							pad.resetMode();
						}
						from = null;
						to = null;
					} else {
						if (from != null) {
							MessageBox.showError(GlobalUI.getText("ERR001"));
						}
					}
				}
			}

			if (!pad.isMovingMode()) {
				//at starting, mark all to the same location
				x1 = x2 = p.x;
				y1 = y2 = p.y;
			}
		}

		if (c instanceof DigitalShape) {
			c.mousePressed(e);
			return;
		}

		if (pad.isConnectingMode()) {
			ByPassNode n = new ByPassNode(pad.getCircuit());
			pad.getCircuit().add(n);
			n.setLocation(p);
			x1 = p.x;
			y1 = p.y;

			from.connect(n);
			from = n; //change the from node to the current node
		}

	}

	/**
	 * Define all events that should happen when the mouse is released on a
	 * Digital Object
	 * 
	 * 1. Get the object at the relased location 2. check wheather it is not
	 * null 3. send the mouseReleased event to the object 4. repaint the drawing
	 * area
	 */
	public void mouseReleased(MouseEvent e) {
		DigitalShape c = Util.getComp(e.getPoint());
		if (c != null) {
			c.mouseReleased(e);
		}
	}

	/**
	 * Define all events that should happen when the mouse is moved on a Digital
	 * Object
	 */
	public void mouseMoved(MouseEvent e) {
		if (pad.isAddingMode()) {
			x1 = e.getX();
			y1 = e.getY();
		}

		if (pad.isMovingMode() && selected != null) {
			selected.mouseMoved(e);
		} else if (pad.isConnectingMode()) {
			x2 = e.getX();
			y2 = e.getY();
		}

		DigitalShape c = Util.getComp(e.getPoint());
		if (c != null) {
			selected = c;
			if (!entered) { //invoke mouseEntered event
				entered = true;
				c.mouseEntered(e);
			}
			//Generate MouseMove if only in NORMAL mode
			if (pad.isNormalMode()) {
				c.mouseMoved(e);
			}
		} else {
			if (entered) { //invoke mouseExited event
				selected.mouseExited(e);
				entered = false;
			}
			selected = null;
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * This functions will check the Rule as follows Connections can only be
	 * made if 1. Source is InputNode and target is InputNode 2. Source is
	 * OutputNode and target is InputNode 3. Source is ByPassNode && target is
	 * another ByPassNode
	 */
	private boolean checkRule(Node source, Node target) {
		return !target.isConnected()
				&& ((source instanceof ByPassNode && target instanceof ByPassNode)
						|| (source instanceof InputNode && target instanceof InputNode) || (source instanceof OutputNode && target instanceof InputNode));

	}
}
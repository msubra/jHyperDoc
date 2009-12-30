/*
 * Created on Apr 15, 2004
 */
package node;

import gates.Gate;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import properties.Properties;
import shape.DigitalObject;
import shape.DigitalShape;
import circuit.DigitalCircuit;

/**
 * @author maheshexp
 */
public abstract class Node extends DigitalShape implements Properties {

	public static final int VGAP = 5;
	public static final int HGAP = 5;
	public static final int HEIGHT = 5;
	public static final int WIDTH = 5;

	protected int id;
	boolean connected = false;
	public Node from;

	protected DigitalObject parent;
	public ArrayList connections;
	private String label;

	public Node(DigitalObject parent) {
		super(WIDTH, HEIGHT);
		this.parent = parent;
		connections = new ArrayList();
	}

	/**
	 * @return Parent of this Node
	 */
	public DigitalObject getParent() {
		return parent;
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setParent(DigitalObject parent) {
		this.parent = parent;
	}

	synchronized public void draw(Graphics2D g) {
		super.draw(g);

		/* draw the node */
		Graphics2D g1 = (Graphics2D) g;
		g1.draw(this);

		int size = connections.size();

		for (int i = 0; i < size; i++) {
			/* draw the respective connections */
			Node n = (Node) connections.get(i);

			/*
			 * connect from the middle of the node to middle of other node x +
			 * width / 2 or y + height / 2 gives the center point of a node
			 */
			g.drawLine(x + width / 2, y + height / 2, n.x + n.width / 2, n.y
					+ n.height / 2);

			//System.out.println(this.getLabel());
			if (this.getLabel() != null) {
				g.drawString(this.getLabel(), x - 5, y - 5);
				System.out.println("Node:" + this.getLabel());
			}

		}
	}

	public DigitalShape find(Point p) {
		return this.contains(p) ? this : null;
	}

	public void connect(Node n) {
		connections.add(n);
		//set the Connected flag of target node, so no other inputs cannot be
		n.setConnected(true);
		n.setSignal(signal);
		n.from = this;
	}

	/**
	 * @returns Wheather this node is already connected to some other sources
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * @param b
	 */
	public void setConnected(boolean b) {
		connected = b;
	}

	public void delete() {
		if (connections != null && connections.size() > 0) {
			//detach the connection from the source where it is connected
			if (from != null)
				from.connections.remove(this);

			//remove other connections from this node
			for (int i = 0; i < connections.size(); i++) {
				Node n = (Node) connections.get(i);
				n.delete();
				n.setConnected(false);

				//if node n is intermediate node, then delete the node
				if (this instanceof ByPassNode) {
					DigitalCircuit d = (DigitalCircuit) parent;
					d.remove(this);
				}
			}
			connections = new ArrayList();
		}
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param string
	 */
	public void setLabel(String string) {
		label = string;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see signal.Properties#getID()
	 */
	public long getID() {
		return Gate.NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see signal.Properties#getName()
	 */
	public String getName() {
		return "NODE";
	}

	public ArrayList getConnections() {
		return new ArrayList(connections);
	}

	public void clearConnections() {
		connections.clear();
		if (from != null)
			from.clearConnections();
		this.setConnected(false);
	}

	public void setInputCount(int count) {
	}

	public void setOutputCount(int count) {
	}

	public String toString() {
		return super.toString() + ":" + this.getLabel();
	}

}
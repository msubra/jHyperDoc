/*
 * Created on May 4, 2004
 */
package gates;

import java.awt.Graphics2D;

/**
 * @author maheshexp
 */
public class Flip1 extends Gate {
	Gate n1, n2;

	public Flip1() {
		super("Flip1", 2, 2, 2, 2, AND);
		//super("Flip1", AND);

		/*
		 * connect the gates to form a flip flop
		 */
		n1 = new Nor();
		n2 = new Nor();

		n1.connect(0, n2, 0);
		n2.connect(0, n1, 1);
	}

	synchronized public void draw(Graphics2D g) {
		simulate();
		super.draw(g);
	}

	protected void drawShape(Graphics2D g) {

	}

	public void simulate() {
		/*
		 * feed the signal to the flip flop
		 */
		boolean b1 = getInputSignal(0).value();
		boolean b2 = getInputSignal(1).value();
		n1.getInputSignal(0).value(b1);
		n1.simulate();

		n2.getInputSignal(1).value(b2);
		n2.simulate();

		/*
		 * place the resultant signal of the flipflop to the output nodes
		 */
		this.getOutputNode(0).setSignal(n1.getOutputSignal(0));
		this.getOutputNode(1).setSignal(n2.getOutputSignal(0));
	}

}
package parser;

import gates.And;
import gates.DC;
import gates.Gate;
import gates.LED;
import gates.Nand;
import gates.Nor;
import gates.Not;
import gates.Or;
import gates.Xor;
import gui.MDIWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

import util.Util;
import circuit.DigitalCircuit;

public class EquationCircuit {
	PostfixExpression expr;
	HashMap store;
	DigitalCircuit cir;
	Stack stk;
	LinkedList list;

	static int x = 30, y = 30, INC_X = 80, INC_Y = 40;

	public EquationCircuit() {
		expr = new PostfixExpression();
		store = new HashMap();
		cir = new DigitalCircuit();
		stk = new Stack();
	}

	public DigitalCircuit convert(String s) {
		expr.toPostFix(s);
		list = expr.getTokens();
		Gate g = null;

		cir.setDrawingPad(MDIWindow.getDrawingPad());

		for (int i = 0; i < list.size(); i++) {
			String x = (String) list.get(i);

			if ("+*|^@~".indexOf(x) > -1) { //some operators
				Gate cir_b = (Gate) stk.pop();

				if (x.equalsIgnoreCase("~")) { //single output gate
					g = new Not();
					cir_b.connect(0, g, 0);
				} else { //double output gate
					Gate cir_a = (Gate) stk.pop();
					if (x.equalsIgnoreCase("+")) {
						g = new Or();
						cir_a.connect(0, g, 0); //connect a to Or gate 0th node
						cir_b.connect(0, g, 1); //connect a to Or gate 1st node
					} else if (x.equalsIgnoreCase("*")) {
						g = new And();
						cir_a.connect(0, g, 0); //connect a to Or gate 0th node
						cir_b.connect(0, g, 1); //connect a to Or gate 1st node
					} else if (x.equalsIgnoreCase("^")) {
						g = new Xor();
						cir_a.connect(0, g, 0); //connect a to Or gate 0th node
						cir_b.connect(0, g, 1); //connect a to Or gate 1st node
					} else if (x.equalsIgnoreCase("|")) {
						g = new Nor();
						cir_a.connect(0, g, 0); //connect a to Or gate 0th node
						cir_b.connect(0, g, 1); //connect a to Or gate 1st node
					} else if (x.equalsIgnoreCase("@")) {
						g = new Nand();
						cir_a.connect(0, g, 0); //connect a to Or gate 0th node
						cir_b.connect(0, g, 1); //connect a to Or gate 1st node
					}
				}
				cir.add(g);
				stk.push(g);
			} else { //create source from variables
				DC d;
				if (!store.containsKey(x)) { //if already the variable is not
					// available, create
					d = new DC();
					d.setLabel(x);
					store.put(x, d);
				} else {
					d = (DC) store.get(x);
				}
				cir.add(d);
				stk.push(d); //push into stack
			}
		}

		LED out = new LED();
		out.setLabel("OUT");
		g = (Gate) stk.pop();
		g.connect(0, out, 0);
		cir.add(out);

		stk.clear();

		return cir;
	}

	public static void align(DigitalCircuit cir) {
		Vector in = cir.getComponents();
		for (Iterator i = in.iterator(); i.hasNext();) {
			Gate g = (Gate) i.next();
			if (g instanceof DC) {
				DC dc = (DC) g;
				dc.setLocation(x, y);
				align(dc, x);
				y += INC_Y;
				x += INC_X;
			}
		}

		x = y = 50;
	}

	public static void align(Gate gate, int x1) {
		ArrayList gates = Util.getConnectedGates(gate);
		for (Iterator j = gates.iterator(); j.hasNext();) {
			Gate g = (Gate) j.next();
			g.setLocation(x1 + INC_X, y);
			align(g, x1 + INC_X);
		}
	}

}
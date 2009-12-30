/*
 * Created on Jan 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package actions;

import gates.And;
import gates.Gate;
import gates.Nand;
import gates.Nor;
import gates.Not;
import gates.Or;
import gates.Xor;
import gui.DrawingPad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import circuit.DigitalCircuit;
import circuit.Module;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class JLogicsXML {
	static XStream st;

	static {
		st = new XStream(new DomDriver());
		st.alias("gate", Gate.class);
		st.alias("circuit", DigitalCircuit.class);
		st.alias("and", And.class);
		st.alias("or", Or.class);
		st.alias("not", Not.class);
		st.alias("nand", Nand.class);
		st.alias("nor", Nor.class);
		st.alias("xor", Xor.class);
		st.alias("pad", DrawingPad.class);
		st.alias("module", Module.class);
	}

	public static void write(Gate g, File filename) {

		FileWriter fr = null;
		try {
			fr = new FileWriter(filename);
			st.toXML(g, fr);
			fr.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public static Gate read(File filename) {
		FileReader fr = null;
		Gate g = null;
		try {
			fr = new FileReader(filename);
			g = (Gate) st.fromXML(fr);
			fr.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			try {
				fr.close();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

		return g;
	}

}
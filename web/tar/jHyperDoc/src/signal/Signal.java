/*
 * Created on Apr 15, 2004
 */
package signal;

import java.io.Serializable;

/**
 * @author maheshexp
 */
public class Signal implements Serializable {
	boolean signal;
	public static final boolean ON = true;
	public static final boolean OFF = false;
	private static final long serialVersionUID = 111L;

	/**
	 *  
	 */
	public Signal() {
		this(false);
	}

	public Signal(boolean signal) {
		this.signal = signal;
	}

	/**
	 * @return
	 */

	/**
	 * @param signal
	 */
	public void value(boolean signal) {
		this.signal = signal;
	}
	
	public String getBit() {
		return "" + (signal ? 1 : 0) ;
	}

	public String toString() {
		return "Signal:[" + value() + "]";
	}

	/**
	 * @return
	 */
	public boolean value() {
		return signal;
	}

	public boolean isOn() {
		return value() == true;
	}

}
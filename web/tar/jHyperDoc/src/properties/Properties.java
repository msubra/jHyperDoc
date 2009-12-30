/*
 * Created on May 13, 2004
 */
package properties;

/**
 * @author maheshexp
 */
public interface Properties {

	int getInputCount();
	int getOutputCount();

	String getName();

	/**
	 * represents what type of Gate or any user defined Circuit
	 * 
	 * @return
	 */
	long getID();

	String getLabel();
	void setLabel(String label);

	void setInputCount(int count);
	void setOutputCount(int count);
}
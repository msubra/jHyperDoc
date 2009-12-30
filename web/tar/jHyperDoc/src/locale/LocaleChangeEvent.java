/*
 * Created on Apr 13, 2004
 */
package locale;

import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author maheshexp
 */
public class LocaleChangeEvent extends EventObject {

	private Locale locale = null;

	public LocaleChangeEvent(Object source) {
		super(source);
	}

	public LocaleChangeEvent(Object source, Locale locale) {
		super(source);
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public ResourceBundle getResource() {
		return locale != null ? ResourceBundle.getBundle("My", locale) : null;
	}

	public String getValue(String key) {
		return getResource().getString(key);
	}
}
package locale;

import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JComboBox;

/**
 * @author maheshexp
 */

class LocaleChooser extends JComboBox implements Language {
	Vector localeChangeListeners;
	String currentLanugage;

	static final String COUNTRY = "IN";

	public LocaleChooser() {
		this.addActionListener(this); //mandatory
	}

	public synchronized void addLocaleChangeListener(LocaleChangeListener l) {
		Vector v = localeChangeListeners == null
				? new Vector(2)
				: (Vector) localeChangeListeners.clone();

		if (!v.contains(l)) {
			v.add(l);
			localeChangeListeners = v;
		}
	}

	protected void fireLocaleChanged(LocaleChangeEvent e) {
		if (localeChangeListeners != null) {
			Vector listeners = localeChangeListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++)
				((LocaleChangeListener) listeners.get(i)).localeChanged(e);
		}
	}

	public void actionPerformed(ActionEvent evt) {
		String name = this.getSelectedItem().toString();
		if (name.equals("Tamil")) {
			currentLanugage = TAMIL;
		} else if (name.equals("English")) {
			currentLanugage = ENGLISH;
		}

		Locale myLocale = new Locale(currentLanugage, COUNTRY);
		fireLocaleChanged(new LocaleChangeEvent(this, myLocale));
	}
}
/*
 * Created on Apr 15, 2004
 */
package actions;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

/**
 * @author maheshexp
 */
public interface MouseSensor
		extends
			MouseListener,
			MouseMotionListener,
			Serializable {
}
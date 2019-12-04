package controller;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class Shortcut {

	public static void addListener(JComponent component, AbstractAction action, int condition, String key) {
		ActionMap actionMap = new ActionMap();
		actionMap.put("key", action);
		component.setActionMap(actionMap);

		InputMap inputMap = component.getInputMap(condition);
		inputMap.put(KeyStroke.getKeyStroke(key), "key");
	}
}

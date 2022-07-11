package com.hcl.adi.chf.enums;

/**
 * Enumeration to define API actions
 *
 * @author AyushRa
 */
public enum Actions {
	LIST, EDIT, ACTIVATE, DEACTIVATE, DELETE, TNCUPDATED, PROFILEUPDATED;

	/**
	 * Convert a action string into the corresponding Action.
	 *
	 * @param actionStr
	 * @return
	 */
	public static Actions fromStringAction(final String actionStr) {
		for (Actions action : Actions.values()) {
			if (action.name().equalsIgnoreCase(actionStr)) {
				return action;
			}
		}

		return null;
	}
}
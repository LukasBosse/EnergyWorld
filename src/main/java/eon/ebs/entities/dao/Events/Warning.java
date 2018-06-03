package eon.ebs.entities.dao.Events;

import android.graphics.Bitmap;

public class Warning extends Event {

	public Warning(String title, String message, Bitmap icon) {
		super("Warnung: " + title, message, icon);
	}

}

package eon.ebs.entities.dao.Events;

import android.graphics.Bitmap;

public class Tutorial extends Event {

	public Tutorial(String title, String message, Bitmap icon) {
		super("Tutorial: " + title, message, icon);
	}

}

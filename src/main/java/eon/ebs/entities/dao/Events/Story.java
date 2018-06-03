package eon.ebs.entities.dao.Events;

import android.graphics.Bitmap;

public class Story extends Event {

	public Story(String title, String message, Bitmap icon) {
		super("Story: " + title, message, icon);
	}

}

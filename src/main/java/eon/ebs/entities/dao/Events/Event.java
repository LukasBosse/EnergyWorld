package eon.ebs.entities.dao.Events;

import android.graphics.Bitmap;

public class Event {

	private String title;
	private String message;
	private Bitmap icon;
	private boolean active;

	public Event(String title, String message, Bitmap icon) {
		this.title = title;
		this.message = message;
		this.icon = icon;
	}

	public void show() {
		//TODO: Implement show function for events
	}

	public void hide() {
		//TODO: Implement hide function for events
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}

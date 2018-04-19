package eon.ebs.sounds;

import android.content.Context;
import android.graphics.Point;
import eon.ebs.storage.SharedPreferencesModul;

public class SoundStorage extends SharedPreferencesModul {

	public SoundStorage(Context context) {
		super(context);
	}
	
	/**
	 * Speichern der Audioeinstellungen in den SharedPreferences unter den Schlüsseln soundVolLeft und soundVolRight
	 * @param l
	 * @param r
	 */
	
	public void set(int l, int r) {
	    editor.putInt("soundVolLeft", l);
	    editor.putInt("soundVolRight", r);
	    editor.commit();
	}
	
	/**
	 * Ausgabe der gespeicherten Audioeinstellungen
	 * @return
	 */
	
	public Point getVol() {
		int l = sharedPreferences.getInt("soundVolLeft", 0);
		int r = sharedPreferences.getInt("soundVolRight", 0);
		return new Point(l,r);
	}
}

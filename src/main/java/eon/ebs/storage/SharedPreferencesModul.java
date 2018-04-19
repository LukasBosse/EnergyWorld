package eon.ebs.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesModul {

	protected static SharedPreferences sharedPreferences;
	protected static SharedPreferences.Editor editor;
		
	@SuppressLint("CommitPrefEdits")
	public SharedPreferencesModul(Context context) {
		SharedPreferencesModul.sharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE);
		SharedPreferencesModul.editor = sharedPreferences.edit();
	}
			
}

package eon.ebs.engine;

import com.eon.energyworld.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Loader extends Activity {

	private ProgressBar prog_loader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature ( Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.loader);
		initGUI();
	}
	
	private void initGUI() {
		prog_loader = (ProgressBar) findViewById(R.id.prog_loader);
		new BackgroundAsyncTask().execute();
	}
			
	public class BackgroundAsyncTask extends AsyncTask<Void, Integer, Void> {
  
		int myProgress;

		protected void onPostExecute(Void result) {
			Toast.makeText(Loader.this,"Fertig!!", Toast.LENGTH_LONG).show();
			Intent mainPage = new Intent(Loader.this, Europa.class);
            startActivity(mainPage);
		}

		protected void onPreExecute() {
			Toast.makeText(Loader.this,"Create a better EnergyWorld.", Toast.LENGTH_LONG).show();
			myProgress = 0;
		}

		protected Void doInBackground(Void... params) {
			while(myProgress<100){
				myProgress++;
				publishProgress(myProgress);
				SystemClock.sleep(100);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			prog_loader.setProgress(values[0]);
		}

	}
	
	
}



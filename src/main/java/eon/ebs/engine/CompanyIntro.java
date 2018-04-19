package eon.ebs.engine;

import com.eon.energyworld.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import eon.ebs.sounds.SoundSystem;

/**
 * Company Intro - Powered By E.ON
 * @author L15905
 * @date 02/03/2017
 * @version 1.0.0
 */

public class CompanyIntro extends Activity {

	private Animation animation_fadeout,animation_fadein;
	private ImageView imgView;
	private SoundSystem soundSystem = new SoundSystem();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)  {

		 super.onCreate(savedInstanceState);
		 requestWindowFeature ( Window.FEATURE_NO_TITLE);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		 setContentView(R.layout.companyscreen); 
		 
		 startMusic();
		 
		 animation_fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);
		 animation_fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
		 imgView = (ImageView) findViewById(R.id.imgView_companyscreen);
		 
		 new BackgroundAsyncTask().execute();
		 
	}
	
	@SuppressWarnings("static-access")
	private void startMusic() {
		soundSystem.initalSound(this, "startsound.mp3");
		soundSystem.startMusic();
	}
	
	public class BackgroundAsyncTask extends AsyncTask<Void, Integer, Void> {
  
		int myProgress;

		protected void onPostExecute(Void result) {
			imgView.setAnimation(animation_fadeout);
			imgView.startAnimation(animation_fadeout);
			Intent mainPage = new Intent(CompanyIntro.this, Europa.class);
            startActivity(mainPage);
		}

		protected void onPreExecute() {
			myProgress = 0;
			imgView.setAnimation(animation_fadein);
			imgView.startAnimation(animation_fadein);
		}

		protected Void doInBackground(Void... params) {
			while(myProgress<25){
				myProgress++;
				publishProgress(myProgress);
				SystemClock.sleep(100);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
		}

	}
	
}

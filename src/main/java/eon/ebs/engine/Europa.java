package eon.ebs.engine;

import com.eon.energyworld.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import eon.ebs.dialogs.MessageBox;
import eon.ebs.sounds.SoundSystem;

public class Europa extends Activity implements OnTouchListener {
	
	private ImageView 			imgView;
	private SoundSystem 		soundSystem = new SoundSystem();
	private Drawable 			imgDrawable;
	private Bitmap 				bitmap;
	private String[] 			laender = {"Deutschland","Frankreich","Italien","Spanien","England","Schweden"};
	private int 				selLand = -1;
	private int 				xCoord = 0;
	private int 				yCoord = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)  {

		 super.onCreate(savedInstanceState);
		 requestWindowFeature ( Window.FEATURE_NO_TITLE);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		 setContentView(R.layout.europa); 
		 		 
		 imgView = (ImageView) findViewById(R.id.imgView_land);
		 imgView.setOnTouchListener(this);
		 		 
		 imgDrawable = imgView.getDrawable();
		 bitmap = ((BitmapDrawable) imgDrawable).getBitmap();
				 		 
	}
	
	@SuppressWarnings("static-access")
	private void startMusic() {
		soundSystem.initalSound(this, "clicksound.mp3");
		soundSystem.startMusic();
	}

	private void analyseGrid(int x, int y) {
			        
        Matrix inverse = new Matrix();
        ((ImageView) imgView).getImageMatrix().invert(inverse);
        float[] touchPoint = new float[]{x, y};
        inverse.mapPoints(touchPoint);
        xCoord = (int) touchPoint[0];
        yCoord = (int) touchPoint[1];
        
        if(xCoord > ((imgView.getWidth()/2)-(bitmap.getWidth()/2)) && xCoord < bitmap.getWidth()) {
        		
			int pixelColour = bitmap.getPixel(xCoord, yCoord);
			
			int pRed = Color.red(pixelColour);
			int pBlue = Color.blue(pixelColour);
			int pGreen = Color.green(pixelColour);
						
			if(pRed == 255 && pBlue == 0 && pGreen == 8) {
				//Deutschland - Rot
				selLand = 0;
			} else if(pRed == 32 && pBlue == 124 && pGreen == 41) {
				//Frankreich - Blau
				selLand = 1;
			} else if(pRed == 38 && pBlue == 0 && pGreen == 127) {
				//Italien - Gruen
				selLand = 2;
			} else if(pRed == 127 && pBlue == 0 && pGreen == 51) {
				//Spanien - Braun
				selLand = 3;
			} else if(pRed == 255 && pBlue == 84 && pGreen == 178) {
				//England - Orange
				selLand = 4;
			} else if(pRed == 255 && pBlue == 0 && pGreen == 255) {
				//Schweden - Gelb
				selLand = 5;
			} else {
				//Umland
				selLand = -1;
			} 
	
        }
		
		if(selLand != -1) {		
			Intent mainPage = new Intent(Europa.this, AndroidLauncher.class);
			mainPage.putExtra("Land", laender[selLand]);
			startActivity(mainPage);
		} else {
			Intent mainPage = new Intent(Europa.this, MessageBox.class);
			startActivity(mainPage);
		}
	
		
	}
	
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View v, MotionEvent event) {
		startMusic();
		analyseGrid((int) event.getX(), (int) event.getY());    
		return false;
	}	

}

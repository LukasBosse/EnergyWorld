package eon.ebs.engine;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.eon.energyworld.R;
import eon.ebs.layouts.GridViewAdapter;
import eon.ebs.layouts.ImageItem;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication implements OnClickListener {
	
	private MainLoop				mainLoop;
	
	private Button 					btn_tools;
	private Button					btn_grabPointer;
	private Button					btn_saveChange;
	private Button					btn_undoChange;
	private TextView				level;
	private ProgressBar				levelProgress;
	
	private GridView 				gridView;
	private GridViewAdapter 		gridAdapter;

	private int						loadingProgress = 0;
	private int						mouseModus = 1;
	private int						selectedItem = 0;

	private boolean					loading = true;
				
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		mainLoop = new MainLoop(this);
		setContentView(R.layout.userinterface);

		GridLayout rel = (GridLayout) findViewById(R.id.gameBg);
		View view = initializeForView(mainLoop, config);
		rel.addView(view);
		configGUI();

		Thread apiUpdateTimerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(loading) {
					try {
						hideLoader();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		apiUpdateTimerThread.start();

	}

	public void updateLoader(int progress) {
		LinearLayout loadingScreen = (LinearLayout) findViewById(R.id.loadingScreenOverlay);
		ProgressBar pB = (ProgressBar) loadingScreen.findViewById(R.id.loadingScreen).findViewById(R.id.prog_loader);
		loadingProgress = progress;
		pB.setProgress(progress);
	}

	public synchronized void hideLoader() {
		if(loadingProgress >= 95) {
			loading = false;
			LinearLayout loadingScreen = (LinearLayout) findViewById(R.id.loadingScreenOverlay);
			loadingScreen.setVisibility(View.GONE);
		}
	}
	
	private void configGUI() {

		btn_tools = (Button) findViewById(R.id.btn_tools);
		btn_grabPointer = (Button) findViewById(R.id.btn_grabPointer);
		btn_saveChange = (Button) findViewById(R.id.btn_saveChange);
		btn_undoChange = (Button) findViewById(R.id.btn_undoChange);

		levelProgress = (ProgressBar) findViewById(R.id.progressLevelBar);
		level = (TextView) findViewById(R.id.levelText);
		
		btn_saveChange.setVisibility(View.INVISIBLE);
		btn_undoChange.setVisibility(View.INVISIBLE);
		
		gridView = (GridView) findViewById(R.id.gridView1);
        gridAdapter = new GridViewAdapter(this, R.layout.grid, getData());
        gridView.setAdapter(gridAdapter);
       
        level.setText("Level " + levelProgress.getProgress());
		      
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				pushPosition(position);
			}
		});
               
		btn_tools.setOnClickListener(this);
		btn_grabPointer.setOnClickListener(this);
		btn_saveChange.setOnClickListener(this);
		btn_undoChange.setOnClickListener(this);
				
	}
	
	public void setModus(int modus) {
		this.mouseModus = modus;
	}
	
	protected void setSelectedItem(int i) {
		this.selectedItem = i;
	}
		
	private void pushPosition(int selectedItem) {
		this.selectedItem = selectedItem;
		this.mainLoop.setSelectedItem(this.selectedItem);
	}
	
	private void pushModus() {
		mainLoop.setGrid();
	}
	
	private ArrayList<ImageItem> getData() {
       final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
       TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
       for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, ""));
        }
        imgs.recycle();
        return imageItems;
	 }
						
	/** Ein- und Ausblenden der Toolbox */
	
	private void setMenuVisibility() {
		if(gridView.getVisibility() == View.INVISIBLE) {
			gridView.setVisibility(View.VISIBLE);
			gridView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomin));
			btn_tools.setText("<");
			btn_saveChange.setVisibility(View.VISIBLE);
			btn_undoChange.setVisibility(View.VISIBLE);
		} else {
			gridView.setVisibility(View.INVISIBLE);
			gridView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomout));
			btn_tools.setText(">");
			btn_saveChange.setVisibility(View.INVISIBLE);
			btn_undoChange.setVisibility(View.INVISIBLE);
		}
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.btn_tools: {
				setMenuVisibility();
				break;
			}
			case R.id.btn_grabPointer: {
				if(mouseModus == 0) {
					btn_grabPointer.setBackground(getResources().getDrawable(R.drawable.mousepointer));
					mouseModus = 1;
				} else {
					btn_grabPointer.setBackground(getResources().getDrawable(R.drawable.mousegrab));
					mouseModus = 0;
				}
				setMenuVisibility();
				pushModus();
				break;
			}
			case R.id.btn_saveChange: {
				break;
			}
			case R.id.btn_undoChange: {
				break;
			}
			case R.id.imgView_Budget: {
	//			Intent mainPage = new Intent(Game.this, BudgetBox.class);
	//			startActivity(mainPage);
				break;
			}
			case R.id.imgViewPersonData: {
	//			Intent mainPage = new Intent(Game.this, UserBox.class);
	//			startActivity(mainPage);
				break;
			}
		}
	}

}

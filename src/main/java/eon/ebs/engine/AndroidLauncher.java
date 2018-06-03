package eon.ebs.engine;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.eon.energyworld.R;
import eon.ebs.entities.dao.Player.Player;
import eon.ebs.layouts.GridViewAdapter;
import eon.ebs.layouts.ImageItem;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication implements OnClickListener {
	
	private MainLoop				mainLoop;
	private Player					player = new Player("Lukas");
	
	private Button 					btn_tools;
	private Button					btn_grabPointer;
	private Button					btn_saveChange;
	private Button					btn_undoChange;

	private ImageButton				btn_scrollDown;
	private ImageButton				btn_scrollUp;

	private TextView				txt_budget;

	private LinearLayout			layout_baumenu;
	private RelativeLayout			btn_Baumenu;
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
		mainLoop = new MainLoop(this, player);
		setContentView(R.layout.ui);

		GridLayout rel = (GridLayout) findViewById(R.id.gameBackground);
		View view = initializeForView(mainLoop, config);
		rel.addView(view);
		configGUI(savedInstanceState);

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
		LinearLayout loadingScreen = (LinearLayout) findViewById(R.id.loadingOverlay);
		ProgressBar pB = (ProgressBar) loadingScreen.findViewById(R.id.loadingScreenLayout).findViewById(R.id.prog_loader);
		loadingProgress = progress;
		pB.setProgress(progress);
	}

	public synchronized void hideLoader() {
		if(loadingProgress >= 95) {
			loading = false;
			LinearLayout loadingScreen = (LinearLayout) findViewById(R.id.loadingOverlay);
			loadingScreen.setVisibility(View.GONE);
		}
	}
	
	private void configGUI(Bundle savedInstanceState) {

		layout_baumenu = (LinearLayout) findViewById(R.id.Baumenu_Layout);
		btn_scrollDown = (ImageButton) findViewById(R.id.btn_ScrollDown);
		btn_scrollUp = (ImageButton) findViewById(R.id.btn_ScrollUp);
		btn_Baumenu = (RelativeLayout) findViewById(R.id.Footer_Item1);
		txt_budget = (TextView) findViewById(R.id.txt_Budget);

		/**
		 * TODO: Implement Country Display
		 * if(savedInstanceState != null) {
			savedInstanceState.getString("Land");
		 }
		 **/

		gridView = (GridView) findViewById(R.id.Baumenu);
        gridAdapter = new GridViewAdapter(this, R.layout.grid, getData());
        gridView.setAdapter(gridAdapter);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				pushPosition(position);
			}
		});

		btn_Baumenu.setOnClickListener(this);
		txt_budget.setText(String.valueOf(player.getBudget()));

	}
	
	public void setModus(int modus) { this.mouseModus = modus; }
	
	protected void setSelectedItem(int i) { this.selectedItem = i; }
		
	private void pushPosition(int selectedItem) {
		this.selectedItem = selectedItem;
		this.mainLoop.setSelectedItem(this.selectedItem);
	}
	
	private void pushModus() { mainLoop.setGrid(); }

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
		if(layout_baumenu.getVisibility() == View.INVISIBLE) {
			layout_baumenu.setVisibility(View.VISIBLE);
			gridView.setVisibility(View.VISIBLE);
			layout_baumenu.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomin));
		} else {
			layout_baumenu.setVisibility(View.INVISIBLE);
			layout_baumenu.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomout));
		}
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.Footer_Item1: {
				setMenuVisibility();
				pushModus();
				break;
			}
			case R.id.Footer_Item2: {
				break;
			}
			case R.id.Footer_Item3: {
				break;
			}
		}
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}

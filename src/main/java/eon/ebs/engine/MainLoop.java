package eon.ebs.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import eon.ebs.entities.dao.*;
import eon.ebs.entities.dto.Tile;

import java.util.LinkedList;
import java.util.List;

public class MainLoop extends ApplicationAdapter implements InputProcessor {

	//UI
	private AndroidLauncher ui;
	private AssetManager am;
	//Rendering
	private IsometricTiledMapRenderer tiledMapRenderer;
	//Camera
	private OrthographicCamera camera;
	private int mouseMode = 0;
	//Map
	private final String MAPPATH = "Kartenmaterial/Karten/Testmap.tmx";
	private TiledMap tiledMap;
	private TiledMapTileLayer groundLayer;
	private TiledMapTileLayer gridLayer;
	private TiledMapTileLayer primaerLayer;
	private float tilePixelWidth;
	private float tilePixelHeight;
	//Picking
	private Vector3 lastPoint = new Vector3(-1,-1,-1);
	private int selectedItem = 0;
	private List<Tile> tileList = new LinkedList<>();
	
	public MainLoop(AndroidLauncher ui) { this.ui = ui; }

	@Override
	public void create() {
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

		am = new AssetManager();
		am.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		am.load(MAPPATH, TiledMap.class);
		while(!am.update()) {
			ui.updateLoader((int) (am.getProgress() * 100));
		}
		am.finishLoading();
		tiledMap = am.get(MAPPATH);
		tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap);

		groundLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
		primaerLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
		gridLayer = (TiledMapTileLayer) tiledMap.getLayers().get(3);
		tilePixelWidth = groundLayer.getTileWidth();
		tilePixelHeight = groundLayer.getTileHeight();
		Vector3 center = new Vector3(groundLayer.getWidth() * groundLayer.getTileWidth() / 2, 0, 0);

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
        camera.position.set(center);
		camera.update();

		GestureDetector gd = new GestureDetector(new GameGestureListener(camera));
		InputMultiplexer im = new InputMultiplexer(gd, this);
		Gdx.input.setInputProcessor(im);

	}

	@Override
	public void render() {
	  	Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
	}
	
	private void checkTouch() {
		if(lastPoint != new Vector3(-1,-1,-1) && mouseMode == 1 && selectedItem != 0) {
			int x = (int) lastPoint.x;
			int y = (int) lastPoint.y;
			if(gridLayer.getCell(x, y) != null) {
				Tile newTile = getPlant(selectedItem, x, y);
				if(!checkIntersection(newTile,x,y)) {
					primaerLayer.getCell(x, y).setTile(tiledMap.getTileSets().getTileSet(2).getTile(5));
					tileList.add(newTile);
				}
				selectedItem = 0;
				pushSelectedItem();
			}
		}
	}

	private Tile getPlant(int selectedItem, int x, int y) {
		int width = (int) tilePixelWidth;
		int height = (int) tilePixelHeight;
		switch (selectedItem) {
			case 1: {
				return new OffshoreBig(x, y,width,height);
			}
			case 2: {
				return new OffshoreSmall(x,y,width,height);
			}
			case 3: {
				return new OnshoreBig(x,y,width,height);
			}
			case 4: {
				return new OnshoreSmall(x,y,width,height);
			}
			case 5: {
				return new Photovoltaic(x,y,width,height);
			}
			default: {
				break;
			}
		}
		return null;
	}
	
	private boolean checkIntersection(Tile newTile, int x, int y) {
		for(Tile tile : tileList) {
			if(tile.getBounding().contains(newTile.getBounding())) {
				return true;
			}
		}
		return false;
	}
	
	private void pushSelectedItem() {
		this.ui.setSelectedItem(0);
	}
	
	protected void setSelectedItem(int i) {
		this.selectedItem = i + 1;
	}
	
	protected void setGrid() {
		if(gridLayer.isVisible()) {
			gridLayer.setVisible(false);
			mouseMode = 0;
		} else {
			gridLayer.setVisible(true);
			mouseMode = 1;
		}
	}
	
	private Vector3 worldToIso(Vector3 point, int tileWidth, int tileHeight) {
	    camera.unproject(point);
	    point.x /= tileWidth;
	    point.y = (point.y - tileHeight / 2) / tileHeight + point.x;
	    point.x -= point.y - point.x;
	    return point;
	}
		
	@Override
	public void dispose() {
		super.dispose();
		tiledMapRenderer.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void resume() {
		super.resume();
	}
	
	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		lastPoint = worldToIso(new Vector3(x,y,0),(int)tilePixelWidth,(int)tilePixelHeight);
		if(lastPoint.x >= 0 && lastPoint.y >= 0) checkTouch();
		System.out.println("TOUCHED: X=" +  (int) lastPoint.x + " Y=" + (int) lastPoint.y + " Z=" + (int) lastPoint.z);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		lastPoint = new Vector3(-1,-1,-1);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		float x = Gdx.input.getDeltaX();
		float y = Gdx.input.getDeltaY();
		camera.translate(-x*10,y*10);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}

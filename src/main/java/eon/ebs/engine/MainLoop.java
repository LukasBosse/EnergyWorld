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
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectMap;
import eon.ebs.entities.dao.Cities.*;
import eon.ebs.entities.dao.Plants.*;
import eon.ebs.entities.dao.Player.Player;
import eon.ebs.entities.dto.Tile;

import java.util.Iterator;
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
	private final String PRIMARY_ELEMENTS_DETECTION = "Primaryelements_Detection";
	private final String PRIMARY_ELEMENTS = "Primaryelements";
	private final String GROUND_LAYER = "Bodentexturen";
	private final String GRID_LAYER = "AvailableGrid";
	private float tilePixelWidth;
	private float tilePixelHeight;

	private TiledMap tiledMap;
	private TiledMapTileLayer groundLayer;
	private TiledMapTileLayer gridLayer;
	private MapLayer primaerLayer_Detection;
	private TiledMapTileLayer primaerLayer;
	private ObjectMap<TiledMapTile, Boolean> objectMap;
	private MapObjects mObjects = new MapObjects();
	private MapObject mObj;
	//Player
	private Player player;
	//Cities
	private List<City> cityList = new LinkedList<>();
	//Plants
	private List<Plant> plantList = new LinkedList<>();
	//Picking
	private Vector3 lastPoint = new Vector3(-1,-1,-1);
	private int selectedItem = 0;
	private List<Tile> tileList = new LinkedList<>();
	
	public MainLoop(AndroidLauncher ui, Player player) { this.ui = ui; this.player = player; }

	@Override
	public void create() {
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        loadMap();

		tiledMap = am.get(MAPPATH);
		tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap);

		objectMap = new ObjectMap<>();

		groundLayer = (TiledMapTileLayer) tiledMap.getLayers().get(GROUND_LAYER);
		primaerLayer = (TiledMapTileLayer) tiledMap.getLayers().get(PRIMARY_ELEMENTS);
		primaerLayer_Detection =  tiledMap.getLayers().get(PRIMARY_ELEMENTS_DETECTION);
		gridLayer = (TiledMapTileLayer) tiledMap.getLayers().get(GRID_LAYER);
		mObjects = primaerLayer_Detection.getObjects();
		tilePixelWidth = groundLayer.getTileWidth();
		tilePixelHeight = groundLayer.getTileHeight();
		Vector3 center = new Vector3(groundLayer.getWidth() * groundLayer.getTileWidth() / 2, 0, 0);

		cityDetection();

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

	private City generateCity(String cityName, int x, int y, int width, int height) {
		Tile tile = new Tile(x,y,width, height);
		switch (cityName) {
			case "Bremen": {
				return new Bremen(tile, null);
			}
			case "Hamburg": {
				return new Hamburg(tile, null);
			}
			case "Hannover": {
				return new Hannover(tile, null);
			}
			case "Rostock": {
				return new Rostock(tile, null);
			}
			default: {
				return null;
			}
		}
	}

	private void loadMap() {
		am = new AssetManager();
		am.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		am.load(MAPPATH, TiledMap.class);
		while(!am.update()) {
			ui.updateLoader((int) (am.getProgress() * 100));
		}
		am.finishLoading();
	}

	private void cityDetection() {
		for(Iterator<MapObject> mObjs = mObjects.iterator(); mObjs.hasNext();) {
			mObj = mObjs.next();
			RectangleMapObject object = (RectangleMapObject) mObj;
			cityList.add(generateCity(mObj.getName(), (int) object.getRectangle().getX(), (int) object.getRectangle().getY(), (int) object.getRectangle().getWidth(), (int) object.getRectangle().getHeight()));
		}
	}

	private void checkTouch() {
		if(lastPoint != new Vector3(-1,-1,-1) && mouseMode == 1 && selectedItem != 0) {
			int x = (int) lastPoint.x;
			int y = (int) lastPoint.y;
			if(gridLayer.getCell(x, y) != null) {
				Plant newTile = getPlant(selectedItem, x, y);
				if(!checkIntersection(newTile)) {
					TiledMapTileLayer.Cell cell;
					if((cell = primaerLayer.getCell(x, y)) != null){
						cell.setTile(tiledMap.getTileSets().getTile(newTile.getTileID_Construction()));
						newTile.resetGridAt(gridLayer, tiledMap.getTileSets().getTile(21), newTile.getMinX(), newTile.getMaxX(), newTile.getMinY(),
								newTile.getMaxY());
						tileList.add(newTile);
					} else {
						System.err.println("ERROR: Primary cell not found!");
					}
				}
				selectedItem = 0;
				pushSelectedItem();
			} else {
				System.err.println("ERROR: Grid cell not found!");
			}
		}
	}

	private Plant getPlant(int selectedItem, int x, int y) {
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
	
	private boolean checkIntersection(Tile newTile) {
		for(Tile tile : tileList) {
			if(tile.getBounding().contains(newTile.getBounding())) {
				return true;
			}
		}
		return false;
	}

	private int calcOutcome() {
		int outcome = 0;
		for(Plant p : plantList) {
			outcome += p.calcCosts();
		}
		return outcome;
	}

	private int calcIncome() {
		int income = 0;
		for(City c : cityList) {
			income += c.calcIncome();
		}
		return income;
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

	private void pushSelectedItem() { this.ui.setSelectedItem(0); }
	
	protected void setSelectedItem(int i) { this.selectedItem = i + 1; }
	
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

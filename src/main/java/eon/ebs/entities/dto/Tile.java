package eon.ebs.entities.dto;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public class Tile {

	private int x;
	private int y;
	private int width;
	private int height;
	private Rectangle bounding;

	public Tile(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bounding = new Rectangle(x,y,width,height);
	}

	public void resetGridAt(TiledMapTileLayer gridLayer, TiledMapTile tile, int minX, int maxX, int minY, int maxY) {
		for(int i = y + minX; i < y + maxX ; i++) {
			for (int j = x - minY; j < x + maxY; j++) {
				gridLayer.getCell(j,i).setTile(tile);
			}
		}
	}

	public Rectangle getBounding() {
		return bounding;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
}

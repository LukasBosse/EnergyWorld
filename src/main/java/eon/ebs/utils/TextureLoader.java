package eon.ebs.utils;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

import com.badlogic.gdx.graphics.Texture;

public class TextureLoader {

	private HashMap<String, Texture> textureList = new HashMap<String, Texture>();
	
	public TextureLoader() {
		textureList.put("groundTexture0", new Texture("ground.jpg"));
		textureList.put("groundTexture1", new Texture("groundTexture1.png"));
		textureList.put("groundTexture2", new Texture("ground2.jpg"));
		textureList.put("groundTexture3", new Texture("groundTexture3.png"));
		textureList.put("emptyCell", new Texture("emptyCell.png"));
		textureList.put("emptyCellGrid", new Texture("emptyCellGrid.png"));
		textureList.put("groundSprite1", new Texture("powerplant.png"));
		textureList.put("rightWall", new Texture("rightWall.png"));
	}
	
	public HashMap<String,Texture> getTextures() {
		return textureList;
	}
	
	public Texture getTextureByKey(String key) {
		return textureList.get(key);
	}
	
	public void addTexture(String index, Texture texture) {
		textureList.put(index, texture);
	}
	
	public void removeTextureByObj(Texture texture) {
		textureList.remove(texture);
	}
	
	public void removeTextureByKey(String key) {
		textureList.remove(key);
	}
	
	public void disposeAll() {
		for(Entry<String, Texture> entry : textureList.entrySet()) {
			entry.getValue().dispose();
		}
	}
	
	public String getKeyByValue(Texture texture) {
		  for (Entry<String, Texture> entry : textureList.entrySet()) {
		        if (Objects.equals(texture, entry.getValue())) {
		            return entry.getKey().toString();
		        }
		    }
		  return null;
	}
	
}

package net.obviam.starassault.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	
	private Texture texture;
	private String texturePath;
	private Sprite sprite;
	private float WIDTH = Gdx.graphics.getWidth();
	private float HEIGHT = Gdx.graphics.getHeight();
	
	public Background(String texturePath){
		this.texturePath = texturePath;
		texture = new Texture(texturePath);
		sprite = new Sprite(texture);
		setSize(getWidth(), getHeight());
	}
	
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
	
	public void setSize(float width, float height){
		sprite.setSize(width, height);
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return WIDTH;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		WIDTH = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return HEIGHT;
	}

	/**
	 * @param hEIGHT the height to set
	 */
	public void setHeight(float height) {
		HEIGHT = height;
	}

}

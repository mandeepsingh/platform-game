package net.obviam.starassault.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DecorationBlock {
	
	public static final float SIZE = 1f;
	
	public Vector2 	position = new Vector2();
	public Rectangle 	bounds = new Rectangle();
	public TextureRegion region;
		
	public DecorationBlock(Vector2 pos, TextureRegion region) {
		
		this.position = pos;
		this.region = region;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public void draw(SpriteBatch batch, float ppux, float ppuy){
		batch.draw(region, getPosition().x * ppux, getPosition().y * ppuy, Block.SIZE * ppux, Block.SIZE * ppuy);
	}

}

package net.obviam.starassault.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface Enemy {
	
	public void update(float delta);
	public void move();
	public void moveLeft();
	public void moveRight();
	public void draw(SpriteBatch batch, float ppuX, float ppuY);
	
	public void checkCollisionWithBlocks(float delta);
	public Rectangle getBounds();
	

}

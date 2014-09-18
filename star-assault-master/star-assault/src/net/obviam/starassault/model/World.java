package net.obviam.starassault.model;

import java.util.ArrayList;
import java.util.List;
import net.obviam.starassault.controller.BobController;
import net.obviam.starassault.controller.PlayerControls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class World {

	/** The blocks making up the world **/
	private Array<Block> blocks = new Array<Block>();
	/** Our player controlled hero **/
	private Bob bob;
	
	private Level level;
	
	private BobController controller;
	
	private Enemy enemy;
	
	private PlayerControls playerControls;
	private Vector3 touchPoint;
	
	Array<Rectangle> collisionRects = new Array<Rectangle>();
	
	public Array<Rectangle> getCollisionRects(){
		return collisionRects;
	}
	
	public Level getLevel(){
		return level;
	}
	
	// Getters -----------
	public Array<Block> getBlocks() {
		return blocks;
	}
	
	public Bob getBob() {
		return bob;
	}
	
	 /** 
	  * Return only the blocks 
	  * that need to be drawn  
	  **/

	public List<Block> getDrawableBlocks(int width, int height) {
		int x = (int) bob.getPosition().x - width;
		int y = (int) bob.getPosition().y - height;
		
		if (x < 0) {
			x = 0;
		}

		if (y < 0) {
			y = 0;
		}
		
		int x2 = x + 2 * width;
		int y2 = y + 2 * height;

		if (x2 > level.getWidth()) {
			x2 = level.getWidth() - 1;
		}

		if (y2 > level.getHeight()) {
			y2 = level.getHeight() - 1;
		}
		
		List<Block> blocks = new ArrayList<Block>();
		Block block;
		for (int col = x; col <= x2; col++) {
			for (int row = y; row <= y2; row++) {
				block = level.getBlocks()[col][row];
				if (block != null) {
					blocks.add(block);
				}
			}
		}
		
		return blocks;
	}
	
	// --------------------

	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		level = new Level(60 ,7);
		bob = new Bob(new Vector2(7, 4));
		enemy = new SimpleEnemy(new Vector2(6, 2), this,true,false,2);
		
	}

	/**
	 * @return the controller
	 */
	public BobController getController() {
		return controller;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(BobController controller) {
		this.controller = controller;
	}

	/**
	 * @return the enemy
	 */
	public Enemy getEnemy() {
		return enemy;
	}

	public void setPlayerControls(PlayerControls playerControls) {
		this.playerControls = playerControls;
		
	}
	
	public PlayerControls getPlayerControls() {
		return this.playerControls;
		
	}
	/**
	 * @return the touchPoint
	 */
	public Vector3 getTouchPoint() {
		return touchPoint;
	}

	/**
	 * @param touchPoint the touchPoint to set
	 */
	public void setTouchPoint(Vector3 touchPoint) {
		this.touchPoint = touchPoint;
	}

}

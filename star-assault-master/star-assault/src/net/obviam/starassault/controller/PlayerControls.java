package net.obviam.starassault.controller;

import net.obviam.starassault.model.Block;
import net.obviam.starassault.model.World;
import net.obviam.starassault.model.Bob.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Logger;


public class PlayerControls {
	
	TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/buttonsui.pack"));
	Logger log = new Logger("DEBUGGING:", Logger.INFO);
	private Rectangle leftButton;
	private Rectangle rightButton;
	private Rectangle jumpButton;
	
	private TextureRegion leftButtonTextureRegion;
	private TextureRegion rightButtonTextureRegion;
	private TextureRegion jumpButtonTextureRegion;
	private Vector3 touchPoint;
	
	float ppuX = 0f;
	float ppuY = 0f;	
	float SIZE = 1f;
	
	private World world;
	private BobController controller;
	
	public PlayerControls(BobController controller){
		this.controller = controller;
		
		setLeftButton(new Rectangle(0, 0, SIZE, SIZE));
		setRightButton(new Rectangle(2, 0, SIZE, SIZE));
		setJumpButton(new Rectangle(9, 0, SIZE, SIZE));
		
		
		leftButtonTextureRegion = atlas.findRegion("leftbutton");
		rightButtonTextureRegion = atlas.findRegion("rightbutton");
		jumpButtonTextureRegion = atlas.findRegion("abutton");
	}
	
	public void drawControls(SpriteBatch spriteBatch, float ppuX, float ppuY){
		/** TODO: remove ppux and ppuy	**/
		spriteBatch.draw(leftButtonTextureRegion, getLeftButton().getX() * ppuX, getLeftButton().getY() * ppuY, SIZE * ppuX , SIZE * ppuY);
		spriteBatch.draw(rightButtonTextureRegion, getRightButton().x * ppuX, getRightButton().y * ppuY, SIZE * ppuX, SIZE * ppuY);
		spriteBatch.draw(jumpButtonTextureRegion, getJumpButton().x * ppuX, getJumpButton().y * ppuY, SIZE * ppuX, SIZE * ppuY);
	}
	
	public void checkForOnScreenInput(OrthographicCamera cam, Vector3 touchPoint){
		this.touchPoint = touchPoint;
		cam.unproject(this.touchPoint);

		controller.leftReleased();
		controller.rightReleased();
		if(!getWorld().getBob().getState().equals(State.JUMPING)){
			controller.jumpReleased();
		}
		//log.setLevel(log.INFO);
		for (int i=0; i<5; i++){
		if (!Gdx.input.isTouched(i)) continue;
		    cam.unproject(getTouchPoint().set(Gdx.input.getX(i), Gdx.input.getY(i), 0)); //used in for loop
		    
		    if (getLeftButton().contains(getTouchPoint().x, getTouchPoint().y)){
		    	controller.rightReleased();
		    	controller.leftPressed();
		    }else if (getRightButton().contains(getTouchPoint().x, getTouchPoint().y)){
		    	controller.leftReleased();
		    	controller.rightPressed();

		    }else if(getJumpButton().contains(getTouchPoint().x, getTouchPoint().y)){
		    	controller.jumpPressed();

		    }
		}

	} //1-888 827 9262
	//Agreement number Mandeep: 022200151
	//Agreement number Abjinder:   022205916
	

	/**
	 * @return the leftButton
	 */
	public Rectangle getLeftButton() {
		return leftButton;
	}

	/**
	 * @param leftButton the leftButton to set
	 */
	public void setLeftButton(Rectangle leftButton) {
		this.leftButton = leftButton;
	}

	/**
	 * @return the rightButton
	 */
	public Rectangle getRightButton() {
		return rightButton;
	}

	/**
	 * @param rightButton the rightButton to set
	 */
	public void setRightButton(Rectangle rightButton) {
		this.rightButton = rightButton;
	}

	/**
	 * @return the jumpButton
	 */
	public Rectangle getJumpButton() {
		return jumpButton;
	}

	/**
	 * @param jumpButton the jumpButton to set
	 */
	public void setJumpButton(Rectangle jumpButton) {
		this.jumpButton = jumpButton;
	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @param world the world to set
	 */
	public void setWorld(World world) {
		this.world = world;
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

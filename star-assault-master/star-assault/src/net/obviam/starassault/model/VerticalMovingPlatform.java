package net.obviam.starassault.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

public class VerticalMovingPlatform extends Block {
	
	Logger log = new Logger("DEBUGGING:", Logger.INFO);
	
	private float size = 1f;
	private Vector2 	position;
	private Rectangle 	bounds;
	private TextureRegion region;
	
	private float ACCELERATION = 1f;
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 2f;
	
	private float displacement = 0;
	private boolean up, down;
	private int distanceToMoveUp = 4;
	private int distanceToMoveDown = -4;
	private float originalYPosition;
	
	public VerticalMovingPlatform(Vector2 position, TextureRegion region){
		super(position, region);
		
		log.setLevel(log.INFO);
		
		this.setPosition(position);
		this.setRegion(region);
		setBounds(new Rectangle());
		getBounds().setX(position.x);
		getBounds().setY(position.y);
		getBounds().width = SIZE; //comes from super class
		getBounds().height = SIZE;
		originalYPosition = position.y;
		this.up = true;
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(getRegion(), getPosition().x, getPosition().y, getSize(), getSize());
		
	}
	
	public void move(){
		
		this.displacement =  bounds.y - originalYPosition;
		log.info("POSITION.Y : " + getPosition().y);
		log.info("Original Y : " + originalYPosition);
		log.info("VELOCITY.x" + getVelocity().x);
		log.info("VELOCITY.y" + getVelocity().y);
		
		log.info("Displacement: " + getDisplacement());
		log.info("DTMU: " + getDistanceToMoveUp());
		log.info("DTMD: " + getDistanceToMoveDown());
		
		if(getDisplacement() >= getDistanceToMoveUp()){			
			setMovingUp(false); //this.up = false;
			setMovingDown(true);//this.down = true;
			log.info("Set to move down");
			
		}else if(getDisplacement() <= getDistanceToMoveDown()){
			setMovingDown(false);//this.down = false;
			setMovingUp(true); //this.up = true;
			log.info("Set to move up");
		}
		
		//adding a return so we don't do an extra check
		if(getMovingUp()){
			moveUp();
			log.info("Moving Up");
			return;
		}
		
		if(getMovingDown()){
			moveDown();
			log.info("Moving Down");
		}
	}
	
	public void setMovingUp(boolean up){
		this.up = up;
	}
	
	public void setMovingDown(boolean down){
		this.down = down;
	}
	
	public boolean getMovingUp(){
		return this.up;
	}
	
	public boolean getMovingDown(){
		return this.down;
	}
		
	public void update(float delta) {
		
		getPosition().add(getVelocity());
		getBounds().x = getPosition().x;
		getBounds().y = getPosition().y;
		getVelocity().scl(1 / delta);
		getAcceleration().scl(delta);
		
		if(up){
			getAcceleration().y = ACCELERATION;
		}else{
			getAcceleration().y = -ACCELERATION;
		}
		
		getVelocity().add(getAcceleration().x, getAcceleration().y);
		getVelocity().y *= DAMP; 
		
		if(getVelocity().y > MAX_VEL){
			getVelocity().y = MAX_VEL;
		}
		
		if(getVelocity().y < -MAX_VEL){
			getVelocity().y = -MAX_VEL;
		}
		
		move();

	}
	
	private void moveUp(){
		getAcceleration().y = ACCELERATION;
	}
	
	private void moveDown(){
		getAcceleration().y = -ACCELERATION;
	}
	
	public float getDisplacement(){
		 return displacement; 
		 
	}
	
	public void setDisplacement(float displacement){
		this.displacement = displacement;
	}
	
	/**
	 * @return the size
	 */
	public float getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(float size) {
		this.size = size;
	}

	/**
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * @param bounds the bounds to set
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	/**
	 * @return the region
	 */
	public TextureRegion getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(TextureRegion region) {
		this.region = region;
	}

	/**
	 * @return the acceleration
	 */
	public Vector2 getAcceleration() {
		return acceleration;
	}

	/**
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * @return the distanceToMoveUp
	 */
	public int getDistanceToMoveUp() {
		return distanceToMoveUp;
	}

	/**
	 * @param distanceToMoveUp the distanceToMoveUp to set
	 */
	public void setDistanceToMoveUp(int distanceToMoveUp) {
		this.distanceToMoveUp = distanceToMoveUp;
	}

	/**
	 * @return the distanceToMoveDown
	 */
	public int getDistanceToMoveDown() {
		return distanceToMoveDown;
	}

	/**
	 * @param distanceToMoveDown the distanceToMoveDown to set
	 */
	public void setDistanceToMoveDown(int distanceToMoveDown) {
		this.distanceToMoveDown = distanceToMoveDown;
	}

	/**
	 * @return the velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

}

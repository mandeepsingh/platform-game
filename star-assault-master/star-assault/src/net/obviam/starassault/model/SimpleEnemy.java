package net.obviam.starassault.model;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;

public class SimpleEnemy implements Enemy {
	Logger log = new Logger("DEBUGGING:", Logger.INFO);
	
	private Texture texture;
	private String texturePath;
	private Sprite sprite;
	private World world;
	
	private TextureRegion enemyRegion;
			
	private static final float ACCELERATION = 20f;
	private static final float GRAVITY = -20f;
	private static final float MAX_VEL = 2f;
	private static final float WIDTH = 10f;
	private static final float DAMP = 0.90f;
	private static final float SIZE = 0.5f;
	
	private Vector2 acceleration = new Vector2();
	private Vector2 position = new Vector2();
		
	private Vector2 velocity = new Vector2();
	private Rectangle bounds = new Rectangle();
	
	float stateTime = 0;
	private boolean grounded;
	
	//check enemy position and displacement
	private float displacement = 0;
	private float originalXPosition;
	
	private boolean left = false, right = false;
	int distanceToMoveLeft = 0;
	int distanceToMoveRight = 0;

	
	//used in collision checking only
	//A pool of objects that can be reused to avoid allocation.
	private Pool<Rectangle> rectPool = new Pool<Rectangle>()
	{
		@Override
		protected Rectangle newObject()
		{
			return new Rectangle();
		}
	}; 
	
	private Array<Block> collidable = new Array<Block>();
	
	public enum State {
		IDLE, WALKING, JUMPING, DYING, WALKING_LEFT, WALKING_RIGHT
	}
	
	State	state = State.IDLE;
	public SimpleEnemy(Vector2 position, World world, boolean left, boolean right, int distanceToMove){
		log.setLevel(log.INFO);
		this.left = left;
		this.right = right;
		this.distanceToMoveLeft = -distanceToMove;
		this.distanceToMoveRight = distanceToMove;
		
		
		originalXPosition = position.x;
		
		this.world = world;
		this.setPosition(position);
		this.getBounds().setX(position.x);
		this.getBounds().setY(position.y);
		this.getBounds().height = SIZE;
		this.getBounds().width = SIZE;
		texturePath = ("images/firefox-right.png");
		texture = new Texture(texturePath);
		sprite = new Sprite(texture);

	}
	@Override
	/**
	 * This method controls the actions
	 * of the enemy
	 */
	public void update(float delta) {
		
		
		getAcceleration().y = GRAVITY;
		getAcceleration().scl(delta);
		getVelocity().add(getAcceleration().x, getAcceleration().y);
		
		checkCollisionWithBlocks(delta);
		
		getVelocity().x *= DAMP;
		
		if(getVelocity().x > MAX_VEL){
			getVelocity().x = MAX_VEL;
		}
		
		if(getVelocity().x < -MAX_VEL){
			getVelocity().x = -MAX_VEL;
		}
		
		move();
		stateTime += delta;
	}

	@Override
	/**
	 * Set the enemy to move 
	 * left or right
	 */
	public void move() {
							
		//log.info("Displacement:DistanceToMoveLeft:DistanceToMoveRight " + getDisplacement() + " : " + distanceToMoveLeft + " : " + distanceToMoveRight);
		
		if(getDisplacement() <= distanceToMoveLeft){			
			left = false;
			right = true;
			
		}else if(getDisplacement() >= distanceToMoveRight){
			left = true;
			right = false;
		}
		
		//adding a return so we don't do an extra check
		if(left){
			moveLeft();
			return;
		}
		
		if(right){
			moveRight();
		}

	
	}

	@Override
	/**
	 * Set the enemy to move left
	 */
	public void moveLeft() {
		getAcceleration().x = -ACCELERATION;
		
		
	}

	@Override
	/**
	 * Set the enemy to move right
	 */
	public void moveRight() {
		getAcceleration().x = ACCELERATION;
	}

	@Override
	/**
	 * draw the enemy sprite
	 */
	public void draw(SpriteBatch batch, float ppuX, float ppuY) {
		batch.draw(sprite, getPosition().x * ppuX, getPosition().y * ppuY, SIZE * ppuX, SIZE * ppuY);
		
	}
	
	//TODO: move the textureRegion
	public void draw(SpriteBatch batch, TextureRegion region, float ppuX, float ppuY) {
		batch.draw(region, getPosition().x * ppuX, getPosition().y * ppuY, SIZE * ppuX, SIZE * ppuY);
		
	}
	
	
	
	public void setPosition(Vector2 position2) {
		this.position = position2;
		this.bounds.setX(position2.x);
		this.bounds.setY(position2.y);
	}
	
	public Vector2 getAcceleration() {
		return acceleration;
	}
	
	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public void checkCollisionWithBlocks(float delta){

		getVelocity().scl(delta);
		
		int startX, endX;
		int startY = (int) getBounds().y;
		int endY = (int) (getBounds().y + getBounds().height);
		if (getVelocity().x < 0) {
			startX = endX = (int) Math.floor(getBounds().x + getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(getBounds().x + getBounds().width + getVelocity().x);
		}
		populateCollidableBlocks(startX, startY, endX, endY);
		
		
		Rectangle enemyRect = rectPool.obtain();
		enemyRect.set(bounds.x, bounds.y, bounds.getWidth(), bounds.getHeight());
		enemyRect.x += getVelocity().x;
		
		for (Block block : collidable) {
			if (block == null) continue;
			if (enemyRect.overlaps(block.getBounds())) {
				getVelocity().x = 0;
				break;
			}
		}
		
		enemyRect.x = getPosition().x;
		startX = (int) getBounds().x;
		endX = (int) (getBounds().x + getBounds().width);
		if (getVelocity().y < 0) {
			startY = endY = (int) Math.floor(getBounds().y + getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(getBounds().y + getBounds().height + getVelocity().y);
		}
		populateCollidableBlocks(startX, startY, endX, endY);
		enemyRect.y += getVelocity().y;
		for (Block block : collidable) {
			if (block == null) continue;
			if (enemyRect.overlaps(block.getBounds())) {
				if (getVelocity().y < 0) {
					grounded = true;
				}
				getVelocity().y = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}
		enemyRect.y = getPosition().y;
		getPosition().add(getVelocity());
		getBounds().x = getPosition().x;
		getBounds().y = getPosition().y;
		getVelocity().scl(1 / delta); //replace mul(float) by scl when updating libgdx
		
		
		//log.info("ORIGNALX: " + originalXPosition);
		//log.info("ENEMY X" + enemyRect.x);		
		
		setDisplacement(enemyRect.x - originalXPosition);		
		setDisplacement(displacement);
	}
	
	@Override
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
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
		collidable.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
					collidable.add(world.getLevel().get(x, y));
				}
			}
		}
	}
	/**
	 * @return the displacement
	 */
	public float getDisplacement() {
		return displacement;
	}
	/**
	 * @param displacement the displacement to set
	 */
	public void setDisplacement(float displacement) {
		this.displacement = displacement;
	}
	
	
}

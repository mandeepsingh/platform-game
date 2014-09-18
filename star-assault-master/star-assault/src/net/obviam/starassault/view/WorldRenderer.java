package net.obviam.starassault.view;

import net.obviam.starassault.controller.PlayerControls;
import net.obviam.starassault.model.Background;
import net.obviam.starassault.model.Block;
import net.obviam.starassault.model.Bob;
import net.obviam.starassault.model.Bob.State;
import net.obviam.starassault.model.DecorationBlock;
import net.obviam.starassault.model.Enemy;
import net.obviam.starassault.model.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class WorldRenderer {

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	private static final float RUNNING_FRAME_DURATION = 0.06f;
	
	private World world;
	private OrthographicCamera cam;
	private OrthographicCamera guicam;
	
	private TextureRegion bobJumpLeft;
	private TextureRegion bobFallLeft;
	private TextureRegion bobJumpRight;
	private TextureRegion bobFallRight;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	/** Textures **/
	private TextureRegion bobIdleLeft;
	private TextureRegion bobIdleRight;
	private TextureRegion blockTexture;
	private TextureRegion bobFrame;
	
	private TextureRegion tundraMid;
	
	
	/** Animations **/
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	
	private SpriteBatch spriteBatch;
	private SpriteBatch guiBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	
	private Background background;
	
	private Enemy enemy;
	private Bob bob;
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		/**
		 * TODO: remove the PPUX and PPUY values
		 */
		ppuX = 1f;
		ppuY = 1f;
	}
	
	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.bob = world.getBob();
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.guicam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		
		cam.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.guicam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
				
		this.cam.update();
		this.guicam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		guiBatch = new SpriteBatch();
		loadTextures();
		
		
	}
	
	private void moveCamera(float x, float y, int z){
		cam.position.set(x, y, z);
		cam.update();
	}
	private void loadTextures() {
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.pack"));
		
		bobIdleLeft = atlas.findRegion("bob-01");
		
		bobIdleRight = new TextureRegion(bobIdleLeft);
		bobIdleRight.flip(true, false);
		
		blockTexture = atlas.findRegion("block");
		
		bobJumpLeft = atlas.findRegion("bob-up");
        
		bobJumpRight = new TextureRegion(bobJumpLeft);
        bobJumpRight.flip(true, false);
        
        bobFallLeft = atlas.findRegion("bob-down");
        
        bobFallRight = new TextureRegion(bobFallLeft);
        bobFallRight.flip(true, false);
		
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			walkLeftFrames[i] = atlas.findRegion("bob-0" + (i + 2));
		}
		
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[5];

		for (int i = 0; i < 5; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
		
		background = new Background("images/bg_grasslands.png");
		
		TextureAtlas tundraAtlas = new TextureAtlas(Gdx.files.internal("images/textures/tundra.pack"));
		tundraMid = tundraAtlas.findRegion("tundraMid");
	}
	
	
	public void render() {
		spriteBatch.setProjectionMatrix(cam.combined);
		guiBatch.setProjectionMatrix(guicam.combined);
		cam.update();
		guicam.update();
		spriteBatch.begin();
			background.draw(spriteBatch);
			drawBlocks();
			drawDecorationBlocks();
			drawBob();
			drawEnemies();	
		spriteBatch.end();
		moveCamera(bob.getPosition().x, CAMERA_HEIGHT / 2f, 0);
		
		guiBatch.begin();
			world.getPlayerControls().drawControls(guiBatch, ppuX, ppuY);
		guiBatch.end();
		
		world.getPlayerControls().checkForOnScreenInput(guicam, world.getTouchPoint());
		if (debug)
			drawDebug();
	}
	
	private void drawCollisionBlocks() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(1, 1, 1, 1));
		for (Rectangle rect : world.getCollisionRects()) {
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		debugRenderer.end();
	}

	/**
	 * Draw the blocks in the level
	 */
	private void drawBlocks() {
		
		Block block = null;
		for(int i = 0; i < world.getLevel().getWidth(); i++){
			for(int j = 0; j < world.getLevel().getHeight(); j++){
			
				block = world.getLevel().getBlocks()[i][j];
				
				//TODO: you don't need this check?
				if(block != null){
					block.draw(spriteBatch, ppuX, ppuY);
				}
			}
		}
	}
	
	private void drawDecorationBlocks() {
		
		DecorationBlock block = null;
		for(int i = 0; i < world.getLevel().getWidth(); i++){
			for(int j = 0; j < world.getLevel().getHeight(); j++){
			
				block = world.getLevel().getDecorationBlocks()[i][j];
				
				//TODO: you don't need this check?
				if(block != null){
					block.draw(spriteBatch, ppuX, ppuY);
				}
			}
		}
	}
	
	
	private void drawBob() {
		Bob bob = world.getBob();
		bobFrame = bob.isFacingLeft() ? bobIdleLeft : bobIdleRight;
		if(bob.getState().equals(State.WALKING)) {
			bobFrame = bob.isFacingLeft() ? walkLeftAnimation.getKeyFrame(bob.getStateTime(), true) : walkRightAnimation.getKeyFrame(bob.getStateTime(), true);
		}else if(bob.getState().equals(State.JUMPING)) {
			  if (bob.getVelocity().y > 0) {
                  bobFrame = bob.isFacingLeft() ? bobJumpLeft : bobJumpRight;
              } else {
                  bobFrame = bob.isFacingLeft() ? bobFallLeft : bobFallRight;
			  }
		}
		spriteBatch.draw(bobFrame, bob.getPosition().x * ppuX, bob.getPosition().y * ppuY, Bob.SIZE * ppuX, Bob.SIZE * ppuY);
	}

	private void drawDebug() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		for (Block block : world.getDrawableBlocks((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
			Rectangle rect = block.getBounds();
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		// render Bob
		Bob bob = world.getBob();
		Rectangle rect = bob.getBounds();
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		Rectangle r = world.getPlayerControls().getLeftButton();
		Rectangle r2 = world.getPlayerControls().getRightButton();
		Rectangle r3 = world.getPlayerControls().getJumpButton();
		debugRenderer.rect(r.x, r.y, r.width, r.height);
		debugRenderer.rect(r2.x, r2.y, r2.width, r2.height);
		debugRenderer.rect(r3.x, r3.y, r3.width, r3.height);
		Rectangle enemyRec = enemy.getBounds();
		debugRenderer.setColor(new Color(1, 1, 0, 1));
		debugRenderer.rect(enemyRec.x, enemyRec.y, enemyRec.width, enemyRec.height);
		debugRenderer.end();
	}
	
	private void drawEnemies(){
		enemy = world.getEnemy();
		enemy.draw(spriteBatch, ppuX, ppuY);
	}
	
}

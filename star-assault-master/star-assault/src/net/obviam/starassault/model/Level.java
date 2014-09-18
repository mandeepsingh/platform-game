package net.obviam.starassault.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Level {

	private int width;
	private int height;
	
	private Block[][] blocks;
	private DecorationBlock[][] decorationBlocks;
	private Block[][] movingBlocks; 
	
	private TextureAtlas textureAtlas;
	private TextureRegion tundraLeftRegion;
	private TextureRegion tundraRightRegion;
	private TextureRegion tundraMidRegion;
	
	
	private TextureRegion tundraPineSapling;
	private TextureRegion tundraRock;
	private TextureRegion tundraPlant;
	private TextureRegion tundraBridge;
	private TextureRegion tundraDeadTree;
	private TextureRegion tundraRockAlt;
	private TextureRegion tundraCenter;
	private TextureRegion boxAlt;

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

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}
	
	/**
	 * Create a new level based on
	 * the width and height
	 * @param width: the width of the world
	 * @param height: the height of the world
	 */
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		textureAtlas = new TextureAtlas(Gdx.files.internal("images/textures/tundra-level.pack"));
		initializeTextureRegions();
		loadDemoLevel();
	}

	public Block get(int x, int y) {
		return blocks[x][y];
	}

	/**
	 * TODO: The Level class should have a TextureAtlas and pass the
	 * specific texture to the Block class. This way a specific class
	 * is not required for each different block. 
	 * i.e., TundraLeftBlock, TundraRightBlock
	 * 
	 **/
	
	private void loadDemoLevel() {
			
		blocks = new Block[width][height];
		
		/**TODO: We can combine this array with blocks array
		*        by having DecorationBlock extend Block. However,
		*        we will need Block class to implement its own collisionDetection.
		**/
		decorationBlocks = new DecorationBlock[width][height];
		setMovingBlocks(new VerticalMovingPlatform[1][1]);
		
		/**
		 * TODO: What is this for loop for? You shouldn't
		 * have to clear the 2d array.
		 */
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				blocks[col][row] = null;
			}
		}

		//draw a line across the bottom
		for (int col = 0; col < 10; col++) {
			blocks[col][0] = new Block(new Vector2(col, 0), tundraMidRegion);
		}

		blocks[5][2] = new Block(new Vector2(5, 2), tundraLeftRegion);
		blocks[6][2] = new Block(new Vector2(6, 2), tundraMidRegion);
		blocks[7][2] = new Block(new Vector2(7, 2), tundraRightRegion);
		
		decorationBlocks[1][1] = new DecorationBlock(new Vector2(1,1), tundraPlant);
		decorationBlocks[8][1] = new DecorationBlock(new Vector2(8,1), tundraRock);
		decorationBlocks[9][1] = new DecorationBlock(new Vector2(9,1), tundraPineSapling);
		
		//add the moving platform to a separate array so we can call the update method
		blocks[10][2] = new VerticalMovingPlatform(new Vector2(10,2), tundraMidRegion);
		movingBlocks[0][0] = blocks[10][2];
		
		blocks[10][3] = new Block(new Vector2(10, 3), tundraBridge);
		
		blocks[13][4] = new Block(new Vector2(13, 4), tundraLeftRegion);
		blocks[14][4] = new Block(new Vector2(14, 4), tundraMidRegion);
		blocks[15][4] = new Block(new Vector2(15, 4), tundraRightRegion);
		
		decorationBlocks[14][5] = new DecorationBlock(new Vector2(14,5), tundraRockAlt );
		decorationBlocks[15][5] = new DecorationBlock(new Vector2(15,5), tundraDeadTree);
		
		//20 to 31
		for(int i = 20; i <32; i++){
			blocks[i][2] = new Block(new Vector2(i, 2), tundraMidRegion);
			blocks[i][1] = new Block(new Vector2(i, 1), tundraCenter);
			blocks[i][0] = new Block(new Vector2(i, 0), tundraCenter);
		}
		
		//wooden box crates
		blocks[25][3] = new Block(new Vector2(25, 3), boxAlt);
		blocks[26][3] = new Block(new Vector2(26, 3), boxAlt);
		blocks[27][3] = new Block(new Vector2(27, 3), boxAlt);
		blocks[26][4] = new Block(new Vector2(26, 4), boxAlt);
		
	}
	
	public void initializeTextureRegions(){
		tundraLeftRegion = textureAtlas.findRegion("tundraLeft");
		tundraRightRegion = textureAtlas.findRegion("tundraRight");
		tundraMidRegion = textureAtlas.findRegion("tundraMid");
		tundraPineSapling = textureAtlas.findRegion("pineSapling");
		tundraRock = textureAtlas.findRegion("rock");
		tundraPlant = textureAtlas.findRegion("plant");
		tundraBridge = textureAtlas.findRegion("bridge");
		
		tundraDeadTree = textureAtlas.findRegion("deadTree");
		tundraRockAlt = textureAtlas.findRegion("rockAlt");
		
		tundraCenter = textureAtlas.findRegion("tundraCenter");
		boxAlt = textureAtlas.findRegion("boxAlt");
	}
	
	/**
	 * @return the decorationBlocks
	 */
	public DecorationBlock[][] getDecorationBlocks() {
		return decorationBlocks;
	}

	/**
	 * @param decorationBlocks the decorationBlocks to set
	 */
	public void setDecorationBlocks(DecorationBlock[][] decorationBlocks) {
		this.decorationBlocks = decorationBlocks;
	}
	
	/**
	 * Returns the deocration block at x, y position in array
	 * @param x: the x position
	 * @param y: the y position
	 * @return DecorationBlock decorationBlock
	 */
	public DecorationBlock getDecorationBlock(int x, int y) {
		return decorationBlocks[x][y];
	}

	/**
	 * @return the movingBlocks
	 */
	public Block[][] getMovingBlocks() {
		return movingBlocks;
	}

	/**
	 * @param movingBlocks the movingBlocks to set
	 */
	public void setMovingBlocks(Block[][] movingBlocks) {
		this.movingBlocks = movingBlocks;
	}
	
}
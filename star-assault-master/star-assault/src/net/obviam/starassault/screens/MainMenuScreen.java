package net.obviam.starassault.screens;

import net.obviam.starassault.listeners.StartButtonListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainMenuScreen implements Screen{
	
	private Texture textureBackground;
	private Sprite spriteBackground;
	private Stage stage;
	private SpriteBatch batch;
	
	private Skin skin;
	private TextureAtlas atlas;
	
	private Table table;
	private TextButton startButton;
	private TextButtonStyle startButtonStyle;
	private BitmapFont font;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
			spriteBackground.draw(batch);
		batch.end();
		
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		
		textureBackground = new Texture("images/screen-backgrounds/mainMenuBackground.png");
        spriteBackground = new Sprite(textureBackground);
        spriteBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        
        
        stage = new Stage();
        
        Gdx.input.setInputProcessor(stage);
        
        table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font = new BitmapFont();
        skin = new Skin();
        
        //crate the pack file using GUI menu items
        atlas = new TextureAtlas(Gdx.files.internal("images/textures/buttonsui.pack"));
        skin.addRegions(atlas);
        startButtonStyle = new TextButtonStyle();
        startButtonStyle.font = font;
        startButtonStyle.up = skin.getDrawable("start");
        //playButtonStyle.down = skin.getDrawable("down-button");
        //playButtonStyle.checked = skin.getDrawable("checked-button");
        startButton = new TextButton("", startButtonStyle);
        startButton.addListener(new StartButtonListener());
        startButton.pad(20f);
		//add a listener for the play button
        
		table.add(startButton);
		stage.addActor(table);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

package net.obviam.starassault.listeners;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import net.obviam.starassault.screens.GameScreen;

public class StartButtonListener extends ClickListener{

	@Override
	public void clicked(InputEvent event, float x, float y){
		((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
	}

}

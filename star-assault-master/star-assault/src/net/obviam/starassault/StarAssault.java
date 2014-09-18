package net.obviam.starassault;

import net.obviam.starassault.screens.SplashScreen;

import com.badlogic.gdx.Game;

public class StarAssault extends Game {

	@Override
	public void create() {
		setScreen(new SplashScreen());
	}

}

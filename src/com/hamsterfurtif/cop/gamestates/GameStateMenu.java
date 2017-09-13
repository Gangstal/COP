package com.hamsterfurtif.cop.gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.display.menu.Menu;

public abstract class GameStateMenu extends BasicGameState{
	public static int fps = 0;
	public static long lastTime = -1;
	public Menu currentMenu;
	public StateBasedGame game;
	public GameContainer container;

	public void countFps() {
		long currentTime = System.currentTimeMillis();
		fps++;
		if (lastTime == -1)
			lastTime = currentTime;
		if (currentTime - lastTime >= 1000) {
			System.out.println("fps: " + fps);
			lastTime += 1000;
			fps = 0;
		}
	}
}

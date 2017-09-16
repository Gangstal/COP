package com.hamsterfurtif.cop.gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.COP.Mode;
import com.hamsterfurtif.cop.display.menu.Menu;
import com.hamsterfurtif.cop.display.menu.CharacterEquip;
import com.hamsterfurtif.cop.display.menu.WaitingForPlayers;
import com.hamsterfurtif.cop.entities.EntityCharacter;

public class GSCharacterEquip extends GameStateMenu{

	public static final int ID = 1;

	public EntityCharacter currentCharacter;
	public GameContainer container;
	public Menu mainMenu;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game=game;
		currentMenu= mainMenu;
		this.container = container;
	}

	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g) throws SlickException {
		g.drawImage(COP.background, 0, 0);
		if(mainMenu != null)
			mainMenu.render(g);
		if(currentMenu != null)
			currentMenu.render(g);
		countFps();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (COP.mode != Mode.SINGLEPLAYER)
			COP.updateRemote();
		if(mainMenu != null)
			mainMenu.update();
	}

	@Override
	public int getID() {
		return ID;
	}

	public void nextCharacter() throws SlickException {
		if (COP.mode == Mode.SINGLEPLAYER) {
			currentCharacter.reset();

			int nextPlayerId = currentCharacter.player.id;
			int nextCharacterId = currentCharacter.id;

			nextCharacterId++;
			if (nextCharacterId == Game.charactersCount) {
				nextCharacterId = 0;
				nextPlayerId++;
				if (nextPlayerId == Game.playersCount) {
					COP.instance.enterState(2);
					return;
				}
			}

			currentCharacter = Game.players[nextPlayerId].characters[nextCharacterId];
			mainMenu = new CharacterEquip(container, this, currentCharacter);
		} else {
			COP.sendCharacterToAll(currentCharacter);
			if (COP.mode == Mode.SERVER) {
				currentCharacter.reset();
				currentCharacter.configured = true;
			}
			if (currentCharacter.id + 1 < Game.charactersCount) {
				currentCharacter = COP.self.characters[currentCharacter.id + 1];
				mainMenu = new CharacterEquip(container, this, currentCharacter);
			} else {
				if (COP.mode == Mode.SERVER) {
					COP.playersReady++;
					if (COP.playersReady == Game.playersCount) {
						COP.instance.enterState(2);
						return;
					}
				}
				mainMenu = new WaitingForPlayers(container, this);
			}
		}
		currentMenu = null;
	}

}

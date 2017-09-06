package com.hamsterfurtif.cop.gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.COP.Mode;
import com.hamsterfurtif.cop.display.menu.Menu;
import com.hamsterfurtif.cop.display.menu.PlayerEquip;
import com.hamsterfurtif.cop.display.menu.WaitingForPlayers;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.packets.PacketCharacterReady;

public class GSPlayerEquip extends GameStateMenu{

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
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(COP.background, 0, 0);
		if(mainMenu != null)
			mainMenu.render(g);
		if(currentMenu != null)
			currentMenu.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		COP.updatePackets();
		if(mainMenu != null)
			mainMenu.update();
	}

	@Override
	public int getID() {
		return ID;
	}

	public void nextCharacter() throws SlickException {
		COP.sendPacket(new PacketCharacterReady(currentCharacter.player.id, currentCharacter.id, EntityCharacter.skins.indexOf(currentCharacter.skin), currentCharacter.getWeapon(WeaponType.PRIMARY), currentCharacter.getWeapon(WeaponType.SECONDARY)));
		if (COP.mode == Mode.SERVER)
			currentCharacter.configured = true;
		if (currentCharacter.id + 1 < Game.charactersCount) {
			currentCharacter = COP.self.characters[currentCharacter.id + 1];
			mainMenu = new PlayerEquip(container, this, currentCharacter);
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
		currentMenu = null;
	}

}

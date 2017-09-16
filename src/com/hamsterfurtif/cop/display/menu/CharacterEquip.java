package com.hamsterfurtif.cop.display.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.gamestates.GSCharacterEquip;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;
import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.statics.Weapons;

public class CharacterEquip extends Menu{

	private String princName = "Aléatoire";
	private String secName   = "Aléatoire";
	private Image picture;
	private EntityCharacter character;

	public Button appearance = new Button("Changer son apparence", this, 50, 80, 320, 25){

		@Override
		public void additionalRender(Graphics g){
			if(picture != null){
				g.setColor(Color.lightGray);
				g.fillRect(50, 110, picture.getHeight(), picture.getWidth());
				g.drawImage(picture, 50, 110);
			}
		}
	}.setTextPlacement(TextPlacement.LEFT);

	public Button princ = new Button("Arme principale", this, 50, 160, 160, 25){

		@Override
		public void additionalRender(Graphics g){
			g.setColor(Color.white);
			g.drawString(princName, 50, this.getY()+25);
		}

	}.setTextPlacement(TextPlacement.LEFT);

	public Button sec = new Button("Arme secondaire", this, 50, 240, 160, 25){

		@Override
		public void additionalRender(Graphics g){
			g.setColor(Color.white);
			g.drawString(secName, 50, this.getY()+25);
		}

	}.setTextPlacement(TextPlacement.LEFT);

	private TextInput playername;



	public Button confirmer = new Button("Confirmer", this, width/4, height-60, 140, 40).centered();

	public CharacterEquip(GameContainer container, GameStateMenu state, EntityCharacter character) throws SlickException {
		super(container, "Équipement des joueurs", state);
		playername = new TextInput(this, 50, 360, 160, 25, "");
		this.choices = new ArrayList<Button>(Arrays.asList(appearance, princ,sec, confirmer));
		width= COP.width/4;
		height=COP.height;
		titleX = width/2;
		titleY = 40;
		this.character=character;
		String nameWanted = character.name = COP.testers[(int) (Math.random() * COP.testers.length)];
		int n = 0;
		boolean found;
		do {
			n++;
			character.name = nameWanted + (n > 1 ? " (" + n + ")" : "");
			found = false;
			for (Player player : Game.players) {
				for (EntityCharacter c : player.characters) {
					if (c != character && c.name.equals(character.name)) {
						found = true;
						break;
					}
				}
				if (found)
					break;
			}
		} while (found);
		playername.setText(character.name);

		if(character.inventory.primary != null)
			princName = character.inventory.primary.name;
		if(character.inventory.secondary != null)
			secName = character.inventory.secondary.name;
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(choices.contains(source)){
			if(source==princ)
				try {
					state.currentMenu = new PickWeapon(container, state, WeaponType.PRIMARY, character);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			else if(source==sec)
				try {
					state.currentMenu = new PickWeapon(container, state, WeaponType.SECONDARY, character);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			else if(source==appearance){
				try {
					state.currentMenu = new PickAppearance(container, state, character);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			else if(source==confirmer){
				if(state instanceof GSCharacterEquip){
					if (character.skin == null)
						character.skin = EntityCharacter.skins.get((int) (Math.random() * EntityCharacter.skins.size()));
					if (character.inventory.primary == null) {
						List<Weapon> weapons = Weapons.getWeaponsByType(WeaponType.PRIMARY);
						character.inventory.primary = weapons.get((int) (Math.random() * weapons.size()));
					}
					if (character.inventory.secondary == null) {
						List<Weapon> weapons = Weapons.getWeaponsByType(WeaponType.SECONDARY);
						character.inventory.secondary = weapons.get((int) (Math.random() * weapons.size()));
					}
					playername.setLocation(playername.getX(), playername.getY() + 20);
					character.name=playername.getText();
					playername.deactivate();
					GSCharacterEquip s = (GSCharacterEquip)state;
					try {
						s.nextCharacter();
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawString(name, titleX+x, titleY+y);
		for(Button button : choices)
			button.render(g);

		playername.render(container, g);

	}

	public void update(){
		if(character.inventory.primary != null && !princName.equals(character.inventory.primary.name))
			princName = character.inventory.primary.name;
		if(character.inventory.secondary != null && !secName.equals(character.inventory.secondary.name))
			secName = character.inventory.secondary.name;
		if(character.skin != null && (picture == null || !picture.equals(character.skin.getScaledCopy(2))))
			picture = character.skin.getScaledCopy(2);
	}

}

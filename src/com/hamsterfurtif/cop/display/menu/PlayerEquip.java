package com.hamsterfurtif.cop.display.menu;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.gamestates.GSPlayerEquip;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;
import com.hamsterfurtif.cop.inventory.WeaponType;

public class PlayerEquip extends Menu{

	private String princName = "Aucune actuellement";
	private String secName   = "Aucune actuellement";
	private Image picture;
	private EntityCharacter player;

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

	private TextInput playername = new TextInput(this, 50, 360, 160, 25, "");



	public Button confirmer = new Button("Confirmer", this, width/4, height-60, 140, 40).centered();

	public PlayerEquip(GameContainer container, GameStateMenu state, EntityCharacter player) throws SlickException {
		super(container, "Équipement des joueurs", state);
		this.choices = new ArrayList<Button>(Arrays.asList(appearance, princ,sec, confirmer));
		width= COP.width/4;
		height=COP.height;
		titleX = width/2;
		titleY = 40;
		this.player=player;
		playername.setText(player.name);

		if(player.inventory.primary != null)
			princName = player.inventory.primary.name;
		if(player.inventory.secondary != null)
			secName = player.inventory.secondary.name;
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(choices.contains(source)){
			if(source==princ)
				try {
					state.currentMenu = new PickWeapon(container, state, WeaponType.PRIMARY, player);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			else if(source==sec)
				try {
					state.currentMenu = new PickWeapon(container, state, WeaponType.SECONDARY, player);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			else if(source==appearance){
				try {
					state.currentMenu = new PickAppearance(container, state, player);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			else if(source==confirmer){
				if(state instanceof GSPlayerEquip){
					player.name=playername.getText();
					player.reset();
					playername.deactivate();
					GSPlayerEquip s = (GSPlayerEquip)state;
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
		if(player.inventory.primary != null && princName != player.inventory.primary.name)
			princName = player.inventory.primary.name;
		if(player.inventory.secondary != null && secName != player.inventory.secondary.name)
			secName = player.inventory.secondary.name;
		if(player.skin != null && (picture == null || !picture.equals(player.skin.getScaledCopy(2))))
			picture = player.skin.getScaledCopy(2);
	}

}

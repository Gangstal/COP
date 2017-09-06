package com.hamsterfurtif.cop.display.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;
import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.statics.Weapons;

public class PickWeapon extends Menu{

	WeaponType type;


	public PickWeapon(GameContainer container, GameStateMenu state, WeaponType type, EntityCharacter player) throws SlickException {
		super(container, "Choisir une arme", state);
		this.type=type;
		width= COP.width/2;
		height=COP.height;
		titleX = width/2-120;
		titleY = 40;
		x=width;
		y=0;

		int offset=0;
		for(Weapon w : Weapons.getWeaponsByType(type)){
			choices.add(new Button(w.name+"(D:"+w.damage+"/R:"+w.range+"/A:"+w.ammo+")", this, 50, 90+offset, 200, 64){
				@Override
				public Object trigger(Object arg){
					player.inventory.setWeapon(type, w);
					return null;
				}

				@Override
				public void additionalRender(Graphics g){
					g.drawImage(w.skin.getScaledCopy(128, 64), this.getX()+this.getWidth()+10, this.getY());
				}
			}.setTextPlacement(TextPlacement.LEFT));
			offset+=94;
		}
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(choices.contains(source)){
			Button s = (Button)source;
			s.trigger(null);
		}
	}

}

package com.hamsterfurtif.cop.inventory;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

import com.hamsterfurtif.cop.ITrigger;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.inventory.slot.Slot;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.map.Path;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.statics.Weapons;


public class Weapon implements ITrigger{
	public final String id;
	public String name;
	public int range, damage, ammo;
	public Image skin;
	public ArrayList<Sound> sounds = new ArrayList<Sound>();
	public boolean hasSound = true;
	
	public boolean hasSpecialShoot = false;

	public Weapon(String id, String name, int range, int damage, int maxAmmo, WeaponType type){
		this.id = id;
		this.name = name;
		this.range = range;
		this.damage = damage;
		this.ammo = maxAmmo;
		this.skin = TextureLoader.loadTexture("sprites\\weapons\\"+name+".png");

		switch(type){
		case PRIMARY:
			Weapons.primary.add(this);
			break;
		case SECONDARY:
			Weapons.secondary.add(this);
			break;
		}
		Weapons.weaponsByID.put(id, this);
	}

	public boolean inRange(MapPos pos1, MapPos pos2){
		double d = pos1.distance(pos2);
		return d<=range;
	}

	public void playSound() {
		if(!sounds.isEmpty()){
			Random r = new Random(1);
			sounds.get(r.nextInt(1)).play();
		}
	}
	
	public Slot getWeaponSlot(){
		return new Slot(this);
	}
	
	public boolean shoot(EntityCharacter character, MapPos pos){
		if(Path.directLOS(character.pos, pos) && this.inRange(character.pos,  pos)){

			this.playSound();
			Random r = new Random(1);

			if(Game.checkForCharacter(pos) != null){
				EntityCharacter target = Game.checkForCharacter(pos);
				if(!target.isShieled || target.orientation == Utils.getOrientationFromPos(character.pos, target.pos)){
					target.health -= this.damage;
					character.turnIsOver = true;
					character.hasShot=true;
					if(target.health<=0){
						Game.kill(target);
						EntityCharacter.deathSounds.get(r.nextInt(1)).play();
					}
					else
						EntityCharacter.hurtSounds.get(0).play();
				}
				return true;
			}
			else if(Game.map.getTile(pos).isDestructible){
				Game.map.destroyTile(pos);
				character.hasShot=true;
				character.turnIsOver = true;
				Tile.destroy.get(r.nextInt(1)).play(0.5f,0.5f);
				return true;
			}
		}
		return false;
	}

	@Override
	public TriggerType getSource() {
		return TriggerType.AIRBORNE;
	}
	
	

}

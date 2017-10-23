package com.hamsterfurtif.cop.inventory;

import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.map.MapPos;

public class WeaponRPG extends Weapon implements IExplosive{
	
	private int explosionRange = 2;  //Rayon, sans prendre en compte la case centrale
	
	public WeaponRPG() {
		super("rpg", "RPG7", 7, 0, 1, WeaponType.SECONDARY);
		this.hasSound = false;
	}

	public boolean inRange(MapPos p1, MapPos p2){
		return((p1.X == p2.X ^ p1.Y == p2.Y) && Math.sqrt(Math.pow(Math.abs(p1.X-p2.X),2) + Math.pow(Math.abs(p1.Y-p2.Y),2))<= range);
	}
	
	public boolean shoot(EntityCharacter character, MapPos pos){
		MapPos offset = new MapPos();
		if(character.pos.X > pos.X)
			offset.set(-1, 0, 0);
		else if(character.pos.X < pos.X)
			offset.set(1, 0, 0);
		else if(character.pos.Y > pos.Y)
			offset.set(0, -1, 0);
		else if(character.pos.Y < pos.Y)
			offset.set(0, 1, 0);
		MapPos p = character.pos.copy();
		for(int i=0 ; i<range ; i++){
			p.add(offset);
			Utils.print("loop");
			if(!Game.map.getTile(p).canShootTrough){
				p.sub(offset);
				explode(p);
				Utils.print("III" + i);
				return true;
			}
			else
				Game.map.getTile(p).trigger(getSource());
		}
		Utils.print(offset.toString());
		explode(p);
		return true;
	}
	
	public void explode(MapPos pos) {
		for(int x=-explosionRange; x<=explosionRange; x++){
			for(int y=-explosionRange; y<=explosionRange; y++){
				MapPos p = new MapPos(pos.X+x, pos.Y+y, 0);
				if(p.X>0 && p.X<Game.map.dimX && p.Y>0 && p.Y<Game.map.dimY){
					Game.map.getTile(p).trigger(TriggerType.EXPLOSION);
					Game.map.getTile(pos).destroy();
					EntityCharacter c;
					if( (c = Game.checkForCharacter(pos)) != null)
						c.health -= (int)(c.pos.distance(pos))+2;
				}
			}
		}
	}

}

package com.hamsterfurtif.cop.map;

import com.hamsterfurtif.cop.Game;

public class Path {
	
	public static boolean directLOS(MapPos pos1, MapPos pos2){
		
		if(pos1.equals(pos2))
			return true;
		
		Map map = Game.map;
		
		if(pos1.Y == pos2.Y){
			print("Same Y");
			for(int x=pos1.X ; x<=pos2.X; x++)
				if(!map.getTile(new MapPos(x,pos1.Y,pos1.Z)).canShootTrough)
					return false;
		}
		
		else if(pos1.X == pos2.X){
			print("Same X");
			for(int y=pos1.Y ; y<=pos2.Y; y++)
				if(!map.getTile(new MapPos(pos1.X,y,pos1.Z)).canShootTrough)
					return false;
		}		
		
		else {
			print("Testing else...");
			float ca = (pos1.Y-pos2.Y)/(pos1.X-pos2.X);
			float cb = 1/ca;
			
			print(""+ca);
			print(""+cb);
			
			MapPos p1 = new MapPos(), p2 = new MapPos();
			
			for(int i = inf(pos1.X,pos2.X) ; i<sup(pos1.X,pos2.X) ; i++){
				
				p1.set(i, Math.round(ca*i+pos1.X), pos1.Z);
				p2.set(i+1, Math.round(ca*i+pos1.X), pos1.Z);
				
				print(p1.toString()+"  "+map.getTile(p1).symbol);
				print(p2.toString()+"  "+map.getTile(p2).symbol);
				
				
				if(!(map.getTile(p1).canShootTrough && map.getTile(p2).canShootTrough))
					return false;
				
			}
			for(int i = inf(pos1.Y,pos2.Y) ; i<sup(pos1.Y,pos2.Y) ; i++){
				p1.set(Math.round(cb*i+pos1.X), i, pos1.Z);
				p2.set(Math.round(cb*i+pos1.X), i+1, pos1.Z);
	
				print(p1.toString()+"  "+map.getTile(p1).symbol);
				print(p2.toString()+"  "+map.getTile(p2).symbol);
				
				
				if(!(map.getTile(p1).canShootTrough && map.getTile(p2).canShootTrough))
					return false;
		
			}
		}
		return true;
	}
	
	private static void print(String s){
		System.out.println(s);
	}
	
	private static int sup(int a, int b){
		return a>b ? a : b ;
	}
	
	private static int inf(int a, int b){
		return a<b ? a : b;
	}

}

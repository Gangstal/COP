package com.hamsterfurtif.cop.map;

import com.hamsterfurtif.cop.Game;

public class Path {
	
	//From http://playtechs.blogspot.fr/2007/03/raytracing-on-grid.html
		public static Boolean directLOS(MapPos pos1, MapPos pos2)
		{
			int x0 = pos1.X, x1 = pos2.X;
			int y0 = pos1.Y, y1 = pos2.Y;
		    int dx = Math.abs(x1 - x0);
		    int dy = Math.abs(y1 - y0);
		    int x = x0;
		    int y = y0;
		    int n = 1 + dx + dy;
		    int x_inc = (x1 > x0) ? 1 : -1;
		    int y_inc = (y1 > y0) ? 1 : -1;
		    int error = dx - dy;
		    dx *= 2;
		    dy *= 2;

		    for (; n > 0; --n)
		    {
		    	MapPos pos = new MapPos(x, y, pos1.Z);
		    	if(!Game.map.getTile(pos).canShootTrough && !pos.equals(pos1) && !pos.equals(pos2))
		    		return false;
		    	
		        if (error > 0)
		        {
		            x += x_inc;
		            error -= dy;
		        }
		        else
		        {
		            y += y_inc;
		            error += dx;
		        }
		    }
		    return true;
		}
	
	/*public static boolean directLOS(MapPos pos1, MapPos pos2){
		
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
			float ca = (float)(pos1.Y-pos2.Y)/(float)(pos1.X-pos2.X);
			float cb = 1/ca;
			
			int maxX =sup(pos1.X, pos2.X);
			int maxY =sup(pos1.Y, pos2.Y);
			int minX =inf(pos1.X, pos2.X);
			int minY =inf(pos1.Y, pos2.Y);
			
			print(""+ca);
			print(""+cb);
			
			print(pos1.toString() + " | " +pos2.toString());
			
			MapPos p1 = new MapPos(), p2 = new MapPos();
			
			for(int i = minX ; i<maxX ; i++){
				
				p1.set(i+minX, Math.round(ca*i+minY), pos1.Z);
				p2.set(i+1+minX, Math.round(ca*i+minY), pos1.Z);
				print(p1.toString()+"  LOL  "+p2.toString());
				
				
				if(!(canShootThrough(p1) && canShootThrough(pos2))){
					print("Denied on "+ p1.toString() + " or " + p2.toString());
					Game.map.setTile(p1, Tiles.crossed);
					Game.map.setTile(p2, Tiles.crossed);
					Engine.displayMap();
					
				}
				else{
					Game.map.setTile(p1, Tiles.horizontal);
					Game.map.setTile(p2, Tiles.horizontal);

				}
				
			}
			for(int i = minY ; i<maxY ; i++){
				p1.set(Math.round(cb*i+minX), i+minY, pos1.Z);
				p2.set(Math.round(cb*i+minX), i+1+minY, pos1.Z);
	
				print(p1.toString()+"  "+map.getTile(p1).symbol);
				print(p2.toString()+"  "+map.getTile(p2).symbol);
				
				
				if(!(canShootThrough(p1) && canShootThrough(pos2))){
					print("Denied on "+ p1.toString() + " or " + p2.toString());
					Game.map.setTile(p1, Tiles.crossed);
					Game.map.setTile(p2, Tiles.crossed);
					Engine.displayMap();
				}
				else{
					if(Game.map.getTile(p1)==Tiles.horizontal)
						Game.map.setTile(p1, Tiles.both);
					else
						Game.map.setTile(p1, Tiles.vertical);
					
					if(Game.map.getTile(p2)==Tiles.horizontal)
						Game.map.setTile(p2, Tiles.both);
					else
						Game.map.setTile(p2, Tiles.vertical);

				}
		
			}
		}
		return true;
	}
	
	private static int sup(int a, int b){
		return a>b ? a : b ;
	}
	
	private static int inf(int a, int b){
		return a<b ? a : b;
	}
	*/
}

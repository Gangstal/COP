package com.hamsterfurtif.cop.map;

import com.hamsterfurtif.cop.gamestates.Game;

public class Path {
	
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
}

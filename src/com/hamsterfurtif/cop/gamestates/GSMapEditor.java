package com.hamsterfurtif.cop.gamestates;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.display.Engine;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.display.menu.MapEditor;
import com.hamsterfurtif.cop.display.poseffects.MapEditorSelect;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.statics.Tiles;

public class GSMapEditor extends GameStateMenu {

	public enum Edit{
		freelook,
		tile,
		line,
		square;
	}

	public static final int ID = 4;
	public static float optimalScale = 2.5f;
	public static float scale = optimalScale;
	public boolean showGrid = false;
	private int mx, my;
	private boolean leftClick = false, rightClick = false;
	public static Edit mode = Edit.freelook;
	public static int mapx = 168;
	public static int mapy=0;
	private int grabbedx, grabbedy;
	public static Tile tile = Tiles.stone;
	private MapPos start, finish;
	private List<MapPos> sel;
	private GameContainer container;

	public static Map map;
	public static String mapname;

	@Override
	public void init(GameContainer container, StateBasedGame state) throws SlickException {
		this.currentMenu = new MapEditor(container, this);
		this.container = container;
		sel = new ArrayList<MapPos>();
	}

	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g) throws SlickException {
		Engine.drawMap(g, scale,mapx, mapy, showGrid, map);
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 168, COP.height);
		g.fillRect(0, 480, COP.width, 120);
		currentMenu.render(g);

	}

	@Override
	public void update(GameContainer container, StateBasedGame state, int delta) throws SlickException {
		
		currentMenu.update();

		Input input = container.getInput();
		mx = input.getMouseX();
		my = input.getMouseY();
		int x=168;
		float c = TextureLoader.textureRes*scale;
		if(MouseIsOverMap(mx, my)){
			MapPos clickMap = getMapPos(mx-x, my);
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
				Engine.removePosEffect(MapEditorSelect.class);
				sel.clear();
				switch(mode){
				default:
					break;

				case freelook:
					if(!leftClick){
						grabbedx=mx-mapx;
						grabbedy=my-mapy;
					}
					else{
						mapx=mx-grabbedx;
						mapy=my-grabbedy;
						if(c*map.dimX<=COP.width-x)
							mapx=(int)(x+COP.width-c*map.dimX)/2;
						if(c*map.dimY<=480)
							mapy=(int)(480-c*map.dimY)/2;
					}
					break;

				case tile:
					if(!map.getTile(clickMap).equals(tile))
						map.setTile(clickMap, tile);
					break;

				case line:
					if (start == null)
						start = new MapPos(clickMap.X, clickMap.Y, 0);
					MapPos p1 = new MapPos(start.X, start.Y, 0), p2 = new MapPos(clickMap.X, clickMap.Y, 0);
					if (p2.Y < p1.Y) {
						MapPos temp = p2;
						p2 = p1;
						p1 = temp;
					}
					int dirX = (p2.X < p1.X ? -1 : 1);
					int dx = Math.abs(p2.X - p1.X), dy = p2.Y - p1.Y;
					int linex, liney;
					int n = 0;
					if (dx >= dy) {
						for (linex = 0, liney = 0; linex <= dx; linex++) {
							sel.add(new MapPos(p1.X + dirX * linex, p1.Y + liney, 0));
							n += dy;
							if (n >= (dx + 1) / 2) {
								n -= dx;
								liney++;
							}
						}
					} else {
						for (linex = 0, liney = 0; liney <= dy; liney++) {
							sel.add(new MapPos(p1.X + dirX * linex, p1.Y + liney, 0));
							n += dx;
							if (n >= (dy + 1) / 2) {
								n -= dy;
								linex++;
							}
						}
					}
					break;
				case square:
					if(!leftClick){
						start = clickMap;
						finish = clickMap;
					}
					if(start != null){
						finish = clickMap;
						for(int px=Utils.smaller(start.X, finish.X); px<=Utils.bigger(start.X, finish.X); px++)
							for(int py=Utils.smaller(start.Y, finish.Y); py<=Utils.bigger(start.Y, finish.Y); py++)
								sel.add(new MapPos(px, py, 0));
					}
				}
				for (MapPos p : sel)
					Engine.addPosEffect(new MapEditorSelect(p, mode));
			}
			else if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !rightClick && !input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				if(mode.ordinal() == Edit.values().length-1)
					mode = Edit.freelook;
				else
					mode = Edit.values()[mode.ordinal()+1];
			}

			else if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !rightClick && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				for (MapPos p : sel)
					map.setTile(p, tile);
			}

			else{
				start = null;
				finish = null;
			}

			if(mode==Edit.tile){
				Engine.removePosEffect(MapEditorSelect.class);
				Engine.addPosEffect(new MapEditorSelect(clickMap, Edit.tile));
			}
		}


		leftClick = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		rightClick = input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
	}

	@Override
	public int getID() {
		return ID;
	}

	private static boolean MouseIsOverMap(int mx, int my){
		int x=168, y=480;
		float c = TextureLoader.textureRes*scale;
		return (mx>x && my<y && mx<COP.width && my>0 && mx>=mapx && my>=mapy && mx<map.dimX*c+mapx && my<map.dimY*c+mapy);
	}

	private MapPos getMapPos(int xpos, int ypos){
		xpos-=mapx-168;
		ypos-=mapy;
		int X =(int)((xpos-xpos%(scale*16))/(scale*16));
		int Y =(int)((ypos-ypos%(scale*16))/(scale*16));
		return new MapPos(X, Y, 0);
	}

	@Override
	public void mouseWheelMoved(int offset) {

		int x=168;
		float c = TextureLoader.textureRes*scale;

		float rx = (mx-mapx)/(c*map.dimX);
		float ry = (my-mapy)/(c*map.dimY);

		if(offset>0 && scale < 2.5)
			scale+=0.25;
		else if(offset<0 && scale > optimalScale)
			scale-=0.25;

		c = TextureLoader.textureRes*scale;

		int px = (int) (c*map.dimX*rx);
		int py = (int) (c*map.dimY*ry);

		Input input = this.container.getInput();
		mx = input.getMouseX();
		my = input.getMouseY();

		mapx = mx-px;
		mapy = my-py;

		if(c*map.dimX<=COP.width-x)
			mapx=(int)(x+COP.width-c*map.dimX)/2;
		if(c*map.dimY<=480)
			mapy=(int)(480-c*map.dimY)/2;
	}

}


package com.hamsterfurtif.cop.gamestates;

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
	private GameContainer container;

	public static Map map;
	public static String mapname;

	@Override
	public void init(GameContainer container, StateBasedGame state) throws SlickException {
		this.currentMenu = new MapEditor(container, this);
		this.container = container;
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
		
		
		Input input = container.getInput();
		mx = input.getMouseX();
		my = input.getMouseY();
		int x=168;
		float c = TextureLoader.textureRes*scale;
		if(MouseIsOverMap(mx, my)){
			MapPos clickMap = getMapPos(mx-x, my);
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
				Engine.removePosEffect(MapEditorSelect.class);
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
					if(!leftClick){
						Engine.addPosEffect(new MapEditorSelect(clickMap, Edit.line));
						start = clickMap;
					}
					else if(start != null){
						 if(finish == null && !clickMap.equals(start) && (clickMap.X == start.X || clickMap.Y == start.Y)){
							Engine.addPosEffect(new MapEditorSelect(clickMap, Edit.line));
							finish = clickMap;
						}
						else if(finish != null && finish.equals(start)){
							finish = clickMap;
						}
						else if(finish != null){
							if(finish.X==start.X){
								finish = new MapPos(finish.X, clickMap.Y, 0);
								for(int py = Utils.smaller(finish.Y, start.Y); py <= Utils.bigger(finish.Y, start.Y); py++)
									Engine.addPosEffect(new MapEditorSelect(new MapPos(finish.X, py, 0), Edit.line));
							}
							else if(finish.Y==start.Y){
								finish = new MapPos(clickMap.X, finish.Y, 0);
								for(int px = Utils.smaller(finish.X, start.X); px <= Utils.bigger(finish.X, start.X); px++)
									Engine.addPosEffect(new MapEditorSelect(new MapPos(px, finish.Y, 0), Edit.line));
							}
						}
					}
					break;
				case square:
					if(!leftClick){
						Engine.addPosEffect(new MapEditorSelect(clickMap, Edit.line));
						start = clickMap;
						finish = clickMap;
					}
					else if(start != null){
						finish = clickMap;
						for(int px=Utils.smaller(start.X, finish.X); px<=Utils.bigger(start.X, finish.X); px++)
							for(int py=Utils.smaller(start.Y, finish.Y); py<=Utils.bigger(start.Y, finish.Y); py++)
								Engine.addPosEffect(new MapEditorSelect(new MapPos(px, py, 0), Edit.square));
							

					}
					
						
					
				}
				
					
			}
			else if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !rightClick && !input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				if(mode.ordinal() == Edit.values().length-1)
					mode = Edit.freelook;
				else
					mode = Edit.values()[mode.ordinal()+1];
			}
			
			else if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !rightClick && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				switch(mode){
					case square:
						for(int px=Utils.smaller(start.X, finish.X); px<=Utils.bigger(start.X, finish.X); px++)
								for(int py=Utils.smaller(start.Y, finish.Y); py<=Utils.bigger(start.Y, finish.Y); py++)
									map.setTile(new MapPos(px, py, 0), tile);
						start = null;
						finish = null;
						break;
					case line:
						if(finish.X==start.X){
							finish = new MapPos(finish.X, clickMap.Y, 0);
							for(int py = Utils.smaller(finish.Y, start.Y); py <= Utils.bigger(finish.Y, start.Y); py++)
								map.setTile(new MapPos(finish.X, py, 0), tile);
						}
						else if(finish.Y==start.Y){
							finish = new MapPos(clickMap.X, finish.Y, 0);
							for(int px = Utils.smaller(finish.X, start.X); px <= Utils.bigger(finish.X, start.X); px++)
								map.setTile(new MapPos(px, finish.Y, 0), tile);
						}
						start = null;
						finish = null;
						break;
				default:
					break;
				}
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


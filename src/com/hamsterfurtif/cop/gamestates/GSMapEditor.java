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
import com.hamsterfurtif.cop.display.menu.MapEditor;
import com.hamsterfurtif.cop.display.poseffects.MapEditorSelect;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.statics.Tiles;

public class GSMapEditor extends GSMap {
	public static final int LINE_DIV = 4;

	public enum Edit{
		freelook,
		tile,
		line,
		square,
		fill,
	}

	public static final int ID = 4;
	public boolean showGrid = false;
	private int mx, my;
	private boolean leftClick = false, rightClick = false;
	public static Edit mode = Edit.freelook;
	private int grabbedx, grabbedy;
	public static Tile tile = Tiles.stone;
	private MapPos start, finish;
	private List<MapPos> currentDrawing, sel;

	@Override
	public void init(GameContainer container, StateBasedGame state) throws SlickException {
		this.currentMenu = new MapEditor(container, this);
		this.container = container;
		sel = new ArrayList<MapPos>();
		currentDrawing = new ArrayList<MapPos>();
		mapx = 168;
		mapy = 0;
	}

	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g) throws SlickException {
		g.drawImage(COP.background, 0, 0);
		Engine.drawMap(g, GSMap.scale,mapx, mapy, showGrid, map, false);
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 168, COP.height);
		g.fillRect(0, 480, COP.width, 120);
		currentMenu.render(g);
		countFps();
	}

	@Override
	public void update(GameContainer container, StateBasedGame state, int delta) throws SlickException {
		currentMenu.update();
		updateScale();
		Input input = container.getInput();
		mx = input.getMouseX();
		my = input.getMouseY();
		int x=168;

		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			sel.clear();
			currentDrawing.clear();
		}

		if(isMouseOverMap(mx, my)){
			MapPos clickMap = getMapPos(mx-x, my);
			if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				addAllToSel(currentDrawing);
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
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
						correctMapLoc();
					}
					break;

				case tile:
					if(!map.getTile(clickMap).equals(tile))
						map.setTile(clickMap, tile);
					break;

				case line:
					if (!leftClick && !input.isKeyDown(Input.KEY_LCONTROL) && !input.isKeyDown(Input.KEY_RCONTROL))
						sel.clear();
					currentDrawing.clear();
					if (start == null)
						start = new MapPos(clickMap.X, clickMap.Y, 0);
					MapPos p1 = new MapPos(start.X, start.Y, 0), p2 = new MapPos(clickMap.X, clickMap.Y, 0);
					int dx, dy;
					dx = Math.abs(p2.X - p1.X); dy = Math.abs(p2.Y - p1.Y);
					if (input.isKeyDown(Input.KEY_LSHIFT) || input.isKeyDown(Input.KEY_RSHIFT)) {
						double dirX, dirY;
						int n = ((int) (Math.atan2(p2.Y - p1.Y, p2.X - p1.X) * (float) LINE_DIV / Math.PI + (double) LINE_DIV * 2.0) % (LINE_DIV * 2) + 1) / 2;
						double a = (double) n * 2 * Math.PI / (double) LINE_DIV;
						dirX = Math.cos(a);
						dirY = Math.sin(a);
						if (dirX > -0.000001 && dirX < 0.000001) {
							p2.X = p1.X;
						} else if (dirY > -0.000001 && dirY < 0.000001) {
							p2.Y = p1.Y;
						} else if (dx == 0 || dy == 0) {
							p2.X = p1.X;
							p2.Y = p1.Y;
						} else if ((double) dx / (double) dy > Math.abs(dirX) / Math.abs(dirY)) {
							p2.X = p1.X + (int) Math.floor(0.5 + dirX * dy / Math.abs(dirY));
							p2.Y = p1.Y + (int) Math.floor(0.5 + dirY * dy / Math.abs(dirY));
						} else {
							p2.X = p1.X + (int) Math.floor(0.5 + dirX * dx / Math.abs(dirX));
							p2.Y = p1.Y + (int) Math.floor(0.5 + dirY * dx / Math.abs(dirX));
						}
					}
					dx = Math.abs(p2.X - p1.X); dy = Math.abs(p2.Y - p1.Y);
					if (p2.Y < p1.Y) {
						MapPos temp = p2;
						p2 = p1;
						p1 = temp;
					}
					int dirX = (p2.X < p1.X ? -1 : 1);
					int linex, liney;
					int n;
					if (dx >= dy) {
						n = dx >> 1;
						for (linex = 0, liney = 0; linex <= dx; linex++) {
							currentDrawing.add(new MapPos(p1.X + dirX * linex, p1.Y + liney, 0));
							n += dy;
							if (n >= dx) {
								n -= dx;
								liney++;
							}
						}
					} else {
						n = dy >> 1;
						for (linex = 0, liney = 0; liney <= dy; liney++) {
							currentDrawing.add(new MapPos(p1.X + dirX * linex, p1.Y + liney, 0));
							n += dx;
							if (n >= dy) {
								n -= dy;
								linex++;
							}
						}
					}
					break;
				case square:
					if (!leftClick && !input.isKeyDown(Input.KEY_LCONTROL) && !input.isKeyDown(Input.KEY_RCONTROL))
						sel.clear();
					currentDrawing.clear();
					if(!leftClick){
						start = clickMap;
						finish = clickMap;
					}
					if(start != null){
						finish = clickMap;
						for(int px=Utils.smaller(start.X, finish.X); px<=Utils.bigger(start.X, finish.X); px++)
							for(int py=Utils.smaller(start.Y, finish.Y); py<=Utils.bigger(start.Y, finish.Y); py++)
								currentDrawing.add(new MapPos(px, py, 0));
					}
					break;
				case fill:
					if (!leftClick) {
						Tile clickMapTile = map.getTile(clickMap);
						if (!clickMapTile.equals(tile))
							recurFill(clickMap, clickMapTile, sel.contains(clickMap));
					}
					break;
				}
			}
			else if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !rightClick && !input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				if(mode.ordinal() == Edit.values().length-1)
					mode = Edit.freelook;
				else
					mode = Edit.values()[mode.ordinal()+1];
			}

			else if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !rightClick && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				for (MapPos p : currentDrawing)
					map.setTile(p, tile);
			}

			else{
				start = null;
				finish = null;
			}

			Engine.removePosEffect(MapEditorSelect.class);
			for (MapPos p : sel)
				Engine.addPosEffect(new MapEditorSelect(p, Edit.line));
			for (MapPos p : currentDrawing) {
				if (!sel.contains(p))
					Engine.addPosEffect(new MapEditorSelect(p, Edit.square));
			}
			if(mode==Edit.tile)
				Engine.addPosEffect(new MapEditorSelect(clickMap, Edit.tile));
		}


		leftClick = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		rightClick = input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
	}

	private void addToSel(MapPos p) {
		if (!sel.contains(p) && map.isInMap(p))
			sel.add(p);
	}

	private void addAllToSel(List<MapPos> subSel) {
		for (MapPos p : subSel)
			addToSel(p);
	}

	private void recurFill(MapPos loc, Tile fillTile, boolean limitToSel) {
		if (map.isInMap(loc) && (!limitToSel || sel.contains(loc)) && map.getTile(loc).equals(fillTile)) {
			map.setTile(loc, tile);
			recurFill(new MapPos(loc.X    , loc.Y - 1, 0), fillTile, limitToSel);
			recurFill(new MapPos(loc.X + 1, loc.Y    , 0), fillTile, limitToSel);
			recurFill(new MapPos(loc.X    , loc.Y + 1, 0), fillTile, limitToSel);
			recurFill(new MapPos(loc.X - 1, loc.Y    , 0), fillTile, limitToSel);
		}
	}

	@Override
	public int getID() {
		return ID;
	}
}


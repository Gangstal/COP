package com.hamsterfurtif.cop.gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.display.Engine;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.display.menu.MainGame;
import com.hamsterfurtif.cop.display.poseffects.MouseHover;
import com.hamsterfurtif.cop.display.poseffects.MoveSelect;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.map.tiles.Tile;

public class GSGame extends GameStateMenu {
	
	public static final int ID = 2;
	public static Image health_end_full, health_end_empty, health_middle_full, health_middle_empty, heart;
	public static float scale = 2.5f;
	public int currentPlayer=0;
	public boolean showGrid = false;
	public boolean movementSelection = false;
	private int mx, my;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.currentMenu = new MainGame(container, this);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(COP.background, 0, 0);
		currentMenu.render(g);
		Engine.drawMapWithPlayers(g, scale,(int)(COP.width-TextureLoader.textureRes*Game.map.dimX*scale), 0, showGrid);
		
		int x=168, y=480;
		
		g.setColor(Color.lightGray);
		g.fillRect(x, y, 840, 120);
		g.setColor(Color.black);
		g.drawRect(x, y, 419, 119);	
		g.drawRect(x+420, y, 419, 119);
		
		drawPlayerInfoBox(x, y, Game.players.get(currentPlayer), g);
		
		
		if(mx>(int)(COP.width-TextureLoader.textureRes*21*scale) && my<(int)(TextureLoader.textureRes*12*scale)){
			renderInfoBox(mx-(int)(COP.width-TextureLoader.textureRes*21*scale), my, g);
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		mx = input.getMouseX();
		my = input.getMouseY();
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			if(mx>(int)(COP.width-TextureLoader.textureRes*21*scale) && my<(int)(TextureLoader.textureRes*12*scale))
				clickOnMap(mx-(int)(COP.width-TextureLoader.textureRes*21*scale), my);
		}
		
		if(mx>(int)(COP.width-TextureLoader.textureRes*21*scale) && my<(int)(TextureLoader.textureRes*12*scale)){
			Engine.removePosEffect(MouseHover.class);
			Engine.addPosEffect(new MouseHover(getMapPos(mx-(int)(COP.width-TextureLoader.textureRes*21*scale), my)));
		}

	}

	@Override
	public int getID() {
		return ID;
	}
	
	private void clickOnMap(int xpos, int ypos){
		MapPos pos = getMapPos(xpos, ypos);
		Engine.addPosEffect(new MoveSelect(pos));
	}
	
	private void drawPlayerInfoBox(int x, int y, Player player, Graphics g){
		g.drawString(player.name, x+10, y+20);
		g.drawImage(player.skin.getScaledCopy(2.5f), x+420-50, y+10);
		if(Game.maxHP>1){
			g.setColor(Color.black);
			g.drawImage(health_end_full, x+10, y+50);
			for(int i=0; i<Game.maxHP-2;i++){
				if(player.health>i+1)
					g.drawImage(health_middle_full, x+10+17*(i+1), y+50);
				else
					g.drawImage(health_middle_empty, x+10+17*(i+1), y+50);

				g.drawLine(x+9+17*(i+1), y+53, x+9+17*(i+1), y+61);
			}
			int i = Game.maxHP-2;
			g.drawLine(x+9+17*(i+1), y+53, x+9+17*(i+1), y+61);
			if(player.health==Game.maxHP)
				g.drawImage(health_end_full.getFlippedCopy(true, false),x+10+17*(i+1), y+50);
			else
				g.drawImage(health_end_empty.getFlippedCopy(true, false),x+10+17*(i+1), y+50);


		}
		
		for(int i=0; i<player.repsawnsLeft;i++){
			g.drawImage(heart, x+20+i*18, y+80);
		}
	}
	
	private void renderInfoBox(int xpos, int ypos, Graphics g){
		MapPos pos = getMapPos(xpos, ypos);		
		int x = 168+420;
		int y = 480;
		Player player = Game.checkForPlayer(pos);
		if(player==null){
			Tile tile = Game.map.getTile(pos);
			g.drawImage(tile.image.getScaledCopy(4), x+320, y+60-2*16);
			g.drawString(tile.name, x+10, y+5);
			g.drawString("Can Walk Over = "+tile.canWalkThrough, x+10, y+30);
			g.drawString("Can Shoot Through = "+tile.canShootTrough, x+10, y+55);
			g.drawString("Is Destructible = "+tile.isDestructible, x+10, y+80);
		}
		else{
			drawPlayerInfoBox(x, y, player, g);
		}

	}
	
	private MapPos getMapPos(int xpos, int ypos){
		int X =(int)((xpos-xpos%(scale*16))/(scale*16));
		int Y =(int)((ypos-ypos%(scale*16))/(scale*16));
		return new MapPos(X, Y, 0);
	}
}

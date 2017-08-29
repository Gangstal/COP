package com.hamsterfurtif.cop.display.menu;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.gamestates.GSGame;
import com.hamsterfurtif.cop.inventory.Weapon;

public class MainGame extends Menu {
	
	private Button move;
	private Button endTurn;
	private Button showGrid;
	private Button reload;
	private Button primary;
	private Button secondary;
	private GSGame state;
	
	public MainGame(GameContainer container, GSGame state) throws SlickException {
		super(container, "", state);
		width = (int)(COP.width-TextureLoader.textureRes*21*state.scale);
		height = COP.height;
		this.state = state;
		
		move = new Button("Bouger", this, 0, 0, this.width, 50){
			@Override
			public void additionalRender(Graphics g){
				g.drawImage(TextureLoader.loadTexture("GUI\\move.gif").getScaledCopy(50, 50) , this.getWidth()-50, this.getY());
				g.setColor(Color.gray);
				g.fillRect(0, this.getY()+this.getHeight(), this.getWidth(), 50);
				g.setColor(Color.darkGray);
				g.drawRect(0, this.getY()+this.getHeight(), this.getWidth()-1, 49);
				
				String stats = Game.players.get(Game.currentPlayer).movesLeft+"/"+Game.players.get(Game.currentPlayer).maxMoves;
				Font font = g.getFont();
				int w = font.getWidth(stats);
				int h = font.getLineHeight();
				int xpos = this.getX()-(w-this.getWidth())/2;
				int ypos = this.getY()-(h-this.getHeight())/2+50;
				if(Game.players.get(Game.currentPlayer).movesLeft>0)
					g.setColor(Color.black);
				else
					g.setColor(Color.red);
				g.drawString(stats, xpos, ypos);

				
			}
		}.setTextPlacement(TextPlacement.LEFT);

		endTurn = new Button("Fin du tour", this, 0, (int)(12*16*state.scale), this.width, (int)(this.height-12*16*state.scale)){
			@Override
			public void additionalRender(Graphics g){
				g.drawImage(TextureLoader.loadTexture("GUI\\endturn.gif").getScaledCopy(50, 50) , this.getWidth()/2-25,this.getY()+this.getHeight()/2);;
			}
		}.setTextPlacement(TextPlacement.CENTERED).setTextMargins(0, 15);
		
		showGrid = new Button("Grille", this, 0, (int)(12*16*state.scale-50), this.width, 50){
			@Override
			public void additionalRender(Graphics g){
				g.drawImage(TextureLoader.loadTexture("GUI\\show_grid.gif").getScaledCopy(50, 50), this.getWidth()-50,this.getY());
			}
		}.setTextPlacement(TextPlacement.LEFT);
		
		reload = new Button("Recharger", this, 0, (int)(12*16*state.scale-100), this.width, 50){
			@Override
			public void additionalRender(Graphics g){
				g.drawImage(TextureLoader.loadTexture("GUI\\reload.gif").getScaledCopy(50, 50), this.getWidth()-50,this.getY());
			}
		}.setTextPlacement(TextPlacement.LEFT);
		
		primary = new Button("Tirer (primaire)", this, 0, 100, this.width, 50){
			@Override
			public void additionalRender(Graphics g){
				g.setColor(Color.lightGray);
				g.fillRect(0, this.getY()+49, this.getWidth(), 91);
				g.setColor(Color.black);
				g.drawRect(0, this.getY()-1, this.getWidth()-1, 140);
				Player player = Game.players.get(state.currentPlayer);
				Weapon w = player.inventory.primary;
				int ypos =  this.getY()+this.getHeight()+5;
				g.drawImage(w.skin.getScaledCopy(64, 32),this.getWidth()-66, ypos);
				g.drawString(w.name, 5, ypos+5);
				g.drawString("A: "+player.inventory.ammoP+"/"+w.ammo, 5, ypos+30);
				g.drawString("D: "+w.damage, 5, ypos+45);
				g.drawString("R: "+w.range, 5, ypos+60);

			}
		};
		
		secondary = new Button("Tirer (secondaire)", this, 0, 240, this.width, 50){
			@Override
			public void additionalRender(Graphics g){
				g.setColor(Color.lightGray);
				g.fillRect(0, this.getY()+49, this.getWidth(), 91);
				g.setColor(Color.black);
				g.drawRect(0, this.getY()-1, this.getWidth()-1, 140);
				Player player = Game.players.get(state.currentPlayer);
				Weapon w = player.inventory.secondary;
				int ypos =  this.getY()+this.getHeight()+5;
				g.drawImage(w.skin.getScaledCopy(64, 32),this.getWidth()-66, ypos);
				g.drawString(w.name, 5, ypos+5);
				g.drawString("A: "+player.inventory.ammoP+"/"+w.ammo, 5, ypos+30);
				g.drawString("D: "+w.damage, 5, ypos+45);
				g.drawString("R: "+w.range, 5, ypos+60);
			}
		};
		
		choices = new ArrayList<Button>(Arrays.asList(move, endTurn, showGrid, reload, primary, secondary));
		
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(choices.contains(source)){
			if(source==showGrid)
				state.showGrid = !state.showGrid;
		}
	}

}

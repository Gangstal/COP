package com.hamsterfurtif.cop.display.menu;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class ConfirmGameSettings extends Menu {

	private Button confirmer = new Button("Confirmer", this, 50, 400, 100, 40).setTextPlacement(TextPlacement.LEFT);
	
	private int hpCount = 10;
	private int livesCount = 5;
	
	private TextInput hp = new TextInput(this, 300, 160, 50, 20, Integer.toString(hpCount));
	private TextInput lives = new TextInput(this, 300, 210, 50, 20, Integer.toString(livesCount));

	private Button plusHP = new Button("+", this, 275, 160, 20, 20).setTextPlacement(TextPlacement.CENTERED);
	private Button minusHP = new Button("-", this, 355, 160, 20, 20).setTextPlacement(TextPlacement.CENTERED);
	private Button plusLives = new Button("+", this, 275, 210, 20, 20).setTextPlacement(TextPlacement.CENTERED);
	private Button minusLives = new Button("-", this, 355, 210, 20, 20).setTextPlacement(TextPlacement.CENTERED);

	
	public ConfirmGameSettings(GameContainer container, GameStateMenu state) throws SlickException {
		super(container, "Confirmer les paramètres de jeu", state);
		choices = new ArrayList<Button>(Arrays.asList(confirmer, plusHP, minusHP, plusLives, minusLives));
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		
		if(choices.contains(source)){
			if(source==confirmer){
				Game.maxHP = hpCount;
				for(Player player : Game.players){
					player.repsawnsLeft = livesCount;
					player.health = hpCount;
				}
				COP.game.setPlayerInitialSpawn();
				COP.instance.enterState(2);
			}
			else{
				if(source==plusHP)
					hpCount++;
				else if(source==minusHP && hpCount>1)
					hpCount--;
				else if(source==plusLives)
					livesCount++;
				else if(source==minusLives && livesCount>0)
					livesCount--;
				
				hp.setText(Integer.toString(hpCount));
				lives.setText(Integer.toString(livesCount));
			}
		}

	}

	@Override
	public void render(Graphics g) { 
		g.drawString(name, titleX+x, titleY+y);
		for(Button button : choices)
			button.render(g);
		
		g.drawString("PV max", 90, 160);
		g.drawString("Vies", 90, 210);
		hp.render(container, g);
		lives.render(container, g);

	}
	
	@Override
	public void update(){
		
		if(Utils.stringIsInteger(hp.getText()))
			hpCount=Integer.parseInt(hp.getText());
		if(Utils.stringIsInteger(lives.getText()))
			livesCount=Integer.parseInt(lives.getText());
		
		hp.setText(Integer.toString(hpCount));
		lives.setText(Integer.toString(livesCount));
		
	}
}

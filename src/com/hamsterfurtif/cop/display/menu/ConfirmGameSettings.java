package com.hamsterfurtif.cop.display.menu;

import java.io.IOException;
import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.COP.Mode;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class ConfirmGameSettings extends Menu {

	private Button confirmer = new Button("Confirmer", this, 50, 400, 100, 40).setTextPlacement(TextPlacement.LEFT);

	private TextInput hp = new TextInput(this, 300, 160, 50, 20, Integer.toString(Game.maxHP));
	private TextInput lives = new TextInput(this, 300, 210, 50, 20, Integer.toString(Game.maxSpawn));

	private Button plusHP = new Button("+", this, 355, 160, 20, 20).setTextPlacement(TextPlacement.CENTERED);
	private Button minusHP = new Button("-", this, 275, 160, 20, 20).setTextPlacement(TextPlacement.CENTERED);
	private Button plusLives = new Button("+", this, 355, 210, 20, 20).setTextPlacement(TextPlacement.CENTERED);
	private Button minusLives = new Button("-", this, 275, 210, 20, 20).setTextPlacement(TextPlacement.CENTERED);


	public ConfirmGameSettings(GameContainer container, GameStateMenu state) throws SlickException {
		super(container, "Confirmer les paramètres de jeu", state);
		choices = Arrays.asList(confirmer, plusHP, minusHP, plusLives, minusLives);
	}

	@Override
	public void componentActivated(AbstractComponent source) {

		if(choices.contains(source)){
			if(source==confirmer){
				if (COP.mode == Mode.SERVER) {
					COP.settingsDone = true;
					try {
						COP.sendSettings();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				COP.setupFromSettings();
			}
			else{
				if(source==plusHP)
					Game.maxHP++;
				else if(source==minusHP && Game.maxHP>1)
					Game.maxHP--;
				else if(source==plusLives)
					Game.maxSpawn++;
				else if(source==minusLives && Game.maxSpawn>0)
					Game.maxSpawn--;

				hp.setText(Integer.toString(Game.maxHP));
				lives.setText(Integer.toString(Game.maxSpawn));
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
			Game.maxHP=Integer.parseInt(hp.getText());
		if(Utils.stringIsInteger(lives.getText()))
			Game.maxSpawn=Integer.parseInt(lives.getText());

		hp.setText(Integer.toString(Game.maxHP));
		lives.setText(Integer.toString(Game.maxSpawn));

	}
}

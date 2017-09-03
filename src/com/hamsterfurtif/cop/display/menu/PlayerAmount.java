package com.hamsterfurtif.cop.display.menu;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class PlayerAmount extends Menu{
	
	private int playercount = 2;
	
	private TextInput players = new TextInput(this, 300, 160, 50, 20, Integer.toString(playercount));

	private Button plus = new Button("+", this, 355, 160, 20, 20).setTextPlacement(TextPlacement.CENTERED);
	private Button minus = new Button("-", this, 275, 160, 20, 20).setTextPlacement(TextPlacement.CENTERED);
	
	public Button confirmer = new Button("Confirmer", this, width/2, height-80, 100, 30).centered();
	public Button quit = new Button("Quitter", this, COP.width-168, 550, 168, 50);

	
	public PlayerAmount(GameContainer container, GameStateMenu state2) throws SlickException {
		super(container, "Nombre de joueurs", state2);
		choices = new ArrayList<Button>(Arrays.asList(plus, minus, confirmer, quit));
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source==confirmer){
			for(int i=0; i<playercount;i++){
				Game.players.add(new Player("Joueur "+i));
			}
			try {
				this.state.currentMenu = new PickMap(container, state);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if(source==plus)
			playercount++;
		else if(source==minus && playercount>1)
			playercount--;
		else if(source == quit){
			try {
				this.state.currentMenu = new Main(container, state);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		players.setText(""+playercount);
	}
	
	@Override
	public void render(Graphics g) { 
		g.drawString(name, titleX+x, titleY+y);
		for(Button button : choices)
			button.render(g);
		
		g.drawString("Joueurs", 90, 160);
		players.render(container, g);

	}
	
	@Override
	public void update(){
		
		if(Utils.stringIsInteger(players.getText()))
			playercount=Integer.parseInt(players.getText());
		
		players.setText(Integer.toString(playercount));
		
	}

}

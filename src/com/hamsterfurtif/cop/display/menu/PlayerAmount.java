package com.hamsterfurtif.cop.display.menu;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.COP.Mode;
import com.hamsterfurtif.cop.ServerThread;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class PlayerAmount extends Menu{

	private int playersCount = 2;
	private TextInput players = new TextInput(this, 325, 160, 50, 20, Integer.toString(playersCount));
	private Button plus = new Button("+", this, 380, 160, 20, 20).setTextPlacement(TextPlacement.CENTERED);
	private Button minus = new Button("-", this, 300, 160, 20, 20).setTextPlacement(TextPlacement.CENTERED);

	private int charactersCount = 1;
	private TextInput characters = new TextInput(this, 325, 185, 50, 20, Integer.toString(charactersCount));
	private Button cPlus = new Button("+", this, 380, 185, 20, 20).setTextPlacement(TextPlacement.CENTERED);
	private Button cMinus = new Button("-", this, 300, 185, 20, 20).setTextPlacement(TextPlacement.CENTERED);

	public Button confirmer = new Button("Démarrer le serveur", this, width/2, height-80, 300, 30).centered();
	public Button quit = new Button("Quitter", this, COP.width-168, 550, 168, 50);


	public PlayerAmount(GameContainer container, GameStateMenu state2) throws SlickException {
		super(container, "Nombre de joueurs", state2);
		choices = new ArrayList<Button>(Arrays.asList(plus, minus, cPlus, cMinus, confirmer, quit));
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source==confirmer){
			Game.playersCount = playersCount;
			Game.charactersCount = charactersCount;
			if (COP.mode == Mode.SINGLEPLAYER) {
				COP.setupPlayers();
			} else if (COP.mode == Mode.SERVER) {
				try {
					COP.playersConnected = 1;
					COP.playersReady = 0;
					COP.settingsDone = false;
					COP.serverSocket = new ServerSocket(42069);
					COP.selfId = 0;
					COP.started = true;
					COP.setupPlayers();
					new ServerThread().start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				throw new RuntimeException("Unknown mode \"" + COP.mode + "\"");
			}
			try {
				this.state.currentMenu = new PickMap(container, state);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(source==plus)
			playersCount++;
		else if(source==minus && playersCount>1)
			playersCount--;
		else if(source==cPlus)
			charactersCount++;
		else if(source==cMinus && charactersCount>1)
			charactersCount--;
		else if(source == quit){
			try {
				this.state.currentMenu = new Main(container, state);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		players.setText(""+playersCount);
		characters.setText(""+charactersCount);
	}

	@Override
	public void render(Graphics g) {
		g.drawString(name, titleX+x, titleY+y);
		for(Button button : choices)
			button.render(g);

		g.drawString("Joueurs", 90, 160);
		players.render(container, g);

		g.drawString("Personnages par joueur", 90, 185);
		characters.render(container, g);

	}

	@Override
	public void update(){

		if(Utils.stringIsInteger(players.getText()))
			playersCount=Integer.parseInt(players.getText());

		players.setText(Integer.toString(playersCount));

		if(Utils.stringIsInteger(characters.getText()))
			charactersCount=Integer.parseInt(characters.getText());

		characters.setText(Integer.toString(charactersCount));

	}

}

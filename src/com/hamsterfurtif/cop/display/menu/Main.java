package com.hamsterfurtif.cop.display.menu;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.gamestates.GSPlayerEquip;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;
import com.hamsterfurtif.cop.packets.Packet;

public class Main extends Menu{



	private Button solo = new Button("Jouer en Solo", this, COP.width/2, COP.height/2-150, 250, 40).centered();
	private Button client = new Button("Multijoueur (Rejoindre)", this, COP.width/2, COP.height/2-80, 250, 40).centered();
	private Button server = new Button("Multijoueur (Héberger)", this, COP.width/2, COP.height/2-10, 250, 40).centered();
	private Button mapeditor = new Button("Editeur de niveaux", this, COP.width/2, COP.height/2+150, 250, 40).centered();
	private Button quit = new Button("Quitter", this, width/2, height-40, 100, 30).centered();

	private TextInput ip = new TextInput(this, COP.width/2, COP.height/2+50, 250, 23, COP.savedip).centered();
	private Button paste = new Button("Coller", this, (COP.width+260+60)/2,COP.height/2+50, 60, 23).centered();

	public Main(GameContainer container, GameStateMenu state) throws SlickException {
		super(container, "Call Of Paper", state);
		choices = Arrays.asList(solo,server,client,quit, paste, mapeditor);
		titleX=width/2-50;
	}

	@Override
	public void componentActivated(AbstractComponent source) {

		if(source==ip)
			Utils.print(ip.getText());

		if(source== solo || source == server || source==client){
			if(source==solo)
				COP.mode = COP.Mode.SINGLEPLAYER;
			else if(source==server)
				COP.mode = COP.Mode.SERVER;
			else if(source==client)
				COP.mode = COP.Mode.CLIENT;
			try {
				COP.writeSavedIP(ip.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			COP.rd = new Random(1);
			COP.conns = new ArrayList<Conn>();
			COP.packets = new ArrayList<Packet>();
			try {
				if (source == client) {
					COP.serverConn = new Conn(new Socket(ip.getText(), 42069));

					COP.started = true;
					GSPlayerEquip state = (GSPlayerEquip) COP.instance.getState(1);
					try {
						state.mainMenu = new WaitingForSettings(container, state);
					} catch (SlickException e) {
						e.printStackTrace();
					}
					COP.instance.enterState(1);
				} else if (source == server) {
					try {
						this.state.currentMenu = new PlayerAmount(container, state);
					} catch (SlickException e) {
						e.printStackTrace();
					}
				} else {
					try {
						this.state.currentMenu = new PlayerAmount(container, state);
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}else if(source==quit){
			container.exit();
		}else if(source==paste){
			ip.setText(Utils.getClipBoard());
		}else if(source==mapeditor){
			try {
				state.currentMenu = new MapEditorMenu(container, state);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.drawString(name, titleX+x, titleY+y);
		for(Button button : choices)
			button.render(g);
		ip.render(container, g);
	}

}

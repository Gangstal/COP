package com.hamsterfurtif.cop.display.menu;

import java.net.ServerSocket;
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
import com.hamsterfurtif.cop.ServerThread;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class Main extends Menu{



	private Button solo = new Button("Jouer en Solo", this, COP.width/2, COP.height/2-70, 250, 40).centered();
	private Button server = new Button("Multijoueur (H�berger)", this, COP.width/2, COP.height/2, 250, 40).centered();
	private Button client = new Button("Multijoueur (Rejoindre)", this, COP.width/2, COP.height/2+70, 250, 40).centered();
	private Button quit = new Button("Quitter", this, width/2, height-40, 100, 30).centered();
	
	private TextInput ip = new TextInput(this, COP.width/2, COP.height/2+150, 250, 23, "Adresse IP (multijoueur)").centered();
	private Button paste = new Button("Coller", this, (COP.width+260+60)/2,COP.height/2+150, 60, 23).centered();
	
	public Main(GameContainer container, GameStateMenu state) throws SlickException {
		super(container, "Call Of Paper", state);
		this.choices = new ArrayList<Button>(Arrays.asList(solo,server,client,quit, paste));
		this.titleX=width/2-50;
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source== solo || source == server || source==client){
			if(source==solo)
				COP.mode = COP.MODE_SINGLEPLAYER;
			else if(source==server)
				COP.mode = COP.MODE_SERVER;
			else if(source==client)
				COP.mode = COP.MODE_CLIENT;
			
			COP.rd = new Random(1);
			COP.conns = new ArrayList<Conn>();
			COP.packets = new ArrayList<String>();
			try {
				if (source==client) {
					COP.conns.add(new Conn(new Socket(ip.getText(), 42069)));
				} else if (source==server) {
					COP.serverSocket = new ServerSocket(42069);
					new ServerThread().start();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			try {
				this.state.currentMenu = new PlayerAmount(container, state);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			
		}else if(source==quit){
			container.exit();
		}else if(source==paste){
			ip.setText(Utils.getClipBoard());;
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

package com.hamsterfurtif.cop.display.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.display.Engine;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.gamestates.GSMapEditor;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapReader;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.statics.Tiles;

public class MapEditorMenu extends Menu{
	
	public static boolean mapAdded = false;

	private boolean newMapSelected = true;
	
	private Button newMap = new Button("Nouveau", this, 50, 100, 250, 50);
	private TextInput dimensionX = new TextInput(this, 550, 100, 100, 20);
	private TextInput dimensionY = new TextInput(this, 550, 130, 100, 20);
	private TextInput mapName = new TextInput(this, 550, 160, 100, 20);

	private Button confirm = new Button("Confirm", this, this.width/2, this.height-80, 250, 50).centered();
	public Button quit = new Button("Quitter", this, COP.width-168, 550, 168, 50);


	public MapEditorMenu(GameContainer container, GameStateMenu state) throws SlickException {
		super(container, "Choisir un niveau", state);
		choices = new ArrayList<Button>(Arrays.asList(newMap, confirm, quit));
		ArrayList<String> mapList = MapReader.scanMapFolder("assets\\maps\\");
		int offset = 60;
		for(String map : mapList){
			choices.add(new Button(map.substring(0, map.length()-4), this, 50, offset+height/6, 250, 50).setTextPlacement(TextPlacement.LEFT));
			offset+=60;
			}	
		}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source == newMap){
			newMapSelected=true;
		}
		else if(source==quit){
			try {
				state.currentMenu = new Main(container, state);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(source ==  confirm){
			dimensionX.setLocation(420*69, 69*420);
			dimensionY.setLocation(420*69, 69*420);
			mapName.setLocation(420*69, 69*420);
			if(newMapSelected){
				Tile[][] m = new Tile[Integer.parseInt(dimensionY.getText())][Integer.parseInt(dimensionX.getText())];
				Tile[] fuckyougaston = new Tile[Integer.parseInt(dimensionX.getText())];
				Arrays.fill(fuckyougaston, Tiles.grass);
				for (int i = 0; i<Integer.parseInt(dimensionY.getText()); i++)
					m[i] = fuckyougaston.clone();
				GSMapEditor.map = new Map(m);
				GSMapEditor.mapname = mapName.getText();
			}
			float xscale = (float)840/(float)(GSMapEditor.map.dimX*TextureLoader.textureRes);
			float yscale = (float)480/(float)(GSMapEditor.map.dimY*TextureLoader.textureRes);
			float optimalScale = xscale > yscale ? yscale : xscale;
			optimalScale -= optimalScale%0.25f;
			GSMapEditor.optimalScale=optimalScale;
			GSMapEditor.scale=optimalScale;
			float c = TextureLoader.textureRes*optimalScale;
			if(c*GSMapEditor.map.dimX<=COP.width-168)
				GSMapEditor.mapx=(int)(168+COP.width-c*GSMapEditor.map.dimX)/2;
			if(c*GSMapEditor.map.dimY<=480)
				GSMapEditor.mapy=(int)(480-c*GSMapEditor.map.dimY)/2;
			COP.instance.enterState(GSMapEditor.ID);

				
		}
		else if(choices.contains(source)){
			newMapSelected=false;
			Button button = (Button)source;
			try {
				GSMapEditor.map = MapReader.readMap("assets\\maps\\"+button.name+".txt");
				GSMapEditor.mapname = button.name;
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.drawString(name, titleX+x, titleY+y);
		for(Button button : choices)
				button.render(g);
		
		if(newMapSelected){
			dimensionX.render(container, g);
			dimensionY.render(container, g);
			mapName.render(container, g);
			g.drawString("Dimension X", 440, 100);
			g.drawString("Dimension Y", 440, 130);
			g.drawString("Nom", 440, 160);

		}
		else if(GSMapEditor.map != null){
			Engine.drawMap(g, 0.5f, 400, 150, false, GSMapEditor.map);
		}
	}
	
	public void update(){
		if(mapAdded){
			choices = new ArrayList<Button>(Arrays.asList(newMap, confirm, quit));
			ArrayList<String> mapList = MapReader.scanMapFolder("assets\\maps\\");
			int offset = 60;
			for(String map : mapList){
				choices.add(new Button(map.substring(0, map.length()-4), this, 50, offset+height/6, 250, 50).setTextPlacement(TextPlacement.LEFT));
				offset+=60;
				}
			dimensionX = new TextInput(this, 550, 100, 100, 20);
			dimensionY = new TextInput(this, 550, 130, 100, 20);
			mapName = new TextInput(this, 550, 160, 100, 20);

			mapAdded = false;
		}
	}


}

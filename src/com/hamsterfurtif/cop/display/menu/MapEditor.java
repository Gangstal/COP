package com.hamsterfurtif.cop.display.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.gamestates.GSMainMenu;
import com.hamsterfurtif.cop.gamestates.GSMap;
import com.hamsterfurtif.cop.gamestates.GSMapEditor;
import com.hamsterfurtif.cop.gamestates.GSMapEditor.Edit;
import com.hamsterfurtif.cop.map.MapReader;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.statics.Tiles;

public class MapEditor extends Menu{

	private  ArrayList<InventoryButton> savedtiles = new ArrayList<InventoryButton>();
	private ArrayList<TileButton> searchedTiles= new ArrayList<TileButton>();

	private String searchedFieldOnPreviousTick = "";

	private class ModeButton extends Button{

		public Edit mode;
		public Image skin;

		public ModeButton(String name, String location, int y, MapEditor me, Edit mode) {
			super(name, me, 0, y, 168, 50);
			this.textPlacement = TextPlacement.LEFT;
			me.choices.add(this);
			skin = TextureLoader.loadTexture(location);
			this.mode = mode;
		}

		public void additionalRender(Graphics g){
			g.drawImage(skin.getScaledCopy(40, 40), this.getWidth()-45, this.getY()+5);
			Color color = new Color(Color.green);
			color.a = 0.25f;
			if(GSMapEditor.mode == mode){
				g.setColor(color);
				g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
			}
		}
	}

	private class InventoryButton extends Button{
		public Tile tile;
		public Image skin;

		public InventoryButton(int y, MapEditor me, Tile tile) {
			super(tile.name, me, 0, y, 168, 50);
			this.textPlacement = TextPlacement.LEFT;
			skin = tile.image;
			this.tile = tile;
		}

		public void additionalRender(Graphics g){
			g.drawImage(skin.getScaledCopy(40, 40), this.getWidth()-45, this.getY()+5);
			Color color = new Color(Color.green);
			color.a = 0.25f;
			if(tile == GSMapEditor.tile){
				g.setColor(color);
				g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
			}
		}
	}

	private class TileButton extends Button{

		public Tile tile;

		public TileButton( Menu menu, Tile tile) {
			super("", menu, 0, 0, 36, 36);
			this.tile = tile;
		}

		public void additionalRender(Graphics g){
			g.drawImage(tile.image.getScaledCopy(2), getX()+2, getY()+2);
		}
	}

	public ModeButton freelook = new ModeButton("Vue libre", "GUI\\freelook.png", 0, this, Edit.freelook);
	public ModeButton single = new ModeButton("Case", "GUI\\single.png", 50, this, Edit.tile);
	public ModeButton line = new ModeButton("Ligne", "GUI\\line.png", 100, this, Edit.line);
	public ModeButton square = new ModeButton("Surface", "GUI\\square.png", 150, this, Edit.square);
	public ModeButton fill = new ModeButton("Remplir", "GUI\\fill.png", 200, this, Edit.fill);

	//J'aime hardcoder des trucs
	public InventoryButton brick = new InventoryButton(200, this, Tiles.wall);
	public InventoryButton glass = new InventoryButton(250, this, Tiles.window);
	public InventoryButton door_grass = new InventoryButton(300, this, Tiles.door_grass);
	public InventoryButton ceramic= new InventoryButton(350, this, Tiles.ceramic_tiles);
	public InventoryButton grass = new InventoryButton(400, this, Tiles.grass);
	public InventoryButton stone = new InventoryButton(450, this, Tiles.stone);
	public InventoryButton planks = new InventoryButton(500, this, Tiles.planks);
	public InventoryButton water = new InventoryButton(550, this, Tiles.water);

	public Button confirm = new Button("Confirmer", this, COP.width-168, 480, 168, 60);
	public Button quit = new Button("Quitter", this, COP.width-168, 540, 168, 60);

	public TextInput search = new TextInput(this, 170, 482, 168, 20);

	public MapEditor(GameContainer container, GSMapEditor state) throws SlickException {
		super(container, "", state);
		width = 168;
		height = COP.height;
		this.state=state;

		choices = new ArrayList<Button>();








	 	choices.addAll(Arrays.asList(freelook, single, line, square, fill, confirm, quit));
	 	savedtiles = new ArrayList<InventoryButton>(Arrays.asList(brick, grass, water, planks, ceramic));
	}

	@Override
	public void componentActivated(AbstractComponent source) {

		if(source instanceof ModeButton)
			GSMapEditor.mode = ((ModeButton)source).mode;
		else if(source instanceof InventoryButton)
			GSMapEditor.tile = ((InventoryButton)source).tile;
		else if(source instanceof TileButton){
			for(InventoryButton button : savedtiles){
				if(button.tile == GSMapEditor.tile){
					Utils.print("ME ligne 43");
					GSMapEditor.tile = ((TileButton) source).tile;
					savedtiles.set(savedtiles.indexOf(button), new InventoryButton(button.getY(), this, GSMapEditor.tile));
					break;
				}
			}
		}
		else if(source==confirm)
			try {
				MapReader.writeMap(GSMapEditor.map, "assets\\maps\\"+GSMap.mapname+".txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(source==quit){
			MapEditorMenu.mapAdded = true;
			COP.instance.enterState(GSMainMenu.ID);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(168, 480, 840-168, 120-1);
		g.drawLine(168+172, 480, 168+172, 600);

		for(Button button : choices)
			button.render(g);
		for(int i=0; i<savedtiles.size();i++){
			if(savedtiles.get(i)!=null){
				InventoryButton button = savedtiles.get(i);
				button.setLocation(0, COP.height-250+i*50);
				button.render(g);
			}
		}
		search.render(container, g);
		for(int i=0; i<searchedTiles.size();i++){
			searchedTiles.get(i).setLocation(360+40*i, 490);
			searchedTiles.get(i).render(g);
		}
	}

	public void update(){
		if(!search.getText().equals(searchedFieldOnPreviousTick)){
			searchedFieldOnPreviousTick = search.getText();
			searchedTiles.clear();
			for(Tile tile : Tiles.tiles)
				if(tile.id.contains(search.getText())){
					searchedTiles.add(new TileButton(this, tile));
				}
		}
	}
}

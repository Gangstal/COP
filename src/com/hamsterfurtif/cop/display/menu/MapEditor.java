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
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.gamestates.GSMainMenu;
import com.hamsterfurtif.cop.gamestates.GSMapEditor;
import com.hamsterfurtif.cop.gamestates.GSMapEditor.Edit;
import com.hamsterfurtif.cop.map.MapReader;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.statics.Tiles;

public class MapEditor extends Menu{

	public GSMapEditor state;

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

	private class BlockButton extends Button{
		public Tile tile;
		public Image skin;

		public BlockButton(int y, MapEditor me, Tile tile) {
			super(tile.name, me, 0, y, 168, 50);
			this.textPlacement = TextPlacement.LEFT;
			me.choices.add(this);
			skin = tile.image;
			this.tile = tile;
		}

		public void additionalRender(Graphics g){
			g.drawImage(skin.getScaledCopy(40, 40), this.getWidth()-45, this.getY()+5);
			Color color = new Color(Color.green);
			color.a = 0.25f;
			if(GSMapEditor.tile == tile){
				g.setColor(color);
				g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
			}
		}
	}

	public ModeButton freelook = new ModeButton("Vue libre", "GUI\\freelook.png", 0, this, Edit.freelook);
	public ModeButton single = new ModeButton("Case", "GUI\\single.png", 50, this, Edit.tile);
	public ModeButton line = new ModeButton("Ligne", "GUI\\line.png", 100, this, Edit.line);
	public ModeButton square = new ModeButton("Surface", "GUI\\square.png", 150, this, Edit.square);

	//J'aime hardcoder des trucs
	public BlockButton brick;
	public BlockButton glass;
	public BlockButton door_grass;
	public BlockButton door_stone;
	public BlockButton grass;
	public BlockButton stone;
	public BlockButton planks;
	public BlockButton water;


	public Button confirm = new Button("Confirmer", this, COP.width-2*168, 550, 168, 50);
	public Button quit = new Button("Quitter", this, COP.width-168, 550, 168, 50);


	public MapEditor(GameContainer container, GSMapEditor state) throws SlickException {
		super(container, "", state);
		width = 168;
		height = COP.height;
		this.state=state;

		choices = new ArrayList<Button>();

		brick = new BlockButton(200, this, Tiles.wall);
		glass = new BlockButton(250, this, Tiles.window);
		door_grass = new BlockButton(300, this, Tiles.door_grass);
		door_stone= new BlockButton(350, this, Tiles.door_stone);
		grass = new BlockButton(400, this, Tiles.grass);
	 	stone = new BlockButton(450, this, Tiles.stone);
	 	planks = new BlockButton(500, this, Tiles.planks);
	 	water = new BlockButton(550, this, Tiles.water);

	 	choices.addAll(Arrays.asList(confirm, quit));
	}

	@Override
	public void componentActivated(AbstractComponent source) {

		if(source instanceof ModeButton)
			GSMapEditor.mode = ((ModeButton)source).mode;
		else if(source instanceof BlockButton)
			GSMapEditor.tile = ((BlockButton)source).tile;
		else if(source==confirm)
			try {
				MapReader.writeMap(GSMapEditor.map, "assets\\maps\\"+GSMapEditor.mapname+".txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else if(source==quit)
			COP.instance.enterState(GSMainMenu.ID);

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.drawString(name, titleX+x, titleY+y);
		for(Button button : choices)
			button.render(g);

	}

}

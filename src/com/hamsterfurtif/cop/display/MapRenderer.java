package com.hamsterfurtif.cop.display;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.display.poseffects.PosEffect;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.gamestates.GSMap;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.inventory.WeaponShield;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.map.tiles.Tile;

public class MapRenderer implements Renderable {
	private final Map map;
	private final Image image;
	private final MapEventListenerQueued melq;
	private boolean showGrid, withPlayer;

	public MapRenderer(Map map) {
		this.map = map;
		try {
			image = new Image(TextureLoader.size * map.dimX, TextureLoader.size * map.dimY);
		} catch (SlickException e) {
			throw new RuntimeException(e);
		}
		map.listener = melq = new MapEventListenerQueued();
		showGrid = false;
		withPlayer = false;
		try {
			Graphics g = image.getGraphics();
			MapPos pos = new MapPos();
			for (pos.Y = 0; pos.Y < map.dimY; pos.Y++)
				for (pos.X = 0; pos.X < map.dimX; pos.X++)
					renderTile(g, pos);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		g.pushTransform();
		try {
			Graphics mapg = image.getGraphics();
			while (true) {
				MapPos pos;
				synchronized (melq.tileChangedQueue) {
					if ((pos = melq.tileChangedQueue.poll()) == null)
						break;
				}
				renderTile(mapg, pos);
			}
			g.drawImage(image, 0, 0);
			for(PosEffect effect : Engine.posEffects)
				renderAt(g, effect.getPos(), effect);
			if (withPlayer) {
				for (Player player : Game.players) {
					for (EntityCharacter character : player.characters) {
						if (character.pos != null) {
							g.pushTransform();
							g.translate((character.pos.X + character.xgoffset) * TextureLoader.size, (character.pos.Y + character.ygoffset) * TextureLoader.size);
							g.rotate((float) TextureLoader.size / 2.0f, (float) TextureLoader.size / 2.0f, Utils.getRotation(character.orientation));
							g.drawImage(character.skin, 0, 0);
							if (character.isShieled)
								g.drawImage(WeaponShield.map_overlay, 0, 0);
							g.popTransform();
						}
					}
				}
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		float dashLen = 6f, dashSpace = 8f;
		float dashSize = dashLen + dashSpace;
		if (showGrid) {
			g.setColor(new Color(0.2f, 0.2f, 0.2f, 0.7f));
			g.setLineWidth(1.0f);
			for (int x = 0; x < map.dimX; x++) {
				float y;
				for (y = 0; y + dashSize <= map.dimY * (float) TextureLoader.size * GSMap.scale; y += dashSize)
					g.drawLine(x * TextureLoader.size, (y) / GSMap.scale, x * TextureLoader.size, (y + dashLen) / GSMap.scale);
				g.drawLine(x * TextureLoader.size, (y) / GSMap.scale, x * TextureLoader.size, Math.min((y + dashLen) / GSMap.scale, map.dimY * (float) TextureLoader.size));
			}
			for (int y = 0; y < map.dimY; y++) {
				float x;
				for (x = 0; x + dashSize <= map.dimX * (float) TextureLoader.size * GSMap.scale; x += dashSize)
					g.drawLine((x) / GSMap.scale, y * TextureLoader.size, (x + dashLen) / GSMap.scale, y * TextureLoader.size);
				g.drawLine((x) / GSMap.scale, y * TextureLoader.size, Math.min((x + dashLen) / GSMap.scale, map.dimX * (float) TextureLoader.size), y * TextureLoader.size);
			}
		}
		g.popTransform();
	}

	public void renderAt(Graphics g, MapPos pos, Renderable obj) {
		g.pushTransform();
		g.translate(pos.X * TextureLoader.size, pos.Y * TextureLoader.size);
		obj.render(g);
		g.popTransform();
	}

	public void renderTile(Graphics g, Tile tile) {
		g.drawImage(tile.image, 0, 0);
	}

	public void renderTile(Graphics g, MapPos pos) {
		g.pushTransform();
		g.translate(pos.X * TextureLoader.size, pos.Y * TextureLoader.size);
		renderTile(g, map.getTile(pos));
		g.popTransform();
	}

	public void setSquares(boolean squares) {
		this.showGrid = squares;
	}

	public void setWithPlayer(boolean withPlayer) {
		this.withPlayer = withPlayer;
	}
}

package com.hamsterfurtif.cop.display;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.display.poseffects.PosEffect;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.inventory.WeaponShield;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.map.tiles.Tile;

public class MapRenderer implements Renderable {
	private final Map map;
	private final Image image;
	private final MapEventListenerQueued melq;
	private boolean squares, withPlayer;

	public MapRenderer(Map map) {
		this.map = map;
		try {
			image = new Image(TextureLoader.size * map.dimX, TextureLoader.size * map.dimY);
		} catch (SlickException e) {
			throw new RuntimeException(e);
		}
		map.listener = melq = new MapEventListenerQueued();
		squares = false;
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
							//Il serai peut être plus audacieux d'avoir une copie avec rotation de l'image enregistrée en permanence, et changée aux moments opportuns,
							//plutôt que de la recalculer à chaque frame. Mais enfin, comme le disait le poète, "C'est Gaston147 qui s'occupe de l'optimisation donc c'
							//est son problème lol"
							Image i = TextureLoader.getRotatedCopy(character.skin, Utils.getRotation(character.orientation));
							g.drawImage(i, (character.pos.X + character.xgoffset) * TextureLoader.size, (character.pos.Y + character.ygoffset) * TextureLoader.size);
							if(character.isShieled){
								g.drawImage(TextureLoader.getRotatedCopy(WeaponShield.map_overlay, Utils.getRotation(character.orientation)), (character.pos.X + character.xgoffset) * TextureLoader.size, (character.pos.Y + character.ygoffset) * TextureLoader.size);
							}
						}
					}
				}
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		if (squares) {
			g.setLineWidth(1.0f);
			for (int x = 0; x < map.dimX; x++)
				g.drawLine(x * TextureLoader.size, 0, x * TextureLoader.size, map.dimY * TextureLoader.size);
			for (int y = 0; y < map.dimY; y++)
				g.drawLine(0, y * TextureLoader.size, map.dimX * TextureLoader.size, y * TextureLoader.size);
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
		this.squares = squares;
	}

	public void setWithPlayer(boolean withPlayer) {
		this.withPlayer = withPlayer;
	}
}

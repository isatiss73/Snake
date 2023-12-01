package model;

import java.util.Random;


/**
 * global game object
 */
public class Game
{
	public static final int DEFAULT_MAX_SPEED = 10;
	public static final int DEFAULT_MAX_LENGTH = 10;
	
	private static Game instance;
	
	private Cell[][] map;
	private Snake[] players;
	private int livingNumber;
	private int maxSpeed;
	private int maxLength;
	
	
	/**
	 * complete constructor
	 * @param hsize horizontal map size
	 * @param vsize vertical map size
	 * @param maxPlayers maximum players number
	 */
	private Game(int hsize, int vsize, int maxPlayers)
	{
		reset(hsize, vsize, maxPlayers);
	}
	
	
	/**
	 * default constructor
	 */
	private Game()
	{
		reset(10, 10, 1);
	}
	
	/**
	 * single instance getter
	 * @return the only game instance
	 */
	public static Game getInstance()
	{
		if (instance == null)
			instance = new Game();
		return instance;
	}
	
	/**
	 * complete reconstructor
	 * @param hsize horizontal map size
	 * @param vsize vertical map size
	 * @param maxPlayers maximum players number
	 */
	public void reset(int hsize, int vsize, int maxPlayers)
	{
		// creation of tables
		map = new Cell[hsize][vsize];
		players = new Snake[maxPlayers];
		
		// variables initialisation
		livingNumber = maxPlayers;
		maxSpeed = DEFAULT_MAX_SPEED;
		maxLength = DEFAULT_MAX_LENGTH;
		
		// map initialisation
		for (int x=0; x<hsize; x++)
		{
			for (int y=0; y<vsize; y++)
			{
				map[x][y] = new Cell();
			}
		}
	}
	
	/**
	 * add spaces before a text to fill a length
	 * @param text text to pad
	 * @param length goal length
	 * @return text with the good length
	 */
	public static String padStart(String text, int length)
	{
		int n = length - text.length();
		String pad = "";
		for (int i=0; i<n; i++)
		{
			pad += ' ';
		}
		return pad + text;
	}
	
	
	/**
	 * text representation
	 */
	public String toString()
	{
		String res = "";
		String word;
		res += "+";
		for (int x=0; x<map.length; x++)
		{
			res += "   -";
		}
		res += "   +\n\n";
		for (int y=0; y<map[0].length; y++)
		{
			res += "|";
			for (int x=0; x<map.length; x++)
			{
				word = "" + map[x][y];
				res += padStart(word, 4);
			}
			res += "   |\n\n";
		}
		res += "+";
		for (int x=0; x<map.length; x++)
		{
			res += "   -";
		}
		res += "   +\n";
		return res;
	}
	
	public String smoothString()
	{
		String res = "";
		String word;
		res += "+";
		for (int x=0; x<map.length; x++)
		{
			res += "--";
		}
		res += "-+\n";
		for (int y=0; y<map[0].length; y++)
		{
			res += "|";
			for (int x=0; x<map.length; x++)
			{
				word = "" + map[x][y].toChar();
				res += padStart(word, 2);
			}
			res += " |\n";
		}
		res += "+";
		for (int x=0; x<map.length; x++)
		{
			res += "--";
		}
		res += "-+\n";
		return res;
	}
	
	/**
	 * create a snake
	 * @param who index of the snake
	 * @param x initial horizontal position
	 * @param y initial vertical position
	 * @param length initial length
	 * @param xdir initial horizontal direction
	 * @param ydir initial vertical direction
	 */
	public void createSnake(int who, int x, int y, int length, int xdir, int ydir)
	{
		if (x - length >= -1)
		{
			players[who] = new Snake(x, y, length, xdir, ydir);
			for (int i=0; i<length; i++)
			{
				map[x-i][y].setDirection(xdir, ydir);
				map[x-i][y].setEntity(Cell.PLAYER);
				map[x-i][y].setDetail(who);
			}
		}
	}
	
	
	/**
	 * kill a snake
	 * @param who position of the snake in the list
	 * @return number of living snakes
	 */
	public int killSnake(int who)
	{
		players[who].setLiving(false);
		livingNumber--;
		return livingNumber;
	}
	
	
	/**
	 * see if we can go on the square (x, y)
	 * @param x horizontal position
	 * @param y vertical position
	 * @return true if we can...
	 */
	public boolean isAccessible(int x, int y)
	{
		return ((x >= 0) && (x < map.length) && 
				(y >= 0) && (y < map[0].length) && 
				((map[x][y].getEntity() == Cell.AIR) || 
				(map[x][y].getEntity() == Cell.APPLE)));
	}
	
	/**
	 * evolve by 10 ms until something change
	 */
	public void evolve()
	{
		while (!evolve(10))
		{
			System.out.print('.');
		}
		System.out.println();
	}
	
	/**
	 * make the game map evolve by one frame
	 * @param delta time (ms) elapsed since last update
	 * @return yes if something has changed
	 */
	public boolean evolve(int delta)
	{
		int[] pos = new int[2];
		int x, y, dx, dy;
		int effect;
		Snake snake;
		int eaten = 0;
		boolean changement = false;
		
		// we move every alive player p one by one
		for (int p=0; p<players.length; p++)
		{
			snake = players[p];
			if (snake.isLiving() && snake.elapseTime(delta))
			{
				changement = true;
				pos = snake.getHead();
				x = pos[0];
				y = pos[1];
				dx = snake.getDirection()[0];
				dy = snake.getDirection()[1];
				map[x][y].setDirection(dx, dy);
				// we check if the head can reach the target position
				if (!isAccessible(x + dx, y + dy))
				{
					// we kill the snake
					System.out.println("DEATH OF " + snake.getPseudo());
					
					// we manage the end game
					if (killSnake(p) == 0)
					{
						System.out.println("GAME OVER");
					}
				}
				// if the snake is still living, we move it
				else
				{
					// head's movement
					if (map[x + dx][y + dy].getEntity() == Cell.APPLE)
					{
						effect = map[x + dx][y + dy].getDetail();
						eaten++;
					}
					else
					{
						effect = 0;
					}
					/*map[x + dx][y + dy].setEntity(Cell.PLAYER);
					map[x + dx][y + dy].setDetail(p);
					map[x + dx][y + dy].setDirection(dx, dy);*/
					map[x + dx][y + dy].reset(dx, dy, Cell.PLAYER, p);
					snake.replaceHead(x + dx, y + dy);
					
					// tail's movement
					if (!((effect == Cell.A_CLASSIC) || (effect == Cell.A_LENGTH_ONLY)))
					{
						x = snake.getTail()[0];
						y = snake.getTail()[1];
						dx = map[x][y].getxdir();
						dy = map[x][y].getydir();
						map[x][y].reset();
						snake.replaceTail(x + dx, y + dy);
					}
				}
			}
		}
		// apples respawn
		for (int a=0; a<eaten; a++)
		{
			createApple(Cell.A_LENGTH_ONLY);
		}
		
		return changement;
	}
	
	/**
	 * try to create an apple on the board
	 * @param detail type of apple
	 */
	public void createApple(int detail)
	{
		boolean created = false;
		int x, y;
		int n = 0;
		Random random = new Random();
		while ((created == false) && (n < map.length*map.length))
		{
			x = random.nextInt(map.length);
			y = random.nextInt(map[0].length);
			if (map[x][y].getEntity() == Cell.AIR)
			{
				map[x][y].reset(0, 0, Cell.APPLE, detail);
				created = true;
			}
			n++;
		}
	}
	
	/**
	 * get a player in the list
	 * @param who position in the list
	 * @return the snake object of the player
	 */
	public Snake getPlayer(int who)
	{
		return players[who];
	}
	
	/**
	 * get max snakes speed
	 * @return max snakes speed
	 */
	public int getMaxSpeed()
	{
		return maxSpeed;
	}
	
	/**
	 * get max snakes length
	 * @return max snakes length
	 */
	public int getMaxLength()
	{
		return maxLength;
	}
	
	public int getLivingNumber()
	{
		return livingNumber;
	}
}

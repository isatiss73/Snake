package model;

import java.util.ArrayList;
import java.util.Random;

import controler.GameControler;

/**
 * global game object
 */
public class Game
{
	public static final int DEFAULT_MAX_SPEED = 10;
	public static final int DEFAULT_MAX_LENGTH = 10;
	public static final int DEFAULT_MAP_SIZE = 10;
	
	/** easier rules for noobs */
	public static final int OPT_EASY_MODE = 0;
	/** revenge mode for dead players to troll alive players */
	public static final int OPT_REVENGE = 1;
	/** say if the snakes speeds are independant */
	public static final int OPT_INDEP_SPEED = 2;
	/** special quest for bonus for the last alive player */
	public static final int OPT_GOLD_QUEST = 3;
	/** time (ms) between two apples spawn | 0 if spawn on eating */
	public static final int RANDOM_APPLE = 4;
	/** type of apples */
	public static final int APPLES_TYPE = 5;
	/** say if a carcass rest on the map after snake death */
	public static final int OPT_CARCASS = 6;
	/** the only game instance */
	private static Game instance;
	/** the game controler */
	private GameControler controler;
	/** status of every gameplay option */
	private int[] options;
	/** variables for gameplay options */
	private int[] optVar;
	/** 2D matrix of map cells */
	private Cell[][] map;
	/** list of all snakes/players in the game */
	private ArrayList<Snake> players;
	/** current number of living players */
	private int livingNumber;
	/** current number of apples on the map */
	private int livingApples;
	/** maximum snake speed */
	private int maxSpeed;
	/** maximum snake length */
	private int maxLength;
	
	
	/**
	 * complete constructor
	 * @param hsize horizontal map size
	 * @param vsize vertical map size
	 */
	private Game(int hsize, int vsize)
	{
		instance = this;
		controler = new GameControler(0, -1);
		reset(hsize, vsize);
	}

	
	/**
	 * default constructor
	 */
	private Game()
	{
		this(DEFAULT_MAP_SIZE, DEFAULT_MAP_SIZE);
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
	 */
	public void reset(int hsize, int vsize)
	{
		// options
		options = new int[]{0, 0, 0, 0, 0, 0, 0};
		optVar = new int[]{0, 0, 0, 0, 1, Cell.A_LENGTH_ONLY, 0};
		
		// creation of tablesy
		map = new Cell[hsize][vsize];
		players = new ArrayList<Snake>();
		
		// variables initialisation
		livingApples = 0;
		livingNumber = 0;
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
	 * rich ASCII representation
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
	
	/**
	 * smooth ASCII representation
	 * @return
	 */
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
	 * @param x initial horizontal position
	 * @param y initial vertical position
	 * @param length initial length
	 * @param xdir initial horizontal direction
	 * @param ydir initial vertical direction
	 */
	public void createSnake(int x, int y, int length, int xdir, int ydir)
	{
		if (x - length >= -1)
		{
			livingNumber++;
			players.add(new Snake(x, y, length, xdir, ydir));
			for (int i=0; i<length; i++)
			{
				map[x-i][y].setDirection(xdir, ydir);
				map[x-i][y].setEntity(Cell.PLAYER);
				map[x-i][y].setDetail(players.size() - 1);
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
		// we update game and snake status
		Snake snake = players.get(who);
		snake.setLiving(false);
		livingNumber--;
		
		// we get snake informations to wander it
		int x = snake.getTail()[0];
		int y = snake.getTail()[1];
		int length = snake.getLength();
		int dx, dy;
		
		// we replace every cell by air ffrom tail to head
		for (int i=0; i<length; i++) {
			dx = map[x][y].getxdir();
			dy = map[x][y].getydir();
			
			map[x][y].setEntity(Cell.AIR);
			
			x += dx;
			y += dy;
		}
		
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
	public boolean evolve(int delta) {
		int[] pos = new int[2];
		int x, y, dx, dy;
		int effect;
		Snake snake;
		int eaten = 0;
		boolean changement = false;
		
		// we move every alive player p one by one
		for (int p=0; p<players.size(); p++) {
			snake = players.get(p);
			if(snake.isLiving()==false) {
				//killSnake(p);
			}
			if (snake.isLiving() && snake.elapseTime(delta)) {
				changement = true;
				pos = snake.getHead();
				x = pos[0];
				y = pos[1];
				dx = snake.getDirection()[0];
				dy = snake.getDirection()[1];
				map[x][y].setDirection(dx, dy);
				// we check if the head can reach the target position
				if (!isAccessible(x + dx, y + dy)) {
					// we kill the snake
					System.out.println("DEATH OF " + snake.getPseudo());

					// we manage the end game
					if (killSnake(p) == 0) {
						System.out.println("GAME OVER");
						//map[x][y].reset(dx, dy, Cell.AIR, 0);
					}
				}
				// if the snake is still living, we move it
				else {
					// head's movement
					if (map[x + dx][y + dy].getEntity() == Cell.APPLE) {
						effect = map[x + dx][y + dy].getDetail();
						eaten++;
					}
					else {
						effect = 0;
					}
					map[x + dx][y + dy].reset(dx, dy, Cell.PLAYER, p);
					snake.replaceHead(x + dx, y + dy);
					
					// tail's movement
					if (!((effect == Cell.A_CLASSIC) || (effect == Cell.A_LENGTH_ONLY))) {
						x = snake.getTail()[0];
						y = snake.getTail()[1];
						dx = map[x][y].getxdir();
						dy = map[x][y].getydir();
						map[x][y].reset();
						snake.replaceTail(x + dx, y + dy);
					}
					else {
						snake.addLength(1);
					}
					if ((effect == Cell.A_CLASSIC) || (effect == Cell.A_SPEED_ONLY)) {
						snake.addSpeed(0.2f);
					}
				}
			}
		}
		
		// apples respawn
		livingApples -= eaten;
		if (controler.isHost()) {
			if (options[RANDOM_APPLE] == 0) {
				int max = optVar[RANDOM_APPLE] - livingApples;
				for (int a=0; a<max; a++) {
					createApple(optVar[APPLES_TYPE]);
				}
			}
			else {
				optVar[RANDOM_APPLE] -= delta;
				if (optVar[RANDOM_APPLE] <= 0) {
					createApple(optVar[APPLES_TYPE]);
					optVar[RANDOM_APPLE] += options[RANDOM_APPLE]*(0.5 + Math.random());
				}
			}
		}
		
		return changement;
	}
	
	/**
	 * try to create an apple on the board
	 * @param detail type of apple
	 */
	public void createApple(int detail) {
		boolean created = false;
		int x, y;
		int n = 0;
		Random random = new Random();
		while ((created == false) && (n < map.length*map.length)) {
			x = random.nextInt(map.length);
			y = random.nextInt(map[0].length);
			if (map[x][y].getEntity() == Cell.AIR) {
				map[x][y].reset(0, 0, Cell.APPLE, detail);
				created = true;
				controler.sendMessage("A:" + detail + ':' + x + ':' + y);
			}
			n++;
		}
		livingApples++;
	}
	
	/**
	 * force to place an apple on a cell
	 * @param detail type of apple
	 * @param x horizontal position on the map
	 * @param y vertical position on the map
	 */
	public void setApple(int detail, int x, int y) {
		if (isAccessible(x, y))
			map[x][y].reset(0, 0, Cell.APPLE, detail);
	}
	
	/**
	 * change gameplay options set
	 * @param options
	 */
	public void setOptions(int[] options) {
		this.options = options;
	}
	
	/** change options variables set */
	public void setOptVar(int[] optVar) {
		this.optVar = optVar;
	}
	
	/** 
	 * randomly add walls of the map
	 * @param detail type of wall (no influence today)
	 */
	public void createWall(int detail) {
		// randomly choose a number of walls
		Random randomwall = new Random();
		int nbWall = randomwall.nextInt((int)(map.length-4)) + 1;
		
		// we add each wall one by one
		for (int i=0; i<nbWall; i++) {
			boolean created = false;
			int x, y;
			int n = 0;
			Random random = new Random();
			
			// we try to add the wall while the cell is not accurate
			while ((created == false) && (n < map.length*map.length)) {
				x = random.nextInt(map.length);
				y = random.nextInt(map[0].length);
				
				// if the cell is empty, we create the wall
				if (map[x][y].getEntity() == Cell.AIR) {
					map[x][y].reset(0, 0, Cell.WALL, detail);
					created = true;
				}
				n++;
			}
		}
	}
	
	public void setWall(int detail, int x, int y) {
		map[x][y].reset(0, 0, Cell.WALL, detail);
	}
	
	/**
	 * set a new controler to the game
	 * @param newControler the new controler
	 */
	public void setControler(GameControler newControler) {
		controler = newControler;
	}
	
	/**
	 * get the controler instance
	 * @return the controler instance
	 */
	public GameControler getControler() {
		return controler;
	}
	
	/**
	 * get a player in the list
	 * @param who position in the list
	 * @return the snake object of the player
	 */
	public Snake getPlayer(int who) {
		return players.get(who);
	}
	
	/**
	 * indicate the total number of snakes in the game
	 * @return the length of the snakes list
	 */
	public int getNumberOfSnakes() {
		return players.size();
	}
	
	/**
	 * get max snakes speed
	 * @return max snakes speed
	 */
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	/**
	 * get max snakes length
	 * @return max snakes length
	 */
	public int getMaxLength() {
		return maxLength;
	}
	
	/**
	 * get number of living players
	 * @return this number
	 */
	public int getLivingNumber() {
		return livingNumber;
	}
	
	/**
	 * return the map of cells
	 * @return a 2D cell array
	 */
	public Cell[][] getMap() {
		return map;
	}
}

package model;


/**
 * snake object
 */
public class Snake
{
	private int playerID;
	private int length;
	private int speed;
	private int[] direction;
	private int score;
	private String pseudo;
	private int snakeSkin;
	private int mapSkin;
	private int appleSkin;
	private boolean living;
	private int power;
	private int[] head;
	private int[] tail;
	
	/**
	 * complete constructor
	 * @param x start head horizontal position
	 * @param y start head vertical position
	 */
	public Snake(int x, int y, int length, int xdir, int ydir)
	{
		playerID = 0;
		this.length = length;
		speed = 1;
		head = new int[2];
		head[0] = x;
		head[1] = y;
		tail = new int[2];
		tail[0] = x - (length - 1) * xdir;
		tail[1] = y - (length - 1) * ydir;
		direction = new int[2];
		direction[0] = xdir;
		direction[1] = ydir;
		score = 0;
		pseudo = "Anonymous";
		snakeSkin = 0;
		mapSkin = 0;
		appleSkin = 0;
		living = true;
		power = 0;
	}
	
	/**
	 * default constructor
	 */
	public Snake()
	{
		this(1, 1, 2, 1, 0);
	}
	
	/**
	 * get player id
	 * @return player id
	 */
	public int getID()
	{
		return playerID;
	}
	
	/**
	 * get snake's head position
	 * @return head 2D position (x, y)
	 */
	public int[] getHead()
	{
		return head;
	}
	
	/**
	 * get snake's tail position
	 * @return tail 2D position (x, y)
	 */
	public int[] getTail()
	{
		return tail;
	}
	
	/**
	 * get snake's length
	 * @return snake's length in squares
	 */
	public int getLength()
	{
		return length;
	}
	
	/**
	 * get snake's speed
	 * @return snake's speed in ???
	 */
	public int getSpeed()
	{
		return speed;
	}
	
	/**
	 * get snake's horizontal direction
	 * @return (xdir, ydir)
	 */
	public int[] getDirection()
	{
		return direction;
	}
	
	/**
	 * get player's score in a game
	 * @return player's score in points
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * get player's pseudo
	 * @return player's text pseudo
	 */
	public String getPseudo()
	{
		return pseudo;
	}
	
	/**
	 * get snake skin id
	 * @return snake skin id
	 */
	public int getSnakeSkin()
	{
		return snakeSkin;
	}
	
	/**
	 * get map skin id
	 * @return map skin id
	 */
	public int getMapSkin()
	{
		return mapSkin;
	}
	
	/**
	 * get apple skin id
	 * @return apple sin id
	 */
	public int getAppleSkin()
	{
		return appleSkin;
	}
	
	/**
	 * know if the snake is living
	 * @return true if snake the snake is living
	 */
	public boolean isLiving()
	{
		return living;
	}
	
	/**
	 * get snake power up id
	 * @return snake power up id
	 */
	public int getPower()
	{
		return power;
	}
	
	/**
	 * change the player id
	 * @param id new player id
	 */
	public void setID(int id)
	{
		this.playerID = id;
	}
	
	/**
	 * change the snake's length (absolute)
	 * @param length new snake's length
	 */
	public void setLength(int length)
	{
		if (length > 0)
			this.length = length;
	}
	
	/**
	 * change the snake's length (relative)
	 * @param delta difference to add/remove
	 */
	public void addLength(int delta)
	{
		if (length + delta > 0)
			length += delta;
	}
	
	/**
	 * change the snake's speed (absolute)
	 * @param speed new snake's speed
	 */
	public void setSpeed(int speed)
	{
		if (speed > 0)
			this.speed = speed;
	}
	
	/**
	 * change the snake's speed (relative)
	 * @param delta difference to add/remove
	 */
	public void addSpeed(int delta)
	{
		if (speed + delta > 0)
			speed += delta;
	}
	
	/**
	 * change the snake's direction
	 * @param xdir new horizontal direction
	 * @param ydir new vertical direction
	 */
	public void setDirection(int xdir, int ydir)
	{
		if ((direction[0] + xdir != 0) && (direction[1] + ydir != 0))
		{
			direction[0] = xdir;
			direction[1] = ydir;
		}
	}
	
	/**
	 * change player's score (absolute)
	 * @param score new score
	 */
	public void setScore(int score)
	{
		if (score >= 0)
			this.score = score;
	}
	
	/**
	 * change player's score (relative)
	 * @param delta difference to add/remove
	 */
	public void addScore(int delta)
	{
		score += delta;
	}
	
	/**
	 * change player's pseudo
	 * @param pseudo new player's pseudo
	 */
	public void setPseudo(String pseudo)
	{
		this.pseudo = pseudo;
	}
	
	/**
	 * change the snake skin id
	 * @param id new snake skin id
	 */
	public void setSnakeSkin(int id)
	{
		snakeSkin = id;
	}
	
	/**
	 * change the map skin id
	 * @param id new map skin id
	 */
	public void setMapSkin(int id)
	{
		mapSkin = id;
	}
	
	/**
	 * chnage the apple skin id
	 * @param id new apple skin id
	 */
	public void setAppleSkin(int id)
	{
		appleSkin = id;
	}
	
	/**
	 * change the living status of the snake
	 * @param living true if living, false if dead
	 */
	public void setLiving(boolean living)
	{
		this.living = living;
	}
	
	/**
	 * change the snake's pwoer up id
	 * @param id new power up id
	 */
	public void setPower(int id)
	{
		power = id;
	}

	/**
	 * replace the head position
	 * @param x new horizontal position
	 * @param y new vertical position
	 */
	public void replaceHead(int x, int y)
	{
		head[0] = x;
		head[1] = y;
	}
	
	/**
	 * replace the tail position
	 * @param x new horizontal position
	 * @param y new vertical position
	 */
	public void replaceTail(int x, int y)
	{
		tail[0] = x;
		tail[1] = y;
	}
}


package model;


/**
 * Snake object
 */
public class Snake
{
	private int playerID;
	private int length;
	private int speed;
	private int direction;
	private int score;
	private String pseudo;
	private int snakeSkin;
	private int mapSkin;
	private int appleSkin;
	private boolean living;
	private int power;
	// ajouter head et tail et ce qui va avec
	
	
	/**
	 * default constructor
	 */
	public Snake()
	{
		playerID = 0;
		length = 3;
		speed = 1;
		direction = 0;
		score = 0;
		pseudo = "Anonymous";
		snakeSkin = 0;
		mapSkin = 0;
		appleSkin = 0;
		living = true;
		power = 0;
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
	 * get snake's direction id
	 * @return 0 (up) / 1(right) / 2 (down) / 3 (left)
	 */
	public int getDirection()
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
	 * @param direction new direction (0/1/2/3)
	 */
	public void setDirection(int direction)
	{
		if ((direction >= 0) && (direction <= 3))
			this.direction = direction;
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
	 * change the snake's power up id
	 * @param id new power up id
	 */
	public void setPower(int id)
	{
		power = id;
	}
	
	
	/**
	 * move the snake with it's direction
	 */
	public void move()
	{
		
	}
}


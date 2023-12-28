package model;

/**
 * game's board cell informations
 */
public class Cell
{
	public static final int AIR = 0;
	public static final int WALL = 1;
	public static final int APPLE = 2;
	public static final int PLAYER = 3;
	
	public static final int A_NOTHING = 0;
	public static final int A_CLASSIC = 1;
	public static final int A_LENGTH_ONLY = 2;
	public static final int A_SPEED_ONLY = 3;
	
	private int xdir;
	private int ydir;
	private int entity;
	private int detail;
	
	/**
	 * complete constructor
	 * @param xdir initial hozirontal direction
	 * @param ydir initial vertical direction
	 * @param entity initial entity on the cell
	 * @param detail initial detail of the cell
	 */
	public Cell(int xdir, int ydir, int entity, int detail)
	{
		reset(xdir, ydir, entity, detail);
	}
	
	/**
	 * default constructor
	 */
	public Cell()
	{
		reset(0, 0, 0, 0);
	}
	
	/**
	 * standard cell reset with nothing
	 */
	public void reset()
	{
		reset(0, 0, 0, 0);
	}
	
	/**
	 * cell reset with every information
	 * @param xdir horizontal direction of the entity
	 * @param ydir vertical direction of the entity
	 * @param entity type of entity
	 * @param detail detail of the entity
	 */
	public void reset(int xdir, int ydir, int entity, int detail)
	{
		this.xdir = xdir;
		this.ydir = ydir;
		this.entity = entity;
		this.detail = detail;
	}
	
	/**
	 * change entity's direction
	 * @param xdir new horizontal direction
	 * @param ydir new vertical direction
	 */
	public void setDirection(int xdir, int ydir)
	{
		this.xdir = xdir;
		this.ydir = ydir;
	}
	
	/**
	 * change the entity on the cell
	 * @param entity new entity type
	 */
	public void setEntity(int entity)
	{
		this.entity = entity;
	}
	
	/**
	 * change entity's detail information
	 * @param detail new detail information
	 */
	public void setDetail(int detail)
	{
		this.detail = detail;
	}
	
	/**
	 * get entity's horizontal direction
	 * @return 1 if right, -1 if left, 0 either
	 */
	public int getxdir()
	{
		return xdir;
	}
	
	/**
	 * get entity's verticla direction
	 * @return 1 if bottom, -1 if up, 0 either
	 */
	public int getydir()
	{
		return ydir;
	}
	
	/**
	 * get the entity type on the cell
	 * @return the entity type so
	 */
	public int getEntity()
	{
		return entity;
	}
	
	/**
	 * get entity's detail information
	 * @return entity's detail information so
	 */
	public int getDetail()
	{
		return detail;
	}
	
	/**
	 * partial string representation
	 */
	public String toString()
	{
		String res = "";
		res += entity + "" + detail;
		return res;
	}
	
	/**
	 * char representation of the entity type
	 * @return the char representig the entity
	 */
	public char toChar()
	{
		if (entity == AIR) return ' ';
		if (entity == PLAYER) return (char) (detail + '0');
		if (entity == APPLE) return 'A';
		if (entity == WALL) return 'X';
		return '?';
	}
}

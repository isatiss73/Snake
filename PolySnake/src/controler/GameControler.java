package controler;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Game;

public class GameControler
{
	private int leftID;
	private int rightID;
	private boolean twoLocal;
	
	public GameControler(int leftID)
	{
		setIDs(leftID, -1);
	}
	
	public GameControler(int leftID, int rightID)
	{
		setIDs(leftID, rightID);
	}
	
	public void setIDs(int left, int right)
	{
		if (left >= 0)
		{
			leftID = left;
			if (right >= 0)
			{
				rightID = right;
				twoLocal = true;
			}
			else
			{
				twoLocal = false;
			}
		}
	}
	
	public void keyReleased(KeyEvent event)
	{
		KeyCode keyCode = event.getCode();
        String keyText = keyCode.getName();
        System.out.println("_" + keyText + "_");
        Game game = Game.getInstance();
        boolean left = true;
        
        switch(keyText)
        {
        case "D":
        	game.getPlayer(leftID).setDirection(1, 0);
        	break;
        case "Q":
        	game.getPlayer(leftID).setDirection(-1, 0);
        	break;
        case "S":
        	game.getPlayer(leftID).setDirection(0, 1);
        	break;
        case "Z":
        	game.getPlayer(leftID).setDirection(0, -1);
        	break;
        default:
        	left = false;
        	break;
        }
        if (!left && twoLocal)
        {
        	switch(keyText)
            {
            case "Right":
            	game.getPlayer(rightID).setDirection(1, 0);
            	break;
            case "Left":
            	game.getPlayer(rightID).setDirection(-1, 0);
            	break;
            case "Down":
            	game.getPlayer(rightID).setDirection(0, 1);
            	break;
            case "Up":
            	game.getPlayer(rightID).setDirection(0, -1);
            	break;
            default:
            	left = false;
            	break;
            }
        }
	}
}

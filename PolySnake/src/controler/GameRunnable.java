package controler;

import model.Game;

public class GameRunnable implements Runnable
{
	@Override
	public void run()
	{
		Game game = Game.getInstance();
		int yesterday = (int) System.currentTimeMillis();
		int today;
		
		while (game.getLivingNumber() > 0)
		{
			today = (int) System.currentTimeMillis();
			if (game.evolve(today - yesterday))
			{
				System.out.println(game);
			}
			yesterday = today;
		}
	}

}

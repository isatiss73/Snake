package network;

import java.net.DatagramPacket;
import java.util.Scanner;

public class UDPClientMessage extends UDPClientBuilder implements Runnable
{
	private Scanner scanner;
	private String message;
	
	private void print(Object o)
	{
		System.out.print("client: ");
		System.out.println(o);
	}
	
	private void tryToSend(String msg)
	{
		try
		{
			setConnection();
			req = new DatagramPacket(new byte[size],0,size,isA.getAddress(),isA.getPort());
			setMessage(req, msg);
			socket.send(req);
			socket.close();
			Thread.sleep(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();;
		}
	}
	
	public void run()
	{
		scanner = new Scanner(System.in);
		message = "hello";
		print("'exit' pour quitter");
		
		while (!message.equals("exit"))
		{
			tryToSend(message);
			message = scanner.nextLine();
			switch(message)
			{
			case "/polytech":
				message = "                       .///.                      \r\n"
						+ "               /////////////////////.             \r\n"
						+ "            ///////////////////////////.          \r\n"
						+ "         /////////////////////////////////.       \r\n"
						+ "       ////////////            *////////////      \r\n"
						+ "      //////////                   */////////     \r\n"
						+ "     /////////                       *////////    \r\n"
						+ "                                      *///////.   \r\n"
						+ "                                       ////////   \r\n"
						+ "                                       ////////   \r\n"
						+ "                                       ////////   \r\n"
						+ "    //////////                        /////////   \r\n"
						+ "    ///////////                     //////////    \r\n"
						+ "    //////////////               ////////////     \r\n"
						+ "    ///////////////////////////////////////       \r\n"
						+ "    /////////////////////////////////////         \r\n"
						+ "     /////// /////////////////////////            \r\n"
						+ "      //////       //////////////                 \r\n"
						+ "        ////                                      \r\n";
				break;
			case "/dormeur":
				message = "C’est un trou de verdure où chante une rivière,\r\n"
						+ "Accrochant follement aux herbes des haillons\r\n"
						+ "D’argent ; où le soleil, de la montagne fière,\r\n"
						+ "Luit : c’est un petit val qui mousse de rayons.\r\n"
						+ "\r\n"
						+ "Un soldat jeune, bouche ouverte, tête nue,\r\n"
						+ "Et la nuque baignant dans le frais cresson bleu,\r\n"
						+ "Dort ; il est étendu dans l’herbe, sous la nue,\r\n"
						+ "Pâle dans son lit vert où la lumière pleut.\r\n"
						+ "\r\n"
						+ "Les pieds dans les glaïeuls, il dort. Souriant comme\r\n"
						+ "Sourirait un enfant malade, il fait un somme :\r\n"
						+ "Nature, berce-le chaudement : il a froid.\r\n"
						+ "\r\n"
						+ "Les parfums ne font pas frissonner sa narine ;\r\n"
						+ "Il dort dans le soleil, la main sur sa poitrine,\r\n"
						+ "Tranquille. Il a deux trous rouges au côté droit.";
				break;
			}
		}
		print("deconnection");
	}
}

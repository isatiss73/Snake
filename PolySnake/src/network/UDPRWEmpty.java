package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public class UDPRWEmpty
{
	private byte[] sB;
	private byte[] array;
	private int p;

	/**
	 * prepare un paquet a envoyer
	 * @param isAR adresse du socket
	 * @param size taille du paquet
	 * @return paquet a envoyer
	 * @throws IOException
	 */
	protected DatagramPacket getSendingPacket(InetSocketAddress isAR, int size) throws IOException
	{
		return new DatagramPacket(new byte[size], 0, size, isAR.getAddress(), isAR.getPort());
	}
	
	/**
	 * prepare un message a envoyer
	 * @param isAR adresse du socket
	 * @param message corps du message
	 * @param size taille du paquet
	 * @return paquet a envoyer
	 * @throws IOException
	 */
	protected DatagramPacket getSendingPacket(InetSocketAddress isAR, String message, int size) throws IOException
	{
		sB = toBytes(message, new byte[size]);
		return new DatagramPacket(sB, 0, size, isAR.getAddress(), isAR.getPort());
	}

	/**
	 * prepare un paquet a recevoir
	 * @param size taille du paquet
	 * @returnpaquet a recevoir
	 * @throws IOException
	 */
	protected DatagramPacket getReceivingPacket(int size) throws IOException
	{
		return new DatagramPacket(new byte[size], size);
	}
	
	/**
	 * je ne sais pas trop
	 * @param packet packet a lire
	 * @param message corps du message
	 * @throws IOException
	 */
	protected void setMessage(DatagramPacket packet, String message) throws IOException
	{
		sB = toBytes(message, packet.getData());
	}
	
	/**
	 * traduit un message en donnees binaires
	 * @param message corps du message
	 * @param buffer conteneur des donnees
	 * @return buffer rempli
	 */
	protected byte[] toBytes(String message, byte[] buffer)
	{
		array = message.getBytes();
		if (array.length < buffer.length)
		{
			for (int i=0; i<array.length; i++)
			{
				buffer[i] = array[i];
			}
		}
		return buffer;
	}
	
	/**
	 * lit le corps textuel d'un message
	 * @param packet packet contenant le message
	 * @return texte du corps du message
	 */
	protected String getMessage(DatagramPacket packet)
	{
		sB = packet.getData();
		for (int i=0; i<sB.length; i++)
		{
			if (sB[i] == 0)
			{
				p = i;
				i = sB.length;
			}
		}
		return new String(packet.getData(), 0, p);
	}
}

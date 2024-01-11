package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class TCPMessage implements Runnable
{
	protected Random random = new Random();
	protected int count;
	protected byte[] buffer;
	private final int size = 8192;

	/** The set method for the buffer. */
	void setStreamBuffer(int size)
	{
		if (size > 0) buffer = new byte[size];
		else buffer = new byte[this.size];
	}
	
	/**
	 * the only interesting method for client
	 * @param out the output stream where you want to write
	 * @param msOut the message you want to right
	 * @throws IOException an error if you cannot write correctly
	 */
	void writeMessage(OutputStream out, String msOut) throws IOException
	{
		if((out!=null)&(msOut!=null))
		{
			fillChar(msOut);
			out.write(buffer);
			out.flush();
			clearBuffer();
		}
	}
	
	/**
	 * white a lot of data in an output stream
	 * @param out the output stream you want to spam
	 * @param loop the amount of lines you want to send
	 * @throws IOException an error if you cannot write correctly
	 */
	void loopWriteMessage(OutputStream out, int loop) throws IOException
	{
		for(int i=0;i<loop;i++)
		{
			/*fillAtRandom(buffer);
			out.write(buffer);
			out.flush();*/
			writeMessage(out, "anticonstitutionnellement " + i);
		}
	}
	
	/**
	 * fill a byte buffer with random data
	 * @param buffer the byte array to write on
	 */
	private void fillAtRandom(byte[] buffer)
	{
		for(int i=0; i<buffer.length; i++)
			buffer[i] = (byte) random.nextInt(256);
	}
	
	/**
	 * fill the buffer with a text message
	 * @param msOut message to write
	 */
	private void fillChar(String msOut)
	{
		if(msOut!=null && msOut.length() < buffer.length)
			for(int i=0;i<msOut.length();i++)
				buffer[i] = (byte)msOut.charAt(i);
	}
	
	/**
	 * clear the buffer, what did you expect ?
	 */
	void clearBuffer()
	{
		for(int i=0;i<buffer.length;i++)
			buffer[i] = 0;
	}
	
	/**
	 * the only interesting method for server
	 * @param in the input stream when you want to read
	 * @return the text recieved in the input stream
	 * @throws IOException an error in reading the stream
	 */
	String readMessage(InputStream in) throws IOException
	{
		if(in != null)
		{
			in.read(buffer);
			count = count();
			if(count>0)
				return new String(buffer,0,count);
		}
		return null;
	}
	
	/**
	 * return the size of the written part of the buffer
	 * @return see the brief...
	 */
	protected int count()
	{
		for(int i=0;i<buffer.length;i++)
			if(buffer[i] == 0)
				return i;
		return buffer.length;
	}

	@Override
	public void run()
	{
		// nothing je crois
	}
}

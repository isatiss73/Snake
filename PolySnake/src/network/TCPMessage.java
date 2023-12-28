package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class TCPMessage extends TCPBuffer
{
	protected Random random = new Random();
	protected int count;
	
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
	
	private void fillAtRandom(byte[] buffer)
	{
		for(int i=0; i<buffer.length; i++)
			buffer[i] = (byte) random.nextInt(256);
	}
	
	private void fillChar(String msOut)
	{
		if(msOut!=null && msOut.length() < buffer.length)
			for(int i=0;i<msOut.length();i++)
				buffer[i] = (byte)msOut.charAt(i);
	}
	
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
	
	protected int count()
	{
		for(int i=0;i<buffer.length;i++)
			if(buffer[i] == 0)
				return i;
		return buffer.length;
	}
}

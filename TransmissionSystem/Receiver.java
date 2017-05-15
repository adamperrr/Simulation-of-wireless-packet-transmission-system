package TransmissionSystem;

import java.util.*;

import Generators.RandomGenerator;

/**
 * 
 * @author Adam Pertek
 * 
 * Simulation of wireless packet transmission system.
 * System implements protocol CSMA with extortion of 
 * transmission with probability of 1.
 * 
 * */

/**
 * Class Receiver
 * Sends packets to channel and waits for the response.
 * */
public class Receiver implements Device
{
	/**
	 * Constructor sets id of the Receiver.
	 * @param id - id of the device.
	 * */
	public Receiver(int id, TransmissionSystem ts)
	{
		this.id = id;
		this.rand = ts.getRandObj();
		transmissionSystem = ts;
	}
	
	/**
	 * Returns id of receiver
	 * @returns id - id of receiver
	 * */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Gets a packet received by receiver from channel
	 * @returns receivedPacket
	 * */
	public Packet getPacket()
	{
		return receivedPacket;
	}
	
	/**
	 * Pushes packet to the Receiver
	 * @param _packet - packet for the receiver
	 * */
	public void pushPacket(Packet packet)
	{
		if( !packet.isACK() && packet.isCorrect() )
		{
			setBusy();
			setACKready();
			packet.setArrivalToReceiverTime(transmissionSystem.getClock());
		}

		receivedPacket = packet;
	}
	
	/**
	 * Checks if device is in the busy state.
	 * */
	public boolean isBusy()
	{
		return isBusy;
	}
	
	/**
	 * Sets device in the busy state.
	 * */
	public void setBusy()
	{
		isBusy = true;
	}
	
	/**
	 * Sets device in the free state.
	 * */
	public void setFree()
	{ 
		isBusy = false;
	}

	/**
	 * Checks if device is ready to send ACK
	 * */
	public boolean getACKstate()
	{
		return readyACK;
	}
	
	/**
	 * Sets device as ready to send ACK.
	 * */
	public void setACKready()
	{
		readyACK = true;
	}
	
	/**
	 * Sets device as NOT ready to send ACK.
	 * */
	public void setACKnotReady()
	{ 
		readyACK = false;
	}
	
	private int id = -1; // Id of this object of device.
	private Packet receivedPacket = null; // Packet received from the channel.
	private RandomGenerator rand = null; // Object of random generator.
	private boolean isBusy = false; // Occupancy state of the device.
	private boolean readyACK = false; // Says when receiver should send a ACK packet
	private TransmissionSystem transmissionSystem = null; // To get current time
}

package TransmissionSystem;

import java.util.*;

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
 * 
 * Class Channel is a connection between transmitters an receivers.
 * 
 * It has two states: busy and free and stores sending packet.
 * 
 * */
public class Channel
{
	/**
	 * Constructor of the class.
	 * @param transmitters - vector of transmitters in the system.
	 * @param receivers - vector of receivers in the system.
	 * */
	public Channel( Vector <Transmitter> transmitters, Vector <Receiver> receivers){
		this.transmitters = transmitters;
		this.receivers  = receivers;
	}
	
	/**
	 * Pushes packet from the devices to the channel.
	 * @param packet Packet to push to the channel.
	 * */
	public void pushPacket(Packet packet)
	{
		buffer.add(packet);
		setBusy();
	}
	
	/**
	 * Checks if channel is busy
	 * */
	public boolean isBusy()
	{
		return isBusy;
	}
	
	/**
	 * Checks if channel is free
	 * */
	public boolean isFree()
	{
		return !isBusy;
	}
	
	/**
	 * Sets the channel in the busy state.
	 * */
	public void setBusy()
	{
		isBusy = true;
	}
	
	/**
	 * Sets the channel in the free state.
	 * */
	public void setFree()
	{ 
		isBusy = false;
	}
	
	/**
	 * Returns number of packets in the channel.
	 * @returns number - number of packets in the channel
	 * */
	public int countPackets()
	{
		return buffer.size();
	}
	
	/**
	 * Returns buffer queue
	 * @returns buffer
	 * */
	public ArrayList<Packet> getBuffer()
	{
		return buffer;
	}
	
	/*
	 * Buffer of packets in the channel.
	 * */
	private ArrayList<Packet> buffer = new ArrayList<>();
	
	/*
	 * Occupancy state of the channel.
	 * */
	private boolean isBusy = false;
	
	/*
	 * Vector of transmitters.
	 * */
	private Vector <Transmitter> transmitters = null;

	/*
	 * Vector of receivers.
	 * */
	private Vector <Receiver> receivers  = null;
}

package Events.ConditionalEvents;

import java.util.ArrayList;
import java.util.Vector;

import Events.ConditionalEvent;
import Events.TimeEvent;
import Events.TimeEvents.ListenIfChannelIsFreeEvent;
import TransmissionSystem.*;

/**
 * Class representing a event of collision in the channel.
 * */
public class CollisionEvent implements ConditionalEvent
{
	/**
	 * CollisionEvent constructor
	 * */
	public CollisionEvent(TransmissionSystem ts)
	{
		channel = ts.getChannel();
		transmissionSystem = ts;
	}
	
	/**
	 * Check if there are more than one packets in the channel
	 * If function returns true it means there is a collision in the channel
	 * */			
	public boolean condition()
	{	
		boolean thereAreCorrectPackets = false; // 
		boolean thereAreMoreThanOnePackets = (channel.getBuffer().size() > 1); // There are more than one packets in the channel
		if(thereAreMoreThanOnePackets)
		{
			for(Packet p : channel.getBuffer())
			{
				if(p.isCorrect())
				{
					thereAreCorrectPackets = true;
					break;
				}
			}
		}
		return thereAreCorrectPackets && thereAreMoreThanOnePackets;
	}
	
	/**
	 * If there are more than one packets in the channel buffer
	 * function sets them faulty
	 * */	
	public void execute()
	{		
		if(condition()){ // If there's collision
			for(Packet p : channel.getBuffer())
				p.setFaulty();
			//" + transmissionSystem.getClock() + ":
			if(TransmissionSystem.logsON)
				System.out.println("-> CollisionEvent: Collision in the channel");
		}
	}
	
	/*
	 * Object of the channel of the system
	 * */
	private Channel channel = null;

	private TransmissionSystem transmissionSystem = null;
}

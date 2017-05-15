package Events.TimeEvents;
import java.util.ArrayList;
import java.util.Vector;

import Events.TimeEvent;
import Generators.RandomGenerator;
import TransmissionSystem.*;

public class ArrivalToTheReceiverEvent implements TimeEvent
{
	/**
	 * Constructor of the class
	 * */		
	public ArrivalToTheReceiverEvent(double clock, int ReceiverId, TransmissionSystem ts)
	{
		packetAddress = ReceiverId;
		
		receiver = ts.getReceivers().get(packetAddress);
		channel = ts.getChannel();
		
		rand = rand = ts.getRandObj();
		
		Transmitter tr = ts.getTransmitters().get(packetAddress);		
		time = clock + tr.transmissionSettings.getPassTime();
		
		transmissionSystem = ts;
	}

	/**
	 * Function starting execution of event.
	 * */		
	public void execute()
	{
		time = TransmissionSystem.correctPrecision(time);
		action();
	}
	
	/**
	 * Time getter
	 * @returns time - time of event
	 * */	
	public double getTime()
	{
		return time;
	}
	
	/*
	 * Watches packets in the buffer of channel looking for a packet from given address
	 * If packet is not faulty than is pushed to the receiver buffer.
	 * Else it is removed.
	 * 	*/
	private void action()
	{
		Packet arrivingPacket = null;
		Packet tempPacket = null;
		int i = 0;
		
		// Looking for a packet for specified address which is not a ACK packet
		for(; i < channel.getBuffer().size(); i++)
		{
			tempPacket = channel.getBuffer().get(i);
			if(tempPacket.getAddress() == packetAddress && !tempPacket.isACK())
			{
				arrivingPacket = tempPacket;
				break;
			}

		}
		
		if(arrivingPacket != null)
		{
			channel.getBuffer().remove(i); // Remove packet form channel buffer
			
			receiver.pushPacket(arrivingPacket);
			
			System.out.println(time + ": ArrivalToTheReceiverEvent: Packet pushed to the receiver "+ receiver.getId() +" from the channel.");
		}
		
	}
	
	private double time = 0.0; // Time when event will be executed
	private int packetAddress = -1; // Address of receivers and transmitters

	// References to elements needed in event's action
	private Channel channel = null;
	private Receiver receiver = null;
	
	private RandomGenerator rand = null;
	private TransmissionSystem transmissionSystem = null;
}

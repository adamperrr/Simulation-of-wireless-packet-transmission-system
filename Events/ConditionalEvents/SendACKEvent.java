package Events.ConditionalEvents;

import java.util.ArrayList;
import java.util.Vector;

import Events.ConditionalEvent;
import Events.TimeEvent;
import Events.TimeEvents.*;
import TransmissionSystem.*;

/**
 * Class representing a event of sending ACK to the channel.
 * */
public class SendACKEvent implements ConditionalEvent{
	/**
	 * SendACKEvent constructor
	 * */
	public SendACKEvent(TransmissionSystem ts) {
		receivers = ts.getReceivers();
		channel = ts.getChannel();
		transmissionSystem = ts;
	}
	
	/**
	 * Check if there are packets to confirm by ACK
	 * */			
	public boolean condition(){
		for(Receiver r : receivers)
		{
			if(r.isBusy() && r.getACKstate())
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * If there's a packet to confirm sends ACK and plans its arrival to the proper transmitter by ACKArrivalEvent
	 * */	
	public void execute(){
		if(condition()){ // If there's a packet to confirm
			for(Receiver r : receivers)
			{
				if(r.isBusy() && r.getACKstate())
				{
					Packet ACK = new Packet(r.getId(), true);
					channel.pushPacket(ACK);

					if(TransmissionSystem.logsON)
					{
						System.out.println("-> SendACKEvent: ACK packet sent to channel from receiver " + r.getId());
					}
					
					r.setFree();
					r.setACKnotReady();
				}
			}
		}
	}
	
	/*
	 * Object of the channel of the system
	 * */
	private Channel channel = null;
	private Vector<Receiver> receivers = null;
	private TransmissionSystem transmissionSystem = null;
}

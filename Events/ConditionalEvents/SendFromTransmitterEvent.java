package Events.ConditionalEvents;

import java.util.ArrayList;
import java.util.Vector;

import Events.ConditionalEvent;
import Events.TimeEvents.*;
import TransmissionSystem.*;

/**
 * Class representing a event of sending a packet to channel.
 * */
public class SendFromTransmitterEvent implements ConditionalEvent{
	/**
	 * SendFromTransmitterEvent constructor
	 * */
	public SendFromTransmitterEvent(TransmissionSystem ts) {
		transmitters = ts.getTransmitters();
		channel = ts.getChannel();
		stat = ts.getStatObj();
		transmissionSystem = ts;
	}
	
	/**
	 * Check if there is any transmitter with packet ready to send
	 * */			
	public boolean condition(){
		// SendFromTransmitterEvent doesn't check if channel is free because it is what listening do
		for(int i = 0; i < TransmissionSystem.NUMBER_OF_DEVICES; i++){
			if(transmitters.get(i).isReady())
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * */	
	public void execute(){
		if(condition()){ // If there's any ready transmitter to send packet
			Transmitter tempTrans = null;
			for(int i = 0; i < TransmissionSystem.NUMBER_OF_DEVICES; i++)
			{ // Loop looks for a ready transmitter to send packet
				
				tempTrans = transmitters.get(i);
				if(tempTrans != null && tempTrans.isReady())
				{
					Packet tempPacket = tempTrans.transmissionSettings.getProcesedPacket(); // Gets packet currently processed in the specified transmitter
					
					if(tempPacket != null){
						channel.pushPacket(tempPacket);
						channel.setBusy(); // Setting channel busy - listening events will see this
						
						int r = tempTrans.transmissionSettings.get_r();
						if(r == 0) // Primary transmission
						{
							tempPacket.setPacketFirstSendTime(transmissionSystem.getClock());
						}
						else // Retransmission
						{
							if(r == 1)
							{
								stat.addPrimaryRetransmission(transmissionSystem.getClock());
							}
							stat.addRetransmission(transmissionSystem.getClock());
						}
								
						//" + transmissionSystem.getClock() + ":
						if(TransmissionSystem.logsON)
						{
							System.out.println("-> SendFromTransmitterEvent: Packet sent to channel from transmitter " + tempTrans.getId());
							System.out.println("--> passTime: " + tempTrans.transmissionSettings.getPassTime());
						}
						transmissionSystem.addTimeEvent(new ArrivalToTheReceiverEvent(transmissionSystem.getClock(), tempPacket.getAddress(), transmissionSystem));
						transmissionSystem.addTimeEvent(new ACKarrivalEvent(transmissionSystem.getClock(), tempPacket.getAddress(), transmissionSystem));
						
						tempTrans.setNotReady();
					}
				}
				
			}
		}
	}
	

	private Vector <Transmitter> transmitters = null; // Vectors of the transmitters in the system
	private Channel channel = null;	// Object of the channel of the system
	private TransmissionSystem transmissionSystem = null;
	private StatisticsCollector stat = null;
}

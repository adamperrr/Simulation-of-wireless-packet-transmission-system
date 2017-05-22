package Events.TimeEvents;
import java.util.ArrayList;
import java.util.Vector;

import Events.TimeEvent;
import Generators.RandomGenerator;
import TransmissionSystem.*;

public class ACKarrivalEvent implements TimeEvent
{
	/**
	 * Constructor of the class
	 * */		
	public ACKarrivalEvent(double clock, int packetAddr, TransmissionSystem ts)
	{
		packetAddress = packetAddr;
		channel = ts.getChannel();
		transmitters = ts.getTransmitters();
		
		rand = ts.getRandObj();
		
		Transmitter tr = ts.getTransmitters().get(packetAddr);
		double passingTime = rand.getCITZ() + tr.transmissionSettings.getPassTime();
		time = clock + passingTime;
		
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
	 * Event planed by SendFromTransmitterEvent
	 * It pushes packet ACK to the proper transmitter or activates retransmission 
	 * 	*/
	private void action()
	{
		Packet ACK = null;
		int i = 0;
		Packet tempPacket = null;
		for(; i < channel.getBuffer().size(); i++)
		{
			tempPacket = channel.getBuffer().get(i);
			if(tempPacket.isACK() && tempPacket.getAddress() == packetAddress)
			{
				ACK = tempPacket;
				break;
			}
		}
		
		if(ACK != null)
		{
			Transmitter transmitter = transmitters.get(packetAddress);
			transmitter.setFree();
			transmitter.setNotReady();
			channel.getBuffer().remove(i);
			
			Packet procesedPacket = transmitter.transmissionSettings.getProcesedPacket();
			procesedPacket.setPacketArrivalTime(transmissionSystem.getClock());
			
			transmitter.transmissionSettings.resetAllSettings();
			
			System.out.println(time + ": ACKarrivalEvent: ACK packet arrived to the transmitter "+ packetAddress +" from the channel.");
		}
		else
		{
			System.out.println(time + ": ACKarrivalEvent: The ACK has not arrived. There will be a retransmission attempt for the transmitter "+ packetAddress);
			
			Transmitter transmitter = transmitters.get(packetAddress);
			transmitter.transmissionSettings.prepareForRetransmission();
			
			double retransListeningStartTime = time + rand.nextR( transmitter.transmissionSettings.get_r() ) * rand.nextCTP();
			transmissionSystem.addTimeEvent(new ListenIfChannelIsFreeEvent(retransListeningStartTime , transmitter, transmissionSystem));
		}
		
		channel.setFree();
	}

	private double time = 0.0; // Time when event will be executed
	// References to elements needed in event's action
	private int packetAddress = -1;
	private Channel channel = null;
	private Vector <Transmitter> transmitters = null;
	
	private RandomGenerator rand = null;
	private TransmissionSystem transmissionSystem = null;
}

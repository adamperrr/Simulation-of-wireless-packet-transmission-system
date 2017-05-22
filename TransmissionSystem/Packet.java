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
 * Class Packet represents packet transmitted in the channel between the devices.
 * 
 * */
public class Packet
{
	/**
	 * Constructor of the class Packet.
	 * @param address - source/destination of the packet
	 * */
	public Packet(int address, double appearingTime, TransmissionSystem ts) {
		this.address = address;
		appearingInTheBufferTime = appearingTime;
		stat = ts.getStatObj();
		transmissionSystem = ts;
		
		ACK = false;
	}
	
	public Packet(int address, boolean isACK){
		this.address = address;
		ACK = isACK;
	}
		
	/**
	 * Getter of the source/destination of the packet.
	 * @return address - source/destination of the packet
	 * */
	public int getAddress() {
		return address;
	}

	/**
	 * Getter of the ACK (boolean) field
	 * @return correct = true when packet is ACK
	 * */
	public boolean isACK(){
		return ACK;
	}	
	
	/**
	 * Getter of the correct field
	 * @return correct = true when packet is correct 
	 * */
	public boolean isCorrect(){
		return correct;
	}
	
	/**
	 * Check if packet is faulty
	 * @return faulty = true when packet is faulty
	 * */
	public boolean isFaulty(){
		return !correct;
	}
	
	/**
	 * Sets packet as correct one.
	 * */
	public void setCorrect(){
		correct = true;
	}
	
	/**
	 * Sets packet as faulty one.
	 * */
	public void setFaulty(){
		correct = false;
	}
		
/*	// Used in: Receiver
	public void setPacketArrivalToReceiverTime(double t)
	{
		arrivalToReceiverTime = t;
		double PacketDelayTime = arrivalToReceiverTime - appearingInTheBufferTime;
		//stat.addPacketDelayTime(PacketDelayTime, transmissionSystem.getClock());
		//stat.addDeliveredPacket(transmissionSystem.getClock());
		//stat.setLastDeliveryTime(t, transmissionSystem.getClock());
	}*/
	
	// In: ACKarrivalEvent
	public void setPacketArrivalTime(double t)
	{
		arrivalToReceiverTime = t;
		double packetDelay = arrivalToReceiverTime - appearingInTheBufferTime;
		stat.addPacketDelayTime(packetDelay, transmissionSystem.getClock());
		stat.incNumOfDeliveredPackets(transmissionSystem.getClock());
		//stat.setFirstSendTime(t);
	}
	
	// Used in: SendFromTransmitterEvent
	public void setPacketFirstSendTime(double t)
	{
		firstSendTime = t;
		double waitingTime = firstSendTime - appearingInTheBufferTime;
		stat.addWaitingTime(waitingTime, transmissionSystem.getClock());
		stat.incPrimaryTransmitedPackets(address, transmissionSystem.getClock());
		//stat.setFirstSendTime(t);
	}
	
	private int address = -1; // Source/destination of the packet.
	private boolean correct = true; // When correct=true packet is not faulty. correct=false - packet is faulty after collision 
	private boolean ACK = false; // Is this packet an ACK
	
	// For statistics:
	private double arrivalToReceiverTime = 0.0;
	private double firstSendTime = 0.0;
	private double appearingInTheBufferTime = 0.0;
	
	TransmissionSystem transmissionSystem = null;
	StatisticsCollector stat = null;
}

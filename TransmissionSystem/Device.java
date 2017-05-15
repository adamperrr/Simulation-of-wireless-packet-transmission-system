package TransmissionSystem;

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
 * Interface Device is the base for Receiver and Transmitter class.
 * It contains basic properties which are necessary to implement in the class.
 * */
public interface Device {
		
	/**
	 * Pushes packet from the channel to the device.
	 * @param packet Packet to push to the device.
	 * */
	void pushPacket(Packet packet);
	
	/**
	 * Checks if device is in the busy state.
	 * */
	boolean isBusy();
	
	/**
	 * Sets device in the busy state.
	 * */
	void setBusy();
	
	/**
	 * Sets device in the free state.
	 * */
	void setFree();
	
	/**
	 * ID of device among the group of transmitters/receivers.
	 * */
	int id = -1;
	
	/**
	 * Packet pushed to the device from channel.
	 * For transmitter it is a ACK packet sent by receiver.
	 * For receiver it is a packet sent by transmitter.
	 * */
	Packet receivedPacket = null;
		
	/**
	 * Occupancy state of the device.
	 * */
	boolean isBusy = false;
}

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
 * 
 * Class contains settings for transmitter K to work properly. 
 * 
 * */
public class TransmissionSettings {
	
	/**
	 * Constructor needed to initialize passTime variable 
	 * */
	public TransmissionSettings(RandomGenerator r)
	{
		rand = r;
		passTime = rand.nextCTP();
	}

	/**
	 * Function resets all of the transmission settings
	 * */
	public void resetAllSettings()
	{
		procesedPacket = null; 
		r = 0;
		freeChannelTime = 0.0;
		passTime = rand.nextCTP();
	}
	
	/**
	 * Function sets procesedPacket
	 * @param packet - new packet
	 * */
	public void setProcesedPacket(Packet packet)
	{
		resetAllSettings();
		procesedPacket = packet;
	}
	
	/**
	 * Function returns procesedPacket
	 * @returns packet - processed packet
	 * */
	public Packet getProcesedPacket()
	{
		return procesedPacket;
	}
	
	/**
	 * Function resets freeChannelTime	
	 * */
	public void resetFreeChannelTime()
	{
		freeChannelTime = 0.0;	
	}
	
	/**
	 * Function returns listeningTime
	 * @returns listeningTime
	 * */
	public double getListeningTime()
	{
		return listeningTime;
	}
	
	/**
	 * Function increments FreeChannelTime by value of listeningTime
	 * */
	public void incFreeChannelTByListeningT()
	{
		freeChannelTime += listeningTime;
	}
	
	/**
	 * Function returns FreeChannelTime
	 * @returns freeChannelTime value of freeChannelTime
	 * */
	public double getFreeChannelTime()
	{
		return freeChannelTime;
	}
	
	/**
	 * Function returns DIFS value
	 * @returns DIFS - listening time must be greater than DIFS to send packet
	 * */
	public double getDIFS()
	{
		return DIFS;
	}
	
	/**
	 * Function increments r by 1.
	 * */
	public void inc_r()
	{
		++r;
	}
	
	/**
	 * Function sets listeningTime
	 * @param lt - listening time value
	 * */
	public void setListeningTime(double lt)
	{
		listeningTime = lt;
	}
	
	/**
	 * Function returns value of r.
	 * @returns r - number of retransmission
	 * */
	public int get_r()
	{
		return r;
	}
	
	/**
	 * Function returns value of LR
	 * @returns LR - maximum number of retransmissions
	 * */
	public int getLR()
	{
		return LR;
	}
	
	/**
	 * Function returns value of passTime
	 * @returns passTime - time of passing through the channel between devices
	 * */
	public double getPassTime()
	{
		return passTime;
	}
	
	/**
	 * Function prepares the transmitter for retransmission: increments r and resets freeChannelTime
	 * */
	public void prepareForRetransmission()
	{
		inc_r();
		resetFreeChannelTime();
		passTime = rand.nextCTP();
		procesedPacket.setCorrect();
	}
	
	private Packet procesedPacket = null; 
	private double listeningTime = 0.5; // Time to the next listening
	private double DIFS = 2.0; // The time the channel must be free to send the packet.
	private int r = 0; // Number of retransmission
	private double freeChannelTime = 0.0; // Sum of listening times when channel was free
	private int LR = 6; //15; // Maximum number of retransmissions
	private double passTime = 0.0; // Time of passing the channel between transmitter and receiver.
	// passTime is specified once while initializing by constructor.
	private RandomGenerator rand = null; // Object of random generator.
}

package Events.TimeEvents;
import java.util.ArrayList;
import java.util.Vector;

import Events.TimeEvent;
import Generators.RandomGenerator;
import TransmissionSystem.*;

/**
 * Class GeneratePacketEvent
 * Generates a packet in the specified transmitter on random time since moment of initialization.
 * */
public class GeneratePacketEvent implements TimeEvent
{
	/**
	 * Constructor of the class
	 * */		
	public GeneratePacketEvent(double clock, Transmitter trans, TransmissionSystem ts)
	{
		transmitter = trans;
		rand = ts.getRandObj();
		transmissionSystem = ts;
		stat = ts.getStatObj();
		
		double randCGP = rand.nextCGP(transmitter.getId());
		time = clock + randCGP;
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
	* Describes all of the actions made by event
	* Function pushes packet to the buffer of transmitter specified in the variable 'transmitter'.
	* It also generates next event of type GeneratePacketEvent and adds it to the agenda/timeEvents.
	*/
	private void action()
	{
		transmitter.pushPacketToBuffer(new Packet(transmitter.getId(), time, transmissionSystem));
		System.out.println(time + ": GeneratePacketEvent: Packet pushed to the transmitter's "+ transmitter.getId() +" buffer");
		
		// Plan next event
		TimeEvent temp = new GeneratePacketEvent(time, transmitter, transmissionSystem);
		transmissionSystem.addTimeEvent(temp);
		System.out.println(time + ": GeneratePacketEvent: Planned next GeneratePacketEvent for transmitter " + transmitter.getId());
	}
	
	private double time = 0.0; // Time when event will be executed
	// References to elements needed in event's action
	private Transmitter transmitter = null;
	private RandomGenerator rand = null;
	private TransmissionSystem transmissionSystem = null;
	private StatisticsCollector stat = null;
}

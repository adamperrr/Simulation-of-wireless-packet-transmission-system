package Events.TimeEvents;
import java.util.ArrayList;
import java.util.Vector;

import Events.TimeEvent;
import Generators.RandomGenerator;
import TransmissionSystem.*;

public class ListenIfChannelIsFreeEvent implements TimeEvent
{
	/**
	 * Constructor of the class
	 * */		
	public ListenIfChannelIsFreeEvent(double clock, Transmitter trans, TransmissionSystem ts)
	{
		transmitter = trans;
		rand = ts.getRandObj();
		stat = ts.getStatObj();
		transmissionSystem = ts;
		
		time = clock + trans.transmissionSettings.getListeningTime();
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
	* When channel is set as free and function increments freeChannelTime variable in TransmissionSettings.
	* If FreeChannelTime variable is bigger than DIFS specified transmitter is set as ready to send packet.
	* Else next listening of channel is planed.
	* If channel is busy freeChannelTime variable is reset and next listening event is plan.
	*/
	private void action()
	{
		int LR = transmitter.transmissionSettings.getLR();
		
		if(transmitter.transmissionSettings.get_r() <= LR)
		{
			if(transmissionSystem.getChannel().isFree()){
				
				transmitter.transmissionSettings.incFreeChannelTByListeningT();
				
				double DIFS = transmitter.transmissionSettings.getDIFS();
				if(transmitter.transmissionSettings.getFreeChannelTime() > DIFS)
				{
					transmitter.setReady();
					System.out.println(time + ": ListenIfChannelIsFreeEvent: Transmitter "+ transmitter.getId() + " is ready to send packet. r = " + transmitter.transmissionSettings.get_r() + " freeChannelTime = " + transmitter.transmissionSettings.getFreeChannelTime());
				}
				else
				{
					TimeEvent temp = new ListenIfChannelIsFreeEvent(time, transmitter, transmissionSystem);
					transmissionSystem.addTimeEvent(temp);
					System.out.println(time + ": ListenIfChannelIsFreeEvent: Transmitter "+ transmitter.getId() + " is NOT ready to send packet. Next listening is planed. r = " + transmitter.transmissionSettings.get_r() + " freeChannelTime = " + transmitter.transmissionSettings.getFreeChannelTime());
				}
			}
			else
			{
				TimeEvent temp = new ListenIfChannelIsFreeEvent(time, transmitter, transmissionSystem);
				transmissionSystem.addTimeEvent(temp);
				transmitter.transmissionSettings.resetFreeChannelTime();
				System.out.println(time + ": ListenIfChannelIsFreeEvent: Transmitter "+ transmitter.getId() + " - channel IS BUSY. Next listening is planed. r = " + transmitter.transmissionSettings.get_r() + " freeChannelTime = " + transmitter.transmissionSettings.getFreeChannelTime());
			}
		}
		else
		{
			System.out.println(time + ": ListenIfChannelIsFreeEvent: Transmitter "+ transmitter.getId() + " - Next retransmissions are not possible (r > LR). r = " + transmitter.transmissionSettings.get_r());
			transmitter.transmissionSettings.resetAllSettings();
			transmitter.setFree();
			transmitter.setNotReady();
		}
	}
	
	private double time = 0.0; // Time when event will be executed
	// References to elements needed in event's action
	private Transmitter transmitter = null;
	private RandomGenerator rand = null;
	private ArrayList<Events.TimeEvent> timeEvents = null;
	private TransmissionSystem transmissionSystem = null;
	private StatisticsCollector stat = null;
}

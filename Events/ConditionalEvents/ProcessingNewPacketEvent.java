package Events.ConditionalEvents;

import java.util.ArrayList;
import java.util.Vector;

import Events.ConditionalEvent;
import Events.TimeEvent;
import Events.TimeEvents.ListenIfChannelIsFreeEvent;
import TransmissionSystem.*;


/**
 * Class representing conditional event of starting processing new packet from transmitter
 * buffer 
 * */
public class ProcessingNewPacketEvent implements ConditionalEvent{
	/**
	 * ProcessingNewPacketEvent constructor
	 * */
	public ProcessingNewPacketEvent(TransmissionSystem ts) {
		transmitters = ts.getTransmitters();
		channel = ts.getChannel();
		transmissionSystem = ts;
	}
	
	/**
	 * Conditions which should return true to execute event
	 * It returns true when there are packets to process in the buffer
	 * */			
	public boolean condition(){
		for(int i = 0; i < TransmissionSystem.NUMBER_OF_DEVICES; i++){
			if(!transmitters.get(i).isBufferEmpty())
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Execution of Event
	 * If conditions of event are met:
	 * Algorithm looks for a transmitters which aren't busy and sets them busy and sets their ProcesedPacket.
	 * */	
	public void execute(){
		if(condition()){
			Transmitter tempTrans = null;
			for(int i = 0; i < TransmissionSystem.NUMBER_OF_DEVICES; i++){
				tempTrans = transmitters.get(i);
				if(tempTrans != null && !tempTrans.isBusy())
				{
					Packet tempPacket = tempTrans.getPacketFromBuffer();
					if(tempPacket != null){
						int addressId = tempPacket.getAddress();
						int numOfPacketRetrans = tempTrans.transmissionSettings.get_r();
						int maxRetrans = tempTrans.transmissionSettings.getLR();
						
						if( numOfPacketRetrans <= maxRetrans ){
							tempTrans.setBusy();
							tempTrans.transmissionSettings.setProcesedPacket(tempPacket);

							if(TransmissionSystem.logsON)
							{
								System.out.println("-> ProcessingNewPacketEvent: Starting ProcessingNewPacket in transmitter " + tempTrans.getId());
							}
							
							transmissionSystem.addTimeEvent(new ListenIfChannelIsFreeEvent(transmissionSystem.getClock(), tempTrans, transmissionSystem));
						}
						/*else{
							tempTrans.setFree();
							tempTrans.setNotReady();
							tempTrans.transmissionSettings.resetAllSettings();

							if(TransmissionSystem.logsON)
							{
								System.out.println("-> ProcessingNewPacketEvent: Too many retransmission attempts in the transmitter " + tempTrans.getId() + " (r > LR)." );
							}
						}*/
						
					}
				}
			}
		}
	}
	

	private Vector <Transmitter> transmitters = null;
	private Channel channel = null;	
	private TransmissionSystem transmissionSystem = null;
}

package TransmissionSystem;

public class StatisticsCollector
{
	public StatisticsCollector(int numOfDevices)
	{
		this.numOfDevices = numOfDevices;
	}
	
	public void addRejectedPacket()
	{
		sumOfRejectedPackets++;
	}
			
	public void addRetransmission()
	{
		numOfRetransmissions++;
	}
	
	public void addTransmission()
	{
		numOfAllTransmissions++;
	}
	
	public void setLastDeliveryTime(double t)
	{
		if(t > lastDeliveryTime)
		{
			lastDeliveryTime = t;
		}
	}
	
	public void setFirstSendTime(double t)
	{
		if( firstSendTime == 0.0 )
		{
			firstSendTime = t;
		}
	}
	
	public void addDeliveredPacket()
	{
		numOfDeliveredPackets++;
	}
	
	public void addPacketDelayTime(double dt)
	{
		packetDelaysSum += dt;
	}
	
	public void addWaitingTime(double dt)
	{
		waitingTimesSum += dt;
	}
	
	public double getAveragePacketError()//sumOfRejectedPackets / (dosz³y poprawnie + usuniête)
	{
		double k = (double) TransmissionSystem.NUMBER_OF_DEVICES;
		averagePacketError = (double)numOfDeliveredPackets / ((double)sumOfRejectedPackets + (double)numOfDeliveredPackets) / k;
		return averagePacketError;
	}

	public double getAverageNumOfRetransmission()
	{
		averageNumOfRetransmission = (double)numOfRetransmissions / (double)numOfAllTransmissions;// nuOfRet / numOfDeliver 
		return averageNumOfRetransmission;
	}

	public double getSystemBitRate()
	{
		systemBitRate = (double)numOfDeliveredPackets / (lastDeliveryTime - firstSendTime);// zamiast first faza pocz¹tkowa zadana, zamiast last koniec sym
		return systemBitRate;
	}
			
	public double getAveragePacketDelay()
	{
		averagePacketDelay = packetDelaysSum / (double) numOfDeliveredPackets;
		return averagePacketDelay;
	}

	public double getAverageWaitingToSendTime()
	{
		averageWaitingToSendTime = waitingTimesSum / (double) numOfAllTransmissions;//od dodania do bufora do pierwszego wys³ania
		return averageWaitingToSendTime;
	}
	
	public void countStatistics()
	{
		getAveragePacketError();
		getAverageNumOfRetransmission();
		getSystemBitRate();
		getAveragePacketDelay();
		getAverageWaitingToSendTime();
		/*double k = (double) TransmissionSystem.NUMBER_OF_DEVICES;
		averagePacketError = (double)numOfDeliveredPackets / (double)sumOfRejectedPackets / k;
		averageNumOfRetransmission = (double)numOfRetransmissions / (double)numOfAllTransmissions;	
		systemBitRate = (double)numOfDeliveredPackets / (lastDeliveryTime - firstSendTime);
		averagePacketDelay = packetDelaysSum / (double) numOfDeliveredPackets;
		averageWaitingToSendTime = waitingTimesSum / (double) numOfAllTransmissions;*/
	}
	
	public void printStats()
	{
		countStatistics();
		System.out.println("\nThe average packet error rate divided by number of devices: " + averagePacketError);
		System.out.println("\tsumOfRejectedPackets: " + sumOfRejectedPackets);
		System.out.println("\tnumOfDeliveredPackets: " + numOfDeliveredPackets);
		System.out.println("\tk: " + TransmissionSystem.NUMBER_OF_DEVICES);

		System.out.println("\nAverage number of retransmission of packets: " + averageNumOfRetransmission);
		System.out.println("\tsumOfRetransmissions: " + numOfRetransmissions);
		System.out.println("\tsumOfAllTransmissions - Including retransmissions: " + numOfAllTransmissions);
		
		System.out.println("\nSystem bit rate: " + systemBitRate);
		System.out.println("\tnumOfDeliveredPackets: " + numOfDeliveredPackets);
		System.out.println("\tfirstSendTime: " + firstSendTime);
		System.out.println("\tlastDeliveryTime: " + lastDeliveryTime);
		
		System.out.println("\nAverage delay of the packet: " + averagePacketDelay);
		System.out.println("\tpacketDelaysSum: " + packetDelaysSum);
		System.out.println("\tnumOfDeliveredPackets: " + numOfDeliveredPackets);
		
		System.out.println("\nAverage waiting to send time: " + averageWaitingToSendTime);
		System.out.println("\twaitingTimesSum: " + waitingTimesSum);
		System.out.println("\tnumOfAllTransmissions: " + numOfAllTransmissions);
	}
	
	// The average packet error rate divided by number of devices:
	private int sumOfRejectedPackets = 0;
	//numOfDeliveredPackets
	private double averagePacketError = 0.0; // Result
	
	// Average number of retransmission of packets:
	private int numOfRetransmissions = 0;
	private int numOfAllTransmissions = 0; // Including retransmissions
	private double averageNumOfRetransmission = 0.0; // Result
	
	// System bit rate:
	private int numOfDeliveredPackets = 0;
	private double firstSendTime = 0.0;
	private double lastDeliveryTime = 0.0;
	private double systemBitRate = 0.0; // Result
	
	// Average delay of the packet:
	private double packetDelaysSum = 0.0;
	//numOfDeliveredPackets
	private double averagePacketDelay = 0.0; // Result
	
	
	// Average waiting to send time:
	private double waitingTimesSum = 0.0;
	//numOfAllTransmissions
	private double averageWaitingToSendTime = 0.0; // Result
	
	private int numOfDevices = 0;
}

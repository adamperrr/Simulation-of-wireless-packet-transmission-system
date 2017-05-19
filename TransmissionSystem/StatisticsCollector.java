package TransmissionSystem;

public class StatisticsCollector
{
	public StatisticsCollector(int numOfDevices, TransmissionSystem ts)
	{
		this.numOfDevices = numOfDevices;
		this.initialPhase = ts.getInitialPhase();
		transmissionSystem = ts;
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
	
	public void setLastDeliveryTime(double t, double c)
	{
		if(c >= initialPhase && t > lastDeliveryTime)
		{
			lastDeliveryTime = t;
		}
	}
	
	public void setFirstSendTime(double t, double c)
	{
		if( c >= initialPhase && firstSendTime == 0.0 )
		{
			firstSendTime = t;
		}
	}
	
	public void addDeliveredPacket(double c)
	{
		if(c >= initialPhase)
			numOfDeliveredPackets++;
	}
	
	public void addPacketDelayTime(double dt, double c)
	{
		if(c >= initialPhase)
			packetDelaysSum += dt;
	}
	
	public void addWaitingTime(double dt, double c)
	{
		if(c >= initialPhase)
			waitingTimesSum += dt;
	}
	
	public double getAveragePacketError()//sumOfRejectedPackets / (dosz�y poprawnie + usuni�te)
	{
		averagePacketError = sumOfRejectedPackets / (double) numOfAllTransmissions / (double)numOfDevices;
		//averagePacketError = (double)numOfDeliveredPackets / ((double)sumOfRejectedPackets + (double)numOfDeliveredPackets) / k;
		return averagePacketError;
	}

	public double getAverageNumOfRetransmission()
	{
		averageNumOfRetransmission = (double)numOfRetransmissions / (double)numOfDeliveredPackets;// nuOfRet / numOfDeliver 
		return averageNumOfRetransmission;
	}

	public double getSystemBitRate()
	{
		systemBitRate = (double)numOfDeliveredPackets / (transmissionSystem.getMaximumSimulationTime() - initialPhase);// zamiast first faza pocz�tkowa zadana, zamiast last koniec sym
		return systemBitRate;
	}
			
	public double getAveragePacketDelay()
	{
		averagePacketDelay = packetDelaysSum / (double) numOfDeliveredPackets;
		return averagePacketDelay;
	}

	public double getAverageWaitingToSendTime()
	{
		averageWaitingToSendTime = waitingTimesSum / (double) numOfAllTransmissions;//od dodania do bufora do pierwszego wys�ania
		return averageWaitingToSendTime;
	}
	
	public void countStatistics()
	{
		getAveragePacketError();
		getAverageNumOfRetransmission();
		getSystemBitRate();
		getAveragePacketDelay();
		getAverageWaitingToSendTime();
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
	
	private double initialPhase = 0.0;
	
	TransmissionSystem transmissionSystem = null;
}

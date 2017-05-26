package TransmissionSystem;

public class StatisticsCollector
{
	public StatisticsCollector(int numOfDevices, TransmissionSystem ts)
	{
		this.numOfDevices = numOfDevices;
		this.initialPhase = ts.getInitialPhase();
		
		rejectedPackets = new int[numOfDevices];
		numberOfPrimaryTransmissions = new int[numOfDevices];
		packetError = new double[numOfDevices];
		
		for(int i = 0; i < numOfDevices; i++)
		{
			rejectedPackets[i] = 0;
			numberOfPrimaryTransmissions[i] = 0;
			packetError[i] = 0;
		}
		transmissionSystem = ts;
	}
			
	/**
	 * Function counts collected statistics
	 * */	
	public void countStatistics()
	{
		getMaxPacketError();
		getAveragePacketError();
		getAverageNumOfRetransmissions();
		getSystemBitRate();
		getAverageDelayOfThePacket();
		getAverageWaitingToSendTime();
	}
	
	/**
	 * Function counts and prints collected statistics
	 * */
	public void printStats()
	{
		countStatistics();
		System.out.println("\nmaxPacketError: " + maxPacketError);
		System.out.println("The average packet error rate divided by number of devices: " + averagePacketError);
		for(int i = 0; i < numOfDevices; i++)
			System.out.println("\trejectedPackets[" + i + "]: " + rejectedPackets[i]);
		System.out.print("\n");
		
		for(int i = 0; i < numOfDevices; i++)
			System.out.println("\tnumberOfPrimaryTransmissions[" + i + "]: " + numberOfPrimaryTransmissions[i]);
		System.out.print("\n");
		
		for(int i = 0; i < numOfDevices; i++)
			System.out.println("\tpacketError[" + i + "]: " + packetError[i]);
		System.out.print("\n");
		
		System.out.println("\tk: " + numOfDevices);

		System.out.println("\nAverage number of retransmission of packets: " + averageNumOfRetransmissions);
		
		System.out.println("\nSystem bit rate: " + systemBitRate);
		System.out.println("\tnumOfDeliveredPackets: " + numOfDeliveredPackets);
		System.out.println("\tinitialPhase: " + initialPhase);
		System.out.println("\tmaximumSimulationTime: " + maximumSimulationTime);
		
		System.out.println("\nAverage delay of the packet: " + averagePacketDelay);
		System.out.println("\tpacketDelaysSum: " + packetDelaysSum);
		System.out.println("\tnumOfDeliveredPackets: " + numOfDeliveredPackets);
		
		System.out.println("\nAverage waiting to send time: " + averageWaitingToSendTime);
		System.out.println("\twaitingTimesSum: " + waitingTimesSum);
		System.out.println("\tnumberOfPrimaryTransmissions: " + sumOfPrimaryTransmissions);
	}
	
	// In: ListenIfChannelIsFreeEvent
	public void incRejectedPackets(int deviceId, double clock)
	{
		if(clock >= initialPhase)
		{
			rejectedPackets[deviceId]++;
		}
	}
	
	public double getMaxPacketError()
	{
		getAveragePacketError();
		
		double max = packetError[0];
		
		for(double temp : packetError)
		{
			if(temp > max)
			{
				max = temp;
			}
		}
		
		maxPacketError = max;
		
		return maxPacketError;
	}
	
	public double getAveragePacketError()
	{
		for(int i = 0; i < numOfDevices; i++)
		{
			if(numberOfPrimaryTransmissions[i] != 0)
			{
				packetError[i] = (double)rejectedPackets[i] / (double)numberOfPrimaryTransmissions[i];
			}
			
			averagePacketError += packetError[i];
		}
		averagePacketError /= numOfDevices;

		return averagePacketError;
	}
//-----------------------------------------------------------------------------------
	//In: SendFromTransmitterEvent
	public void addPrimaryRetransmission(double clock)
	{
		if(clock >= initialPhase)
		{
			numOfPrimaryRetransmissions++;
		}
	}
	
	public void addRetransmission(double clock)
	{
		if(clock >= initialPhase)
		{
			numOfRetransmissions++;
		}
	}
	
	public double getAverageNumOfRetransmissions()
	{
		averageNumOfRetransmissions = (double)numOfRetransmissions / (double)numOfPrimaryRetransmissions;
		return averageNumOfRetransmissions;
	}
	
//-----------------------------------------------------------------------------------		
	public double getSystemBitRate()
	{
		maximumSimulationTime = transmissionSystem.getMaximumSimulationTime();
		systemBitRate = (double) numOfDeliveredPackets / (maximumSimulationTime - initialPhase);
		return systemBitRate;
	}
//-----------------------------------------------------------------------------------	
	// In: Packet
	public void addPacketDelayTime(double dt, double clock)
	{
		if(clock >= initialPhase)
		{
			packetDelaysSum += dt;
		}
	}
		
	// In: Packet
	public void incNumOfDeliveredPackets(double clock)
	{
		if(clock >= initialPhase)
		{
			//System.out.println("D:" + clock);
			numOfDeliveredPackets++;
		}
	}
		
	public double getAverageDelayOfThePacket()
	{
		averagePacketDelay = packetDelaysSum / (double) numOfDeliveredPackets;
		return averagePacketDelay;
	}
	
//-----------------------------------------------------------------------------------	
	// In: Packet
	public void addWaitingTime(double dt, double clock)
	{
		if(clock >= initialPhase)
		{
			waitingTimesSum += dt;
		}
	}
	
	// In: Packet
	public void incPrimaryTransmitedPackets(int deviceId, double clock)
	{
		if(clock >= initialPhase)
		{
			//System.out.println("T:" + clock);
			numberOfPrimaryTransmissions[deviceId]++;
			sumOfPrimaryTransmissions++;
		}
	}
	
	public double getAverageWaitingToSendTime()
	{ //waitingTimesSum - czas od dodania do bufora do pierwszego wys³ania
		averageWaitingToSendTime = waitingTimesSum / (double) sumOfPrimaryTransmissions;
		return averageWaitingToSendTime;
	}
	
//-----------------------------------------------------------------------------------		
	
	// The average packet error rate divided by number of devices:
	private int[] rejectedPackets = null;
	private int[] numberOfPrimaryTransmissions = null;
	private double[] packetError = null;
	private double maxPacketError = 0;
	private double averagePacketError = 0.0; // Result
	
	// Average number of retransmission of packets:
	private int numOfRetransmissions = 0;
	private int numOfPrimaryRetransmissions = 0;
	private double averageNumOfRetransmissions = 0.0; // Result
	
	// System bit rate:
	//numOfDeliveredPackets
	//initialPhase
	private double maximumSimulationTime = 0.0;
	private double systemBitRate = 0.0; // Result
	
	// Average delay of the packet:
	private double packetDelaysSum = 0.0;
	private int numOfDeliveredPackets = 0;
	private double averagePacketDelay = 0.0; // Result
	
	// Average waiting to send time:
	private double waitingTimesSum = 0.0;
	private int sumOfPrimaryTransmissions = 0;
	private double averageWaitingToSendTime = 0.0; // Result
	
	private int numOfDevices = 0;
	private double initialPhase = 0.0;
	TransmissionSystem transmissionSystem = null;
	
	//public ArrayList<Integer> transIds = new ArrayList<>();
	//public ArrayList<Integer> deliveredIds = new ArrayList<>();
}

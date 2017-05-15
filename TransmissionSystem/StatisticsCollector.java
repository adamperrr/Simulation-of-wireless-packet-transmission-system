package TransmissionSystem;

public class StatisticsCollector
{
	public StatisticsCollector(int numOfDevices)
	{
		this.numOfDevices = numOfDevices;
	}

	public void addPacketDelayTime(double dt)
	{
		packetDelaysSum += dt;
		numOfDeliveredPackets++;
	}
	
	public void addWaitingTime(double dt)
	{
		waitingTimesSum += dt;
		numOfSentPackets++;
	}
	
	public void printStats()
	{
		System.out.println("\nThe average packet error rate: ");
		System.out.println("\tsumOfRejectedPackets: " + sumOfRejectedPackets);
		System.out.println("\tsumOfAllTransmissions - Including retransmissions: " + sumOfAllTransmissions);
		
		System.out.println("\nAverage number of retransmission of packets: ");
		System.out.println("\tsumOfRetransmissions: " + sumOfRetransmissions);
		System.out.println("\tsumOfAllTransmissions - Including retransmissions: " + sumOfAllTransmissions);
		
		System.out.println("\nSystem bit rate: ");
		System.out.println("\tnumOfDeliveredPackets: " + numOfDeliveredPackets);
		System.out.println("\ttimeOfTheExperiment: " + timeOfTheExperiment);
		
		averagePacketDelay = packetDelaysSum / (double) numOfDeliveredPackets;
		System.out.println("\nAverage delay of the packet: " + averagePacketDelay);
		System.out.println("\tpacketDelaysSum: " + packetDelaysSum);
		System.out.println("\tnumOfDeliveredPackets: " + numOfDeliveredPackets);
		
		averageWaitingToSendTime = waitingTimesSum / (double) numOfSentPackets;
		System.out.println("\nAverage waiting to send time: " + averageWaitingToSendTime);
		System.out.println("\twaitingTimesSum: " + waitingTimesSum);
		System.out.println("\tnumOfSentPackets: " + numOfSentPackets);
	}
	
	// The average packet error rate:
	private int sumOfRejectedPackets = 0;
	private int sumOfAllTransmissions = 0; // Including retransmissions
	
	// Average number of retransmission of packets:
	private int sumOfRetransmissions = 0;
	// sumOfAllTransmissions - Including retransmissions
	
	// System bit rate:
	private int numOfDeliveredPackets = 0;
	private double timeOfTheExperiment = 0.0;
	
	// Average delay of the packet:
	private double packetDelaysSum = 0.0;
	// numOfDeliveredPackets
	private double averagePacketDelay = 0.0; // Result
	
	
	// Average waiting to send time:
	private double waitingTimesSum = 0.0;
	private int numOfSentPackets = 0;
	private double averageWaitingToSendTime = 0.0; // Result
	
	private int numOfDevices = 0;
}

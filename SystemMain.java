import java.util.*;
import java.util.regex.*;

import TransmissionSystem.StatisticsCollector;

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
 * Program main class
 * */
public class SystemMain
{
	public static void main(String[] args)
	{	
		TransmissionSystem.StatisticsCollector[] allStat = new TransmissionSystem.StatisticsCollector[10];
		
		printWelcomeInfo();
		checkNSetParams(args);
		
		int currentId = 0;
		if(singleSimulationON)
		{
			currentId = singleSimulationId;
			maxSingleSimulationId = singleSimulationId;
		}
		
		for(; currentId <= maxSingleSimulationId; currentId++)
		{
			System.out.println("SIMULATION ID: " + currentId + "\n");
			
			TransmissionSystem.TransmissionSystem simulation = new TransmissionSystem.TransmissionSystem(lambda, currentId, initialPhase, logsON);
			
			System.out.println("Single simulation time: " + singleTime);
					
			System.out.print("Step mode: ");
			if(stepMode)
			{
				System.out.print("ON - Click Enter to move to next step.\n");
				simulation.stepModeON();
			}
			else
			{
				System.out.print("OFF\n");
			}
			System.out.print("\n");
		
			simulation.setSimulationTime(singleTime);
			simulation.simulate();
			
			allStat[currentId] = simulation.getStatObj();
			allStat[currentId].printStats();
			System.out.println("---------------------------------------------------------------");	
			System.out.println("--------------------------------------------------------------- \n");	
		}
		
		if(!singleSimulationON)
		{
			double averagePacketError = 0.0;
			double averageNumOfRetransmission = 0.0;
			double systemBitRate = 0.0;
			double averagePacketDelay = 0.0;
			double averageWaitingToSendTime = 0.0;
			
			for(int i = 0; i <= maxSingleSimulationId; i++)
			{	
				averagePacketError += allStat[i].getAveragePacketError();
				averageNumOfRetransmission += allStat[i].getAverageNumOfRetransmissions();
				systemBitRate += allStat[i].getSystemBitRate();
				averagePacketDelay += allStat[i].getAverageDelayOfThePacket();
				averageWaitingToSendTime += allStat[i].getAverageWaitingToSendTime();
			}
			
			System.out.println("Average main statistics: ");
			averagePacketError /= (double)(maxSingleSimulationId+1);
			averageNumOfRetransmission /= (double)(maxSingleSimulationId+1);
			systemBitRate /= (double)(maxSingleSimulationId+1);
			averagePacketDelay /= (double)(maxSingleSimulationId+1);
			averageWaitingToSendTime /= (double)(maxSingleSimulationId+1);
			
			System.out.println("averagePacketError: " + averagePacketError);
			System.out.println("averageNumOfRetransmission: " + averageNumOfRetransmission);
			System.out.println("systemBitRate: " + systemBitRate);
			System.out.println("averagePacketDelay: " + averagePacketDelay);
			System.out.println("averageWaitingToSendTime: " + averageWaitingToSendTime);
			
			System.out.println("lambda: " + lambda);
		}
	}
	
	/**
	 * Function prints welcome informations about simulation
	 * */
	public static void printWelcomeInfo()
	{
		System.out.println("Simulation of wireless packet transmission system.");
		System.out.println("System implements protocol CSMA with extortion");
		System.out.println("of transmission with probability of 1.");
		System.out.println("--------------------------------------------------------------- \n");
		System.out.println("For help write parameter -h while starting the program.\n");
		System.out.println("--------------------------------------------------------------- \n");	
	}
	
	/**
	 * Function checks args array of parameters and sets them to the proper fields.
	 * @param args - array of args from main function
	 * */
	public static void checkNSetParams(String[] args)
	{
        Pattern regexPattern;
        Matcher regexMatch;
		
		for(int i = 0; i < args.length; i++)
		{		
			if(args[i].equals("-h"))
			{
				System.out.println("\nDescription of program parameters:");
				
				System.out.println("-log parameter turns logs on.\n\tDefault: OFF");
				System.out.println("-lam parameter sets value of lambda.\n\tDefault: 0.02");
				System.out.println("-n parameter turns a single simulation of specified number on.");
				System.out.println("-ip parameter sets initial phase time (in ms) of a simulation.\n\tDefault: 200ms");
				System.out.println("-t parameter sets time (in ms) of a single simulation.\n\tDefault: 1000ms");
				System.out.println("-sm parameter turns on the step work mode. In order to start this mode the parameter must be equal to 1.\n\tDefault: OFF\n");
				System.out.println("-gk parameter turns on a function which generates and displays kernels which can be used in simulation files.\n\tArguments: -gk initialKernel numOfKernels\n");
				System.exit(0);
			}
			else if(args[i].equals("-log"))
			{
				logsON = true;
			}
			else if( args[i].equals("-gk") && ((i+1 < args.length) && (i+2 < args.length)) )
			{
				System.out.println("Genetared kernels:");
				
				int initialKernel = 123;
				int numOfKernels = 10;
				
				regexPattern = Pattern.compile("^\\d*$");
				
				regexMatch = regexPattern.matcher(args[i+1]);
				if(regexMatch.matches()){
					int temp = Integer.parseInt(args[i+1]);
					if(temp > 0){ initialKernel = temp;	}
				}
				
				regexMatch = regexPattern.matcher(args[i+2]);
				if(regexMatch.matches()){
					int temp = Integer.parseInt(args[i+2]);
					if(temp > 0){ numOfKernels = temp; }
				}
					
				generateKernels(initialKernel, numOfKernels);
				
				System.exit(0);
			}
			else if( args[i].equals("-n") && (i+1 < args.length) )
			{
				regexPattern = Pattern.compile("^\\d*$");
				regexMatch = regexPattern.matcher(args[i+1]);
				
				if(regexMatch.matches()){
					int temp = Integer.parseInt(args[i+1]);
					if(temp >= 0 && temp <= maxSingleSimulationId)
					{ 
						singleSimulationId = temp;
						singleSimulationON = true;
					}else
					{
						System.out.println("Number of simulation is not correct");
						System.exit(0);
					}
				}
			}
			else if( args[i].equals("-lam") && (i+1 < args.length) )
			{
				regexPattern = Pattern.compile("^[0-9]+\\.[0-9]+?$");
				regexMatch = regexPattern.matcher(args[i+1]);
				
				if(regexMatch.matches()){
					double temp = Double.parseDouble(args[i+1]);
					if(temp > 0){ lambda = temp; }
				}
			}
			else if( args[i].equals("-t") && (i+1 < args.length) )
			{
				regexPattern = Pattern.compile("^\\d*$");
				regexMatch = regexPattern.matcher(args[i+1]);
				
				if(regexMatch.matches()){
					double temp = Double.parseDouble(args[i+1]);
					if(temp > 0){ singleTime = temp; }
				}
			}
			else if( args[i].equals("-ip") && (i+1 < args.length) )
			{
				regexPattern = Pattern.compile("^\\d*$");
				regexMatch = regexPattern.matcher(args[i+1]);
				
				if(regexMatch.matches()){
					double temp = Double.parseDouble(args[i+1]);
					if(temp > 0){ initialPhase = temp; }
				}
			}
			else if( args[i].equals("-sm"))
			{		
				logsON = true;
				stepMode = true;
			}
		}
	}
	
	/**
	 * Function which generates and displays kernels which can be used in simulation files.
	 * @param initialKernel
	 * @param numOfKernels
	 * */
	public static void generateKernels( int initialKernel, int numOfKernels )
	{	
		Generators.UniformGenerator uniGen = new Generators.UniformGenerator(initialKernel);
		
		int kernel = 0;
		for(int i = 0; i < numOfKernels; i++)
		{
			for(int j = 0; j < 100_000; j++)
			{
				uniGen.rand();
				kernel = uniGen.getKernel();
			}
			System.out.println(kernel);
		}
		
		System.exit(0);
	}
	
	private static double lambda = 0.02;
	private static double initialPhase = 200.0;
	private static int maxSingleSimulationId = 9;
	
	private static int singleSimulationId = 0;
	private static boolean singleSimulationON = false;
	
	private static boolean logsON = false;
	
	private static double singleTime = 1000.0;
	private static boolean stepMode = false;
}

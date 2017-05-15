
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.*;

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
		printWelcomeInfo();
		checkNSetParams(args);
		
		TransmissionSystem.TransmissionSystem simulation = new TransmissionSystem.TransmissionSystem(0.04, singleSimulationId);
		
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
				
				System.out.println("-n parameter sets number of a single simulation.\n\tDefault value equals to 0");
				System.out.println("-t parameter sets time (in ms) of a single simulation.\n\tDefault value equals to 400ms.");
				System.out.println("-s parameter turns on the step work mode. In order to start this mode the parameter must be equal to 1.\n\tIn default the step work mode is off.\n");
				System.out.println("-gk parameter turns on a function which generates and displays kernels which can be used in simulation files.\n\tArguments: -gk initialKernel numOfKernels\n");
				System.exit(0);
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
					if(temp >= 0 && temp <= maxSingleSimulationNum)
					{ 
						singleSimulationId = temp; 
					}else
					{
						System.out.println("Number of simulation is not correct");
						System.exit(0);
					}
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
			else if( args[i].equals("-s") && (i+1 < args.length) )
			{
		        regexPattern = Pattern.compile("^\\d{1}$");
		        regexMatch = regexPattern.matcher(args[i+1]);
				
				if(regexMatch.matches() && Integer.parseInt(args[i+1]) == 1){ stepMode = true; }
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
	
	private static int maxSingleSimulationNum = 9;
	private static int singleSimulationId = 0;
	private static double singleTime = 400.0;
	private static boolean stepMode = false;
}

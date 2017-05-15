package Generators;

import java.util.*;
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
 * Class containing the functionality
 * of all the necessary random generators in the program.
 * 
 * */

public class RandomGenerator
{	// Wyk³adniczy per ndajnik i nie tym samym ziarnym wyk³adniczy
	// Dla ka¿dej funkcji w RandomGenerator daj inne ziarno a nie dla nadajników
	
	/**
	 * Constructor
	 * @param valL lambda value
	 * @param numOfSimulation simulation number
	 * @param transmitterId 
	 * */
	public RandomGenerator(double lambda, int numOfSimulation)
	{
		L = lambda;
		this.numOfSimulation = numOfSimulation;
		
		ugCGP = new UniformGenerator(kernels[numOfSimulation][0]);
		expCGP = new ExpGenerator(L, ugCGP);
		ugCTP = new UniformGenerator(kernels[numOfSimulation][1]);
		ugR = new UniformGenerator(kernels[numOfSimulation][2]);
	}
		
	/**
	 * Function returns exponential distribution random number.
	 * Time between packets generation.
	 * */
	public double nextCGP()
	{
		return expCGP.rand();
	}
	
	/**
	 * Function returns uniform distribution random number between 1 and 10.
	 * Time of packet in the channel in ms.
	 * */
	public double nextCTP()
	{
		return (double)( (int)ugCTP.rand(1, 10) );
	}
		
	/**
	 * Function returns uniform distribution random number.
	 * @param r - exponent of two, which is the upper limit of the interval
	 * */
	public double nextR(int r)
	{
		return ugR.rand(0, ((int)Math.pow(2, r) - 1) );
	}
		
	/**
	 * Function returns time of transmission ACK packet in the channel.
	 * */
	public double getCITZ()
	{
		return 1.0;
	}
	
	/**
	 * Function sets parameter L for exponential distribution random number
	 * @param valL - value of L param
	 * */	
	public void setL(double valL)
	{
		L = valL;
	}
	
	/**
	 * Function returns parameter L of exponential distribution random number
	 * */	
	public double getL()
	{
		return L;
	}
	
	private int[][] kernels = { 
			{1465331068, 1028724222, 730191016},
			{1452115327, 1014712603, 127399397},
			{1529432182, 1689457332, 769966478},
			{631996391, 155872730, 1746847398},
			{211050020, 2110278028, 1642577804},
			{754061791, 1162790328, 1477995502},
			{1597899664, 1219906847, 2137118428},
			{734084188, 1782794748, 798451857},
			{195798370, 1007926069, 676063154},
			{268910487, 192316291, 667932835},
			{1097927789, 960616877, 688960041},
			{1411244668, 255999122, 1773032477},
			{1346295554, 1881321630, 1586341650},
			{1688172069, 1545582719, 2070801728},
			{395140493, 140752266, 356390456},
			{1321376938, 2907925, 378300445},
			{502846175, 27392916, 1468769432},
			{673129837, 446176432, 1859499460},
			{538740147, 503597529, 688450497},
			{1440842596, 125885593, 253882970}
	};
	
	private double L = 0.0;
	private int numOfSimulation = 0;
	
	private UniformGenerator ugCGP = null;
	private ExpGenerator expCGP = null;
	private UniformGenerator ugCTP = null;
	private UniformGenerator ugR = null;
}

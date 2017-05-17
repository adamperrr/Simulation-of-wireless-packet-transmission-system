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
{	
	/**
	 * Constructor
	 * @param valL lambda value
	 * @param numOfSimulation simulation number
	 * @param transmitterId 
	 * */
	public RandomGenerator(double lambda, int simulationId)
	{
		L = lambda;
		this.simulationId = simulationId;
		numOfDevices = TransmissionSystem.TransmissionSystem.NUMBER_OF_DEVICES;
		
		expCGP = new ExpGenerator[numOfDevices];
		
		int kernel = 0;
		for(int i = 0; i < numOfDevices; i++)
		{
			kernel = kernels[simulationId][i];
			expCGP[i] = new ExpGenerator(L, new UniformGenerator(kernel));
		}

		ugCTP = new UniformGenerator(kernels[simulationId][numOfDevices]);
		ugR = new UniformGenerator(kernels[simulationId][numOfDevices+1]);
	}
		
	/**
	 * Function returns exponential distribution random number.
	 * Time between packets generation.
	 * @param k - transmitter/receiver id
	 * */
	public double nextCGP(int k)
	{
		return expCGP[k].rand();
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
			{207649607,1659031691,1925456182,244770060,1573018809,374048795,1067317268,379100918},
			{1534895405,328059867,930696947,550111416,1151530508,1790264919,1442369702,1586370763},
			{1447163646,1426956284,936850428,1216296359,1140341948,2139461338,1796297557,1445566448},
			{941871529,570008600,1059843140,1253247204,1566383367,858405535,708831458,1493107616},
			{638251813,1035628446,1360555917,1488220610,1938362674,233959241,1202854496,691883018},
			{178083338,522545489,1887834220,843403935,1790720755,879501059,719184999,1466830881},
			{2064555015,1498198540,1451223482,812027676,1230925877,1657881148,412449570,738521967},
			{1318573008,1541197261,389760368,1881889379,91261302,1562529835,1510205266,1798550493},
			{88375628,1706683789,1898709793,1506171960,1465289308,1694785199,1977005540,870623581},
			{1925089173,1050697402,20770241,705425957,1132887024,426327460,648021312,1832336402}
	};
	
	private double L = 0.0;
	private int simulationId = 0;
	private int numOfDevices = 0;
	
	private ExpGenerator[] expCGP = null;
	private UniformGenerator ugCTP = null;
	private UniformGenerator ugR = null;
}

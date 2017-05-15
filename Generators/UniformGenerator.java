package Generators;

import java.util.*;

public class UniformGenerator
{
	public UniformGenerator(int k)
	{
		kernel = k;
	}

	public double rand()
	{
		int h = kernel/Q;
		kernel = A*(kernel - Q*h) - R*h;
		
		if(kernel < 0)
			kernel = kernel + (int)(M);
		
		return kernel/M;
	}

	public double rand(int start, int end)
	{
		return rand()*(end-start) + start;
	}
	
	public int getKernel()
	{
		return kernel;
	}
	
	protected int kernel = 0;
	
	protected double M = 2_147_483_647.0;
	protected int A = 16_807;
	protected int Q = 127_773;
	protected int R = 2_836;
}
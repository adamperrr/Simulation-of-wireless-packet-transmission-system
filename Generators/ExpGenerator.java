package Generators;

import java.util.*;

class ExpGenerator
{
	public ExpGenerator(double la, UniformGenerator ug)
	{
		lambda = la;
		uniGen = ug;
	}

	public double rand()
	{
		double k = uniGen.rand();
		return -(1.0/lambda) * Math.log(k);
	}
	
	protected double lambda = 0.0;
	protected UniformGenerator uniGen;
}
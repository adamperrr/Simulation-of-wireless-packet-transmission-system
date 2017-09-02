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
    return -(1.0 / lambda) * Math.log(k);
  }

  private double lambda = 0.0;
  private UniformGenerator uniGen;
}
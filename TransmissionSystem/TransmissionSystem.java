package TransmissionSystem;

import java.io.IOException;
import java.util.*;
import Events.ConditionalEvent;
import Events.TimeEvent;
import Events.ConditionalEvents.*;
import Generators.RandomGenerator;

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
 * Class TransmissionSystem combines all of the functions and object of the
 * system.
 * 
 * Class contains two vectors: vector of transmitters and vector of receivers,
 * their size and object of channel.
 * 
 */

public class TransmissionSystem
{
  /**
   * Number of transmitters or receivers in the system.
   */
  public static final int NUMBER_OF_DEVICES = 15;// 6;

  /**
   * TransmissionSystem class constructor
   */
  public TransmissionSystem(double lambda, int simulationId, double initialPhase, boolean logsON)
  {
    Packet.resetIDGenerator();

    this.logsON = logsON;
    rand = new Generators.RandomGenerator(lambda, simulationId);

    if (initialPhase < 0.0)
    {
      this.initialPhase = 0.0;
    } else
    {
      this.initialPhase = initialPhase;
    }

    stat = new StatisticsCollector(NUMBER_OF_DEVICES, this);

    for (int i = 0; i < NUMBER_OF_DEVICES; i++)
    { // Initialization of transmitters and receivers variables
      transmitters.add(i, new Transmitter(i, this));
      receivers.add(i, new Receiver(i, this));
    }

    conditionalEvents.add(new ProcessingNewPacketEvent(this));
    conditionalEvents.add(new SendFromTransmitterEvent(this));
    conditionalEvents.add(new CollisionEvent(this));
    conditionalEvents.add(new SendACKEvent(this));
  }

  /**
   * Function with main loop of program
   */
  public void simulate()
  {
    // Initialization of first packets generations
    for (int i = 0; i < NUMBER_OF_DEVICES; i++)
    {
      Transmitter tempTrans = transmitters.get(i);
      TimeEvent tempEv = new Events.TimeEvents.GeneratePacketEvent(clock, tempTrans, this);
      addTimeEvent(tempEv);
    }

    if (TransmissionSystem.logsON)
      System.out.println(clock + ": Initialization of first packets generations is done");

    // Main simulation loop
    TimeEvent currentTimeEvent = null;

    if (stepMode)
    {
      while (clock < maximumSimulationTime && !timeEvents.isEmpty())
      {
        if (!timeEvents.isEmpty())
        {
          currentTimeEvent = timeEvents.get(0);
          timeEvents.remove(0);

          clock = currentTimeEvent.getTime();
          currentTimeEvent.execute();

          if (timeEvents.isEmpty() || timeEvents.get(0).getTime() != clock)
          {
            for (int i = 0; i < conditionalEvents.size(); i++)
            {
              conditionalEvents.get(i).execute();
            }
          }
        }

        System.out.println("---------------------------------------------------------------");
        try
        {
          System.in.read();
        } catch (IOException e)
        {
          e.printStackTrace();
        }

      }
    } else
    {
      while (clock < maximumSimulationTime && !timeEvents.isEmpty())
      {
        if (!timeEvents.isEmpty())
        {
          currentTimeEvent = timeEvents.get(0);
          timeEvents.remove(0);

          clock = currentTimeEvent.getTime();
          currentTimeEvent.execute();

          if (timeEvents.isEmpty() || timeEvents.get(0).getTime() != clock)
          {
            for (int i = 0; i < conditionalEvents.size(); ++i)
            {
              conditionalEvents.get(i).execute();
            }
          }
        }
      }
    }
  }

  /**
   * Returns value of the maximum simulation time
   * 
   * @returns maximumSimulationTime - maximumSimulationTime value
   */
  public double getMaximumSimulationTime()
  {
    return maximumSimulationTime;
  }

  /**
   * Returns value of the initial phase
   * 
   * @returns InitialPhase - InitialPhase value
   */
  public double getInitialPhase()
  {
    return initialPhase;
  }

  /**
   * Returns value of the clock
   * 
   * @returns clock - clock value
   */
  public double getClock()
  {
    return clock;
  }

  /**
   * Returns vector of the transmitters
   * 
   * @returns transmitters - transmitters vector
   */
  public Vector<Transmitter> getTransmitters()
  {
    return transmitters;
  }

  /**
   * Returns vector of the receivers
   * 
   * @returns receivers - receivers vector
   */
  public Vector<Receiver> getReceivers()
  {
    return receivers;
  }

  /**
   * Returns object of the channel
   * 
   * @returns channel - channel object
   */
  public Channel getChannel()
  {
    return channel;
  }

  /**
   * Adds event to the agenda (time events list)
   * 
   * @param timeEvent
   *          - event to add to agenda
   */
  public void addTimeEvent(TimeEvent in)
  {
    int size = timeEvents.size();

    if (timeEvents.isEmpty() || (timeEvents.get(0).getTime() >= in.getTime()))
    {
      timeEvents.add(0, in);
    } else if (timeEvents.get(size - 1).getTime() <= in.getTime())
    { // To speed up adding an item to the end.
      timeEvents.add(in);
    } else
    {
      for (int p = 0; p < size; p++)
      {
        if (timeEvents.get(p + 1).getTime() >= in.getTime())
        {
          timeEvents.add(p + 1, in);
          break;
        }
      }
    }
  }

  /**
   * Turns step work mode on
   */
  public void stepModeON()
  {
    stepMode = true;
  }

  /**
   * Turns step work mode off
   */
  public void stepModeOFF()
  {
    stepMode = false;
  }

  /**
   * Sets maximum simulation time in milliseconds Default time is 400.0ms
   * 
   * @param t
   *          time of simulation
   */
  public void setSimulationTime(double t)
  {
    maximumSimulationTime = t;
  }

  /**
   * Function returns main object of random generator
   * 
   * @returns rand - random generator main object
   */
  public Generators.RandomGenerator getRandObj()
  {
    return rand;
  }

  /**
   * Function returns main object of statistics collector
   * 
   * @returns stat - statistics collector main object
   */
  public StatisticsCollector getStatObj()
  {
    return stat;
  }

  /**
   * Function corrects precision of the given number
   * 
   * @param in
   */
  public static double correctPrecision(double in)
  {
    int precision = 10;
    int tempTime = (int) (precision * in);
    in = (double) (tempTime) / (double) (precision);
    return in;
  }

  private ArrayList<Events.TimeEvent> timeEvents = new ArrayList<>(); // List of
                                                                      // time
                                                                      // events
  private ArrayList<Events.ConditionalEvent> conditionalEvents = new ArrayList<>(); // List
                                                                                    // of
                                                                                    // time
                                                                                    // events
  private Vector<Transmitter> transmitters = new Vector<>(NUMBER_OF_DEVICES); // Vector
                                                                              // of
                                                                              // transmitters.
  private Vector<Receiver> receivers = new Vector<>(NUMBER_OF_DEVICES); // Vector
                                                                        // of
                                                                        // receivers.
  private Channel channel = new Channel(transmitters, receivers); // Channel
                                                                  // object.

  private double initialPhase = 0.0;
  private double clock = 0.0; // Clock value.
  private Generators.RandomGenerator rand = null;

  private boolean stepMode = false; // The step work mode
  private double maximumSimulationTime = 400.0; // Maximum simulation time
  private StatisticsCollector stat = null;

  public static boolean logsON = false;
}
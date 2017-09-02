package Events;
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
 * Interface of time event
 */
public interface TimeEvent
{
  /**
   * Clock time when event should be called.
   */
  void execute();

  /**
   * Time getter
   * 
   * @returns time - time of event
   */
  double getTime();

  /**
   * Clock time when event should be called.
   */
  double time = 0.0;
}

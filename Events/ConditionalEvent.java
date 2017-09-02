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
 * Interface of conditional event
 */
public interface ConditionalEvent
{
  /**
   * Conditions which should return true to execute event
   */
  boolean condition();

  /**
   * Execution of Event
   */
  void execute();
}

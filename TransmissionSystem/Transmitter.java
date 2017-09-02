package TransmissionSystem;

import java.util.*;

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
 * Class Transmitter Sends packets to channel and waits for the response.
 */
public class Transmitter implements Device
{
  /**
   * Constructor sets id of the Receiver.
   * 
   * @param id
   *          - id of the device.
   */
  public Transmitter(int id, TransmissionSystem ts)
  {
    this.id = id;
    this.rand = ts.getRandObj();
    transmissionSettings = new TransmissionSettings(rand);
  }

  /**
   * Pushes packet to the Transmitter from the channel
   * 
   * @param packet
   *          - packet for the Transmitter
   */
  public void pushPacket(Packet packet)
  {
    receivedPacket = packet;
  }

  /**
   * Checks if buffer is empty
   * 
   * @returns true when is empty
   */
  public boolean isBufferEmpty()
  {
    return buffer.isEmpty();
  }

  /**
   * Pushes packet to the buffer
   * 
   * @param packet
   *          - packet for the transmitter
   */
  public void pushPacketToBuffer(Packet packet)
  {
    buffer.add(packet);
  }

  /**
   * Returns packet from the peak of buffer and removes it from buffer
   * 
   * @returns packet from buffer
   */
  public Packet getPacketFromBuffer()
  {
    Packet temp = null;
    if (!buffer.isEmpty())
    {
      temp = buffer.peek();
      buffer.remove();
    }
    return temp;
  }

  /**
   * Returns id of transmitter
   * 
   * @returns id - id of transmitter
   */
  public int getId()
  {
    return id;
  }

  /**
   * Checks if device is in the busy state.
   */
  public boolean isBusy()
  {
    return isBusy;
  }

  /**
   * Sets device in the busy state.
   */
  public void setBusy()
  {
    isBusy = true;
  }

  /**
   * Sets device in the free state.
   */
  public void setFree()
  {
    isBusy = false;
  }

  /**
   * Checks if transmitter has a packet ready to send (after listening)
   * 
   * @returns ready - has a packet ready to send
   */
  public boolean isReady()
  {
    return packetReadyToSend;
  }

  /**
   * Sets device not ready to send packet.
   */
  public void setNotReady()
  {
    packetReadyToSend = false;
  }

  /**
   * Sets device in the ready state.
   */
  public void setReady()
  {
    packetReadyToSend = true;
  }

  /**
   * Transmission settings for this transmitter Declaration is placed here
   * because it needs rand to work.
   */
  public TransmissionSettings transmissionSettings = null;

  private int id = -1; // Id of this device.
  private Queue<Packet> buffer = new LinkedList<>(); // Buffer of packets to
                                                     // send to channel.
  private Packet receivedPacket = null; // Packet received from the channel.
  private boolean isBusy = false; // Occupancy state of the device. If packet is
                                  // processed. It doesn't mean packet is ready
                                  // to send.
  private boolean packetReadyToSend = false; // Packet is ready to send after
                                             // listening
  private RandomGenerator rand = null; // Object of random generator.
}

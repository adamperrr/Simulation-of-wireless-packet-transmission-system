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
 * Class containing the functionality of all the necessary random generators in
 * the program.
 * 
 */

public class RandomGenerator
{
  /**
   * Constructor
   * 
   * @param valL
   *          lambda value
   * @param numOfSimulation
   *          simulation number
   * @param transmitterId
   */
  public RandomGenerator(double lambda, int simulationId)
  {
    L = lambda;
    this.simulationId = simulationId;
    numOfDevices = TransmissionSystem.TransmissionSystem.NUMBER_OF_DEVICES;

    expCGP = new ExpGenerator[numOfDevices];

    int kernel = 0;
    for (int i = 0; i < numOfDevices; i++)
    {
      kernel = kernels[simulationId][i];
      expCGP[i] = new ExpGenerator(L, new UniformGenerator(kernel));
    }

    ugCTP = new UniformGenerator(kernels[simulationId][numOfDevices]);
    ugR = new UniformGenerator(kernels[simulationId][numOfDevices + 1]);
  }

  /**
   * Function returns exponential distribution random number. Time between
   * packets generation.
   * 
   * @param k
   *          - transmitter/receiver id
   */
  public double nextCGP(int k)
  {
    return expCGP[k].rand();
  }

  /**
   * Function returns uniform distribution random number between 1 and 10. Time
   * of packet in the channel in ms.
   */
  public double nextCTP()
  {
    return (double) ((int) ugCTP.rand(1, 10));
  }

  /**
   * Function returns uniform distribution random number.
   * 
   * @param r
   *          - exponent of two, which is the upper limit of the interval
   */
  public double nextR(int r)
  {
    return ugR.rand(0, ((int) Math.pow(2, r) - 1));
  }

  /**
   * Function returns time of transmission ACK packet in the channel.
   */
  public double getCITZ()
  {
    return 1.0;
  }

  /**
   * Function sets parameter L for exponential distribution random number
   * 
   * @param valL
   *          - value of L param
   */
  public void setL(double valL)
  {
    L = valL;
  }

  /**
   * Function returns parameter L of exponential distribution random number
   */
  public double getL()
  {
    return L;
  }

  private int[][] kernels = {
      { 1465331068, 1028724222, 730191016, 1452115327, 1014712603, 127399397, 1529432182, 1689457332, 769966478,
          631996391, 155872730, 1746847398, 211050020, 2110278028, 1642577804, 754061791, 1162790328, 1477995502,
          1597899664, 1219906847, 2137118428, 734084188, 1782794748, 798451857, 195798370, 1007926069, 676063154,
          268910487, 192316291, 667932835, 1097927789, 960616877 },
      { 688960041, 1411244668, 255999122, 1773032477, 1346295554, 1881321630, 1586341650, 1688172069, 1545582719,
          2070801728, 395140493, 140752266, 356390456, 1321376938, 2907925, 378300445, 502846175, 27392916, 1468769432,
          673129837, 446176432, 1859499460, 538740147, 503597529, 688450497, 1440842596, 125885593, 253882970,
          865452745, 1761783297, 1731583027, 363172553 },
      { 1286400662, 1342216572, 1357317713, 1909566880, 1874154442, 1725111278, 351395445, 1888366014, 1289428712,
          970603627, 940501705, 1170593575, 1314012283, 1639626084, 228605501, 1647657127, 1585914057, 2068647802,
          2141160380, 99762414, 1045056233, 106097542, 217751780, 1366771, 303657592, 1657613323, 1165986147, 370188870,
          824324190, 744544720, 99458669, 1083839931 },
      { 1221611353, 778405308, 1773233354, 630633685, 1244490312, 2136948615, 235575807, 338605962, 1250592935,
          448720982, 1431411483, 236952308, 1048113010, 743795113, 1796475567, 1423897734, 1018421628, 836450152,
          1658255315, 1967822595, 928029324, 1107232279, 82310167, 2145086133, 1540474679, 697955886, 877171285,
          657780972, 1413507550, 830369074, 870532441, 734504602 },
      { 1503405538, 777650303, 1928352739, 816317569, 649566828, 28398371, 755732433, 886006725, 2131112372, 70462090,
          1305761908, 7492721, 136663221, 1011452157, 447776514, 2115719950, 1299162300, 1474043950, 979607154,
          1537732581, 978108827, 1374322368, 905834499, 594302375, 40436566, 1675856088, 892443106, 1509427925,
          1745488783, 1762083173, 889601591, 1667605164 },
      { 1045175192, 585003770, 1645355650, 1723544949, 257378345, 1109391773, 1397124832, 1876668472, 70347676,
          1076023857, 1970876794, 1092703995, 1028072434, 726580602, 1773400556, 1258156911, 388152735, 186865603,
          654095312, 1857810782, 120164037, 218657884, 109762227, 1026177224, 330925372, 1130911387, 184335730,
          1236189087, 672539875, 1050923106, 206922283, 1082149902 },
      { 1991221337, 730352641, 744802402, 1073419484, 1105210297, 531427124, 2087753774, 934045222, 979402620,
          641097605, 1460627274, 1112502799, 2134235808, 20827269, 2101098168, 146871541, 1120451097, 316039875,
          2084374962, 629423242, 1274139729, 1699791871, 2053553860, 1575343740, 1262825362, 841704953, 2002190244,
          105743170, 130309628, 379952994, 1156263595, 1305248251 },
      { 1159497120, 801737749, 1848766339, 1060238228, 1174463724, 1475391536, 465379802, 710450286, 1335452207,
          735720608, 854579739, 1056889538, 637372333, 86255139, 1700993644, 1867526746, 2043033399, 1295733075,
          327760401, 1646320100, 527275898, 541592546, 1107953447, 126092390, 1903942765, 1763274535, 617596112,
          18283043, 522785825, 120565830, 613770212, 1938295888 },
      { 1988860320, 2090180226, 1694433905, 8629524, 279804726, 989640191, 529410953, 1945421396, 1394977011,
          1767371531, 806662474, 1761763630, 1963141692, 1441775402, 898691683, 1801301614, 1463802837, 569674477,
          79600820, 759241663, 1443004729, 475489198, 163178549, 2089583803, 367316225, 1253263522, 1261787727,
          2044349226, 1659903348, 1582707317, 681148599, 41584723 },
      { 1758607312, 1838789304, 512070610, 1691754345, 2029050976, 43996341, 666364681, 1435415019, 107570016,
          822153919, 1530704509, 689435961, 818280935, 1745458880, 1513275335, 5383435, 800431090, 205036721,
          1547112514, 612693244, 1466997148, 1854117291, 1957926059, 1450596527, 691861457, 1904726741, 1416117436,
          33643306, 767158463, 970106813, 137110387, 292205817 } };

  private double L = 0.0;
  private int simulationId = 0;
  private int numOfDevices = 0;

  private ExpGenerator[] expCGP = null;
  private UniformGenerator ugCTP = null;
  private UniformGenerator ugR = null;
}

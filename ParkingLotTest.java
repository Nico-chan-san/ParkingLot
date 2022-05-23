import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ParkingLotTest {
  private static Vehicle astroSurfer;
  private static Vehicle voidWalker;
  private static Vehicle starStreamer;
  private static Vehicle galaxyHopper;
  private static Vehicle victoria;

  private static Date date1;
  private static Date date2;
  private static Date date3;
  private static Date date4;
  private static Date date5;

  private static ParkingLot spaceComplex;

  @BeforeClass
  public static void onceBefore() throws Exception {
    // Create vehicles used by all tests
    astroSurfer = new Vehicle("FDW531");
    voidWalker = new Vehicle("HBT824");
    starStreamer = new Vehicle("LQT194");
    galaxyHopper = new Vehicle("KLA125");
    victoria = new Vehicle("MBU934");

    // Create arbitrary dates used by all tests. In practice, current timestamps would
    // be used.
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss", Locale.ENGLISH);
    date1 = sdf.parse("2022/05/21/18/27/55");
    date2 = sdf.parse("2022/05/22/22/29/30");
    date3 = sdf.parse("2022/05/22/23/17/19");
    date4 = sdf.parse("2022/05/22/23/29/58");
    date5 = sdf.parse("2022/05/23/23/29/58");
  }

  @Before
  public void beforeEachTest() {
    // Create empty parking lot for each test
    spaceComplex = new ParkingLot();
  }

  @Test
  public void testParkVehicle() throws Exception {
    boolean res = spaceComplex.parkVehicle(astroSurfer, 18, date3);
    assertEquals(true, res);
  }

  @Test(expected = Exception.class)
  public void testParkVehicleInvalidIdPositive() throws Exception {
    spaceComplex.parkVehicle(voidWalker, 73, date3);
  }

  @Test(expected = Exception.class)
  public void testParkVehicleInvalidIdNegative() throws Exception {
    spaceComplex.parkVehicle(victoria, -28, date3);
  }

  @Test(expected = Exception.class)
  public void testParkVehicleOccupiedSpot() throws Exception {
    spaceComplex.parkVehicle(astroSurfer, 18, date3);
    spaceComplex.parkVehicle(voidWalker, 18, date4);
  }

  @Test
  public void testUnparkVehicleById() throws Exception {
    spaceComplex.parkVehicle(galaxyHopper, 18, date1);
    boolean res = spaceComplex.unparkVehicle(18, date3);
    assertEquals(true, res);
  }

  @Test(expected = Exception.class)
  public void testUnparkVehicleByIdEmptySpot() throws Exception {
    spaceComplex.parkVehicle(voidWalker, 18, date2);
    spaceComplex.unparkVehicle(19, date4);
  }

  @Test
  public void testUnparkVehicleByVehicle() throws Exception {
    spaceComplex.parkVehicle(victoria, 18, date1);
    boolean res = spaceComplex.unparkVehicle(victoria, date4);
    assertEquals(true, res);
  }

  @Test(expected = Exception.class)
  public void testUnparkVehicleByVehicleNotParked() throws Exception {
    spaceComplex.unparkVehicle(victoria, date5);
  }

  @Test(expected = Exception.class)
  public void testUnparkVehicleByVehicleInvalid() throws Exception {
    spaceComplex.parkVehicle(starStreamer, 18, date2);
    starStreamer = null;
    spaceComplex.unparkVehicle(starStreamer, date5);
  }

  @Test
  public void testCalculateParkingFee() throws Exception {
    double fee = ParkingSystem.calculateParkingFee(date4, date5);
    assert(410 == fee);
  }

  @Test(expected = Exception.class)
  public void testCalculateParkingFeeInvertedDates() throws Exception {
    ParkingSystem.calculateParkingFee(date5, date4);
  }

  @Test
  public void testLogParkingEventArrive() throws Exception {
    ParkingSpot spot = new ParkingSpot(99);
    String license = voidWalker.getLicense();
    boolean res = ParkingSystem.logParkingEvent(spot, license, date4, true, "TestLog.txt");
    assertEquals(true, res);
  }

  @Test
  public void testLogParkingEventDepart() throws Exception {
    ParkingSpot spot = new ParkingSpot(99);
    String license = voidWalker.getLicense();
    boolean res = ParkingSystem.logParkingEvent(spot, license, date4, false, "TestLog.txt");
    assertEquals(true, res);
  }
}

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Main {

  public static void main(String[] args) throws Exception {
    /*
     * Basic example of program functions
     * Creates a parking lot, an arbitrary start and end time, and a vehicle.
     * Parks and unparks the vehicle. The total fee will be written in the console.
     * The parking event will be logged to ParkingLog.txt
     * 
     * JUnit 4 tests available in ParkingLotTest.java
     */

    ParkingLot spaceComplex = new ParkingLot();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss", Locale.ENGLISH);
    Date startTime = sdf.parse("2022/05/23/18/27/55");
    Date endTime = sdf.parse("2022/05/23/19/27/56");
    // ParkingSystem.calculateParkingFee(startTime, endTime);

    Vehicle astroSurfer = new Vehicle("QES782");

    spaceComplex.parkVehicle(astroSurfer, 18, startTime);
    spaceComplex.unparkVehicle(astroSurfer, endTime);
    // spaceComplex.unparkVehicle(18, end); // Can also unpark by parking spot ID
  }
}

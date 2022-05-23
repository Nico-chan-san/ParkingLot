import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ParkingSystem {

  public static double calculateParkingFee(Date start, Date end) throws Exception {
    double fee = 0;
    long diffInMilliseconds = (end.getTime() - start.getTime());
    if (diffInMilliseconds < 0) {
      throw new Exception("Invalid dates. Cannot park for negative duration.");
    }
    long diffInHours = TimeUnit.HOURS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
    fee += diffInHours * 15;
    fee += ((int)(diffInHours / 24)) * 50;
    return fee;
  }

  public static boolean logParkingEvent(ParkingSpot spot, String license, Date time, boolean arriving, String fileName) {
    String logData = "";
    if (arriving) {
      logData += "Parking space occupied: " + spot.getId() + "\n";
    } else {
      logData += "Parking space vacated: " + spot.getId() + "\n";
    }
    logData += "Vehicle license plate: " + license + "\n" +
        "Date: " + time + "\n\n-----------------------------------------\n\n";

    try {
      FileWriter fileWriter = new FileWriter(fileName, true);
      fileWriter.append(logData);
      fileWriter.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}
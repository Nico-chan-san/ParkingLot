import java.util.ArrayList;
import java.util.Date;

public class ParkingLot {
  private ArrayList<ArrayList<ParkingSpot>> parkingSpots;

  final static int numFloors = 3;
  final static int numSpots = 15;

  public ParkingLot() {
    parkingSpots = new ArrayList<ArrayList<ParkingSpot>>(numFloors);

    for (int i = 1; i <= numFloors; i++) {
      ArrayList<ParkingSpot> floor = new ArrayList<ParkingSpot>(numSpots);
      for (int j = 1; j <= numSpots; j++) {
        int spotId = j + ((i - 1) * 15);
        floor.add(new ParkingSpot(spotId));
      }
      parkingSpots.add(floor);
    }
  }

  private ParkingSpot getParkingSpot(int id) throws Exception {
    if (id <= numFloors * numSpots && id > 0) {
      int floorNumber = id / 15;
      ArrayList<ParkingSpot> floor = parkingSpots.get(floorNumber);
      ParkingSpot spot = floor.get((id % numSpots) - 1);
      return spot;
    } else {
      throw new Exception("Invalid parking spot ID: " + id);
    }
  }

  public boolean parkVehicle(Vehicle ship, int id, Date time) throws Exception {
    ParkingSpot spot = getParkingSpot(id);
    if (ship != null){
      if (!spot.occupied()) {
        spot.beginParking(time, ship);
      } else {
        throw new Exception("Spot " + id + " already occupied.");
      }
  
      String license = ship.getLicense();
      ParkingSystem.logParkingEvent(spot, license, time, true, "ParkingLog.txt");
      return true;
    } else {
      throw new Exception("Unable to park; vehicle does not exist.");
    }
    
  }

  public boolean unparkVehicle(int id, Date time) throws Exception {
    ParkingSpot spot = getParkingSpot(id);
    Vehicle ship = spot.getVehicle();
    if (ship == null) {
      throw new Exception("No vehicle parked in spot: " + spot.getId());
    } else {
      String license = ship.getLicense();
      ParkingSystem.logParkingEvent(spot, license, time, false, "ParkingLog.txt");
      double fee = ParkingSystem.calculateParkingFee(spot.getParkingBegan(), time);
      System.out.println("Total fee for parking session: " + fee + " SEK");
      spot.stopParking();
      return true;
    }
  }

  public boolean unparkVehicle(Vehicle vehicle, Date time) throws Exception {
    if (vehicle != null) {
      ParkingSpot affectedSpot = null;
      boolean unparked = false;
      for (ArrayList<ParkingSpot> floor : parkingSpots) {
        for (ParkingSpot spot : floor) {
          if (spot.occupied()) {
            String parkedLicense = spot.getVehicle().getLicense();
            String leavingLicense = vehicle.getLicense();
            if (parkedLicense.equals(leavingLicense)) {
              affectedSpot = spot;
              unparked = true;
              break;
            }
          }
        }
        if (unparked)
          break;
      }

      if (!unparked) {
        throw new Exception("Vehicle is not parked in lot.");
      } else {
        ParkingSystem.logParkingEvent(affectedSpot, vehicle.getLicense(), time, false, "ParkingLog.txt");
        double fee = ParkingSystem.calculateParkingFee(affectedSpot.getParkingBegan(), time);
        System.out.println("Total fee for parking session: " + fee + " SEK");
        affectedSpot.stopParking();
        return true;
      }
    } else {
      throw new Exception("Unable to depart; vehicle does not exist.");
    }
  }
}
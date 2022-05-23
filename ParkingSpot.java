import java.util.Date;

public class ParkingSpot {
  private int id;
  private boolean occupied;
  private Date parkingBegan;
  private Vehicle parkedVehicle;

  public ParkingSpot(int id) {
    this.id = id;
    occupied = false;
  }

  public int getId() {
    return id;
  }

  public Vehicle getVehicle() {
    return parkedVehicle;
  }

  public Date getParkingBegan() {
    return parkingBegan;
  }

  public boolean occupied() {
    return occupied;
  }

  public void beginParking(Date time, Vehicle ship) {
    occupied = true;
    parkedVehicle = ship;
    parkingBegan = time;
  }

  public void stopParking() {
    occupied = false;
    parkingBegan = null;
    parkedVehicle = null;
  }

}
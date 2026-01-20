// src/main/java/Reservation.java
import java.time.LocalDateTime;

public class Reservation {
    private int reservationID;
    private int userID;
    private ParkingSpot spot;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean confirmed;

    public Reservation(int reservationID, int userID, ParkingSpot spot,
                       LocalDateTime startTime, LocalDateTime endTime) {
        this.reservationID = reservationID;
        this.userID = userID;
        this.spot = spot;
        this.startTime = startTime;
        this.endTime = endTime;
        this.confirmed = false;
    }

    public boolean processReservation() {
        if (startTime == null || endTime == null || !startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Invalid reservation times");
        }
        if (spot == null || spot.getStatus() != ParkingSpot.Status.AVAILABLE) {
            throw new IllegalStateException("Parking spot not available");
        }
        confirmed = true;
        spot.setStatus(ParkingSpot.Status.RESERVED);
        return true;
    }

    public boolean isConfirmed() { return confirmed; }
}
// src/test/java/ReservationTest.java
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    private Reservation res;
    private ParkingSpot spot;
    private LocalDateTime now;
    private LocalDateTime futureTime;

    @BeforeEach
    void setUp() {
        // initialize the default value
        now = LocalDateTime.of(2025, 12, 1, 10, 0);
        futureTime = now.plusHours(2);

        // create a new ParkingSpot
        spot = new ParkingSpot(1, ParkingSpot.Status.AVAILABLE);
    }

    @AfterEach
    void tearDown() {
        // reset the variable of instance
        res = null;
        spot = null;
        now = null;
        futureTime = null;
    }

    @Test
    @DisplayName("Case 1: Should throw IllegalArgumentException when startTime >= endTime")
    void testInvalidTimeThrowsIllegalArgumentException() {
        // if startTime(now) == endTime(now):
        res = new Reservation(1, 1001, spot, now, now);

        // Using assertThrows to valid processReservation
        assertThrows(IllegalArgumentException.class, () -> {
            res.processReservation();
        });
    }

    @Test
    @DisplayName("Case 2: Should throw IllegalStateException when parking spot is not AVAILABLE")
    void testUnavailableSpotThrowsIllegalStateException() {
        // create a RESERVED ParkingSpot
        ParkingSpot reservedSpot = new ParkingSpot(2, ParkingSpot.Status.RESERVED);
        // another status selectable: ParkingSpot reservedSpot = new ParkingSpot(2, ParkingSpot.Status.OUT_OF_SERVICE);
        res = new Reservation(2, 1002, reservedSpot, now, futureTime);

        // Using assertThrows to valid processReservation
        assertThrows(IllegalStateException.class, () -> {
            res.processReservation();
        });
    }

    @Test
    @DisplayName("Case 3: Should return true and confirm reservation for valid inputs")
    @Timeout(100) // the test must halt in 100ms
    void testValidReservationReturnsTrue() {
        res = new Reservation(3, 1003, spot, now, futureTime);

        // valid the return value: true
        assertTrue(res.processReservation());

        // valid the reservation state confirmed or not
        assertTrue(res.isConfirmed());

        // valid the status of ParkingSpot released or ont
        assertEquals(ParkingSpot.Status.RESERVED, spot.getStatus());
    }
}
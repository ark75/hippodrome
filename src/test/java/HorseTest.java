import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {
    public static String HORSE_NAME_TEST = "Тестовое имя лошади";
    public static double HORSE_SPEED_TEST = 40.0;
    public static double HORSE_NEGATIVE_SPEED_TEST = -40.0;
    public static double HORSE_DISTANCE_TEST = 50.0;
    public static double HORSE_NEGATIVE_DISTANCE_TEST = -50.0;

    @Test
    void exception_OnNullName_Test() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, HORSE_SPEED_TEST, HORSE_DISTANCE_TEST));
    }

    @Test
    void messageOfException_OnNullName_Test() {
        try {
            new Horse(null, HORSE_SPEED_TEST, HORSE_DISTANCE_TEST);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "\t"})
    void exception_OnNameIsBlank_Test(String name) {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, HORSE_SPEED_TEST, HORSE_DISTANCE_TEST));
    }


    @ParameterizedTest
    @ValueSource(strings = {" ", "", "\t"})
    void messageOfException_OnNameIsBlank_Test(String name) {
        try {
            new Horse(name, HORSE_SPEED_TEST, HORSE_DISTANCE_TEST);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    void exception_OnSpeedIsNegative_Test() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(HORSE_NAME_TEST, HORSE_NEGATIVE_SPEED_TEST, HORSE_DISTANCE_TEST));
    }

    @Test
    void messageOfException_OnSpeedIsNegative_Test() {
        try {
            new Horse(HORSE_NAME_TEST, HORSE_NEGATIVE_SPEED_TEST, HORSE_DISTANCE_TEST);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    void exception_OnDistanceIsNegative_Test() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(HORSE_NAME_TEST, HORSE_SPEED_TEST, HORSE_NEGATIVE_DISTANCE_TEST));
    }

    @Test
    void messageOfException_OnDistanceIsNegative_Test() {
        try {
            new Horse(HORSE_NAME_TEST, HORSE_SPEED_TEST, HORSE_NEGATIVE_DISTANCE_TEST);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Distance cannot be negative."));
        }
    }

    @Test
    void getNameTest() {
        assertEquals(HORSE_NAME_TEST, new Horse(HORSE_NAME_TEST, HORSE_SPEED_TEST, HORSE_DISTANCE_TEST).getName());
    }

    @Test
    void getSpeedTest() {
        assertEquals(HORSE_SPEED_TEST, new Horse(HORSE_NAME_TEST, HORSE_SPEED_TEST, HORSE_DISTANCE_TEST).getSpeed());
    }

    @Test
    void getDistanceTest() {
        assertEquals(HORSE_DISTANCE_TEST, new Horse(HORSE_NAME_TEST, HORSE_SPEED_TEST, HORSE_DISTANCE_TEST).getDistance());
    }

    @Test
    void getZeroDistance_OnTwoArguments_Test() {
        assertEquals(0.0, new Horse(HORSE_NAME_TEST, HORSE_SPEED_TEST).getDistance());
    }

    @Test
    void moveTest() {
        Horse horse = Mockito.spy(new Horse(HORSE_NAME_TEST, HORSE_SPEED_TEST, HORSE_DISTANCE_TEST));
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.3, 0.6, 1.0, 999.99, 0.0})
    void moveTest_ExpectedDistance(double random) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse(HORSE_NAME_TEST,HORSE_SPEED_TEST,HORSE_DISTANCE_TEST);
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(HORSE_DISTANCE_TEST + HORSE_SPEED_TEST * random, horse.getDistance());
        }
    }
}
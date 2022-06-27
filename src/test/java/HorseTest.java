import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {
public static String HORSE_NAME_TEST ="Тестовое имя лошади";
public static double HORSE_SPEED_TEST = 40.0;
public static double HORSE_DISTANCE_TEST = 50.0;


    @Test
    void exception_OnFirstArgumentIsNull_Test() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1, 2));
    }

    @Test
    void messageOfException_OnFirstArgumentIsNull_Test() {
        try {
            new Horse(null, 1, 2);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Name cannot be null."));
        }
    }

    @ParameterizedTest
    @MethodSource("blankStringStream")
    void exception_OnFirstArgumentIsBlank_Test(String name) {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 1, 2));
    }


    @ParameterizedTest
    @MethodSource("blankStringStream")
    void messageOfException_OnFirstArgumentIsBlank_Test(String name) {
        try {
            new Horse(name, 1, 2);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Name cannot be blank."));
        }
    }

    @Test
    void exception_OnSecondArgumentIsNegative_Test() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(HORSE_NAME_TEST, -1, 2));
    }

    @Test
    void messageOfException_OnSecondArgumentIsNegative_Test() {
        try {
            new Horse(HORSE_NAME_TEST, -1, 2);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Speed cannot be negative."));
        }
    }

    @Test
    void exception_OnThirdArgumentIsNegative_Test() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(HORSE_NAME_TEST, 1, -2));
    }

    @Test
    void messageOfException_OnThirdArgumentIsNegative_Test() {
        try {
            new Horse(HORSE_NAME_TEST, 1, -2);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Distance cannot be negative."));
        }
    }

    @Test
    void getNameTest() {
        assertEquals(HORSE_NAME_TEST, new Horse(HORSE_NAME_TEST, 1, 2).getName());
    }

    @Test
    void getSpeedTest() {
        assertEquals(HORSE_SPEED_TEST, new Horse(HORSE_NAME_TEST, HORSE_SPEED_TEST, 2).getSpeed());
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
    @Test
    void moveTest_ExpectedDistance() {
        double expectedResult = 0.99;
        Horse horse = Mockito.spy(new Horse(HORSE_NAME_TEST, HORSE_SPEED_TEST, HORSE_DISTANCE_TEST));
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {

            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(expectedResult);
            horse.move();
            assertEquals(HORSE_DISTANCE_TEST + HORSE_SPEED_TEST * expectedResult, horse.getDistance());
        }
    }

    static Stream<String> blankStringStream() {
        return Stream.of(" ", "", "\t");
    }
}
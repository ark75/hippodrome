import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {
    @Test
    void exception_OnArgumentIsNull_Test() {
        assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
    }

    @Test
    void messageOfException_OnArgumentIsNull_Test() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Horses cannot be null."));
        }
    }

    @Test
    void exception_OnBlankList_Test() {
        assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(List.of()));
    }

    @Test
    void messageOfException_OnBlankList_Test() {
        try {
            new Hippodrome(List.of());
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Horses cannot be empty."));
        }
    }

    @Test
    void getHorses_SameOrder_Test() {
        List<Horse> horses =
                IntStream.range(1, 31)
                        .mapToObj(horse -> Mockito.spy(new Horse(String.valueOf(horse), horse)))
                        .collect(Collectors.toList());

        assertArrayEquals(horses.toArray(new Horse[0]), new Hippodrome(horses).getHorses().toArray(new Horse[0]));
    }


    @Test
    void moveAllHorsesCheckTest() {
        List<Horse> horses =
                IntStream.range(1, 51)
                        .mapToObj(horse -> Mockito.mock(Horse.class))
                        .collect(Collectors.toList());
        Hippodrome hippodrome = Mockito.spy(new Hippodrome(horses));
        hippodrome.move();
        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horses =
                IntStream.range(1, 51)
                        .mapToObj(horse -> Mockito.spy(new Horse(String.valueOf(horse), horse)))
                        .collect(Collectors.toList());

        Hippodrome hippodrome = Mockito.spy(new Hippodrome(horses));
        hippodrome.move();
        Horse winner = horses.stream().max(Comparator.comparing(Horse::getDistance)).get();
        assertEquals(winner, hippodrome.getWinner());
    }
}
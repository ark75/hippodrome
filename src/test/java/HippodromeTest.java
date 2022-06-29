import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
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
                () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void messageOfException_OnBlankList_Test() {
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Horses cannot be empty."));
        }
    }

    @Test
    void getHorses_SameOrder_Test() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            horses.add(new Horse("Лошадь" + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }


    @Test
    void moveAllHorsesCheckTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            horses.add(new Horse("Лошадь" + i, i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        assertSame(horses.get(29), hippodrome.getWinner());
    }
}
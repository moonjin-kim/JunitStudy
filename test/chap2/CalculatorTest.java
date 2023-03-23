package chap2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void plus() {
        int result = Calculator.plus(1,2);
        assertEquals(3,result);
    }
}
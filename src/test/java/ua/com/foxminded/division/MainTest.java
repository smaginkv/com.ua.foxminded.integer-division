package ua.com.foxminded.division;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import ua.com.foxminded.division.exception.DivisionNoSuchOptionExeption;

class MainTest {    
    @Test
    void shouldThrowExceptionWhenIncorrectOption() { 
        assertThrows(DivisionNoSuchOptionExeption.class, new Executable() {                
            public void execute() throws Throwable {
                Main.proccessDivision("10", "2", "-m");                    
            }
        });
    }
}

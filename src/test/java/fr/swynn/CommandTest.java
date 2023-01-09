package fr.swynn;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.swynn.utils.Command;

public class CommandTest {
    
    @Test
    public void testFalseCommand() {
        Command command = new Command("arz");
        assertFalse("Commande existante", command.isSuccessful());
    }

    @Test
    public void testTrueCommand() {
        Command command = new Command("ls");
        assertTrue("Commande inexistante", command.isSuccessful());
    }
}

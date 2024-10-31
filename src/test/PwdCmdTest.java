package test;

import main.commands.PwdCommand;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;

public class PwdCmdTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PwdCommand pwdCommand;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        pwdCommand = new PwdCommand();
    }

    @Test
    public void testPrintCurrentDirectory() {
        pwdCommand.execute(new String[]{});
        
        String expectedOutput = System.getProperty("user.dir").trim();
        String actualOutput = outContent.toString().trim();
        
        assertEquals("PWD command should output the current directory path", expectedOutput, actualOutput);
    }
}

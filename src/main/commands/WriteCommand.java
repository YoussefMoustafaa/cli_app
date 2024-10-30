package main.commands;
import java.io.FileWriter;
import java.io.IOException;
// implement > command
public class WriteCommand implements Command {

    private String input;

    public WriteCommand() {
        input = "";
    }

    @Override
    public void execute(String[] args) {
        // Check if a filename and content are provided as arguments
        if (args == null || args.length < 2) {
            System.out.println("Error: '>' command requires a filename and text to write.");
            return;
        }

        // The first argument is the filename, and the rest is the content
        String filename = args[0];
        StringBuilder content = new StringBuilder();

        // Append the rest of the args as the content
        for (int i = 1; i < args.length; i++) {
            content.append(args[i]).append(" ");
        }

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content.toString().trim());  // Trim extra space at the end
            System.out.println("Success: Content written to " + filename);
        } catch (IOException e) {
            System.out.println("Error: Unable to write to file - " + e.getMessage());
        }
    }

    public void setInput(String inputString) {
        this.input = inputString;
    }
}

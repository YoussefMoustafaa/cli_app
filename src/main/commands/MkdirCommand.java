package main.commands;

import java.io.File;

public class MkdirCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("No directory name provided.");
            return;
        }
        for (String dirName : args) {
            File dir = new File(dirName);
            if (!dir.exists()) 
            {
                if (dir.mkdirs()) 
                {
                    System.out.println("Directory created: " + dirName);
                } else 
                {
                    System.out.println("Failed to create directory: " + dirName);
                }
            } 
            else 
            {
                System.out.println("Directory already exists: " + dirName);
            }
        }

    }
}

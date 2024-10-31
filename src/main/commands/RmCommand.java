package main.commands;

import java.io.File;
import java.util.Arrays;

import main.executor.CommandExecutor;
import main.fileSystem.FileSystem;

public class RmCommand implements Command {

    public RmCommand(){
//        this.currPath = path;
    }

    @Override
    public void execute(String[] args) {
        FileSystem fileSystem = FileSystem.getInstance();
        CommandExecutor executor = new CommandExecutor();
        File dir = new File(fileSystem.getCurrentDirectory().toString());
//        System.out.println(dir);

        File[] listOfFiles = dir.listFiles();

        if (args == null || args.length == 0) {
            System.out.println("rm: missing operand");
        } else {
            for (int i = 0; i < args.length; i++) {
                String fileToDel = args[i];
                // boolean found = false;

                if (executor.isChainedCmd(args[i])) {
                    String[] remArgs = Arrays.copyOfRange(args, i+1, args.length);
                    executor.executeChainedCmd(args[i], remArgs, "");
                }

                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().equals(fileToDel)) {
                        if (file.delete()) {
                            System.out.println("File '" + fileToDel + "' has been removed.");
                            // found = true;
                            break; // Exit the loop once the file is found and deleted
                        }
                    }
                    if (file.isDirectory() && file.getName().equals(fileToDel)) {
                        System.out.println("rm: cannot remove '" + args[i] + "': Is a directory");
                        // found = true;
                        break;
                    }
                }

                // if (!found) {
                //     System.out.println("rm: cannot remove '" + args[i] + "': No such file or directory");
                // }
            }

        }
    }
    
}

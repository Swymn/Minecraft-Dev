package fr.swynn.utils;

import java.io.IOException;

import fr.swynn.App;

enum Status {
    SUCCESS(0),
    ERROR(-1),
    INTERRUPTED(-2);

    Status(int value) {}
}

public class Command {
    
    private Status status;

    /**
     * Execute a command and wait for its end
     * 
     * @param command The command to execute - String
     */
    public Command(String command) {
        try {
            Process process = Runtime.getRuntime().exec((App.isWindowsOS() ? "cmd /c " : "") + command);
            process.waitFor();
            this.status = process.exitValue() == 0 ? Status.SUCCESS : Status.ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log(State.ERROR,"Error while executing the command: " + command);
            this.status = Status.ERROR;
        } catch (InterruptedException e) {
            Logger.log(State.ERROR,"The command was interrupted: " + command);
            this.status = Status.INTERRUPTED;
        }
    }

    /**
     * Check if the command was executed successfully
     * 
     * @return true if the command was executed successfully, false otherwise
     */
    public boolean isSuccessful() {
        return status == Status.SUCCESS;
    }
}

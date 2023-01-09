package fr.swynn.utils;

import java.io.IOException;
import java.io.InputStream;

enum Status {
    SUCCESS(0),
    ERROR(-1),
    INTERRUPTED(-2);

    private Status(int value) {}
}

public class Command {
    
    private Status status;
    private InputStream result;

    /**
     * Execute a command and wait for its end
     * 
     * @param command The command to execute - String
     */
    public Command(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            this.status = process.exitValue() == 0 ? Status.SUCCESS : Status.ERROR;
            this.result = process.getInputStream();
        } catch (IOException e) {
            this.status = Status.ERROR;
        } catch (InterruptedException e) {
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

    /**
     * Get the result of the command
     * 
     * @return The result of the command - InputStream
     */
    public InputStream getResult() {
        return result;
    }

    /**
     * Get the status of the command
     * 
     * @return The status of the command - Status
     */
    public Status getStatus() {
        return status;
    }
}

package com.qentelli.pojo;

public class OATSStatus {

    private boolean exitStatus;
    private String bufferMessage;


    public boolean isExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(boolean exitStatus) {
        this.exitStatus = exitStatus;
    }

    public String getBufferMessage() {
        return bufferMessage;
    }

    public void setBufferMessage(String bufferMessage) {
        this.bufferMessage = bufferMessage;
    }
}

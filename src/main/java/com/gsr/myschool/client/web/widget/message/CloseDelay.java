package com.gsr.myschool.client.web.widget.message;

public enum CloseDelay {
    NEVER(0),
    DEFAULT(5000),
    LONG(20000);

    private int delay;

    private CloseDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }
}

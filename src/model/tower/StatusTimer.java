package model.tower;

import javafx.animation.AnimationTimer;

public abstract class StatusTimer extends AnimationTimer {

    private volatile boolean running;

    @Override
    public void start() {
         super.start();
         running = true;
    }

    @Override
    public void stop() {
        super.stop();
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
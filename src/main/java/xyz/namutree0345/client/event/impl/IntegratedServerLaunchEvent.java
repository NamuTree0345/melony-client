package xyz.namutree0345.client.event.impl;

import xyz.namutree0345.client.event.Event;

public class IntegratedServerLaunchEvent extends Event {

    public String worldName;

    public IntegratedServerLaunchEvent(String worldName) {
        this.worldName = worldName;
    }
}

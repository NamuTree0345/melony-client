package xyz.namutree0345.client.event.impl.network;

import xyz.namutree0345.client.event.Event;

public class ConnectServerEvent extends Event {

    public String ip;
    public int port;

    public ConnectServerEvent(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

}

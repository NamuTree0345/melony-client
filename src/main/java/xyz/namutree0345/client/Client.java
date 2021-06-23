package xyz.namutree0345.client;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.namutree0345.client.event.EventManager;
import xyz.namutree0345.client.event.EventTarget;
import xyz.namutree0345.client.event.impl.ClientShutdownEvent;

public class Client {

    public static DiscordRP discordRP = DiscordRP.getInstance();
    public static Client INSTANCE = new Client();
    public Logger logger = LogManager.getLogger("MelonyClient");

    public void init() {
        EventManager.register(new DiscordRP.DiscordRPHandler());
        EventManager.register(this);
        //EventManager.register(new OverrideGUI());
        discordRP.initRP();
    }

    @EventTarget
    public void onShutdown(ClientShutdownEvent event) {
        discordRP.shutdownRP();
    }

}

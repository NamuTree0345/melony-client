package xyz.namutree0345.client;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.multiplayer.GuiConnecting;
import xyz.namutree0345.client.event.EventTarget;
import xyz.namutree0345.client.event.impl.IntegratedServerLaunchEvent;
import xyz.namutree0345.client.event.impl.gui.ClientGuiChangedEvent;
import xyz.namutree0345.client.event.impl.network.ConnectServerEvent;

public class DiscordRP {

    public static class DiscordRPHandler {
        DiscordRP rp = DiscordRP.getInstance();

        @EventTarget
        public void guiChanged(ClientGuiChangedEvent event) {
            if(rp.isInitiallized) {
                if (Minecraft.getMinecraft().getCurrentServerData() == null) {
                    if (event.screen instanceof GuiMainMenu) {
                        rp.updateRP("메인 메뉴", "");
                    } else if (event.screen instanceof GuiSelectWorld || event.screen instanceof GuiCreateWorld) {
                        rp.updateRP("싱글플레이 메뉴", "");
                    } else if (event.screen instanceof GuiMultiplayer) {
                        rp.updateRP("멀티플레이 메뉴", "");
                    }
                }
            }
        }

        @EventTarget
        public void integratedServerLaunch(IntegratedServerLaunchEvent event) {
            rp.updateRP("싱글플레이", event.worldName + "에서 플레이 중");
        }

        @EventTarget
        public void serverConnect(ConnectServerEvent event) {
            if(event.port == 25565) {
                rp.updateRP("멀티플레이", event.ip + "에서 플레이 중");
            } else {
                rp.updateRP("멀티플레이", event.ip + ":" + event.port + "에서 플레이 중");
            }
        }
    }

    private static DiscordRP INSTANCE;

    private Client theClient = Client.INSTANCE;

    public boolean isInitiallized = false;

    public static DiscordRP getInstance() {
        if(INSTANCE == null) INSTANCE = new DiscordRP();
        return INSTANCE;
    }

    public void initRP() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            theClient.logger.info("Initiallized DiscordRP Successfully! - " + user.username + "#" + user.discriminator);
        }).build();
        DiscordRPC.discordInitialize("856741644002131968", handlers, true);
        updateRP("시작중!", "");
        isInitiallized = true;
    }

    public void updateRP(String line1, String line2) {
        DiscordRichPresence rich = new DiscordRichPresence.Builder(line2).setDetails(line1).setBigImage("large", "Playing Minecraft " + Minecraft.getMinecraft().getVersion()).build();
        DiscordRPC.discordUpdatePresence(rich);
    }

    public void shutdownRP() {
        DiscordRPC.discordShutdown();
    }

}

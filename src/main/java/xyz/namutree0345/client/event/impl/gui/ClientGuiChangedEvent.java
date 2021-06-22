package xyz.namutree0345.client.event.impl.gui;

import net.minecraft.client.gui.GuiScreen;
import xyz.namutree0345.client.event.Event;

public class ClientGuiChangedEvent extends Event {

    public GuiScreen screen;

    public ClientGuiChangedEvent(GuiScreen screen) {
        this.screen = screen;
    }

}

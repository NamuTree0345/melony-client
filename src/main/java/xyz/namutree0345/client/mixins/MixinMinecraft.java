package xyz.namutree0345.client.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.world.WorldSettings;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.namutree0345.client.Client;
import xyz.namutree0345.client.event.impl.ClientShutdownEvent;
import xyz.namutree0345.client.event.impl.ClientTickEvent;
import xyz.namutree0345.client.event.impl.IntegratedServerLaunchEvent;
import xyz.namutree0345.client.event.impl.gui.ClientGuiChangedEvent;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow private boolean fullscreen;
    @Shadow private static Logger logger;


    @Inject(method = "runTick", at = @At("RETURN"))
    public void runTick(CallbackInfo ci) {
        new ClientTickEvent().call();
    }

    @Inject(method = "startGame", at = @At("HEAD"))
    public void startGame(CallbackInfo ci) {
        Client.INSTANCE.init();
    }

    @Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
    public void shutdownMinecraftApplet(CallbackInfo ci) {
        new ClientShutdownEvent().call();
    }

    @Inject(method = "displayGuiScreen", at = @At("HEAD"))
    public void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo ci) {
        new ClientGuiChangedEvent(guiScreenIn).call();
    }

    @Inject(method = "launchIntegratedServer", at = @At("TAIL"))
    public void launchIntegratedServer(String folderName, String worldName, WorldSettings worldSettingsIn, CallbackInfo event) {
        new IntegratedServerLaunchEvent(worldName).call();
    }

    /**
     * @author
     */
    @Overwrite
    public void createDisplay() throws LWJGLException {
        Display.setResizable(true);
        Display.setTitle("Client");

        try
        {
            Display.create((new PixelFormat()).withDepthBits(24));
        }
        catch (LWJGLException lwjglexception)
        {
            logger.error((String)"Couldn\'t set pixel format", (Throwable)lwjglexception);

            try
            {
                Thread.sleep(1000L);
            }
            catch (InterruptedException var3)
            {
                ;
            }

            if (this.fullscreen)
            {
                this.updateDisplayMode();
            }

            Display.create();
        }
    }

    @Shadow
    private void updateDisplayMode() {
    }

}

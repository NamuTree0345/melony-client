package xyz.namutree0345.client.mixins;

import net.minecraft.client.multiplayer.GuiConnecting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.namutree0345.client.event.impl.network.ConnectServerEvent;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting {

    @Inject(method = "connect", at = @At("RETURN"))
    public void connect(String ip, int port, CallbackInfo ci) {
        new ConnectServerEvent(ip, port).call();
    }

}

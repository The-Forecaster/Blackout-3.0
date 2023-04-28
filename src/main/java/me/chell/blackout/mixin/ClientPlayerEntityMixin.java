package me.chell.blackout.mixin;

import me.chell.blackout.api.event.EventManager;
import me.chell.blackout.api.events.PlayerTickEvent;
import me.chell.blackout.api.util.GlobalsKt;
import me.chell.blackout.impl.features.misc.AutoRespawn;
import me.chell.blackout.impl.features.movement.NoSlow;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    public void postTick(CallbackInfo ci) {
        EventManager.INSTANCE.post(new PlayerTickEvent());
    }

    @Inject(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/client/input/Input;movementForward:F", shift = At.Shift.AFTER))
    public void noSlowForward(CallbackInfo ci) {
        if(NoSlow.INSTANCE.getMainSetting().getValue()) {
            GlobalsKt.getPlayer().input.movementForward /= 0.2f;
        }
    }
    @Inject(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/client/input/Input;movementSideways:F", shift = At.Shift.AFTER))
    public void noSlowSideways(CallbackInfo ci) {
        if(NoSlow.INSTANCE.getMainSetting().getValue()) {
            GlobalsKt.getPlayer().input.movementSideways /= 0.2f;
        }
    }

    @Inject(method = "showsDeathScreen", at = @At("HEAD"), cancellable = true)
    public void deathScreen(CallbackInfoReturnable<Boolean> cir) {
        if(AutoRespawn.INSTANCE.getMainSetting().getValue()) cir.setReturnValue(false);
    }

}
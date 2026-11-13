package lain.mods.skins.init.ornithe.mixin;

import lain.mods.skins.init.ornithe.OrnitheOfflineSkins;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftClientTickMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void offlineskins$onClientTick(CallbackInfo ci) {
        OrnitheOfflineSkins.onClientTick((Minecraft) (Object) this);
    }
}

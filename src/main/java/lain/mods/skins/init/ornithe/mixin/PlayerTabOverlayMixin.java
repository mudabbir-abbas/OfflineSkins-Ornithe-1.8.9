package lain.mods.skins.init.ornithe.mixin;

import net.minecraft.client.gui.overlay.PlayerTabOverlay;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerTabOverlay.class)
public abstract class PlayerTabOverlayMixin {

    @Redirect(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Connection;isEncrypted()Z"),
            require = 0
    )
    private boolean offlineskins$alwaysRenderHeads(Connection connection) {
        return true;
    }
}

package lain.mods.skins.init.ornithe.mixin;

import com.mojang.authlib.GameProfile;
import lain.mods.skins.init.ornithe.OrnitheOfflineSkins;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.resource.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInfo.class)
public abstract class PlayerInfoMixin {

    @Shadow
    public abstract GameProfile getProfile();

    @Inject(method = "getSkinTexture", at = @At("RETURN"), cancellable = true)
    private void offlineskins$overrideTabSkinTexture(CallbackInfoReturnable<Identifier> cir) {
        Identifier custom = OrnitheOfflineSkins.bindTexture(this.getProfile(), cir.getReturnValue());
        if (custom != null) {
            cir.setReturnValue(custom);
        }
    }
}

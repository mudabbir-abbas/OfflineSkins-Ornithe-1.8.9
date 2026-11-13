package lain.mods.skins.init.ornithe.mixin;

import lain.mods.skins.init.ornithe.OrnitheOfflineSkins;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.resource.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Inject(method = "getSkinTextureLocation", at = @At("RETURN"), cancellable = true)
    private void offlineskins$overrideSkinTexture(CallbackInfoReturnable<Identifier> cir) {
        Identifier custom = OrnitheOfflineSkins.getLocationSkin(
                (ClientPlayerEntity) (Object) this,
                cir.getReturnValue()
        );
        if (custom != null) {
            cir.setReturnValue(custom);
        }
    }

    @Inject(method = "getModelType", at = @At("RETURN"), cancellable = true)
    private void offlineskins$overrideModelType(CallbackInfoReturnable<String> cir) {
        String customType = OrnitheOfflineSkins.getSkinType(
                (ClientPlayerEntity) (Object) this,
                cir.getReturnValue()
        );
        if (customType != null) {
            cir.setReturnValue(customType);
        }
    }

    @Inject(method = "getCapeTextureLocation", at = @At("RETURN"), cancellable = true)
    private void offlineskins$overrideCapeTexture(CallbackInfoReturnable<Identifier> cir) {
        Identifier custom = OrnitheOfflineSkins.getLocationCape(
                (ClientPlayerEntity) (Object) this,
                cir.getReturnValue()
        );
        if (custom != null) {
            cir.setReturnValue(custom);
        }
    }
}

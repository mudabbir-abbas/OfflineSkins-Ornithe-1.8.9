package lain.mods.skins.init.ornithe.mixin;

import com.mojang.authlib.GameProfile;
import lain.mods.skins.init.ornithe.OrnitheOfflineSkins;
import net.minecraft.client.render.block.entity.SkullRenderer;
import net.minecraft.resource.Identifier;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkullRenderer.class)
public abstract class SkullBlockEntityRendererMixin {

    @Unique
    private GameProfile offlineskins$skullProfile;
    @Unique
    private int offlineskins$skullType;

    @Inject(
            method = "render(FFFLnet/minecraft/util/math/Direction;FILcom/mojang/authlib/GameProfile;I)V",
            at = @At("HEAD")
    )
    private void offlineskins$captureProfile(
            float x, float y, float z, Direction facing, float rotation, int skullType, GameProfile profile, int blockMiningProgress, CallbackInfo ci
    ) {
        offlineskins$skullProfile = profile;
        offlineskins$skullType = skullType;
    }

    @ModifyArg(
            method = "render(FFFLnet/minecraft/util/math/Direction;FILcom/mojang/authlib/GameProfile;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/SkullRenderer;bindTexture(Lnet/minecraft/resource/Identifier;)V"),
            index = 0
    )
    private Identifier offlineskins$swapSkullTexture(Identifier original) {
        if (offlineskins$skullType == 3 && original != null && !original.getPath().contains("destroy_stage")) {
            Identifier custom = OrnitheOfflineSkins.bindTexture(offlineskins$skullProfile, original);
            if (custom != null) {
                return custom;
            }
        }
        return original;
    }

    @Inject(
            method = "render(FFFLnet/minecraft/util/math/Direction;FILcom/mojang/authlib/GameProfile;I)V",
            at = @At("RETURN")
    )
    private void offlineskins$clearProfile(
            float x, float y, float z, Direction facing, float rotation, int skullType, GameProfile profile, int blockMiningProgress, CallbackInfo ci
    ) {
        offlineskins$skullProfile = null;
        offlineskins$skullType = -1;
    }
}

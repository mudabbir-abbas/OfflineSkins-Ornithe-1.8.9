package lain.mods.skins.init.ornithe;

import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.GameProfile;
import lain.mods.skins.api.SkinProviderAPI;
import lain.mods.skins.api.interfaces.ISkin;
import lain.mods.skins.impl.LegacyConversion;
import lain.mods.skins.impl.PlayerProfile;
import lain.mods.skins.impl.ornithe.CustomSkinTexture;
import lain.mods.skins.providers.UserManagedSkinProvider;
import lain.mods.skins.providers.UserManagedCapeProvider;
import lain.mods.skins.providers.MojangSkinProvider;
import lain.mods.skins.providers.MojangCapeProvider;
import lain.mods.skins.providers.CrafatarSkinProvider;
import lain.mods.skins.providers.CrafatarCapeProvider;
import lain.mods.skins.providers.CustomServerSkinProvider;
import lain.mods.skins.providers.CustomServerCapeProvider;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.resource.Identifier;

import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

public class OrnitheOfflineSkins implements ClientModInitializer {

    private static boolean skinPass;
    private static boolean capePass;
    public static boolean overrideVanilla = true;

    private static final Set<String> DEFAULT_SKINS = ImmutableSet.of(
            "textures/entity/steve.png",
            "textures/entity/alex.png"
    );
    private static final Map<ByteBuffer, CustomSkinTexture> TEXTURES = new WeakHashMap<>();

    @Override
    public void onInitializeClient() {
        OfflineSkinsConfig config = OfflineSkinsConfig.load();

        SkinProviderAPI.SKIN.clearProviders();
        // Always prioritize local cachedImages/skins/<username>.png or <uuid>.png
        SkinProviderAPI.SKIN.registerProvider(
                new UserManagedSkinProvider(Paths.get(".", "cachedImages"))
                        .withFilter(LegacyConversion.createFilter())
        );
        if (config.useCustomServer) {
            SkinProviderAPI.SKIN.registerProvider(
                    new CustomServerSkinProvider()
                            .setHost(config.hostCustomServer)
                            .withFilter(LegacyConversion.createFilter())
            );
        }
        if (config.useMojang) {
            SkinProviderAPI.SKIN.registerProvider(
                    new MojangSkinProvider().withFilter(LegacyConversion.createFilter())
            );
        }
        if (config.useCrafatar) {
            SkinProviderAPI.SKIN.registerProvider(
                    new CrafatarSkinProvider().withFilter(LegacyConversion.createFilter())
            );
        }

        SkinProviderAPI.CAPE.clearProviders();
        SkinProviderAPI.CAPE.registerProvider(new UserManagedCapeProvider(Paths.get(".", "cachedImages")));
        if (config.useCustomServer) {
            SkinProviderAPI.CAPE.registerProvider(new CustomServerCapeProvider().setHost(config.hostCustomServer));
        }
        if (config.useMojang) {
            SkinProviderAPI.CAPE.registerProvider(new MojangCapeProvider());
        }
        if (config.useCrafatar) {
            SkinProviderAPI.CAPE.registerProvider(new CrafatarCapeProvider());
        }
        overrideVanilla = true;
    }

    public static void onClientTick(Minecraft client) {
        if (client != null && client.world != null) {
            for (PlayerEntity player : client.world.players) {
                SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapGameProfile(player.getGameProfile()));
                SkinProviderAPI.CAPE.getSkin(PlayerProfile.wrapGameProfile(player.getGameProfile()));
            }
        }
    }

    public static Identifier bindTexture(GameProfile profile, Identifier result) {
        if ((overrideVanilla || isDefaultSkin(result)) && profile != null) {
            ISkin skin = SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapGameProfile(profile));
            if (skin != null && skin.isDataReady()) {
                return getOrCreateTexture(skin.getData(), skin).getLocation();
            }
        }
        return null;
    }

    public static Identifier getLocationSkin(ClientPlayerEntity player, Identifier result) {
        if (skinPass || player == null) {
            return null;
        }
        if (overrideVanilla || isDefaultSkin(result)) {
            ISkin skin = SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapGameProfile(player.getGameProfile()));
            if (skin != null && skin.isDataReady()) {
                return getOrCreateTexture(skin.getData(), skin).getLocation();
            }
        }
        return null;
    }

    public static Identifier getLocationCape(ClientPlayerEntity player, Identifier result) {
        if (capePass || player == null) {
            return null;
        }
        if (overrideVanilla || result == null) {
            ISkin skin = SkinProviderAPI.CAPE.getSkin(PlayerProfile.wrapGameProfile(player.getGameProfile()));
            if (skin != null && skin.isDataReady()) {
                return getOrCreateTexture(skin.getData(), skin).getLocation();
            }
        }
        return null;
    }

    public static String getSkinType(ClientPlayerEntity player, String result) {
        if (player == null) {
            return null;
        }
        Identifier location = getLocationSkin(player, null);
        if (location != null) {
            ISkin skin = SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapGameProfile(player.getGameProfile()));
            if (skin != null && skin.isDataReady()) {
                return skin.getSkinType();
            }
        }
        return null;
    }

    private static synchronized CustomSkinTexture getOrCreateTexture(ByteBuffer data, ISkin skin) {
        CustomSkinTexture existing = TEXTURES.get(data);
        if (existing == null) {
            Minecraft client = Minecraft.getInstance();
            CustomSkinTexture texture = new CustomSkinTexture(generateRandomLocation(), data);
            client.getTextureManager().register(texture.getLocation(), texture);
            TEXTURES.put(data, texture);

            if (skin != null) {
                skin.setRemovalListener(removed -> {
                    if (data == removed.getData()) {
                        client.executeTask(() -> {
                            client.getTextureManager().close(texture.getLocation());
                            TEXTURES.remove(data);
                        });
                    }
                });
            }
            return texture;
        }
        return existing;
    }

    private static Identifier generateRandomLocation() {
        return new Identifier("offlineskins", "textures/generated/" + UUID.randomUUID());
    }

    private static boolean isDefaultSkin(Identifier location) {
        return location != null
                && "minecraft".equals(location.getNamespace())
                && DEFAULT_SKINS.contains(location.getPath());
    }
}

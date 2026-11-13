package lain.mods.skins.impl.ornithe;

import lain.mods.skins.api.interfaces.ISkinTexture;
import lain.mods.skins.impl.SkinData;
import net.minecraft.client.render.texture.AbstractTexture;
import net.minecraft.client.render.texture.TextureUtil;
import net.minecraft.client.resource.manager.ResourceManager;
import net.minecraft.resource.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

public class CustomSkinTexture extends AbstractTexture implements ISkinTexture {

    private static BufferedImage loadImage(ByteBuffer buf) {
        try (InputStream in = SkinData.wrapByteBufferAsInputStream(buf)) {
            return ImageIO.read(in);
        } catch (Throwable t) {
            return null;
        }
    }

    private final Identifier _location;
    private final WeakReference<ByteBuffer> _data;

    public CustomSkinTexture(Identifier location, ByteBuffer data) {
        if (data == null) {
            throw new IllegalArgumentException("buffer must not be null");
        }
        _location = location;
        _data = new WeakReference<>(data);
    }

    @Override
    public ByteBuffer getData() {
        return _data.get();
    }

    public Identifier getLocation() {
        return _location;
    }

    @Override
    public void load(ResourceManager resMan) throws IOException {
        clearGlId();

        ByteBuffer buf = _data.get();
        if (buf == null) {
            throw new FileNotFoundException(getLocation().toString());
        }
        BufferedImage image = loadImage(buf);
        if (image == null) {
            throw new FileNotFoundException(getLocation().toString());
        }

        TextureUtil.upload(getGlId(), image, false, false);
    }
}

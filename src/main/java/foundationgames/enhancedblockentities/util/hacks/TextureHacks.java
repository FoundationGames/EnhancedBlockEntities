package foundationgames.enhancedblockentities.util.hacks;

import net.minecraft.client.texture.NativeImage;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;

/*
                    SUFFER MORE
 */

public enum TextureHacks {;
    public static byte[] cropImage(@Nullable InputStream image, float u0, float v0, float u1, float v1) throws IOException {
        byte[] r = new byte[0];
        if (image != null) {
            NativeImage src = NativeImage.read(NativeImage.Format.RGBA, image);
            int w = src.getWidth();
            int h = src.getHeight();
            int x = (int)Math.floor(u0 * w);
            int y = (int)Math.floor(v0 * h);
            int sw = (int)Math.floor((u1 - u0) * w);
            int sh = (int)Math.floor((v1 - v0) * h);
            NativeImage prod = new NativeImage(src.getFormat(), sw, sh, false);
            for (int u = 0; u < sw; u++) {
                for (int v = 0; v < sh; v++) {
                    prod.setColor(u, v, src.getColor(x + u, y + v));
                }
            }
            src.close();
            r = prod.getBytes();
            prod.close();
        }
        return r;
    }
}

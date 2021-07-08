package foundationgames.enhancedblockentities.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

public class SignRenderManager {
    private static int lastRenderedSigns = 0;
    public static int renderedSigns = 0;

    public static int getRenderedSignAmount() {
        return lastRenderedSigns;
    }

    public static void endFrame(WorldRenderContext ctx) {
        lastRenderedSigns = renderedSigns;
        renderedSigns = 0;
    }
}

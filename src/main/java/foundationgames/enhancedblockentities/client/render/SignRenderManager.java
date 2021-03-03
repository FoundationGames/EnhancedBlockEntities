package foundationgames.enhancedblockentities.client.render;

public class SignRenderManager {
    private static int lastRenderedSigns = 0;
    public static int renderedSigns = 0;

    public static int getRenderedSignAmount() {
        return lastRenderedSigns;
    }

    public static void endFrame() {
        lastRenderedSigns = renderedSigns;
        renderedSigns = 0;
    }
}

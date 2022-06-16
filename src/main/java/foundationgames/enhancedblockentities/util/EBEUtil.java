package foundationgames.enhancedblockentities.util;

import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.random.Random;

public enum EBEUtil {;
    private static final Random dummy = Random.create();

    // Contains all dye colors, and null
    public static final DyeColor[] DEFAULTED_DYE_COLORS;

    static {
        var dColors = DyeColor.values();
        DEFAULTED_DYE_COLORS = new DyeColor[dColors.length + 1];
        System.arraycopy(dColors, 0, DEFAULTED_DYE_COLORS, 0, dColors.length);
    }

    public static void renderBakedModel(VertexConsumerProvider vertexConsumers, BlockState state, MatrixStack matrices, BakedModel model, int light, int overlay) {
        VertexConsumer vertices = vertexConsumers.getBuffer(RenderLayers.getEntityBlockLayer(state, false));
        for (int i = 0; i <= 6; i++) {
            for(BakedQuad q : model.getQuads(null, ModelHelper.faceFromIndex(i), dummy)) {
                vertices.quad(matrices.peek(), q, 1, 1, 1, light, overlay);
            }
        }
    }
}

package foundationgames.enhancedblockentities.util;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

import java.io.IOException;
import java.nio.file.Files;

public enum EBEUtil {;
    private static final Random dummy = Random.create();

    // Contains all dye colors, and null
    public static final DyeColor[] DEFAULTED_DYE_COLORS;
    // All directions except up and down
    public static final Direction[] HORIZONTAL_DIRECTIONS;

    static {
        var dColors = DyeColor.values();
        DEFAULTED_DYE_COLORS = new DyeColor[dColors.length + 1];
        System.arraycopy(dColors, 0, DEFAULTED_DYE_COLORS, 0, dColors.length);

        HORIZONTAL_DIRECTIONS = new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    }

    public static void renderBakedModel(VertexConsumerProvider vertexConsumers, BlockState state, MatrixStack matrices, BakedModel model, int light, int overlay) {
        VertexConsumer vertices = vertexConsumers.getBuffer(RenderLayers.getEntityBlockLayer(state, false));
        for (int i = 0; i <= 6; i++) {
            for (BakedQuad q : model.getQuads(null, ModelHelper.faceFromIndex(i), dummy)) {
                vertices.quad(matrices.peek(), q, 1, 1, 1, light, overlay);
            }
        }
    }

    public static boolean isVanillaResourcePack(ResourcePack pack) {
        return (pack instanceof DefaultResourcePack) ||
                // Terrible quilt compat hack
                ("org.quiltmc.qsl.resource.loader.api.GroupResourcePack$Wrapped".equals(pack.getClass().getName()));
    }

    public static Identifier id(String path) {
        return new Identifier(EnhancedBlockEntities.NAMESPACE, path);
    }

    public static final String DUMP_FOLDER_NAME = "enhanced_bes_dump";

    public static void dumpResources() throws IOException {
        var path = FabricLoader.getInstance().getGameDir().resolve(DUMP_FOLDER_NAME);

        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        ResourceUtil.dumpAllPacks(path);
    }
}

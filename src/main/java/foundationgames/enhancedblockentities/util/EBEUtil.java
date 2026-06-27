package foundationgames.enhancedblockentities.util;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.loader.api.FabricLoader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

import java.io.IOException;
import java.nio.file.Files;

public enum EBEUtil {;
    private static final RandomSource dummy = RandomSource.create();

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

    public static int angle(Direction dir) {
        int h = dir.getHorizontalQuarterTurns();
        return h >= 0 ? h * 90 : 0;
    }

    public static void renderBakedModel(MultiBufferSource vertexConsumers, BlockState state, PoseStack matrices, BakedModel model, int light, int overlay) {
        VertexConsumer vertices = vertexConsumers.getBuffer(net.minecraft.client.renderer.ItemBlockRenderTypes.getChunkRenderType(state));
        for (int i = 0; i <= 6; i++) {
            for (BakedQuad q : model.getQuads(null, ModelHelper.faceFromIndex(i), dummy)) {
                vertices.putBulkData(matrices.last(), q, 1, 1, 1, 1, light, overlay);
            }
        }
    }

    public static boolean isVanillaResourcePack(PackResources pack) {
        // In Mojang mappings, check for vanilla pack by class name patterns
        String className = pack.getClass().getName();
        return className.contains("VanillaPackResources") || className.contains("DefaultResourcePack") ||
                // Terrible quilt compat hack
                ("org.quiltmc.qsl.resource.loader.api.GroupResourcePack$Wrapped".equals(className));
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(EnhancedBlockEntities.NAMESPACE, path);
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

package foundationgames.enhancedblockentities.util;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum WorldUtil {;
    public static boolean FORCE_SYNCHRONOUS_CHUNK_REBUILD = false;

    public static void rebuildChunkSynchronously(World world, BlockPos pos, boolean forceSync) {
        var bState = world.getBlockState(pos);
        try {
            WorldUtil.FORCE_SYNCHRONOUS_CHUNK_REBUILD = forceSync;
            MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, bState, bState, 8);
        } catch (NullPointerException ignored) {
            EnhancedBlockEntities.LOG.warn("Error rebuilding chunk at block pos "+pos);
        }
    }
}

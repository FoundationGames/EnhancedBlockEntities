package foundationgames.enhancedblockentities.util;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public enum WorldUtil {;
    public static Set<ChunkSectionPos> FORCE_SYNCHRONOUS_CHUNK_REBUILD = new HashSet<>();

    public static void rebuildChunkSynchronously(World world, BlockPos pos, boolean forceSync) {
        var bState = world.getBlockState(pos);
        try {
            if (forceSync) {
                WorldUtil.FORCE_SYNCHRONOUS_CHUNK_REBUILD.add(ChunkSectionPos.from(pos));
            }
            MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, bState, bState, 8);
        } catch (NullPointerException ignored) {
            EnhancedBlockEntities.LOG.warn("Error rebuilding chunk at block pos "+pos);
        }
    }
}

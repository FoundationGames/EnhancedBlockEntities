package foundationgames.enhancedblockentities.util.duck;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.util.WorldUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ModelStateHolder {
    int getModelState();

    void applyModelState(int state);

    default void setModelState(int state, World world, BlockPos pos) {
        if (!world.isClient()) {
            return;
        }

        this.applyModelState(state);
        var bState = world.getBlockState(pos);
        try {
            WorldUtil.FORCE_SYNCHRONOUS_CHUNK_REBUILD = true;
            MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, bState, bState, 8);
        } catch (NullPointerException ignored) {
            EnhancedBlockEntities.LOG.warn("Error rebuilding chunk at block pos "+pos);
        }
    }
}

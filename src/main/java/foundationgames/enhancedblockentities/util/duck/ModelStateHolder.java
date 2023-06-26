package foundationgames.enhancedblockentities.util.duck;

import foundationgames.enhancedblockentities.util.WorldUtil;
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
        WorldUtil.rebuildChunkSynchronously(world, pos, true);
    }
}

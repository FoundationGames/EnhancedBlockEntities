package foundationgames.enhancedblockentities.util.duck;

import foundationgames.enhancedblockentities.util.WorldUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface AppearanceStateHolder {
    int getModelState();

    void setModelState(int state);

    int getRenderState();

    void setRenderState(int state);

    default void updateAppearanceState(int state, World world, BlockPos pos) {
        if (!world.isClient()) {
            return;
        }

        this.setModelState(state);
        WorldUtil.rebuildChunkAndThen(world, pos, () -> this.setRenderState(state));
    }
}

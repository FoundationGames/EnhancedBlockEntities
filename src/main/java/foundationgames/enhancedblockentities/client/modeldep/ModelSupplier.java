package foundationgames.enhancedblockentities.client.modeldep;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

public interface ModelSupplier {
    int getModel(BlockRenderView view, BlockPos pos, BlockState state);
}

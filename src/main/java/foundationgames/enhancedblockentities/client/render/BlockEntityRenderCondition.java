package foundationgames.enhancedblockentities.client.render;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.block.ChestAnimationProgress;

public interface BlockEntityRenderCondition {
    BlockEntityRenderCondition CHEST = entity -> {
        if(entity instanceof ChestAnimationProgress) {
            return ((ChestAnimationProgress)entity).getAnimationProgress(0) > 0;
        }
        return false;
    };

    BlockEntityRenderCondition NEVER = entity -> false;

    boolean shouldRender(BlockEntity entity);
}

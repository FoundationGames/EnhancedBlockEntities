package foundationgames.enhancedblockentities.client.render;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.block.ChestAnimationProgress;

public interface BlockEntityRenderCondition {
    BlockEntityRenderCondition CHEST = entity -> {
        if(entity instanceof ChestAnimationProgress) {
            return ((ChestAnimationProgress)entity).getAnimationProgress(0) > 0;
        }
        return false;
    };

    BlockEntityRenderCondition SIGN = entity -> {
        SignRenderManager.renderedSigns++;
        return MinecraftClient.getInstance().player.getBlockPos().getSquaredDistance(entity.getPos()) <
                80 + Math.max(0, 580 - (SignRenderManager.getRenderedSignAmount() * 0.7));
    };

    BlockEntityRenderCondition NEVER = entity -> false;

    BlockEntityRenderCondition ALWAYS = entity -> true;

    boolean shouldRender(BlockEntity entity);
}

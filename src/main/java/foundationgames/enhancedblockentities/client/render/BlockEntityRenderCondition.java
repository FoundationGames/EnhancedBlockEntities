package foundationgames.enhancedblockentities.client.render;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.mixin.SignBlockEntityRenderAccessor;
import foundationgames.enhancedblockentities.util.duck.ModelStateHolder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

@FunctionalInterface
public interface BlockEntityRenderCondition {
    BlockEntityRenderCondition STATE_GREATER_THAN_1 = entity -> {
        if(entity instanceof ModelStateHolder stateHolder) {
            return stateHolder.getModelState() > 0;
        }
        return false;
    };

    BlockEntityRenderCondition CHEST = STATE_GREATER_THAN_1;

    BlockEntityRenderCondition BELL = STATE_GREATER_THAN_1;

    BlockEntityRenderCondition SHULKER_BOX = STATE_GREATER_THAN_1;

    BlockEntityRenderCondition SIGN = entity -> {
        EBEConfig config = EnhancedBlockEntities.CONFIG;
        if (config.signTextRendering.equals("all")) {
            return true;
        }
        double playerDistance = MinecraftClient.getInstance().player.getBlockPos().getSquaredDistance(entity.getPos());
        if (config.signTextRendering.equals("smart")) {
            SignRenderManager.renderedSigns++;
            return playerDistance < 80 + Math.max(0, 580 - (SignRenderManager.getRenderedSignAmount() * 0.7));
        }
        double dist = SignBlockEntityRenderAccessor.enhanced_bes$getRenderDistance();
        Vec3d blockPos = Vec3d.ofCenter(entity.getPos());
        Vec3d playerPos = MinecraftClient.getInstance().player.getPos();
        if (config.signTextRendering.equals("most")) {
            return blockPos.isInRange(playerPos, dist * 0.6);
        }
        if (config.signTextRendering.equals("some")) {
            return blockPos.isInRange(playerPos, dist * 0.3);
        }
        if (config.signTextRendering.equals("few")) {
            return blockPos.isInRange(playerPos, dist * 0.15);
        }
        return false;
    };

    BlockEntityRenderCondition NEVER = entity -> false;

    BlockEntityRenderCondition ALWAYS = entity -> true;

    boolean shouldRender(BlockEntity entity);
}

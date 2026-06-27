package foundationgames.enhancedblockentities.client.render;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.mixin.AbstractSignBlockEntityRenderAccessor;
import foundationgames.enhancedblockentities.util.duck.AppearanceStateHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.entity.BlockEntity;

@FunctionalInterface
public interface BlockEntityRenderCondition {
    BlockEntityRenderCondition NON_ZERO_STATE = entity -> {
        if (entity instanceof AppearanceStateHolder stateHolder) {
            return stateHolder.getRenderState() > 0;
        }
        return false;
    };

    BlockEntityRenderCondition CHEST = NON_ZERO_STATE;

    BlockEntityRenderCondition BELL = NON_ZERO_STATE;

    BlockEntityRenderCondition SHULKER_BOX = NON_ZERO_STATE;

    BlockEntityRenderCondition SIGN = entity -> {
        EBEConfig config = EnhancedBlockEntities.CONFIG;
        if (config.signTextRendering.equals("all")) {
            return true;
        }
        double playerDistance = Minecraft.getInstance().player.blockPosition().distSqr(entity.getBlockPos());
        if (config.signTextRendering.equals("smart")) {
            SignRenderManager.renderedSigns++;
            return playerDistance < 80 + Math.max(0, 580 - (SignRenderManager.getRenderedSignAmount() * 0.7));
        }
        double dist = AbstractSignBlockEntityRenderAccessor.enhanced_bes$getRenderDistance();
        Vec3 blockPos = Vec3.atCenterOf(entity.getBlockPos());
        Vec3 playerPos = Minecraft.getInstance().player.position();
        if (config.signTextRendering.equals("most")) {
            return blockPos.closerThan(playerPos, dist * 0.6);
        }
        if (config.signTextRendering.equals("some")) {
            return blockPos.closerThan(playerPos, dist * 0.3);
        }
        if (config.signTextRendering.equals("few")) {
            return blockPos.closerThan(playerPos, dist * 0.15);
        }
        return false;
    };

    BlockEntityRenderCondition DECORATED_POT = NON_ZERO_STATE;

    BlockEntityRenderCondition NEVER = entity -> false;

    BlockEntityRenderCondition ALWAYS = entity -> true;

    boolean shouldRender(BlockEntity entity);
}

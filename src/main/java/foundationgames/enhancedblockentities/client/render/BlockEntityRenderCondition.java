package foundationgames.enhancedblockentities.client.render;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.client.render.entity.ChestBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.mixin.SignBlockEntityRenderAccessor;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.util.math.Vec3d;

@FunctionalInterface
public interface BlockEntityRenderCondition {
    BlockEntityRenderCondition CHEST = entity -> {
        if(entity instanceof ChestAnimationProgress) {
            return ChestBlockEntityRendererOverride.getAnimationProgress(entity, 0).getAnimationProgress(0) > 0;
        }
        return false;
    };

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
        double dist = SignBlockEntityRenderAccessor.getRenderDistance();
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

    BlockEntityRenderCondition BELL = entity -> {
        if(entity instanceof BellBlockEntity) {
            return ((BellBlockEntity)entity).ringing;
        }
        return false;
    };

    BlockEntityRenderCondition NEVER = entity -> false;

    BlockEntityRenderCondition ALWAYS = entity -> true;

    boolean shouldRender(BlockEntity entity);
}

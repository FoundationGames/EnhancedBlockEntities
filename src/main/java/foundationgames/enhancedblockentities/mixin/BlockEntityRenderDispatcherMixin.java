package foundationgames.enhancedblockentities.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import foundationgames.enhancedblockentities.EnhancedBlockEntityRegistry;
import foundationgames.enhancedblockentities.client.render.BlockEntityRenderCondition;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {
    @Inject(
            method = "render(Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void enhanced_bes$renderOverrides(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, CallbackInfo ci) {
        if (EnhancedBlockEntityRegistry.ENTITIES.containsKey(blockEntity.getType()) && EnhancedBlockEntityRegistry.BLOCKS.contains(blockEntity.getBlockState().getBlock())) {
            Pair<BlockEntityRenderCondition, BlockEntityRendererOverride> entry = EnhancedBlockEntityRegistry.ENTITIES.get(blockEntity.getType());
            if (entry.getFirst().shouldRender(blockEntity)) {
                entry.getSecond().render(renderer, blockEntity, tickDelta, matrices, vertexConsumers, LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY);
            }
            ci.cancel();
        }
    }
}

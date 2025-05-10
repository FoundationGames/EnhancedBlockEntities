package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntityRegistry;
import foundationgames.enhancedblockentities.client.render.BlockEntityRenderCondition;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {
    @Inject(
            method = "render(Lnet/minecraft/client/render/block/entity/BlockEntityRenderer;Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static <T extends BlockEntity> void enhanced_bes$renderOverrides(BlockEntityRenderer<BlockEntity> renderer, T blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, Vec3d cameraPos, CallbackInfo ci) {
        if (EnhancedBlockEntityRegistry.ENTITIES.containsKey(blockEntity.getType()) && EnhancedBlockEntityRegistry.BLOCKS.contains(blockEntity.getCachedState().getBlock())) {
            Pair<BlockEntityRenderCondition, BlockEntityRendererOverride> entry = EnhancedBlockEntityRegistry.ENTITIES.get(blockEntity.getType());
            if (entry.getLeft().shouldRender(blockEntity)) {
                entry.getRight().render(renderer, blockEntity, tickDelta, matrices, vertexConsumers, WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos()), OverlayTexture.DEFAULT_UV);
            }
            ci.cancel();
        }
    }
}

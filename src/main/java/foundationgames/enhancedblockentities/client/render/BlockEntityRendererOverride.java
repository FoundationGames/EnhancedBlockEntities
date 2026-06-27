package foundationgames.enhancedblockentities.client.render;

import foundationgames.enhancedblockentities.event.EBEEvents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class BlockEntityRendererOverride {
    public static final BlockEntityRendererOverride NO_OP = new BlockEntityRendererOverride() {
        @Override
        public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {}
    };

    public BlockEntityRendererOverride() {
        EBEEvents.RESOURCE_RELOAD.register(this::onModelsReload);
    }

    public abstract void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay);

    public void onModelsReload() {}

}

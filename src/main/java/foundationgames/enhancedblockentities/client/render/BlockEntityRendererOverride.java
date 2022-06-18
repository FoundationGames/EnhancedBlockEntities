package foundationgames.enhancedblockentities.client.render;

import foundationgames.enhancedblockentities.event.EBEEvents;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;

public abstract class BlockEntityRendererOverride {
    public static final BlockEntityRendererOverride NO_OP = new BlockEntityRendererOverride() {
        @Override
        public void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {}
    };

    public BlockEntityRendererOverride() {
        EBEEvents.RELOAD_MODELS.register((loader, manager, profiler) -> this.onModelsReload());
    }

    public abstract void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay);

    public void onModelsReload() {}

}

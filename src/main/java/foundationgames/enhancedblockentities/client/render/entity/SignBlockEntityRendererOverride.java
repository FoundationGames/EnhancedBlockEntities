package foundationgames.enhancedblockentities.client.render.entity;

import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.mixin.SignBlockEntityRenderAccessor;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;

public class SignBlockEntityRendererOverride extends BlockEntityRendererOverride {
    public SignBlockEntityRendererOverride() {}

    @Override
    public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
         if (blockEntity instanceof SignBlockEntity entity) {
            var state = entity.getCachedState();
            AbstractSignBlock block = (AbstractSignBlock) state.getBlock();
            var textRenderer = (SignBlockEntityRenderAccessor) renderer;
            textRenderer.enhanced_bes$setAngles(matrices, -block.getRotationDegrees(state), state);
            textRenderer.enhanced_bes$renderText(entity.getPos(), entity.getFrontText(), matrices, vertexConsumers, light, entity.getTextLineHeight(), entity.getMaxTextWidth(), true);
            textRenderer.enhanced_bes$renderText(entity.getPos(), entity.getBackText(), matrices, vertexConsumers, light, entity.getTextLineHeight(), entity.getMaxTextWidth(), false);
        }
    }

    public static class DummyRenderContext extends BlockEntityRendererFactory.Context {
        private static final EntityModelLoader DUMMY_LOADER = new EntityModelLoader() {
            @Override
            public ModelPart getModelPart(EntityModelLayer layer) {
                return SignBlockEntityRenderer.getTexturedModelData().createModel();
            }
        };

        public DummyRenderContext() {
            super(null, null, null, null,
                    DUMMY_LOADER, null);
        }

        @Override
        public TextRenderer getTextRenderer() {
            return MinecraftClient.getInstance().textRenderer;
        }
    }
}

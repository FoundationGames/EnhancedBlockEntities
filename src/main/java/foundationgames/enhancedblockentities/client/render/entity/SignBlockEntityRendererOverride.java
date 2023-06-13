package foundationgames.enhancedblockentities.client.render.entity;

import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.mixin.SignBlockEntityRenderAccessor;
import net.minecraft.block.HangingSignBlock;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.RotationPropertyHelper;

public class SignBlockEntityRendererOverride extends BlockEntityRendererOverride {
    private SignBlockEntityRenderAccessor textRenderer;

    public SignBlockEntityRendererOverride() {}

    @Override
    public void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (textRenderer == null) {
            textRenderer = ((SignBlockEntityRenderAccessor) new SignBlockEntityRenderer(new DummyRenderContext()));
        }

        if (blockEntity instanceof SignBlockEntity be) {
            matrices.push();
            var state = be.getCachedState();

            if (be instanceof HangingSignBlockEntity) {
                // 15/128 matches the vanilla positioning of the text best
                matrices.translate(0.5, 15f/128, 0.5);
                if (state.contains(Properties.ATTACHED) && state.get(Properties.ATTACHED)) {
                    matrices.multiply(
                            RotationAxis.NEGATIVE_Y.rotationDegrees(RotationPropertyHelper.toDegrees(state.get(HangingSignBlock.ROTATION))));
                } else {
                    matrices.multiply(
                            RotationAxis.NEGATIVE_Y.rotationDegrees(state.getBlock() instanceof HangingSignBlock ?
                                    ((state.get(HangingSignBlock.ROTATION) * 360f) / 16) :
                                    (state.get(WallSignBlock.FACING)).asRotation()
                            ));
                }

                matrices.translate(0.0, -0.3125, 0.0);
                matrices.scale(1.5f, 1.5f, 1.5f);
                textRenderer.enhanced_bes$renderText(be.getPos(), be.getFrontText(), matrices, vertexConsumers, light, be.getTextLineHeight(), be.getMaxTextWidth(), true);
                textRenderer.enhanced_bes$renderText(be.getPos(), be.getBackText(), matrices, vertexConsumers, light, be.getTextLineHeight(), be.getMaxTextWidth(), false);
                // textRenderer.enhanced_bes$renderText(be, matrices, vertexConsumers, light, 1);
            } else {
                if (state.getBlock() instanceof SignBlock) {
                    matrices.translate(0.5, 0.5, 0.5);
                    matrices.multiply(
                            RotationAxis.NEGATIVE_Y.rotationDegrees(RotationPropertyHelper.toDegrees(state.get(SignBlock.ROTATION))));
                } else {
                    matrices.translate(0.5, 0.5, 0.5);
                    matrices.multiply(
                            RotationAxis.NEGATIVE_Y.rotationDegrees((state.get(WallSignBlock.FACING)).asRotation()));
                    matrices.translate(0.0, -0.3125, -0.4375);
                }

                textRenderer.enhanced_bes$renderText(be.getPos(), be.getFrontText(), matrices, vertexConsumers, light, be.getTextLineHeight(), be.getMaxTextWidth(), true);
                textRenderer.enhanced_bes$renderText(be.getPos(), be.getBackText(), matrices, vertexConsumers, light, be.getTextLineHeight(), be.getMaxTextWidth(), false);
                // textRenderer.enhanced_bes$renderText(be, matrices, vertexConsumers, light, 2f/3);
            }
            matrices.pop();
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

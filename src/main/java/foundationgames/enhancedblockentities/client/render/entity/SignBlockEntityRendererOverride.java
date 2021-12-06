package foundationgames.enhancedblockentities.client.render.entity;

import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.mixin.SignBlockEntityRenderAccessor;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.Vec3f;

public class SignBlockEntityRendererOverride extends BlockEntityRendererOverride {
    public SignBlockEntityRendererOverride() {}

    @Override
    public void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (blockEntity instanceof SignBlockEntity be) {
            matrices.push();

            float signAngle;
            matrices.translate(0.5D, 0.5D, 0.5D);
            if (be.getCachedState().getBlock() instanceof SignBlock) {
                signAngle = - ((float)(be.getCachedState().get(SignBlock.ROTATION) * 360) / 16);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(signAngle));
            } else {
                signAngle = - be.getCachedState().get(WallSignBlock.FACING).asRotation();
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(signAngle));
                matrices.translate(0.0D, -0.3125, -0.4375);
            }
            matrices.translate(0.0, 0.333333, 0.0466666);
            matrices.scale(0.01f, -0.01f, 0.01f);
            var tr = MinecraftClient.getInstance().textRenderer;
            var orderedTexts = be.updateSign(MinecraftClient.getInstance().shouldFilterText(), (text) -> {
                var list = tr.wrapLines(text, 90);
                return list.isEmpty() ? OrderedText.EMPTY : list.get(0);
            });
            int outlineColor = SignBlockEntityRenderAccessor.enhanced_bes$getColor(be);
            int textColor;
            boolean outline;
            int textLight;
            if (be.isGlowingText()) {
                textColor = be.getTextColor().getSignColor();
                outline = true;
                textLight = 15728880;
            } else {
                textColor = outlineColor;
                outline = false;
                textLight = light;
            }

            for(int i = 0; i < 4; ++i) {
                var orderedText = orderedTexts[i];
                float t = (float)(-tr.getWidth(orderedText) / 2);
                if (outline) {
                    tr.drawWithOutline(orderedText, t, (float)((i * 10) - 20), textColor, outlineColor, matrices.peek().getPositionMatrix(), vertexConsumers, textLight);
                } else {
                    tr.draw(orderedText, t, (float)((i * 10) - 20), textColor, false, matrices.peek().getPositionMatrix(), vertexConsumers, false, 0, textLight);
                }
            }

            matrices.pop();
        }
    }
}

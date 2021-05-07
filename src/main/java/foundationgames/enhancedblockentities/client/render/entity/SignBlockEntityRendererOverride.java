package foundationgames.enhancedblockentities.client.render.entity;

import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.text.OrderedText;

import java.util.List;

public class SignBlockEntityRendererOverride extends BlockEntityRendererOverride {
    public SignBlockEntityRendererOverride() {}

    @Override
    public void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (blockEntity instanceof SignBlockEntity) {
            matrices.push();

            SignBlockEntity be = (SignBlockEntity)blockEntity;
            float signAngle;
            matrices.translate(0.5D, 0.5D, 0.5D);
            if (be.getCachedState().getBlock() instanceof SignBlock) {
                signAngle = - ((float)(be.getCachedState().get(SignBlock.ROTATION) * 360) / 16);
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(signAngle));
            } else {
                signAngle = - be.getCachedState().get(WallSignBlock.FACING).asRotation();
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(signAngle));
                matrices.translate(0.0D, -0.3125, -0.4375);
            }
            matrices.translate(0.0, 0.333333, 0.0466666);
            matrices.scale(0.01f, -0.01f, 0.01f);
            TextRenderer tr = MinecraftClient.getInstance().textRenderer;
            int color = be.getTextColor().getSignColor();
            int tColor = NativeImage.getAbgrColor(0,
                    (int)(NativeImage.getBlue(color) * 0.4),
                    (int)(NativeImage.getGreen(color) * 0.4),
                    (int)(NativeImage.getRed(color) * 0.4)
            );
            for (int i = 0; i < 4; i++) {
                OrderedText text = be.getTextBeingEditedOnRow(i, (t) -> {
                    List<OrderedText> texts = tr.wrapLines(t, 90);
                    return texts.isEmpty() ? OrderedText.EMPTY : texts.get(0);
                });
                if (text != null) {
                    float t = (float)(-tr.getWidth(text) / 2);
                    tr.draw(text, t, (float)(i * 10 - 20), tColor, false, matrices.peek().getModel(), vertexConsumers, false, 0, light);
                }
            }

            matrices.pop();
        }
    }
}

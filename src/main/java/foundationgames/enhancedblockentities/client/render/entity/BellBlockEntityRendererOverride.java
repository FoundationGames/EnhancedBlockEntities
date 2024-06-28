package foundationgames.enhancedblockentities.client.render.entity;

import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class BellBlockEntityRendererOverride extends BlockEntityRendererOverride {
    private BakedModel bellModel = null;

    @Override
    public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (bellModel == null) bellModel = getBellModel();
        if (bellModel != null && blockEntity instanceof BellBlockEntity self) {
            float ringTicks = (float)self.ringTicks + tickDelta;
            float bellPitch = 0.0F;
            float bellRoll = 0.0F;
            if (self.ringing) {
                float swingAngle = MathHelper.sin(ringTicks / (float)Math.PI) / (4.0F + ringTicks / 3.0F);
                if (self.lastSideHit == Direction.NORTH) {
                    bellPitch = -swingAngle;
                } else if (self.lastSideHit == Direction.SOUTH) {
                    bellPitch = swingAngle;
                } else if (self.lastSideHit == Direction.EAST) {
                    bellRoll = -swingAngle;
                } else if (self.lastSideHit == Direction.WEST) {
                    bellRoll = swingAngle;
                }
            }
            matrices.push();
            matrices.translate(8f/16, 12f/16, 8f/16);
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(bellPitch));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(bellRoll));
            matrices.translate(-8f/16, -12f/16, -8f/16);
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getCachedState(), matrices, bellModel, light, overlay);

            matrices.pop();
        }
    }

    private BakedModel getBellModel() {
        return MinecraftClient.getInstance().getBakedModelManager().getModel(ModelIdentifiers.BELL_BODY);
    }

    @Override
    public void onModelsReload() {
        bellModel = null;
    }
}

package foundationgames.enhancedblockentities.client.render.entity;

import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.duck.BakedModelManagerAccess;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class BellBlockEntityRendererOverride extends BlockEntityRendererOverride {
    private BakedModel bellModel = null;

    @Override
    public void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (bellModel == null) bellModel = getBellModel();
        if (blockEntity instanceof BellBlockEntity) {
            BellBlockEntity self = (BellBlockEntity)blockEntity;
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
            matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(bellPitch));
            matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(bellRoll));
            matrices.translate(-8f/16, -12f/16, -8f/16);
            renderBakedModel(vertexConsumers.getBuffer(RenderLayer.getSolid()), matrices, bellModel, light, overlay, Direction.NORTH);

            matrices.pop();
        }
    }

    private BakedModel getBellModel() {
        BakedModelManagerAccess manager = (BakedModelManagerAccess)MinecraftClient.getInstance().getBakedModelManager();
        return manager.getModel(ModelIdentifiers.BELL_BODY);
    }

    @Override
    public void onModelsReload() {
        bellModel = null;
    }
}

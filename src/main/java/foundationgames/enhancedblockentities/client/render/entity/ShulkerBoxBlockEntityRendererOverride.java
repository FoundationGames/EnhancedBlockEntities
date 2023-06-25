package foundationgames.enhancedblockentities.client.render.entity;

import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ShulkerBoxBlockEntityRendererOverride extends BlockEntityRendererOverride {
    private final Map<DyeColor, BakedModel> models = new HashMap<>();
    private final Consumer<Map<DyeColor, BakedModel>> modelMapFiller;

    public ShulkerBoxBlockEntityRendererOverride(Consumer<Map<DyeColor, BakedModel>> modelMapFiller) {
        this.modelMapFiller = modelMapFiller;
    }

    @Override
    public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (models.isEmpty()) modelMapFiller.accept(models);
        if (blockEntity instanceof ShulkerBoxBlockEntity entity) {
            Direction dir = Direction.UP;
            BlockState state = entity.getWorld().getBlockState(entity.getPos());
            if (state.getBlock() instanceof ShulkerBoxBlock) {
                dir = state.get(ShulkerBoxBlock.FACING);
            }
            matrices.push();

            float animation = entity.getAnimationProgress(tickDelta);

            matrices.translate(0.5, 0.5, 0.5);
            matrices.multiply(dir.getRotationQuaternion());
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270 * animation));
            matrices.translate(-0.5, -0.5, -0.5);

            matrices.translate(0, animation * 0.5f, 0);

            var lidModel = models.get(entity.getColor());
            if (lidModel != null) {
                EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getCachedState(), matrices, lidModel, light, overlay);
            }

            matrices.pop();
        }
    }

    @Override
    public void onModelsReload() {
        this.models.clear();
    }
}

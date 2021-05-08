package foundationgames.enhancedblockentities.client.render.entity;

import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.function.Function;
import java.util.function.Supplier;

public class ChestBlockEntityRendererOverride extends BlockEntityRendererOverride {
    private BakedModel[] models = null;
    private final Supplier<BakedModel[]> modelGetter;
    private final Function<BlockEntity, Integer> modelSelector;

    public ChestBlockEntityRendererOverride(Supplier<BakedModel[]> modelGetter, Function<BlockEntity, Integer> modelSelector) {
        this.modelGetter = modelGetter;
        this.modelSelector = modelSelector;
    }

    @Override
    public void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (models == null) models = modelGetter.get();
        if (blockEntity instanceof ChestAnimationProgress) {
            matrices.push();

            ChestAnimationProgress chest = getAnimationProgress(blockEntity, tickDelta);
            matrices.translate(0.5f, 0, 0.5f);
            Direction dir = blockEntity.getCachedState().get(ChestBlock.FACING);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180 - dir.asRotation()));
            matrices.translate(-0.5f, 0, -0.5f);
            float yPiv = 9f / 16;
            float zPiv = 15f / 16;
            matrices.translate(0, yPiv, zPiv);
            float rot = chest.getAnimationProgress(tickDelta);
            rot = 1f - rot;
            rot = 1f - (rot * rot * rot);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(rot * 90));
            matrices.translate(0, -yPiv, -zPiv);
            renderBakedModel(vertexConsumers.getBuffer(RenderLayer.getSolid()), matrices, models[modelSelector.apply(blockEntity)], light, overlay, dir);

            matrices.pop();
        }
    }

    public static ChestAnimationProgress getAnimationProgress(BlockEntity blockEntity, float tickDelta) {
        ChestAnimationProgress chest = (ChestAnimationProgress)blockEntity;

        BlockState state = blockEntity.getCachedState();
        if (state.contains(ChestBlock.CHEST_TYPE) && state.get(ChestBlock.CHEST_TYPE) != ChestType.SINGLE) {
            BlockEntity neighbor = null;
            BlockPos pos = blockEntity.getPos();
            Direction facing = state.get(ChestBlock.FACING);
            switch (state.get(ChestBlock.CHEST_TYPE)) {
                case LEFT:
                    neighbor = blockEntity.getWorld().getBlockEntity(pos.offset(facing.rotateYClockwise()));
                    break;
                case RIGHT:
                    neighbor = blockEntity.getWorld().getBlockEntity(pos.offset(facing.rotateYCounterclockwise()));
            }
            if (neighbor instanceof ChestAnimationProgress) {
                float nAnim = ((ChestAnimationProgress)neighbor).getAnimationProgress(tickDelta);
                if (nAnim > chest.getAnimationProgress(tickDelta)) {
                    chest = ((ChestAnimationProgress)neighbor);
                }
            }
        }

        return chest;
    }

    @Override
    public void onModelsReload() {
        this.models = null;
    }
}

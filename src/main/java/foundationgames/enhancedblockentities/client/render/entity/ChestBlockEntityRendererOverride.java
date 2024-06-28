package foundationgames.enhancedblockentities.client.render.entity;

import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

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
    public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (models == null) models = modelGetter.get();
        if (blockEntity instanceof LidOpenable) {
            matrices.push();

            LidOpenable chest = getLidAnimationHolder(blockEntity, tickDelta);
            matrices.translate(0.5f, 0, 0.5f);
            Direction dir = blockEntity.getCachedState().get(ChestBlock.FACING);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180 - dir.asRotation()));
            matrices.translate(-0.5f, 0, -0.5f);
            float yPiv = 9f / 16;
            float zPiv = 15f / 16;
            matrices.translate(0, yPiv, zPiv);
            float rot = chest.getAnimationProgress(tickDelta);
            rot = 1f - rot;
            rot = 1f - (rot * rot * rot);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(rot * 90));
            matrices.translate(0, -yPiv, -zPiv);
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getCachedState(), matrices, models[modelSelector.apply(blockEntity)], light, overlay);

            matrices.pop();
        }
    }

    public static LidOpenable getLidAnimationHolder(BlockEntity blockEntity, float tickDelta) {
        LidOpenable chest = (LidOpenable)blockEntity;

        BlockState state = blockEntity.getCachedState();
        if (state.contains(ChestBlock.CHEST_TYPE) && state.get(ChestBlock.CHEST_TYPE) != ChestType.SINGLE) {
            BlockEntity neighbor = null;
            BlockPos pos = blockEntity.getPos();
            Direction facing = state.get(ChestBlock.FACING);
            switch (state.get(ChestBlock.CHEST_TYPE)) {
                case LEFT -> neighbor = blockEntity.getWorld().getBlockEntity(pos.offset(facing.rotateYClockwise()));
                case RIGHT -> neighbor = blockEntity.getWorld().getBlockEntity(pos.offset(facing.rotateYCounterclockwise()));
            }
            if (neighbor instanceof LidOpenable) {
                float nAnim = ((LidOpenable)neighbor).getAnimationProgress(tickDelta);
                if (nAnim > chest.getAnimationProgress(tickDelta)) {
                    chest = ((LidOpenable)neighbor);
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

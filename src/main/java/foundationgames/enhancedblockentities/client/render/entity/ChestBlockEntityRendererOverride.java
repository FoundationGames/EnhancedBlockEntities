package foundationgames.enhancedblockentities.client.render.entity;

import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.EBEUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import com.mojang.math.Axis;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

import java.util.function.Function;
import java.util.function.Supplier;

public class ChestBlockEntityRendererOverride extends BlockEntityRendererOverride {
    private BakedModel[] models = null;
    private final Supplier<BakedModel[]> modelGetter;
    private final Function<BlockEntity, Integer> modelSelector;

    // LidOpenable was renamed to LidBlockEntity in Mojang mappings

    public ChestBlockEntityRendererOverride(Supplier<BakedModel[]> modelGetter, Function<BlockEntity, Integer> modelSelector) {
        this.modelGetter = modelGetter;
        this.modelSelector = modelSelector;
    }

    @Override
    public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (models == null) models = modelGetter.get();
        if (blockEntity instanceof LidBlockEntity) {
            matrices.pushPose();

            LidBlockEntity chest = getLidAnimationHolder(blockEntity, tickDelta);
            matrices.translate(0.5f, 0, 0.5f);
            Direction dir = blockEntity.getBlockState().getValue(ChestBlock.FACING);
            matrices.mulPose(Axis.YP.rotationDegrees(180 - EBEUtil.angle(dir)));
            matrices.translate(-0.5f, 0, -0.5f);
            float yPiv = 9f / 16;
            float zPiv = 15f / 16;
            matrices.translate(0, yPiv, zPiv);
            float rot = chest.getOpenNess(tickDelta);
            rot = 1f - rot;
            rot = 1f - (rot * rot * rot);
            matrices.mulPose(Axis.XP.rotationDegrees(rot * 90));
            matrices.translate(0, -yPiv, -zPiv);
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getBlockState(), matrices, models[modelSelector.apply(blockEntity)], light, overlay);

            matrices.popPose();
        }
    }

    public static LidBlockEntity getLidAnimationHolder(BlockEntity blockEntity, float tickDelta) {
        LidBlockEntity chest = (LidBlockEntity)blockEntity;

        BlockState state = blockEntity.getBlockState();
        if (state.hasProperty(ChestBlock.TYPE) && state.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
            BlockEntity neighbor = null;
            BlockPos pos = blockEntity.getBlockPos();
            Direction facing = state.getValue(ChestBlock.FACING);
            switch (state.getValue(ChestBlock.TYPE)) {
                case LEFT -> neighbor = blockEntity.getLevel().getBlockEntity(pos.relative(facing.getClockWise()));
                case RIGHT -> neighbor = blockEntity.getLevel().getBlockEntity(pos.relative(facing.getCounterClockWise()));
            }
            if (neighbor instanceof LidBlockEntity) {
                float nAnim = ((LidBlockEntity)neighbor).getOpenNess(tickDelta);
                if (nAnim > chest.getOpenNess(tickDelta)) {
                    chest = ((LidBlockEntity)neighbor);
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

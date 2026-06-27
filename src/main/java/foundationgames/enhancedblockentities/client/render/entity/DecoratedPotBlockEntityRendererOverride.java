package foundationgames.enhancedblockentities.client.render.entity;

import com.google.common.collect.ImmutableMap;
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.EBEUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import com.mojang.math.Axis;
import net.minecraft.world.level.block.DecoratedPotPattern;
import net.minecraft.world.level.block.DecoratedPotPatterns;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;

import java.util.Map;

public class DecoratedPotBlockEntityRendererOverride extends BlockEntityRendererOverride {
    public static final float WOBBLE_STRENGTH = 1f / 64;

    private BakedModel baseModel = null;
    private Map<ResourceKey<DecoratedPotPattern>, BakedModel[]> potPatternModels = null;

    private void tryGetModels() {
        var models = Minecraft.getInstance().getModelManager();

        if (this.baseModel == null) {
            this.baseModel = models.getModel(ModelIdentifiers.DECORATED_POT_BASE);
        }

        if (this.potPatternModels == null) {
            var builder = ImmutableMap.<ResourceKey<DecoratedPotPattern>, BakedModel[]>builder();

            BuiltInRegistries.DECORATED_POT_PATTERN.registryKeySet().forEach(k -> {
                var patternModelIDs = ModelIdentifiers.POTTERY_PATTERNS.get(k);
                BakedModel[] patternPerFaceModels = new BakedModel[patternModelIDs.length];

                for (int i = 0; i < patternModelIDs.length; i++) {
                    patternPerFaceModels[i] = models.getModel(patternModelIDs[i]);
                }

                builder.put(k, patternPerFaceModels);
            });

            this.potPatternModels = builder.build();
        }
    }

    @Override
    public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        tryGetModels();

        if (blockEntity instanceof DecoratedPotBlockEntity pot) {
            matrices.pushPose();

            var dir = pot.getHorizontalFacing();

            matrices.translate(0.5f, 0, 0.5f);
            matrices.mulPose(Axis.YP.rotationDegrees(180 - EBEUtil.angle(dir)));
            matrices.translate(-0.5f, 0, -0.5f);

            var wobbleType = pot.lastWobbleType;
            if (wobbleType != null && pot.getLevel() != null) {
                float tilt = ((float)(pot.getLevel().getGameTime() - pot.lastWobbleTime) + tickDelta) / (float)wobbleType.lengthInTicks;
                if (tilt >= 0.0F && tilt <= 1.0F) {
                    if (wobbleType == DecoratedPotBlockEntity.WobbleType.POSITIVE) {
                        float animPeriod = tilt * Mth.TWO_PI;

                        float tiltX = -1.5f * (Mth.cos(animPeriod) + 0.5f) * Mth.sin(animPeriod * 0.5f);
                        matrices.mulPose(Axis.XP.rotation(tiltX * WOBBLE_STRENGTH));

                        float tiltZ = Mth.sin(animPeriod);
                        matrices.mulPose(Axis.ZP.rotation(tiltZ * WOBBLE_STRENGTH));
                    } else {
                        float yaw = (1f - tilt) * Mth.sin(-tilt * 3 * Mth.PI) * 0.125f;
                        matrices.mulPose(Axis.YP.rotation(yaw));
                    }
                }
            }

            var sherds = pot.getSherds();
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getBlockState(), matrices, this.baseModel, light, overlay);

            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getBlockState(), matrices,
                    this.potPatternModels.get(
                            sherds.back().map(DecoratedPotPatterns::fromSherd).orElse(DecoratedPotPatterns.BLANK)
                    )[0], light, overlay);
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getBlockState(), matrices,
                    this.potPatternModels.get(
                            sherds.left().map(DecoratedPotPatterns::fromSherd).orElse(DecoratedPotPatterns.BLANK)
                    )[1], light, overlay);
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getBlockState(), matrices,
                    this.potPatternModels.get(
                            sherds.right().map(DecoratedPotPatterns::fromSherd).orElse(DecoratedPotPatterns.BLANK)
                    )[2], light, overlay);
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getBlockState(), matrices,
                    this.potPatternModels.get(
                            sherds.front().map(DecoratedPotPatterns::fromSherd).orElse(DecoratedPotPatterns.BLANK)
                    )[3], light, overlay);

            matrices.popPose();
        }
    }

    @Override
    public void onModelsReload() {
        this.baseModel = null;
        this.potPatternModels = null;
    }
}
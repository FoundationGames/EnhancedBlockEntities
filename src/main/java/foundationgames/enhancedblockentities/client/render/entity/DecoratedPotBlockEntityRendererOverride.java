package foundationgames.enhancedblockentities.client.render.entity;

import com.google.common.collect.ImmutableMap;
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.minecraft.block.DecoratedPotPattern;
import net.minecraft.block.DecoratedPotPatterns;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

import java.util.Map;

public class DecoratedPotBlockEntityRendererOverride extends BlockEntityRendererOverride {
    public static final float WOBBLE_STRENGTH = 1f / 64;

    private BlockStateModel baseModel = null;
    private Map<RegistryKey<DecoratedPotPattern>, BlockStateModel[]> potPatternModels = null;

    private void tryGetModels() {
        var models = MinecraftClient.getInstance().getBakedModelManager();

        if (this.baseModel == null) {
            this.baseModel = models.getModel(ExtraModelKey.create(ModelIdentifiers.DECORATED_POT_BASE::toString));
        }

        if (this.potPatternModels == null) {
            var builder = ImmutableMap.<RegistryKey<DecoratedPotPattern>, BlockStateModel[]>builder();

            Registries.DECORATED_POT_PATTERN.getKeys().forEach(k -> {
                var patternModelIDs = ModelIdentifiers.POTTERY_PATTERNS.get(k);
                BlockStateModel[] patternPerFaceModels = new BlockStateModel[patternModelIDs.length];

                for (int i = 0; i < patternModelIDs.length; i++) {
                    patternPerFaceModels[i] = models.getModel(ExtraModelKey.create(patternModelIDs[i]::toString));
                }

                builder.put(k, patternPerFaceModels);
            });

            this.potPatternModels = builder.build();
        }
    }

    @Override
    public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        tryGetModels();

        if (blockEntity instanceof DecoratedPotBlockEntity pot) {
            matrices.push();

            var dir = pot.getHorizontalFacing();

            matrices.translate(0.5f, 0, 0.5f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180 - EBEUtil.angle(dir)));
            matrices.translate(-0.5f, 0, -0.5f);

            var wobbleType = pot.lastWobbleType;
            if (wobbleType != null && pot.getWorld() != null) {
                float tilt = ((float)(pot.getWorld().getTime() - pot.lastWobbleTime) + tickDelta) / (float)wobbleType.lengthInTicks;
                if (tilt >= 0.0F && tilt <= 1.0F) {
                    if (wobbleType == DecoratedPotBlockEntity.WobbleType.POSITIVE) {
                        float animPeriod = tilt * MathHelper.TAU;

                        float tiltX = -1.5f * (MathHelper.cos(animPeriod) + 0.5f) * MathHelper.sin(animPeriod * 0.5f);
                        matrices.multiply(RotationAxis.POSITIVE_X.rotation(tiltX * WOBBLE_STRENGTH), 0.5f, 0f, 0.5f);

                        float tiltZ = MathHelper.sin(animPeriod);
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotation(tiltZ * WOBBLE_STRENGTH), 0.5f, 0f, 0.5f);
                    } else {
                        float yaw = (1f - tilt) * MathHelper.sin(-tilt * 3 * MathHelper.PI) * 0.125f;
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(yaw), 0.5f, 0f, 0.5f);
                    }
                }
            }

            var sherds = pot.getSherds();
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getCachedState(), matrices, this.baseModel, light, overlay);

            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getCachedState(), matrices,
                    this.potPatternModels.get(
                            sherds.back().map(DecoratedPotPatterns::fromSherd).orElse(DecoratedPotPatterns.BLANK)
                    )[0], light, overlay);
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getCachedState(), matrices,
                    this.potPatternModels.get(
                            sherds.left().map(DecoratedPotPatterns::fromSherd).orElse(DecoratedPotPatterns.BLANK)
                    )[1], light, overlay);
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getCachedState(), matrices,
                    this.potPatternModels.get(
                            sherds.right().map(DecoratedPotPatterns::fromSherd).orElse(DecoratedPotPatterns.BLANK)
                    )[2], light, overlay);
            EBEUtil.renderBakedModel(vertexConsumers, blockEntity.getCachedState(), matrices,
                    this.potPatternModels.get(
                            sherds.front().map(DecoratedPotPatterns::fromSherd).orElse(DecoratedPotPatterns.BLANK)
                    )[3], light, overlay);

            matrices.pop();
        }
    }

    @Override
    public void onModelsReload() {
        this.baseModel = null;
        this.potPatternModels = null;
    }
}
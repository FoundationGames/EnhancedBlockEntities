package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.EnhancedBlockEntityRegistry;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignEditScreen.class)
public class SignEditScreenMixin {
    @Inject(method = "renderSignBackground", at = @At("HEAD"), cancellable = true)
    private void enhanced_bes$renderBakedModelSign(DrawContext context, BlockState state, CallbackInfo ci) {
        boolean enhanceSigns = EnhancedBlockEntities.CONFIG.renderEnhancedSigns;

        if (!EnhancedBlockEntityRegistry.BLOCKS.contains(state.getBlock())) return;

        if (enhanceSigns) {
            var models = MinecraftClient.getInstance().getBakedModelManager().getBlockModels();
            var buffers = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
            float up = 0;
            if (state.contains(Properties.HORIZONTAL_FACING)) {
                state = state.with(Properties.HORIZONTAL_FACING, Direction.SOUTH);
                up += 5f/16;
            } else if (state.contains(Properties.ROTATION)) {
                state = state.with(Properties.ROTATION, 0);
            }

            var signModel = models.getModel(state);
            var matrices = context.getMatrices();

            matrices.push();
            matrices.translate(0, 31, -50);
            matrices.scale(93.75f, -93.75f, 93.75f);
            matrices.translate(-0.5, up - 0.5, 0);

            EBEUtil.renderBakedModel(buffers, state, matrices, signModel, 15728880, OverlayTexture.DEFAULT_UV);

            matrices.pop();

            ci.cancel();
        }
    }
}

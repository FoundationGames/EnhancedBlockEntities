package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.EnhancedBlockEntityRegistry;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignEditScreen.class)
public class SignEditScreenMixin {
    @Shadow private SignBlockEntityRenderer.SignModel model;
    @Shadow @Final private SignBlockEntity sign;

    @Inject(method = "render", at = @At("HEAD"))
    private void enhanced_bes$renderBakedModelSign(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        boolean enhanceSigns = EnhancedBlockEntities.CONFIG.renderEnhancedSigns;

        var state = this.sign.getCachedState();
        if (!EnhancedBlockEntityRegistry.BLOCKS.contains(state.getBlock())) return;

        this.model.root.visible = !enhanceSigns;

        if (enhanceSigns) {
            var self = (SignEditScreen)(Object)this;
            var models = MinecraftClient.getInstance().getBakedModelManager().getBlockModels();
            var buffers = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
            if (state.contains(Properties.HORIZONTAL_FACING)) {
                state = state.with(Properties.HORIZONTAL_FACING, Direction.NORTH);
            } else if (state.contains(Properties.ROTATION)) {
                state = state.with(Properties.ROTATION, 0);
            }

            var signModel = models.getModel(state);

            matrices.push();
            matrices.translate(self.width / 2d, 0.0D, 50.0D);

            matrices.scale(93.75f, -93.75f, 93.75f);
            matrices.translate(-0.5, -1.8125, -1);

            EBEUtil.renderBakedModel(buffers, state, matrices, signModel, 15728880, OverlayTexture.DEFAULT_UV);

            matrices.pop();
        }
    }
}

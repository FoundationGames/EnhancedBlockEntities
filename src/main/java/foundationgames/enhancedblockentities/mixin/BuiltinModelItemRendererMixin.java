package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.EnhancedBlockEntityRegistry;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.block.BedBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void enhanced_bes$renderBeds(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        if (EnhancedBlockEntities.CONFIG.renderEnhancedBeds &&
                stack.getItem() instanceof BlockItem item &&
                item.getBlock() instanceof BedBlock bed &&
                EnhancedBlockEntityRegistry.BLOCKS.contains(bed)) {
            var models = MinecraftClient.getInstance().getBakedModelManager().getBlockModels();

            var bedState = bed.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH);
            var footState = bedState.with(Properties.BED_PART, BedPart.FOOT);
            var footModel = models.getModel(footState);
            var headState = bedState.with(Properties.BED_PART, BedPart.HEAD);
            var headModel = models.getModel(headState);

            matrices.push();
            EBEUtil.renderBakedModel(vertexConsumers, headState, matrices, headModel, light, overlay);
            matrices.translate(0, 0, -1);
            EBEUtil.renderBakedModel(vertexConsumers, footState, matrices, footModel, light, overlay);
            matrices.pop();

            ci.cancel();
        }
    }
}

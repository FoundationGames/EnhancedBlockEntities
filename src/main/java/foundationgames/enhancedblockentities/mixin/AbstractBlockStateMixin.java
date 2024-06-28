package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntityRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Shadow public abstract Block getBlock();

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void enhanced_bes$overrideRenderType(CallbackInfoReturnable<BlockRenderType> cir) {
        Block block = this.getBlock();
        if (EnhancedBlockEntityRegistry.BLOCKS.contains(block)) {
            cir.setReturnValue(BlockRenderType.MODEL);
        }
    }
}

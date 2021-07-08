package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.util.WorldUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/block/entity/ChestBlockEntity$1")
public class ChestBlockEntityStateManagerMixin {
    @Inject(method = "onChestOpened(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At("HEAD"))
    private void ebe$listenForOpen(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if(EnhancedBlockEntities.CONFIG.renderEnhancedChests) WorldUtil.scheduleRebuild(pos, state, 2);
    }
}

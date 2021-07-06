package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/block/entity/ChestBlockEntity$1")
public class ChestBlockEntityStateManagerMixin {
    @Inject(method = "onChestOpened", at = @At("HEAD"))
    private void ebe$listenForOpen(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
        rebuildChunk(world, pos, state);
    }

    private static void rebuildChunk(World world, BlockPos pos, BlockState state) {
        if(EnhancedBlockEntities.CONFIG.renderEnhancedBells) {
            MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, state, state, 1);
        }
    }
}

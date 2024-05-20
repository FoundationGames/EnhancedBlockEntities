package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.WorldUtil;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotBlockEntity.class)
public class DecoratedPotBlockEntityMixin {
    @Inject(method = "readNbt", at = @At("TAIL"))
    private void enhanced_bes$updateChunkOnPatternsLoaded(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfo ci) {
        var self = (DecoratedPotBlockEntity)(Object)this;

        if (self.getWorld() != null && self.getWorld().isClient()) {
            WorldUtil.rebuildChunkSynchronously(self.getWorld(), self.getPos(), false);
        }
    }
}

package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.WorldUtil;
import foundationgames.enhancedblockentities.util.duck.AppearanceStateHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotBlockEntity.class)
public class DecoratedPotBlockEntityMixin implements AppearanceStateHolder {
    @Unique private int enhanced_bes$modelState = 0;
    @Unique private int enhanced_bes$renderState = 0;

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void enhanced_bes$updateChunkOnPatternsLoaded(CompoundTag nbt, HolderLookup.Provider rwl, CallbackInfo ci) {
        var self = (DecoratedPotBlockEntity)(Object)this;

        if (self.getLevel() != null && self.getLevel().isClientSide()) {
            WorldUtil.rebuildChunk(self.getLevel(), self.getBlockPos());
        }
    }

    @Inject(method = "triggerEvent", at = @At(value = "RETURN", shift = At.Shift.BEFORE, ordinal = 0))
    private void enhanced_bes$updateOnWobble(int type, int data, CallbackInfoReturnable<Boolean> cir) {
        var self = (DecoratedPotBlockEntity)(Object)this;
        var world = self.getLevel();

        if (self.lastWobbleType == null) {
            return;
        }

        this.updateAppearanceState(1, world, self.getBlockPos());

        WorldUtil.scheduleTimed(world, self.lastWobbleTime + self.lastWobbleType.lengthInTicks,
                () -> {
                    if (self.getLevel().getGameTime() >= self.lastWobbleTime + self.lastWobbleType.lengthInTicks) {
                        this.updateAppearanceState(0, world, self.getBlockPos());
                    }
                });
    }

    @Override
    public int getModelState() {
        return enhanced_bes$modelState;
    }

    @Override
    public void setModelState(int state) {
        this.enhanced_bes$modelState = state;
    }

    @Override
    public int getRenderState() {
        return enhanced_bes$renderState;
    }

    @Override
    public void setRenderState(int state) {
        this.enhanced_bes$renderState = state;
    }
}

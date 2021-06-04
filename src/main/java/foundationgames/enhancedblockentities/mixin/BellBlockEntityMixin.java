package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BellBlockEntity.class)
public class BellBlockEntityMixin extends BlockEntity {
    @Shadow public int ringTicks;

    @Shadow public boolean ringing;

    protected BellBlockEntityMixin(BlockEntityType<?> type) {
        super(type);
    }

    @Inject(method = "tick", at = @At(
            value = "JUMP", opcode = Opcodes.IF_ICMPLT, ordinal = 0, shift = At.Shift.BEFORE
    ))
    public void enhanced_bes$listenForStopRinging(CallbackInfo ci) {
        if (this.ringTicks >= 50) {
            rebuildChunk();
        }
    }

    @Inject(method = "onSyncedBlockEvent", at = @At("HEAD"))
    public void enhanced_bes$listenForRing(int type, int data, CallbackInfoReturnable<Boolean> cir) {
        // the !ringing prevents people from unnecessarily destroying your fps by spamming a bell in a complex, hard-to-rebuild chunk
        if (type == 1 && !ringing) {
            rebuildChunk();
        }
    }

    private void rebuildChunk() {
        if(EnhancedBlockEntities.CONFIG.renderEnhancedBells) {
            MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, getCachedState(), getCachedState(), 1);
        }
    }
}

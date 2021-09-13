package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.util.duck.ModelStateHolder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BellBlockEntity.class)
public class BellBlockEntityMixin extends BlockEntity implements ModelStateHolder {
    @Unique private int enhanced_bes$modelState = 0;

    public BellBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // Can't inject because one of the args' type is private (BellBlockEntity$Effect) and I don't feel like AW
    // So now you get to suffer through this
    @ModifyVariable(method = "tick", at = @At(
            value = "JUMP", opcode = Opcodes.IF_ICMPLT, ordinal = 0, shift = At.Shift.BEFORE
    ), index = 3)
    private static BellBlockEntity enhanced_bes$listenForStopRinging(BellBlockEntity blockEntity) {
        int mState = blockEntity.ringTicks > 0 ? 1 : 0;
        if (EnhancedBlockEntities.CONFIG.renderEnhancedBells && ((ModelStateHolder)blockEntity).getModelState() != mState) {
            ((ModelStateHolder)blockEntity).setModelState(mState, blockEntity.getWorld(), blockEntity.getPos());
        }
        return blockEntity;
    }

    @Override
    public int getModelState() {
        return enhanced_bes$modelState;
    }

    @Override
    public void applyModelState(int state) {
        this.enhanced_bes$modelState = state;
    }
}

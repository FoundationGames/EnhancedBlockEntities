package foundationgames.enhancedblockentities.mixin;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin extends Block {
    public ChestBlockMixin(Settings settings) {
        super(settings);
    }

    public BlockRenderType getRenderType(BlockState state) {
        //System.out.println("GETTING RENDER TYPE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return BlockRenderType.MODEL;
    }
}

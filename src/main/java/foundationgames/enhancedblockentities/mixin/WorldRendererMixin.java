package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.WorldUtil;
import net.minecraft.client.render.WorldRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @ModifyVariable(method = "updateChunks", at = @At(value = "JUMP", shift = At.Shift.BEFORE, opcode = Opcodes.IFEQ, ordinal = 4), index = 9)
    private boolean enhanced_bes$forceSynchronousChunkRebuild(boolean old) {
        if (WorldUtil.FORCE_SYNCHRONOUS_CHUNK_REBUILD) {
            WorldUtil.FORCE_SYNCHRONOUS_CHUNK_REBUILD = false;
            return true;
        }
        return old;
    }
}

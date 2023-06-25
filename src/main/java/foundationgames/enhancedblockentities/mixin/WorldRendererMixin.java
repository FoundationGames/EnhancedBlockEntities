package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.WorldUtil;
import net.minecraft.client.render.WorldRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    /*  X------------------------updateChunks(Camera camera)-------------------------X
        |---> HERE <---                                                              |
        |   if (bl) {                                                                |
        |       this.client.getProfiler().push("build_near_sync");                   |
        |       this.chunkBuilder.rebuild(builtChunk, chunkRendererRegionBuilder);   |
        X----------------------------[END: 5 LINES DOWN]-----------------------------X  */
    @ModifyVariable(method = "updateChunks", at = @At(value = "JUMP", shift = At.Shift.BEFORE, opcode = Opcodes.IFEQ, ordinal = 4), index = 10)
    private boolean enhanced_bes$forceSynchronousChunkRebuild(boolean old) {
        if (WorldUtil.FORCE_SYNCHRONOUS_CHUNK_REBUILD) {
            WorldUtil.FORCE_SYNCHRONOUS_CHUNK_REBUILD = false;
            return true;
        }
        return old;
    }
}

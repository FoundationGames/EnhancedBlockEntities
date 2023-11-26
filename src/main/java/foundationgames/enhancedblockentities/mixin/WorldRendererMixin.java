package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.WorldUtil;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.chunk.ChunkRendererRegionBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.light.LightingProvider;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    private ChunkSectionPos enhanced_bes$updatingChunk = null;

    @ModifyVariable(method = "updateChunks",
            at = @At(value = "INVOKE_ASSIGN", shift = At.Shift.AFTER, target = "Lnet/minecraft/util/math/ChunkSectionPos;from(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/ChunkSectionPos;"),
            index = 8)
    private ChunkSectionPos enhanced_bes$cacheUpdatedChunkPos(ChunkSectionPos c) {
        enhanced_bes$updatingChunk = c;
        return c;
    }

    /*  X------------------------updateChunks(Camera camera)-------------------------X
        |---> HERE <---                                                              |
        |   if (bl) {                                                                |
        |       this.client.getProfiler().push("build_near_sync");                   |
        |       this.chunkBuilder.rebuild(builtChunk, chunkRendererRegionBuilder);   |
        X----------------------------[END: 5 LINES DOWN]-----------------------------X  */
    @ModifyVariable(method = "updateChunks", at = @At(value = "JUMP", shift = At.Shift.BEFORE, opcode = Opcodes.IFEQ, ordinal = 4), index = 9)
    private boolean enhanced_bes$forceSynchronousChunkRebuild(boolean old) {
        if (enhanced_bes$updatingChunk != null && WorldUtil.FORCE_SYNCHRONOUS_CHUNK_REBUILD.contains(enhanced_bes$updatingChunk)) {
            WorldUtil.FORCE_SYNCHRONOUS_CHUNK_REBUILD.remove(enhanced_bes$updatingChunk);

            enhanced_bes$updatingChunk = null;
            return true;
        }

        enhanced_bes$updatingChunk = null;
        return old;
    }
}

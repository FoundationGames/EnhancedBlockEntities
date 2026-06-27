package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.WorldUtil;
import foundationgames.enhancedblockentities.util.duck.ChunkRebuildTaskAccess;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {

    @ModifyVariable(method = "applyFrustum",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0, target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;"),
            index = 7)
    private ChunkRenderDispatcher.RenderChunk enhanced_bes$addPostRebuildTask(ChunkRenderDispatcher.RenderChunk chunk) {
        if (WorldUtil.CHUNK_UPDATE_TASKS.size() > 0) {
            var origin = chunk.getOrigin();
            var pos = new ChunkPos(origin);

            if (WorldUtil.CHUNK_UPDATE_TASKS.containsKey(pos)) {
                var task = WorldUtil.CHUNK_UPDATE_TASKS.remove(pos);
                ((ChunkRebuildTaskAccess) chunk).enhanced_bes$setTaskAfterRebuild(task);
            }
        }

        return chunk;
    }

    @Inject(method = "addRecentlyCompiledChunk", at = @At("HEAD"))
    private void enhanced_bes$runPostRebuildTask(ChunkRenderDispatcher.RenderChunk chunk, CallbackInfo ci) {
        ((ChunkRebuildTaskAccess) chunk).enhanced_bes$runAfterRebuildTask();
    }
}

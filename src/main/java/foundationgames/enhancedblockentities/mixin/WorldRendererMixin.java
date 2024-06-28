package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.WorldUtil;
import foundationgames.enhancedblockentities.util.duck.ChunkRebuildTaskAccess;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.ChunkSectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @ModifyVariable(method = "updateChunks",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0, target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;"),
            index = 8)
    private ChunkBuilder.BuiltChunk enhanced_bes$addPostRebuildTask(ChunkBuilder.BuiltChunk chunk) {
        if (WorldUtil.CHUNK_UPDATE_TASKS.size() > 0) {
            var pos = ChunkSectionPos.from(chunk.getOrigin());

            if (WorldUtil.CHUNK_UPDATE_TASKS.containsKey(pos)) {
                var task = WorldUtil.CHUNK_UPDATE_TASKS.remove(pos);
                ((ChunkRebuildTaskAccess) chunk).enhanced_bes$setTaskAfterRebuild(task);
            }
        }

        return chunk;
    }

    @Inject(method = "addBuiltChunk", at = @At("HEAD"))
    private void enhanced_bes$runPostRebuildTask(ChunkBuilder.BuiltChunk chunk, CallbackInfo ci) {
        ((ChunkRebuildTaskAccess) chunk).enhanced_bes$runAfterRebuildTask();
    }
}

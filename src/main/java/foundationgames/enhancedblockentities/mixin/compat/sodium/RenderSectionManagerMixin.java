package foundationgames.enhancedblockentities.mixin.compat.sodium;

import foundationgames.enhancedblockentities.util.WorldUtil;
import foundationgames.enhancedblockentities.util.duck.ChunkRebuildTaskAccess;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSection;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSectionManager;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildOutput;
import net.minecraft.util.math.ChunkSectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * <p>Adapted from {@link foundationgames.enhancedblockentities.mixin.WorldRendererMixin}</p>
 */
@Pseudo
@Mixin(value = RenderSectionManager.class, remap = false)
public class RenderSectionManagerMixin {
    @ModifyVariable(method = "submitRebuildTasks",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0, target = "Lme/jellysquid/mods/sodium/client/render/chunk/RenderSection;isDisposed()Z"),
            index = 4, require = 0
    )
    private RenderSection enhanced_bes$compat_sodium$cacheUpdatingChunk(RenderSection section) {
        if (WorldUtil.CHUNK_UPDATE_TASKS.size() > 0) {
            var pos = ChunkSectionPos.from(section.getChunkX(), section.getChunkY(), section.getChunkZ());

            if (WorldUtil.CHUNK_UPDATE_TASKS.containsKey(pos)) {
                var task = WorldUtil.CHUNK_UPDATE_TASKS.remove(pos);
                ((ChunkRebuildTaskAccess) section).enhanced_bes$setTaskAfterRebuild(task);
            }
        }

        return section;
    }

    @ModifyVariable(method = "processChunkBuildResults",
            at = @At(value = "INVOKE_ASSIGN", shift = At.Shift.BEFORE, ordinal = 0, target = "Lme/jellysquid/mods/sodium/client/render/chunk/RenderSection;getBuildCancellationToken()Lme/jellysquid/mods/sodium/client/util/task/CancellationToken;"),
            index = 4, require = 0
    )
    private ChunkBuildOutput enhanced_bes$runPostRebuildTask(ChunkBuildOutput output) {
        ((ChunkRebuildTaskAccess) output.render).enhanced_bes$runAfterRebuildTask();

        return output;
    }
}

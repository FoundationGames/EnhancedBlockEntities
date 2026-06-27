package foundationgames.enhancedblockentities.mixin.compat.sodium;

import foundationgames.enhancedblockentities.util.WorldUtil;
import foundationgames.enhancedblockentities.util.duck.ChunkRebuildTaskAccess;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSectionManager;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.BuilderTaskOutput;
import net.minecraft.world.level.ChunkPos;
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
    @ModifyVariable(method = "submitSectionTasks(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/executor/ChunkJobCollector;Lnet/caffeinemc/mods/sodium/client/render/chunk/ChunkUpdateType;Z)V",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0, target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/RenderSection;isDisposed()Z"),
            index = 5, require = 0
    )
    private RenderSection enhanced_bes$compat_sodium$cacheUpdatingChunk(RenderSection section) {
        if (WorldUtil.CHUNK_UPDATE_TASKS.size() > 0) {
            var pos = new ChunkPos(section.getChunkX(), section.getChunkZ());

            if (WorldUtil.CHUNK_UPDATE_TASKS.containsKey(pos)) {
                var task = WorldUtil.CHUNK_UPDATE_TASKS.remove(pos);
                ((ChunkRebuildTaskAccess) section).enhanced_bes$setTaskAfterRebuild(task);
            }
        }

        return section;
    }

    @ModifyVariable(method = "processChunkBuildResults",
            at = @At(value = "INVOKE_ASSIGN", shift = At.Shift.BEFORE, ordinal = 0, target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/RenderSection;getTaskCancellationToken()Lnet/caffeinemc/mods/sodium/client/util/task/CancellationToken;"),
            index = 5, require = 0
    )
    private BuilderTaskOutput enhanced_bes$runPostRebuildTask(BuilderTaskOutput output) {
        ((ChunkRebuildTaskAccess) output.render).enhanced_bes$runAfterRebuildTask();

        return output;
    }
}

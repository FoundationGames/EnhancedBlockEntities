package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.duck.ChunkRebuildTaskAccess;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChunkBuilder.BuiltChunk.class)
public class BuiltChunkMixin implements ChunkRebuildTaskAccess {
    private @Unique
    @Nullable Runnable enhanced_bes$taskAfterRebuild = null;

    @Override
    public Runnable enhanced_bes$getTaskAfterRebuild() {
        return enhanced_bes$taskAfterRebuild;
    }

    @Override
    public void enhanced_bes$setTaskAfterRebuild(Runnable task) {
        enhanced_bes$taskAfterRebuild = task;
    }
}

package foundationgames.enhancedblockentities.util.duck;

public interface ChunkRebuildTaskAccess {
    Runnable enhanced_bes$getTaskAfterRebuild();

    void enhanced_bes$setTaskAfterRebuild(Runnable task);

    default void enhanced_bes$runAfterRebuildTask() {
        var task = this.enhanced_bes$getTaskAfterRebuild();

        if (task != null) {
            task.run();

            this.enhanced_bes$setTaskAfterRebuild(null);
        }
    }
}

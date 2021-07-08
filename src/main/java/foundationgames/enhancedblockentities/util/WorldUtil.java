package foundationgames.enhancedblockentities.util;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public enum WorldUtil {;
    private static final Set<ScheduledRebuild> REBUILDS = new HashSet<>();

    public static void scheduleRebuild(BlockPos pos, BlockState state, int ticks) {
        REBUILDS.add(new ScheduledRebuild(pos, state, ticks));
    }

    public static void tick(World world) {
        REBUILDS.forEach(r -> r.tick(world));
        REBUILDS.removeIf(r -> r.removed);
    }

    public static class ScheduledRebuild {
        private final BlockPos pos;
        private final BlockState state;
        private int timer;
        public boolean removed;

        private ScheduledRebuild(BlockPos pos, BlockState state, int ticks) {
            this.pos = pos;
            this.state = state;
            this.timer = ticks;
        }

        public void tick(World world) {
            timer--;
            if (timer <= 0) {
                MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, state, state, 1);
                removed = true;
            }
        }
    }
}

package foundationgames.enhancedblockentities.util;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ChunkPos;

import java.util.HashMap;
import java.util.Map;

public enum WorldUtil implements ClientTickEvents.EndWorldTick {
    EVENT_LISTENER;

    public static final Map<ChunkPos, ExecutableRunnableHashSet> CHUNK_UPDATE_TASKS = new HashMap<>();
    private static final Map<ResourceKey<Level>, Long2ObjectMap<Runnable>> TIMED_TASKS = new HashMap<>();

    public static void rebuildChunk(Level world, BlockPos pos) {
        var state = world.getBlockState(pos);
        Minecraft.getInstance().levelRenderer.blockChanged(world, pos, state, state, 8);
    }

    public static void rebuildChunkAndThen(Level world, BlockPos pos, Runnable action) {
        CHUNK_UPDATE_TASKS.computeIfAbsent(new ChunkPos(pos), k -> new ExecutableRunnableHashSet()).add(action);
        rebuildChunk(world, pos);
    }

    public static void scheduleTimed(Level world, long time, Runnable action) {
        TIMED_TASKS.computeIfAbsent(world.dimension(), k -> new Long2ObjectOpenHashMap<>()).put(time, action);
    }

    @Override
    public void onEndTick(ClientLevel world) {
        var key = world.dimension();

        if (TIMED_TASKS.containsKey(key)) {
            TIMED_TASKS.get(key).long2ObjectEntrySet().removeIf(entry -> {
                if (world.getGameTime() >= entry.getLongKey()) {
                    entry.getValue().run();
                    return true;
                }

                return false;
            });
        }
    }
}

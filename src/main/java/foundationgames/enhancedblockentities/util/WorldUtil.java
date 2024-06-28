package foundationgames.enhancedblockentities.util;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public enum WorldUtil implements ClientTickEvents.EndWorldTick {
    EVENT_LISTENER;

    public static final Map<ChunkSectionPos, ExecutableRunnableHashSet> CHUNK_UPDATE_TASKS = new HashMap<>();
    private static final Map<RegistryKey<World>, Long2ObjectMap<Runnable>> TIMED_TASKS = new HashMap<>();

    public static void rebuildChunk(World world, BlockPos pos) {
        var state = world.getBlockState(pos);
        MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, state, state, 8);
    }

    public static void rebuildChunkAndThen(World world, BlockPos pos, Runnable action) {
        CHUNK_UPDATE_TASKS.computeIfAbsent(ChunkSectionPos.from(pos), k -> new ExecutableRunnableHashSet()).add(action);
        rebuildChunk(world, pos);
    }

    public static void scheduleTimed(World world, long time, Runnable action) {
        TIMED_TASKS.computeIfAbsent(world.getRegistryKey(), k -> new Long2ObjectOpenHashMap<>()).put(time, action);
    }

    @Override
    public void onEndTick(ClientWorld world) {
        var key = world.getRegistryKey();

        if (TIMED_TASKS.containsKey(key)) {
            TIMED_TASKS.get(key).long2ObjectEntrySet().removeIf(entry -> {
                if (world.getTime() >= entry.getLongKey()) {
                    entry.getValue().run();
                    return true;
                }

                return false;
            });
        }
    }
}

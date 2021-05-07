package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.render.BlockEntityRenderCondition;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class EnhancedBlockEntityRegistry {
    public static final Map<BlockEntityType<?>, Pair<BlockEntityRenderCondition, BlockEntityRendererOverride>> ENTITIES = new HashMap<>();
    public static final Set<Block> BLOCKS = new HashSet<>();

    private EnhancedBlockEntityRegistry() {}

    public static void register(Block block, BlockEntityType<?> type, BlockEntityRenderCondition condition, BlockEntityRendererOverride renderer) {
        ENTITIES.put(type, new Pair<>(condition, renderer));
        BLOCKS.add(block);
    }

    public static void clear() {
        ENTITIES.clear();
        BLOCKS.clear();
    }
}

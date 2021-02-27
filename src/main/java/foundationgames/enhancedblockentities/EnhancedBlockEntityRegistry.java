package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.render.BlockEntityRenderCondition;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class EnhancedBlockEntityRegistry {
    public static final Map<Block, Pair<BlockEntityRenderCondition, BlockEntityRendererOverride>> ENTRIES = new HashMap<>();

    public static void register(Block block, BlockEntityRenderCondition condition, BlockEntityRendererOverride renderer) {
        ENTRIES.put(block, new Pair<>(condition, renderer));
    }
}

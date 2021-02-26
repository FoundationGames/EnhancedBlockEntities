package foundationgames.enhancedblockentities.impl;

import net.minecraft.block.Block;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.UnbakedModel;

public interface BlockModelMapAccess {
    void putModelOverride(Block block, UnbakedModel model);

    BakedModel getModelOverride(Block block);
}

package foundationgames.enhancedblockentities.client.modeldep;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.block.Block;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SwappableBlockModelProvider implements ModelResourceProvider {
    private final Block block;

    public SwappableBlockModelProvider(Block block) {
        this.block = block;
    }

    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier id, ModelProviderContext ctx) throws ModelProviderException {
        return null;//new SwappableBlockUnbakedModel(block, settings);
    }
}

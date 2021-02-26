package foundationgames.enhancedblockentities.client.modeldep;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.UnbakedModel;

import java.util.function.Function;

public final class UnbakedModelSet {
    public final UnbakedModel[] models;
    public final Function<BlockState, Integer> stateModelSelector;

    public UnbakedModelSet(UnbakedModel[] models, Function<BlockState, Integer> stateModelSelector) {
        this.models = models;
        this.stateModelSelector = stateModelSelector;
    }
}

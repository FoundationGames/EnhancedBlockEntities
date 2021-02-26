package foundationgames.enhancedblockentities.client.modeldep;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.ModelBakeSettings;

public interface TransformationSupplier {
    ModelBakeSettings getRotation(BlockState state);
}

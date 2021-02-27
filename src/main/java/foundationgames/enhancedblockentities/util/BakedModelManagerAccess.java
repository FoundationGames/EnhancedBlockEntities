package foundationgames.enhancedblockentities.util;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.Identifier;

public interface BakedModelManagerAccess {
    BakedModel getModel(Identifier id);
}

package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.BakedModelManagerAccess;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin implements BakedModelManagerAccess {

    @Shadow private Map<Identifier, BakedModel> models;

    @Override
    public BakedModel getModel(Identifier id) {
        return this.models.get(id);
    }
}

package foundationgames.enhancedblockentities.dashloader;

import foundationgames.enhancedblockentities.client.model.DynamicBakedModel;
import net.minecraft.client.render.model.BakedModel;
import net.quantumfusion.dashloader.DashRegistry;
import net.quantumfusion.dashloader.api.models.ModelFactory;
import net.quantumfusion.dashloader.models.DashModel;

public class DynamicBakedModelFactory implements ModelFactory {

    @Override
    public <K> DashModel toDash(BakedModel model, DashRegistry registry, K var1) {
        return new DashDynamicBakedModel((DynamicBakedModel) model,registry);
    }

    @Override
    public Class<? extends BakedModel> getType() {
        return DynamicBakedModel.class;
    }

    @Override
    public Class<? extends DashModel> getDashType() {
        return DashDynamicBakedModel.class;
    }
}

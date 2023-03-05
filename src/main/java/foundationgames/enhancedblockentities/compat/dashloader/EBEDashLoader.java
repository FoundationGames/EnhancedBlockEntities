package foundationgames.enhancedblockentities.compat.dashloader;

import dev.notalpha.dashloader.CacheFactory;
import dev.notalpha.dashloader.api.DashEntrypoint;
import dev.notalpha.dashloader.api.MissingHandler;

import java.util.List;

public class EBEDashLoader implements DashEntrypoint {
    @Override
    public void onDashLoaderInit(CacheFactory factory) {
        factory.addDashObject(DashDynamicBakedModel.class);
    }

    @Override
    public void onDashLoaderSave(List<MissingHandler<?>> handlers) {
    }
}

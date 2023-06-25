package foundationgames.enhancedblockentities.compat.dashloader;

import dev.notalpha.dashloader.api.cache.CacheFactory;
import dev.notalpha.dashloader.api.DashEntrypoint;

public class EBEDashLoader implements DashEntrypoint {
    @Override
    public void onDashLoaderInit(CacheFactory factory) {
        factory.addDashObject(DashDynamicBakedModel.class);
    }
}

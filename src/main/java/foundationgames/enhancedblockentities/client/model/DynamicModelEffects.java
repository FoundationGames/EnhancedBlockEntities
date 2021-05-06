package foundationgames.enhancedblockentities.client.model;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;

public abstract class DynamicModelEffects {
    public static final DynamicModelEffects DEFAULT = new DynamicModelEffects() {};

    public static final DynamicModelEffects CHEST = new DynamicModelEffects() {
        @Override
        public boolean ambientOcclusion() {
            return EnhancedBlockEntities.CONFIG.chestAO;
        }
    };

    // TODO: Bells

    public boolean ambientOcclusion() {
        return true;
    }
}

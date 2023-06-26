package foundationgames.enhancedblockentities.client.model;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;

import java.util.ArrayList;
import java.util.List;

public abstract class DynamicModelEffects {
    private static final List<DynamicModelEffects> REGISTRY = new ArrayList<>();

    public static final DynamicModelEffects DEFAULT = new DynamicModelEffects() {};

    public static final DynamicModelEffects CHEST = new DynamicModelEffects() {
        @Override
        public boolean ambientOcclusion() {
            return EnhancedBlockEntities.CONFIG.chestAO;
        }
    };

    public static final DynamicModelEffects BELL = new DynamicModelEffects() {
        @Override
        public boolean ambientOcclusion() {
            return EnhancedBlockEntities.CONFIG.bellAO;
        }
    };

    public static final DynamicModelEffects SHULKER_BOX = new DynamicModelEffects() {
        @Override
        public boolean ambientOcclusion() {
            return EnhancedBlockEntities.CONFIG.shulkerBoxAO;
        }
    };

    public static final DynamicModelEffects DECORATED_POT = new DynamicModelEffects() {
        @Override
        public boolean ambientOcclusion() {
            return EnhancedBlockEntities.CONFIG.decoratedPotAO;
        }
    };

    public final int id;

    public DynamicModelEffects() {
        this.id = REGISTRY.size();
        REGISTRY.add(this);
    }

    public boolean ambientOcclusion() {
        return true;
    }

    public static DynamicModelEffects fromId(int id) {
        return REGISTRY.get(id);
    }
}

package foundationgames.enhancedblockentities.client.model;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.DecoratedPotPattern;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final class ModelIdentifiers implements ModelLoadingPlugin {
    private static final Map<Predicate<EBEConfig>, Set<ResourceLocation>> modelLoaders = new HashMap<>();

    public static final Predicate<EBEConfig> CHEST_PREDICATE = c -> c.renderEnhancedChests;
    public static final Predicate<EBEConfig> BELL_PREDICATE = c -> c.renderEnhancedBells;
    public static final Predicate<EBEConfig> SHULKER_BOX_PREDICATE = c -> c.renderEnhancedShulkerBoxes;
    public static final Predicate<EBEConfig> DECORATED_POT_PREDICATE = c -> c.renderEnhancedDecoratedPots;

    public static final ResourceLocation CHEST_CENTER = of("block/chest_center", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_CENTER_TRUNK = of("block/chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_CENTER_LID = of("block/chest_center_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_LEFT = of("block/chest_left", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_LEFT_TRUNK = of("block/chest_left_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_LEFT_LID = of("block/chest_left_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_RIGHT = of("block/chest_right", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_RIGHT_TRUNK = of("block/chest_right_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_RIGHT_LID = of("block/chest_right_lid", CHEST_PREDICATE);

    public static final ResourceLocation TRAPPED_CHEST_CENTER = of("block/trapped_chest_center", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_CENTER_TRUNK = of("block/trapped_chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_CENTER_LID = of("block/trapped_chest_center_lid", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_LEFT = of("block/trapped_chest_left", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_LEFT_TRUNK = of("block/trapped_chest_left_trunk", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_LEFT_LID = of("block/trapped_chest_left_lid", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_RIGHT = of("block/trapped_chest_right", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_RIGHT_TRUNK = of("block/trapped_chest_right_trunk", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_RIGHT_LID = of("block/trapped_chest_right_lid", CHEST_PREDICATE);

    public static final ResourceLocation CHRISTMAS_CHEST_CENTER = of("block/christmas_chest_center", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_CENTER_TRUNK = of("block/christmas_chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_CENTER_LID = of("block/christmas_chest_center_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_LEFT = of("block/christmas_chest_left", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_LEFT_TRUNK = of("block/christmas_chest_left_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_LEFT_LID = of("block/christmas_chest_left_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_RIGHT = of("block/christmas_chest_right", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_RIGHT_TRUNK = of("block/christmas_chest_right_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_RIGHT_LID = of("block/christmas_chest_right_lid", CHEST_PREDICATE);

    public static final ResourceLocation ENDER_CHEST_CENTER = of("block/ender_chest_center", CHEST_PREDICATE);
    public static final ResourceLocation ENDER_CHEST_CENTER_TRUNK = of("block/ender_chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation ENDER_CHEST_CENTER_LID = of("block/ender_chest_center_lid", CHEST_PREDICATE);

    public static final ResourceLocation BELL_BETWEEN_WALLS = of("block/bell_between_walls", BELL_PREDICATE);
    public static final ResourceLocation BELL_CEILING = of("block/bell_ceiling", BELL_PREDICATE);
    public static final ResourceLocation BELL_FLOOR = of("block/bell_floor", BELL_PREDICATE);
    public static final ResourceLocation BELL_WALL = of("block/bell_wall", BELL_PREDICATE);
    public static final ResourceLocation BELL_BETWEEN_WALLS_WITH_BELL = of("block/bell_between_walls_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_CEILING_WITH_BELL = of("block/bell_ceiling_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_FLOOR_WITH_BELL = of("block/bell_floor_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_WALL_WITH_BELL = of("block/bell_wall_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_BODY = of("block/bell_body", BELL_PREDICATE);

    public static final ResourceLocation DECORATED_POT_BASE = of("block/decorated_pot_base", DECORATED_POT_PREDICATE);
    public static final ResourceLocation DECORATED_POT_SHAKING = of("block/decorated_pot_shaking", DECORATED_POT_PREDICATE);

    public static final Map<DyeColor, ResourceLocation> SHULKER_BOXES = new HashMap<>();
    public static final Map<DyeColor, ResourceLocation> SHULKER_BOX_BOTTOMS = new HashMap<>();
    public static final Map<DyeColor, ResourceLocation> SHULKER_BOX_LIDS = new HashMap<>();

    public static final Map<ResourceKey<DecoratedPotPattern>, ResourceLocation[]> POTTERY_PATTERNS = new HashMap<>();

    static {
        for (DyeColor color : EBEUtil.DEFAULTED_DYE_COLORS) {
            var id = color != null ? "block/"+color.getName()+"_shulker_box" : "block/shulker_box";
            SHULKER_BOXES.put(color, of(id, SHULKER_BOX_PREDICATE));
            SHULKER_BOX_BOTTOMS.put(color, of(id+"_bottom", SHULKER_BOX_PREDICATE));
            SHULKER_BOX_LIDS.put(color, of(id+"_lid", SHULKER_BOX_PREDICATE));
        }

        refreshPotteryPatterns();
    }

    public static void init() {
        ModelLoadingPlugin.register(new ModelIdentifiers());
    }

    public static void refreshPotteryPatterns() {
        POTTERY_PATTERNS.clear();

        // The order decorated pots store patterns per face
        Direction[] orderedHorizontalDirs = new Direction[] {Direction.NORTH, Direction.WEST, Direction.EAST, Direction.SOUTH};

        for (var patternKey : BuiltInRegistries.DECORATED_POT_PATTERN.registryKeySet()) {
            var pattern = patternKey.location().getPath();
            var ids = new ResourceLocation[orderedHorizontalDirs.length];

            for (int i = 0; i < 4; i++) {
                ids[i] = of("block/" + pattern + "_" + orderedHorizontalDirs[i].getName(),
                        DECORATED_POT_PREDICATE);
            }

            POTTERY_PATTERNS.put(patternKey, ids);
        }
    }

    private static ResourceLocation of(String id, Predicate<EBEConfig> condition) {
        ResourceLocation rl = ResourceLocation.parse(id);
        modelLoaders.computeIfAbsent(condition, k -> new HashSet<>()).add(rl);
        return rl;
    }

    @Override
    public void initialize(Context ctx) {
        var config = EnhancedBlockEntities.CONFIG;

        for (var entry : modelLoaders.entrySet()) {
            if (entry.getKey().test(config)) {
                ctx.addModels(entry.getValue());
            }
        }
    }
}

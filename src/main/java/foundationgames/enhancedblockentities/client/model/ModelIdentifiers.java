package foundationgames.enhancedblockentities.client.model;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.block.DecoratedPotPattern;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final class ModelIdentifiers implements ModelLoadingPlugin {
    private static final Map<Predicate<EBEConfig>, Set<ExtraModelKey<BlockStateModel>>> modelLoaders = new HashMap<>();

    public static final Predicate<EBEConfig> CHEST_PREDICATE = c -> c.renderEnhancedChests;
    public static final Predicate<EBEConfig> BELL_PREDICATE = c -> c.renderEnhancedBells;
    public static final Predicate<EBEConfig> SHULKER_BOX_PREDICATE = c -> c.renderEnhancedShulkerBoxes;
    public static final Predicate<EBEConfig> DECORATED_POT_PREDICATE = c -> c.renderEnhancedDecoratedPots;

    // Chest
    public static final ExtraModelKey<BlockStateModel> CHEST_CENTER_KEY = key("block/chest_center", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHEST_CENTER_TRUNK_KEY = key("block/chest_center_trunk", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHEST_CENTER_LID_KEY = key("block/chest_center_lid", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHEST_LEFT_KEY = key("block/chest_left", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHEST_LEFT_TRUNK_KEY = key("block/chest_left_trunk", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHEST_LEFT_LID_KEY = key("block/chest_left_lid", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHEST_RIGHT_KEY = key("block/chest_right", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHEST_RIGHT_TRUNK_KEY = key("block/chest_right_trunk", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHEST_RIGHT_LID_KEY = key("block/chest_right_lid", CHEST_PREDICATE);

    // Trapped Chest
    public static final ExtraModelKey<BlockStateModel> TRAPPED_CHEST_CENTER_KEY = key("block/trapped_chest_center", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> TRAPPED_CHEST_CENTER_TRUNK_KEY = key("block/trapped_chest_center_trunk", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> TRAPPED_CHEST_CENTER_LID_KEY = key("block/trapped_chest_center_lid", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> TRAPPED_CHEST_LEFT_KEY = key("block/trapped_chest_left", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> TRAPPED_CHEST_LEFT_TRUNK_KEY = key("block/trapped_chest_left_trunk", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> TRAPPED_CHEST_LEFT_LID_KEY = key("block/trapped_chest_left_lid", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> TRAPPED_CHEST_RIGHT_KEY = key("block/trapped_chest_right", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> TRAPPED_CHEST_RIGHT_TRUNK_KEY = key("block/trapped_chest_right_trunk", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> TRAPPED_CHEST_RIGHT_LID_KEY = key("block/trapped_chest_right_lid", CHEST_PREDICATE);

    // Christmas Chest
    public static final ExtraModelKey<BlockStateModel> CHRISTMAS_CHEST_CENTER_KEY = key("block/christmas_chest_center", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHRISTMAS_CHEST_CENTER_TRUNK_KEY = key("block/christmas_chest_center_trunk", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHRISTMAS_CHEST_CENTER_LID_KEY = key("block/christmas_chest_center_lid", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHRISTMAS_CHEST_LEFT_KEY = key("block/christmas_chest_left", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHRISTMAS_CHEST_LEFT_TRUNK_KEY = key("block/christmas_chest_left_trunk", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHRISTMAS_CHEST_LEFT_LID_KEY = key("block/christmas_chest_left_lid", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHRISTMAS_CHEST_RIGHT_KEY = key("block/christmas_chest_right", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHRISTMAS_CHEST_RIGHT_TRUNK_KEY = key("block/christmas_chest_right_trunk", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> CHRISTMAS_CHEST_RIGHT_LID_KEY = key("block/christmas_chest_right_lid", CHEST_PREDICATE);

    // Ender Chest
    public static final ExtraModelKey<BlockStateModel> ENDER_CHEST_CENTER_KEY = key("block/ender_chest_center", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> ENDER_CHEST_CENTER_TRUNK_KEY = key("block/ender_chest_center_trunk", CHEST_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> ENDER_CHEST_CENTER_LID_KEY = key("block/ender_chest_center_lid", CHEST_PREDICATE);

    // Bell
    public static final ExtraModelKey<BlockStateModel> BELL_BETWEEN_WALLS_KEY = key("block/bell_between_walls", BELL_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> BELL_CEILING_KEY = key("block/bell_ceiling", BELL_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> BELL_FLOOR_KEY = key("block/bell_floor", BELL_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> BELL_WALL_KEY = key("block/bell_wall", BELL_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> BELL_BETWEEN_WALLS_WITH_BELL_KEY = key("block/bell_between_walls_with_bell", BELL_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> BELL_CEILING_WITH_BELL_KEY = key("block/bell_ceiling_with_bell", BELL_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> BELL_FLOOR_WITH_BELL_KEY = key("block/bell_floor_with_bell", BELL_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> BELL_WALL_WITH_BELL_KEY = key("block/bell_wall_with_bell", BELL_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> BELL_BODY_KEY = key("block/bell_body", BELL_PREDICATE);


    public static final ExtraModelKey<BlockStateModel> DECORATED_POT_BASE_KEY = key("block/decorated_pot_base", DECORATED_POT_PREDICATE);
    public static final ExtraModelKey<BlockStateModel> DECORATED_POT_SHAKING_KEY = key("block/decorated_pot_shaking", DECORATED_POT_PREDICATE);

    public static final Map<DyeColor, ExtraModelKey<BlockStateModel>> SHULKER_BOXES = new HashMap<>();
    public static final Map<DyeColor, ExtraModelKey<BlockStateModel>> SHULKER_BOX_BOTTOMS = new HashMap<>();
    public static final Map<DyeColor, ExtraModelKey<BlockStateModel>> SHULKER_BOX_LIDS = new HashMap<>();

    public static final Map<RegistryKey<DecoratedPotPattern>, ExtraModelKey<BlockStateModel>[]> POTTERY_PATTERNS = new HashMap<>();

    static {
        for (DyeColor color : EBEUtil.DEFAULTED_DYE_COLORS) {
            var id = color != null ? "block/"+color.getId()+"_shulker_box" : "block/shulker_box";

            ExtraModelKey<BlockStateModel> shulkerKey = key(id, SHULKER_BOX_PREDICATE);
            SHULKER_BOXES.put(color, shulkerKey);

            ExtraModelKey<BlockStateModel> shulkerBoxBottomKey = key(id+"_bottom", SHULKER_BOX_PREDICATE);
            SHULKER_BOX_BOTTOMS.put(color, shulkerBoxBottomKey);

            ExtraModelKey<BlockStateModel> shulkerBoxLidKey = key(id+"_bottom", SHULKER_BOX_PREDICATE);
            SHULKER_BOX_LIDS.put(color, shulkerBoxLidKey);
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

        for (var patternKey : Registries.DECORATED_POT_PATTERN.getKeys()) {
            var pattern = patternKey.getValue().getPath();
            var ids = new ExtraModelKey[orderedHorizontalDirs.length];

            for (int i = 0; i < 4; i++) {
                ExtraModelKey<BlockStateModel> id = key("block/" + pattern + "_" + orderedHorizontalDirs[i].getId(),
                        DECORATED_POT_PREDICATE);
                ids[i] = id;
            }

            POTTERY_PATTERNS.put(patternKey, ids);
        }
    }

    private static ExtraModelKey<BlockStateModel> key(String id, Predicate<EBEConfig> condition) {
        ExtraModelKey<BlockStateModel> key = ExtraModelKey.create(() -> Identifier.of(id).toString());
        modelLoaders.computeIfAbsent(condition, k -> new HashSet<>()).add(key);
        return key;
    }
/*        ModelLoadingPlugin.register(new DynamicModelProvidingPlugin(
                Identifier.of("builtin", "chest_center"),
                () -> new DynamicUnbakedModel(
                        new ExtraModelKey[] {
                                ModelIdentifiers.CHEST_CENTER_KEY,
                                ModelIdentifiers.CHEST_CENTER_TRUNK_KEY,
                                ModelIdentifiers.CHRISTMAS_CHEST_CENTER_KEY,
                                ModelIdentifiers.CHRISTMAS_CHEST_CENTER_TRUNK_KEY
                        },
                        ModelSelector.CHEST_WITH_CHRISTMAS,
                        DynamicModelEffects.CHEST
                )
        ));
        ModelLoadingPlugin.register(new DynamicModelProvidingPlugin(
                Identifier.of("builtin", "chest_left"),
                () -> new DynamicUnbakedModel(
                        new ExtraModelKey[] {
                                ModelIdentifiers.CHEST_LEFT_KEY,
                                ModelIdentifiers.CHEST_LEFT_TRUNK_KEY,
                                ModelIdentifiers.CHRISTMAS_CHEST_LEFT_KEY,
                                ModelIdentifiers.CHRISTMAS_CHEST_LEFT_TRUNK_KEY
                        },
                        ModelSelector.CHEST_WITH_CHRISTMAS,
                        DynamicModelEffects.CHEST
                )
        ));

        ModelLoadingPlugin.register(new DynamicModelProvidingPlugin(
                Identifier.of("builtin", "chest_right"),
                () -> new DynamicUnbakedModel(
                        new ExtraModelKey[] {
                                ModelIdentifiers.CHEST_RIGHT_KEY,
                                ModelIdentifiers.CHEST_RIGHT_TRUNK_KEY,
                                ModelIdentifiers.CHRISTMAS_CHEST_RIGHT_KEY,
                                ModelIdentifiers.CHRISTMAS_CHEST_RIGHT_TRUNK_KEY
                        },
                        ModelSelector.CHEST_WITH_CHRISTMAS,
                        DynamicModelEffects.CHEST
                )
        ));*/
    @SuppressWarnings("unchecked")
    @Override
    public void initialize(Context ctx) {
        var config = EnhancedBlockEntities.CONFIG;
        ctx.addModel(ExtraModelKey.create(Identifier.of("builtin", "chest_center")::toString), new DynamicUnbakedModel(
                new ExtraModelKey[] {
                        ModelIdentifiers.CHEST_CENTER_KEY,
                        ModelIdentifiers.CHEST_CENTER_TRUNK_KEY,
                        ModelIdentifiers.CHRISTMAS_CHEST_CENTER_KEY,
                        ModelIdentifiers.CHRISTMAS_CHEST_CENTER_TRUNK_KEY
                },
                ModelSelector.CHEST_WITH_CHRISTMAS,
                DynamicModelEffects.CHEST
        ));
        ctx.addModel(ExtraModelKey.create(Identifier.of("builtin", "chest_left")::toString), new DynamicUnbakedModel(
                new ExtraModelKey[] {
                        ModelIdentifiers.CHEST_LEFT_KEY,
                        ModelIdentifiers.CHEST_LEFT_TRUNK_KEY,
                        ModelIdentifiers.CHRISTMAS_CHEST_LEFT_KEY,
                        ModelIdentifiers.CHRISTMAS_CHEST_LEFT_TRUNK_KEY
                },
                ModelSelector.CHEST_WITH_CHRISTMAS,
                DynamicModelEffects.CHEST
        ));
        ctx.addModel(ExtraModelKey.create(Identifier.of("builtin", "chest_right")::toString), new DynamicUnbakedModel(
                new ExtraModelKey[] {
                        ModelIdentifiers.CHEST_RIGHT_KEY,
                        ModelIdentifiers.CHEST_RIGHT_TRUNK_KEY,
                        ModelIdentifiers.CHRISTMAS_CHEST_RIGHT_KEY,
                        ModelIdentifiers.CHRISTMAS_CHEST_RIGHT_TRUNK_KEY
                },
                ModelSelector.CHEST_WITH_CHRISTMAS,
                DynamicModelEffects.CHEST
        ));
        for (var entry : modelLoaders.entrySet()) {
            if (entry.getKey().test(config)) {
                Set<ExtraModelKey<BlockStateModel>> keys = entry.getValue();

            }
        }
    }
}

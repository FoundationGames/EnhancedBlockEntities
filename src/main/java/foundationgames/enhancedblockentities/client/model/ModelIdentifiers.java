package foundationgames.enhancedblockentities.client.model;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.config.EBEConfig;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public final class ModelIdentifiers {
    public static final Predicate<EBEConfig> CHEST_PREDICATE = c -> c.renderEnhancedChests;
    public static final Predicate<EBEConfig> BELL_PREDICATE = c -> c.renderEnhancedChests;

    public static final Identifier CHEST_CENTER = of("block/chest_center", CHEST_PREDICATE);
    public static final Identifier CHEST_CENTER_TRUNK = of("block/chest_center_trunk", CHEST_PREDICATE);
    public static final Identifier CHEST_CENTER_LID = of("block/chest_center_lid", CHEST_PREDICATE);
    public static final Identifier CHEST_LEFT = of("block/chest_left", CHEST_PREDICATE);
    public static final Identifier CHEST_LEFT_TRUNK = of("block/chest_left_trunk", CHEST_PREDICATE);
    public static final Identifier CHEST_LEFT_LID = of("block/chest_left_lid", CHEST_PREDICATE);
    public static final Identifier CHEST_RIGHT = of("block/chest_right", CHEST_PREDICATE);
    public static final Identifier CHEST_RIGHT_TRUNK = of("block/chest_right_trunk", CHEST_PREDICATE);
    public static final Identifier CHEST_RIGHT_LID = of("block/chest_right_lid", CHEST_PREDICATE);

    public static final Identifier TRAPPED_CHEST_CENTER = of("block/trapped_chest_center", CHEST_PREDICATE);
    public static final Identifier TRAPPED_CHEST_CENTER_TRUNK = of("block/trapped_chest_center_trunk", CHEST_PREDICATE);
    public static final Identifier TRAPPED_CHEST_CENTER_LID = of("block/trapped_chest_center_lid", CHEST_PREDICATE);
    public static final Identifier TRAPPED_CHEST_LEFT = of("block/trapped_chest_left", CHEST_PREDICATE);
    public static final Identifier TRAPPED_CHEST_LEFT_TRUNK = of("block/trapped_chest_left_trunk", CHEST_PREDICATE);
    public static final Identifier TRAPPED_CHEST_LEFT_LID = of("block/trapped_chest_left_lid", CHEST_PREDICATE);
    public static final Identifier TRAPPED_CHEST_RIGHT = of("block/trapped_chest_right", CHEST_PREDICATE);
    public static final Identifier TRAPPED_CHEST_RIGHT_TRUNK = of("block/trapped_chest_right_trunk", CHEST_PREDICATE);
    public static final Identifier TRAPPED_CHEST_RIGHT_LID = of("block/trapped_chest_right_lid", CHEST_PREDICATE);

    public static final Identifier CHRISTMAS_CHEST_CENTER = of("block/christmas_chest_center", CHEST_PREDICATE);
    public static final Identifier CHRISTMAS_CHEST_CENTER_TRUNK = of("block/christmas_chest_center_trunk", CHEST_PREDICATE);
    public static final Identifier CHRISTMAS_CHEST_CENTER_LID = of("block/christmas_chest_center_lid", CHEST_PREDICATE);
    public static final Identifier CHRISTMAS_CHEST_LEFT = of("block/christmas_chest_left", CHEST_PREDICATE);
    public static final Identifier CHRISTMAS_CHEST_LEFT_TRUNK = of("block/christmas_chest_left_trunk", CHEST_PREDICATE);
    public static final Identifier CHRISTMAS_CHEST_LEFT_LID = of("block/christmas_chest_left_lid", CHEST_PREDICATE);
    public static final Identifier CHRISTMAS_CHEST_RIGHT = of("block/christmas_chest_right", CHEST_PREDICATE);
    public static final Identifier CHRISTMAS_CHEST_RIGHT_TRUNK = of("block/christmas_chest_right_trunk", CHEST_PREDICATE);
    public static final Identifier CHRISTMAS_CHEST_RIGHT_LID = of("block/christmas_chest_right_lid", CHEST_PREDICATE);

    public static final Identifier ENDER_CHEST_CENTER = of("block/ender_chest_center", CHEST_PREDICATE);
    public static final Identifier ENDER_CHEST_CENTER_TRUNK = of("block/ender_chest_center_trunk", CHEST_PREDICATE);
    public static final Identifier ENDER_CHEST_CENTER_LID = of("block/ender_chest_center_lid", CHEST_PREDICATE);

    public static final Identifier BELL_BETWEEN_WALLS = of("block/bell_between_walls", BELL_PREDICATE);
    public static final Identifier BELL_CEILING = of("block/bell_ceiling", BELL_PREDICATE);
    public static final Identifier BELL_FLOOR = of("block/bell_floor", BELL_PREDICATE);
    public static final Identifier BELL_WALL = of("block/bell_wall", BELL_PREDICATE);
    public static final Identifier BELL_BETWEEN_WALLS_WITH_BELL = of("block/bell_between_walls_with_bell", BELL_PREDICATE);
    public static final Identifier BELL_CEILING_WITH_BELL = of("block/bell_ceiling_with_bell", BELL_PREDICATE);
    public static final Identifier BELL_FLOOR_WITH_BELL = of("block/bell_floor_with_bell", BELL_PREDICATE);
    public static final Identifier BELL_WALL_WITH_BELL = of("block/bell_wall_with_bell", BELL_PREDICATE);
    public static final Identifier BELL_BODY = of("block/bell_body", BELL_PREDICATE);

    public static void init() {
    }

    private static Identifier of(String id, Predicate<EBEConfig> condition) {
        Identifier idf = new Identifier(id);
        ModelLoadingRegistry.INSTANCE.registerModelProvider((resourceManager, consumer) -> {
            if(condition.test(EnhancedBlockEntities.CONFIG)) consumer.accept(idf);
        });
        return idf;
    }
}

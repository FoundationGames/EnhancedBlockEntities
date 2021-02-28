package foundationgames.enhancedblockentities.client.model;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.util.Identifier;

public final class ModelIdentifiers {
    public static final Identifier CHEST_CENTER = of("block/chest_center");
    public static final Identifier CHEST_CENTER_TRUNK = of("block/chest_center_trunk");
    public static final Identifier CHEST_CENTER_LID = of("block/chest_center_lid");
    public static final Identifier CHEST_LEFT = of("block/chest_left");
    public static final Identifier CHEST_LEFT_TRUNK = of("block/chest_left_trunk");
    public static final Identifier CHEST_LEFT_LID = of("block/chest_left_lid");
    public static final Identifier CHEST_RIGHT = of("block/chest_right");
    public static final Identifier CHEST_RIGHT_TRUNK = of("block/chest_right_trunk");
    public static final Identifier CHEST_RIGHT_LID = of("block/chest_right_lid");

    public static final Identifier TRAPPED_CHEST_CENTER = of("block/trapped_chest_center");
    public static final Identifier TRAPPED_CHEST_CENTER_TRUNK = of("block/trapped_chest_center_trunk");
    public static final Identifier TRAPPED_CHEST_CENTER_LID = of("block/trapped_chest_center_lid");
    public static final Identifier TRAPPED_CHEST_LEFT = of("block/trapped_chest_left");
    public static final Identifier TRAPPED_CHEST_LEFT_TRUNK = of("block/trapped_chest_left_trunk");
    public static final Identifier TRAPPED_CHEST_LEFT_LID = of("block/trapped_chest_left_lid");
    public static final Identifier TRAPPED_CHEST_RIGHT = of("block/trapped_chest_right");
    public static final Identifier TRAPPED_CHEST_RIGHT_TRUNK = of("block/trapped_chest_right_trunk");
    public static final Identifier TRAPPED_CHEST_RIGHT_LID = of("block/trapped_chest_right_lid");

    public static final Identifier CHRISTMAS_CHEST_CENTER = of("block/christmas_chest_center");
    public static final Identifier CHRISTMAS_CHEST_CENTER_TRUNK = of("block/christmas_chest_center_trunk");
    public static final Identifier CHRISTMAS_CHEST_CENTER_LID = of("block/christmas_chest_center_lid");
    public static final Identifier CHRISTMAS_CHEST_LEFT = of("block/christmas_chest_left");
    public static final Identifier CHRISTMAS_CHEST_LEFT_TRUNK = of("block/christmas_chest_left_trunk");
    public static final Identifier CHRISTMAS_CHEST_LEFT_LID = of("block/christmas_chest_left_lid");
    public static final Identifier CHRISTMAS_CHEST_RIGHT = of("block/christmas_chest_right");
    public static final Identifier CHRISTMAS_CHEST_RIGHT_TRUNK = of("block/christmas_chest_right_trunk");
    public static final Identifier CHRISTMAS_CHEST_RIGHT_LID = of("block/christmas_chest_right_lid");

    public static final Identifier ENDER_CHEST_CENTER = of("block/ender_chest_center");
    public static final Identifier ENDER_CHEST_CENTER_TRUNK = of("block/ender_chest_center_trunk");
    public static final Identifier ENDER_CHEST_CENTER_LID = of("block/ender_chest_center_lid");

    public static void init() {
    }

    private static Identifier of(String id) {
        Identifier idf = new Identifier(id);
        ModelLoadingRegistry.INSTANCE.registerModelProvider((resourceManager, consumer) -> consumer.accept(idf));
        return idf;
    }
}

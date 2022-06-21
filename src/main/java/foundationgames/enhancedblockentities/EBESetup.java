package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.model.*;
import foundationgames.enhancedblockentities.client.render.BlockEntityRenderCondition;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.BellBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.ChestBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.ShulkerBoxBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.SignBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.DateUtil;
import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.duck.BakedModelManagerAccess;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.resource.ResourceType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public enum EBESetup {;
    public static void setupRRPChests() {
        RuntimeResourcePack p = ResourceUtil.getPack();

        ResourceUtil.addChestBlockStates("chest", p);
        ResourceUtil.addChestBlockStates("trapped_chest", p);
        ResourceUtil.addChestBlockStates("christmas_chest", p);
        ResourceUtil.addSingleChestOnlyBlockStates("ender_chest", p);

        ResourceUtil.addSingleChestModels("entity/chest/normal", "chest", p);
        ResourceUtil.addDoubleChestModels("entity/chest/normal_left", "entity/chest/normal_right","chest", p);
        ResourceUtil.addSingleChestModels("entity/chest/trapped", "trapped_chest", p);
        ResourceUtil.addDoubleChestModels("entity/chest/trapped_left", "entity/chest/trapped_right","trapped_chest", p);
        ResourceUtil.addSingleChestModels("entity/chest/christmas", "christmas_chest", p);
        ResourceUtil.addDoubleChestModels("entity/chest/christmas_left", "entity/chest/christmas_right","christmas_chest", p);
        ResourceUtil.addSingleChestModels("entity/chest/ender", "ender_chest", p);

        p.addResource(ResourceType.CLIENT_RESOURCES, new Identifier("models/item/chest.json"), ResourceUtil.CHEST_ITEM_MODEL_RESOURCE.getBytes());
        p.addModel(JModel.model("block/trapped_chest_center"), new Identifier("item/trapped_chest"));
        p.addModel(JModel.model("block/ender_chest_center"), new Identifier("item/ender_chest"));
    }

    public static void setupRRPSigns() {
        RuntimeResourcePack p = ResourceUtil.getPack();

        ResourceUtil.addSignBlockStates("oak_sign", "oak_wall_sign", p);
        ResourceUtil.addSignBlockStates("birch_sign", "birch_wall_sign", p);
        ResourceUtil.addSignBlockStates("spruce_sign", "spruce_wall_sign", p);
        ResourceUtil.addSignBlockStates("jungle_sign", "jungle_wall_sign", p);
        ResourceUtil.addSignBlockStates("acacia_sign", "acacia_wall_sign", p);
        ResourceUtil.addSignBlockStates("dark_oak_sign", "dark_oak_wall_sign", p);
        ResourceUtil.addSignBlockStates("mangrove_sign", "mangrove_wall_sign", p);
        ResourceUtil.addSignBlockStates("crimson_sign", "crimson_wall_sign", p);
        ResourceUtil.addSignBlockStates("warped_sign", "warped_wall_sign", p);

        ResourceUtil.addSignModels("entity/signs/oak", "oak_sign", "oak_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/birch", "birch_sign", "birch_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/spruce", "spruce_sign", "spruce_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/jungle", "jungle_sign", "jungle_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/acacia", "acacia_sign", "acacia_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/dark_oak", "dark_oak_sign", "dark_oak_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/mangrove", "mangrove_sign", "mangrove_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/crimson", "crimson_sign", "crimson_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/warped", "warped_sign", "warped_wall_sign", p);
    }

    public static void setupRRPBells() {
        ResourceUtil.addBellBlockState(ResourceUtil.getPack());
    }

    public static void setupRRPBeds() {
        RuntimeResourcePack p = ResourceUtil.getPack();

        for (DyeColor color : DyeColor.values()) {
            ResourceUtil.addBedBlockState(color, p);
            ResourceUtil.addBedModels(color, p);
        }
    }

    public static void setupRRPShulkerBoxes() {
        RuntimeResourcePack p = ResourceUtil.getPack();

        for (DyeColor color : EBEUtil.DEFAULTED_DYE_COLORS) {
            var id = color != null ? color.getName()+"_shulker_box" : "shulker_box";
            ResourceUtil.addShulkerBoxBlockStates(color, p);
            ResourceUtil.addShulkerBoxModels(color, p);
            p.addModel(JModel.model("block/"+id), new Identifier("item/"+id));
        }
    }

    public static void setupResourceProviders() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "chest_center"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.CHEST_CENTER,
                                ModelIdentifiers.CHEST_CENTER_TRUNK,
                                ModelIdentifiers.CHRISTMAS_CHEST_CENTER,
                                ModelIdentifiers.CHRISTMAS_CHEST_CENTER_TRUNK
                        },
                        ModelSelector.CHEST_WITH_CHRISTMAS,
                        DynamicModelEffects.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "chest_left"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.CHEST_LEFT,
                                ModelIdentifiers.CHEST_LEFT_TRUNK,
                                ModelIdentifiers.CHRISTMAS_CHEST_LEFT,
                                ModelIdentifiers.CHRISTMAS_CHEST_LEFT_TRUNK
                        },
                        ModelSelector.CHEST_WITH_CHRISTMAS,
                        DynamicModelEffects.CHEST
                )
        ));

        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "chest_right"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.CHEST_RIGHT,
                                ModelIdentifiers.CHEST_RIGHT_TRUNK,
                                ModelIdentifiers.CHRISTMAS_CHEST_RIGHT,
                                ModelIdentifiers.CHRISTMAS_CHEST_RIGHT_TRUNK
                        },
                        ModelSelector.CHEST_WITH_CHRISTMAS,
                        DynamicModelEffects.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "trapped_chest_center"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.TRAPPED_CHEST_CENTER,
                                ModelIdentifiers.TRAPPED_CHEST_CENTER_TRUNK
                        },
                        ModelSelector.CHEST,
                        DynamicModelEffects.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "trapped_chest_left"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.TRAPPED_CHEST_LEFT,
                                ModelIdentifiers.TRAPPED_CHEST_LEFT_TRUNK
                        },
                        ModelSelector.CHEST,
                        DynamicModelEffects.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "trapped_chest_right"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.TRAPPED_CHEST_RIGHT,
                                ModelIdentifiers.TRAPPED_CHEST_RIGHT_TRUNK
                        },
                        ModelSelector.CHEST,
                        DynamicModelEffects.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "ender_chest_center"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.ENDER_CHEST_CENTER,
                                ModelIdentifiers.ENDER_CHEST_CENTER_TRUNK
                        },
                        ModelSelector.CHEST,
                        DynamicModelEffects.CHEST
                )
        ));

        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "bell_between_walls"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.BELL_BETWEEN_WALLS_WITH_BELL,
                                ModelIdentifiers.BELL_BETWEEN_WALLS
                        },
                        ModelSelector.BELL,
                        DynamicModelEffects.BELL
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "bell_ceiling"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.BELL_CEILING_WITH_BELL,
                                ModelIdentifiers.BELL_CEILING
                        },
                        ModelSelector.BELL,
                        DynamicModelEffects.BELL
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "bell_floor"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.BELL_FLOOR_WITH_BELL,
                                ModelIdentifiers.BELL_FLOOR
                        },
                        ModelSelector.BELL,
                        DynamicModelEffects.BELL
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "bell_wall"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.BELL_WALL_WITH_BELL,
                                ModelIdentifiers.BELL_WALL
                        },
                        ModelSelector.BELL,
                        DynamicModelEffects.BELL
                )
        ));
        for (DyeColor color : EBEUtil.DEFAULTED_DYE_COLORS) {
            ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                    new Identifier("builtin", color != null ? color.getName()+"_shulker_box" : "shulker_box"),
                    () -> new DynamicUnbakedModel(
                            new Identifier[] {
                                    ModelIdentifiers.SHULKER_BOXES.get(color),
                                    ModelIdentifiers.SHULKER_BOX_BOTTOMS.get(color)
                            },
                            ModelSelector.SHULKER_BOX,
                            DynamicModelEffects.CHEST
                    )
            ));
        }
    }

    public static void setupChests() {
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.CHEST, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.TRAPPED_CHEST, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.ENDER_CHEST, RenderLayer.getCutoutMipped());
        EnhancedBlockEntityRegistry.register(Blocks.CHEST, BlockEntityType.CHEST, BlockEntityRenderCondition.CHEST,
                new ChestBlockEntityRendererOverride(() -> {
                    BakedModelManagerAccess manager = (BakedModelManagerAccess) MinecraftClient.getInstance().getBakedModelManager();
                    return new BakedModel[] {
                            manager.getModel(ModelIdentifiers.CHEST_CENTER_LID),
                            manager.getModel(ModelIdentifiers.CHEST_LEFT_LID),
                            manager.getModel(ModelIdentifiers.CHEST_RIGHT_LID),
                            manager.getModel(ModelIdentifiers.CHRISTMAS_CHEST_CENTER_LID),
                            manager.getModel(ModelIdentifiers.CHRISTMAS_CHEST_LEFT_LID),
                            manager.getModel(ModelIdentifiers.CHRISTMAS_CHEST_RIGHT_LID)
                    };
                }, entity -> {
                    int os = DateUtil.isChristmas() ? 3 : 0;
                    ChestType type = entity.getCachedState().get(Properties.CHEST_TYPE);
                    return type == ChestType.RIGHT ? 2 + os : type == ChestType.LEFT ? 1 + os : os;
                })
        );
        EnhancedBlockEntityRegistry.register(Blocks.TRAPPED_CHEST, BlockEntityType.TRAPPED_CHEST, BlockEntityRenderCondition.CHEST,
                new ChestBlockEntityRendererOverride(() -> {
                    BakedModelManagerAccess manager = (BakedModelManagerAccess)MinecraftClient.getInstance().getBakedModelManager();
                    return new BakedModel[] {
                            manager.getModel(ModelIdentifiers.TRAPPED_CHEST_CENTER_LID),
                            manager.getModel(ModelIdentifiers.TRAPPED_CHEST_LEFT_LID),
                            manager.getModel(ModelIdentifiers.TRAPPED_CHEST_RIGHT_LID)
                    };
                }, entity -> {
                    ChestType type = entity.getCachedState().get(Properties.CHEST_TYPE);
                    return type == ChestType.RIGHT ? 2 : type == ChestType.LEFT ? 1 : 0;
                })
        );
        EnhancedBlockEntityRegistry.register(Blocks.ENDER_CHEST, BlockEntityType.ENDER_CHEST, BlockEntityRenderCondition.CHEST,
                new ChestBlockEntityRendererOverride(() -> {
                    BakedModelManagerAccess manager = (BakedModelManagerAccess)MinecraftClient.getInstance().getBakedModelManager();
                    return new BakedModel[] { manager.getModel(ModelIdentifiers.ENDER_CHEST_CENTER_LID) };
                }, entity -> 0)
        );
    }

    public static void setupSigns() {
        EnhancedBlockEntityRegistry.register(Blocks.OAK_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.OAK_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.BIRCH_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.BIRCH_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.SPRUCE_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.SPRUCE_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.JUNGLE_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.JUNGLE_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.ACACIA_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.ACACIA_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.DARK_OAK_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.DARK_OAK_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.MANGROVE_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.MANGROVE_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.CRIMSON_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.CRIMSON_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.WARPED_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
        EnhancedBlockEntityRegistry.register(Blocks.WARPED_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                new SignBlockEntityRendererOverride()
        );
    }

    public static void setupBells() {
        EnhancedBlockEntityRegistry.register(Blocks.BELL, BlockEntityType.BELL, BlockEntityRenderCondition.BELL,
                new BellBlockEntityRendererOverride()
        );
    }

    public static void setupBeds() {
        EnhancedBlockEntityRegistry.register(Blocks.BLACK_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.BLUE_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.BROWN_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.CYAN_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.GRAY_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.GREEN_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.LIGHT_BLUE_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.LIGHT_GRAY_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.LIME_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.MAGENTA_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.ORANGE_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.PINK_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.PURPLE_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.RED_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.WHITE_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
        EnhancedBlockEntityRegistry.register(Blocks.YELLOW_BED, BlockEntityType.BED, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
    }

    public static void setupShulkerBoxes() {
        for (DyeColor color : EBEUtil.DEFAULTED_DYE_COLORS) {
            var block = ShulkerBoxBlock.get(color);
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
            EnhancedBlockEntityRegistry.register(block, BlockEntityType.SHULKER_BOX, BlockEntityRenderCondition.SHULKER_BOX,
                    new ShulkerBoxBlockEntityRendererOverride((map) -> {
                        var models = (BakedModelManagerAccess) MinecraftClient.getInstance().getBakedModelManager();
                        for (DyeColor dc : EBEUtil.DEFAULTED_DYE_COLORS) {
                            map.put(dc, models.getModel(ModelIdentifiers.SHULKER_BOX_LIDS.get(dc)));
                        }
                    })
            );
        }
    }
}

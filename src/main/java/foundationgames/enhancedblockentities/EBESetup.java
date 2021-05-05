package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.model.DynamicModelProvider;
import foundationgames.enhancedblockentities.client.model.DynamicUnbakedModel;
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.model.ModelSelector;
import foundationgames.enhancedblockentities.client.render.BlockEntityRenderCondition;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.duck.BakedModelManagerAccess;
import foundationgames.enhancedblockentities.util.DateUtil;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
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

        p.addModel(JModel.model("item/chest_model"), new Identifier("item/chest"));
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
        ResourceUtil.addSignBlockStates("crimson_sign", "crimson_wall_sign", p);
        ResourceUtil.addSignBlockStates("warped_sign", "warped_wall_sign", p);

        ResourceUtil.addSignModels("entity/signs/oak", "oak_sign", "oak_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/birch", "birch_sign", "birch_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/spruce", "spruce_sign", "spruce_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/jungle", "jungle_sign", "jungle_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/acacia", "acacia_sign", "acacia_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/dark_oak", "dark_oak_sign", "dark_oak_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/crimson", "crimson_sign", "crimson_wall_sign", p);
        ResourceUtil.addSignModels("entity/signs/warped", "warped_sign", "warped_wall_sign", p);
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
                        ModelSelector.CHEST_WITH_CHRISTMAS
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
                        ModelSelector.CHEST_WITH_CHRISTMAS
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
                        ModelSelector.CHEST_WITH_CHRISTMAS
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "trapped_chest_center"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.TRAPPED_CHEST_CENTER,
                                ModelIdentifiers.TRAPPED_CHEST_CENTER_TRUNK
                        },
                        ModelSelector.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "trapped_chest_left"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.TRAPPED_CHEST_LEFT,
                                ModelIdentifiers.TRAPPED_CHEST_LEFT_TRUNK
                        },
                        ModelSelector.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "trapped_chest_right"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.TRAPPED_CHEST_RIGHT,
                                ModelIdentifiers.TRAPPED_CHEST_RIGHT_TRUNK
                        },
                        ModelSelector.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "ender_chest_center"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.ENDER_CHEST_CENTER,
                                ModelIdentifiers.ENDER_CHEST_CENTER_TRUNK
                        },
                        ModelSelector.CHEST
                )
        ));
    }

    public static void setupChests() {
        EnhancedBlockEntityRegistry.register(Blocks.CHEST, BlockEntityType.CHEST, BlockEntityRenderCondition.CHEST,
                BlockEntityRendererOverride.chest(() -> {
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
                BlockEntityRendererOverride.chest(() -> {
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
                BlockEntityRendererOverride.chest(() -> {
                    BakedModelManagerAccess manager = (BakedModelManagerAccess)MinecraftClient.getInstance().getBakedModelManager();
                    return new BakedModel[] { manager.getModel(ModelIdentifiers.ENDER_CHEST_CENTER_LID) };
                }, entity -> 0)
        );
        EnhancedBlockEntityRegistry.register(Blocks.ENDER_CHEST, BlockEntityType.ENDER_CHEST, BlockEntityRenderCondition.CHEST,
                BlockEntityRendererOverride.chest(() -> {
                    BakedModelManagerAccess manager = (BakedModelManagerAccess)MinecraftClient.getInstance().getBakedModelManager();
                    return new BakedModel[] { manager.getModel(ModelIdentifiers.ENDER_CHEST_CENTER_LID) };
                }, entity -> 0)
        );
    }

    public static void setupSigns() {
        EnhancedBlockEntityRegistry.register(Blocks.OAK_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.OAK_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.BIRCH_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.BIRCH_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.SPRUCE_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.SPRUCE_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.JUNGLE_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.JUNGLE_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.ACACIA_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.ACACIA_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.DARK_OAK_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.DARK_OAK_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.CRIMSON_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.CRIMSON_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.WARPED_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
        EnhancedBlockEntityRegistry.register(Blocks.WARPED_WALL_SIGN, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                BlockEntityRendererOverride.sign()
        );
    }
}

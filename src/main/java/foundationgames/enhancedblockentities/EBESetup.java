package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.model.DynamicModelEffects;
import foundationgames.enhancedblockentities.client.model.DynamicModelProvider;
import foundationgames.enhancedblockentities.client.model.DynamicUnbakedModel;
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.model.ModelSelector;
import foundationgames.enhancedblockentities.client.model.misc.DecoratedPotModelSelector;
import foundationgames.enhancedblockentities.client.render.BlockEntityRenderCondition;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.BellBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.ChestBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.ShulkerBoxBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.render.entity.SignBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.client.resource.EBEPack;
import foundationgames.enhancedblockentities.util.DateUtil;
import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.duck.BakedModelManagerAccess;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.ResourceType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public enum EBESetup {;
    public static void setupRRPChests() {
        EBEPack p = ResourceUtil.getPackForCompat();

        ResourceUtil.addChestBlockStates("chest", p);
        ResourceUtil.addChestBlockStates("trapped_chest", p);
        ResourceUtil.addChestBlockStates("christmas_chest", p);
        ResourceUtil.addSingleChestOnlyBlockStates("ender_chest", p);

        p = ResourceUtil.getBasePack();

        ResourceUtil.addSingleChestModels("entity/chest/normal", "chest", p);
        ResourceUtil.addDoubleChestModels("entity/chest/normal_left", "entity/chest/normal_right","chest", p);
        ResourceUtil.addSingleChestModels("entity/chest/trapped", "trapped_chest", p);
        ResourceUtil.addDoubleChestModels("entity/chest/trapped_left", "entity/chest/trapped_right","trapped_chest", p);
        ResourceUtil.addSingleChestModels("entity/chest/christmas", "christmas_chest", p);
        ResourceUtil.addDoubleChestModels("entity/chest/christmas_left", "entity/chest/christmas_right","christmas_chest", p);
        ResourceUtil.addSingleChestModels("entity/chest/ender", "ender_chest", p);

        p.addResource(ResourceType.CLIENT_RESOURCES, new Identifier("models/item/chest.json"),
                ResourceUtil.createChestItemModelResource("chest_center").getBytes());
        p.addResource(ResourceType.CLIENT_RESOURCES, new Identifier("models/item/trapped_chest.json"),
                ResourceUtil.createChestItemModelResource("trapped_chest_center").getBytes());
        p.addModel(JModel.model("block/ender_chest_center"), new Identifier("item/ender_chest"));

        p.addDirBlockSprites("entity/chest", "entity/chest/");
    }

    public static void setupRRPSigns() {
        EBEPack p = ResourceUtil.getPackForCompat();

        ResourceUtil.addSignBlockStates("oak_sign", "oak_wall_sign", p);
        ResourceUtil.addSignBlockStates("birch_sign", "birch_wall_sign", p);
        ResourceUtil.addSignBlockStates("spruce_sign", "spruce_wall_sign", p);
        ResourceUtil.addSignBlockStates("jungle_sign", "jungle_wall_sign", p);
        ResourceUtil.addSignBlockStates("acacia_sign", "acacia_wall_sign", p);
        ResourceUtil.addSignBlockStates("dark_oak_sign", "dark_oak_wall_sign", p);
        ResourceUtil.addSignBlockStates("mangrove_sign", "mangrove_wall_sign", p);
        ResourceUtil.addSignBlockStates("cherry_sign", "cherry_wall_sign", p);
        ResourceUtil.addSignBlockStates("crimson_sign", "crimson_wall_sign", p);
        ResourceUtil.addSignBlockStates("warped_sign", "warped_wall_sign", p);
        ResourceUtil.addSignBlockStates("bamboo_sign", "bamboo_wall_sign", p);

        ResourceUtil.addHangingSignBlockStates("oak_hanging_sign", "oak_wall_hanging_sign", p);
        ResourceUtil.addHangingSignBlockStates("birch_hanging_sign", "birch_wall_hanging_sign", p);
        ResourceUtil.addHangingSignBlockStates("spruce_hanging_sign", "spruce_wall_hanging_sign", p);
        ResourceUtil.addHangingSignBlockStates("jungle_hanging_sign", "jungle_wall_hanging_sign", p);
        ResourceUtil.addHangingSignBlockStates("acacia_hanging_sign", "acacia_wall_hanging_sign", p);
        ResourceUtil.addHangingSignBlockStates("dark_oak_hanging_sign", "dark_oak_wall_hanging_sign", p);
        ResourceUtil.addHangingSignBlockStates("mangrove_hanging_sign", "mangrove_wall_hanging_sign", p);
        ResourceUtil.addHangingSignBlockStates("cherry_hanging_sign", "cherry_wall_hanging_sign", p);
        ResourceUtil.addHangingSignBlockStates("crimson_hanging_sign", "crimson_wall_hanging_sign", p);
        ResourceUtil.addHangingSignBlockStates("warped_hanging_sign", "warped_wall_hanging_sign", p);
        ResourceUtil.addHangingSignBlockStates("bamboo_hanging_sign", "bamboo_wall_hanging_sign", p);

        p = ResourceUtil.getBasePack();

        ResourceUtil.addSignTypeModels("oak", p);
        ResourceUtil.addSignTypeModels("birch", p);
        ResourceUtil.addSignTypeModels("spruce", p);
        ResourceUtil.addSignTypeModels("jungle", p);
        ResourceUtil.addSignTypeModels("acacia", p);
        ResourceUtil.addSignTypeModels("dark_oak", p);
        ResourceUtil.addSignTypeModels("mangrove", p);
        ResourceUtil.addSignTypeModels("cherry", p);
        ResourceUtil.addSignTypeModels("crimson", p);
        ResourceUtil.addSignTypeModels("warped", p);
        ResourceUtil.addSignTypeModels("bamboo", p);

        p.addDirBlockSprites("entity/signs", "entity/signs/");
        p.addDirBlockSprites("entity/signs/hanging", "entity/signs/hanging/");
        p.addDirBlockSprites("gui/hanging_signs", "block/particle_hanging_sign_");
    }

    public static void setupRRPBells() {
        ResourceUtil.addBellBlockState(ResourceUtil.getPackForCompat());

        ResourceUtil.getBasePack().addSingleBlockSprite(new Identifier("entity/bell/bell_body"));
    }

    public static void setupRRPBeds() {
        EBEPack p = ResourceUtil.getBasePack();
        EBEPack pCompat = ResourceUtil.getPackForCompat();

        for (DyeColor color : DyeColor.values()) {
            ResourceUtil.addBedBlockState(color, pCompat);
            ResourceUtil.addBedModels(color, p);
        }

        p.addDirBlockSprites("entity/bed", "entity/bed/");
    }

    public static void setupRRPShulkerBoxes() {
        EBEPack p = ResourceUtil.getBasePack();
        EBEPack pCompat = ResourceUtil.getPackForCompat();

        for (DyeColor color : EBEUtil.DEFAULTED_DYE_COLORS) {
            var id = color != null ? color.getName()+"_shulker_box" : "shulker_box";
            ResourceUtil.addShulkerBoxBlockStates(color, pCompat);
            ResourceUtil.addShulkerBoxModels(color, p);
            p.addModel(JModel.model("block/"+id), new Identifier("item/"+id));
        }

        p.addDirBlockSprites("entity/shulker", "entity/shulker/");
    }

    public static void setupRRPDecoratedPots() {
        EBEPack p = ResourceUtil.getBasePack();
        EBEPack pCompat = ResourceUtil.getPackForCompat();

        ResourceUtil.addDecoratedPotBlockState(pCompat);
        for (RegistryKey<String> patternKey : Registries.DECORATED_POT_PATTERN.getKeys()) {
            ResourceUtil.addDecoratedPotPatternModels(patternKey, p);
        }

        p.addDirBlockSprites("entity/decorated_pot", "entity/decorated_pot/");
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
                                ModelIdentifiers.TRAPPED_CHEST_CENTER_TRUNK,
                                ModelIdentifiers.CHRISTMAS_CHEST_CENTER,
                                ModelIdentifiers.CHRISTMAS_CHEST_CENTER_TRUNK
                        },
                        ModelSelector.CHEST_WITH_CHRISTMAS,
                        DynamicModelEffects.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "trapped_chest_left"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.TRAPPED_CHEST_LEFT,
                                ModelIdentifiers.TRAPPED_CHEST_LEFT_TRUNK,
                                ModelIdentifiers.CHRISTMAS_CHEST_LEFT,
                                ModelIdentifiers.CHRISTMAS_CHEST_LEFT_TRUNK
                        },
                        ModelSelector.CHEST_WITH_CHRISTMAS,
                        DynamicModelEffects.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "trapped_chest_right"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.TRAPPED_CHEST_RIGHT,
                                ModelIdentifiers.TRAPPED_CHEST_RIGHT_TRUNK,
                                ModelIdentifiers.CHRISTMAS_CHEST_RIGHT,
                                ModelIdentifiers.CHRISTMAS_CHEST_RIGHT_TRUNK
                        },
                        ModelSelector.CHEST_WITH_CHRISTMAS,
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
                            DynamicModelEffects.SHULKER_BOX
                    )
            ));
        }

        DecoratedPotModelSelector decoratedPotSelector = new DecoratedPotModelSelector();
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "decorated_pot"),
                () -> new DynamicUnbakedModel(
                        decoratedPotSelector.createModelIDs(),
                        decoratedPotSelector,
                        DynamicModelEffects.DECORATED_POT
                )
        ));
    }

    public static void setupChests() {
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.CHEST, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.TRAPPED_CHEST, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.ENDER_CHEST, RenderLayer.getCutoutMipped());

        Function<BlockEntity, Integer> christmasChestSelector = entity -> {
            int os = DateUtil.isChristmas() ? 3 : 0;
            ChestType type = entity.getCachedState().get(Properties.CHEST_TYPE);
            return type == ChestType.RIGHT ? 2 + os : type == ChestType.LEFT ? 1 + os : os;
        };
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
                }, christmasChestSelector)
        );
        EnhancedBlockEntityRegistry.register(Blocks.TRAPPED_CHEST, BlockEntityType.TRAPPED_CHEST, BlockEntityRenderCondition.CHEST,
                new ChestBlockEntityRendererOverride(() -> {
                    BakedModelManagerAccess manager = (BakedModelManagerAccess)MinecraftClient.getInstance().getBakedModelManager();
                    return new BakedModel[] {
                            manager.getModel(ModelIdentifiers.TRAPPED_CHEST_CENTER_LID),
                            manager.getModel(ModelIdentifiers.TRAPPED_CHEST_LEFT_LID),
                            manager.getModel(ModelIdentifiers.TRAPPED_CHEST_RIGHT_LID),
                            manager.getModel(ModelIdentifiers.CHRISTMAS_CHEST_CENTER_LID),
                            manager.getModel(ModelIdentifiers.CHRISTMAS_CHEST_LEFT_LID),
                            manager.getModel(ModelIdentifiers.CHRISTMAS_CHEST_RIGHT_LID)
                    };
                }, christmasChestSelector)
        );
        EnhancedBlockEntityRegistry.register(Blocks.ENDER_CHEST, BlockEntityType.ENDER_CHEST, BlockEntityRenderCondition.CHEST,
                new ChestBlockEntityRendererOverride(() -> {
                    BakedModelManagerAccess manager = (BakedModelManagerAccess)MinecraftClient.getInstance().getBakedModelManager();
                    return new BakedModel[] { manager.getModel(ModelIdentifiers.ENDER_CHEST_CENTER_LID) };
                }, entity -> 0)
        );
    }

    public static void setupSigns() {
        for (var sign : new Block[] {
                Blocks.OAK_SIGN, Blocks.OAK_WALL_SIGN,
                Blocks.BIRCH_SIGN, Blocks.BIRCH_WALL_SIGN,
                Blocks.SPRUCE_SIGN, Blocks.SPRUCE_WALL_SIGN,
                Blocks.JUNGLE_SIGN, Blocks.JUNGLE_WALL_SIGN,
                Blocks.ACACIA_SIGN, Blocks.ACACIA_WALL_SIGN,
                Blocks.DARK_OAK_SIGN, Blocks.DARK_OAK_WALL_SIGN,
                Blocks.MANGROVE_SIGN, Blocks.MANGROVE_WALL_SIGN,
                Blocks.CHERRY_SIGN, Blocks.CHERRY_WALL_SIGN,
                Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN,
                Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN,
                Blocks.BAMBOO_SIGN, Blocks.BAMBOO_WALL_SIGN
        }) {
            EnhancedBlockEntityRegistry.register(sign, BlockEntityType.SIGN, BlockEntityRenderCondition.SIGN,
                    new SignBlockEntityRendererOverride()
            );
        }

        for (var sign : new Block[] {
                Blocks.OAK_HANGING_SIGN, Blocks.OAK_WALL_HANGING_SIGN,
                Blocks.BIRCH_HANGING_SIGN, Blocks.BIRCH_WALL_HANGING_SIGN,
                Blocks.SPRUCE_HANGING_SIGN, Blocks.SPRUCE_WALL_HANGING_SIGN,
                Blocks.JUNGLE_HANGING_SIGN, Blocks.JUNGLE_WALL_HANGING_SIGN,
                Blocks.ACACIA_HANGING_SIGN, Blocks.ACACIA_WALL_HANGING_SIGN,
                Blocks.DARK_OAK_HANGING_SIGN, Blocks.DARK_OAK_WALL_HANGING_SIGN,
                Blocks.MANGROVE_HANGING_SIGN, Blocks.MANGROVE_WALL_HANGING_SIGN,
                Blocks.CHERRY_HANGING_SIGN, Blocks.CHERRY_WALL_HANGING_SIGN,
                Blocks.CRIMSON_HANGING_SIGN, Blocks.CRIMSON_WALL_HANGING_SIGN,
                Blocks.WARPED_HANGING_SIGN, Blocks.WARPED_WALL_HANGING_SIGN,
                Blocks.BAMBOO_HANGING_SIGN, Blocks.BAMBOO_WALL_HANGING_SIGN
        }) {
            EnhancedBlockEntityRegistry.register(sign, BlockEntityType.HANGING_SIGN, BlockEntityRenderCondition.SIGN,
                    new SignBlockEntityRendererOverride()
            );
            BlockRenderLayerMap.INSTANCE.putBlock(sign, RenderLayer.getCutout());
        }
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

    public static void setupDecoratedPots() {
        EnhancedBlockEntityRegistry.register(Blocks.DECORATED_POT, BlockEntityType.DECORATED_POT, BlockEntityRenderCondition.NEVER, BlockEntityRendererOverride.NO_OP);
    }
}

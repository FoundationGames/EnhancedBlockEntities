package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.model.DynamicModelProvider;
import foundationgames.enhancedblockentities.client.model.DynamicUnbakedModel;
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.model.ModelSelector;
import foundationgames.enhancedblockentities.client.render.BlockEntityRenderCondition;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.BakedModelManagerAccess;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class EnhancedBlockEntities implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelIdentifiers.init();

        EnhancedBlockEntityRegistry.register(Blocks.CHEST, BlockEntityType.CHEST, BlockEntityRenderCondition.CHEST,
                BlockEntityRendererOverride.chest(() -> {
                    BakedModelManagerAccess manager = (BakedModelManagerAccess)MinecraftClient.getInstance().getBakedModelManager();
                    return new BakedModel[] {
                            manager.getModel(ModelIdentifiers.CHEST_CENTER_LID),
                            manager.getModel(ModelIdentifiers.CHEST_LEFT_LID),
                            manager.getModel(ModelIdentifiers.CHEST_RIGHT_LID)
                    };
                }, entity -> {
                    ChestType type = entity.getCachedState().get(Properties.CHEST_TYPE);
                    return type == ChestType.RIGHT ? 2 : type == ChestType.LEFT ? 1 : 0;
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

        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "chest_center"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.CHEST_CENTER,
                                ModelIdentifiers.CHEST_CENTER_TRUNK
                        },
                        ModelSelector.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "chest_left"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.CHEST_LEFT,
                                ModelIdentifiers.CHEST_LEFT_TRUNK
                        },
                        ModelSelector.CHEST
                )
        ));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider(
                new Identifier("builtin", "chest_right"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.CHEST_RIGHT,
                                ModelIdentifiers.CHEST_RIGHT_TRUNK
                        },
                        ModelSelector.CHEST
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
}

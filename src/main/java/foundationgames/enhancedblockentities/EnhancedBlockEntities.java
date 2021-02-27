package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.model.DynamicModelProvider;
import foundationgames.enhancedblockentities.client.model.DynamicUnbakedModel;
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.model.ModelSelector;
import foundationgames.enhancedblockentities.client.render.BlockEntityRenderCondition;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

public class EnhancedBlockEntities implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelIdentifiers.init();

        EnhancedBlockEntityRegistry.register(Blocks.CHEST, BlockEntityRenderCondition.CHEST, null);
        EnhancedBlockEntityRegistry.register(Blocks.TRAPPED_CHEST, BlockEntityRenderCondition.CHEST, null);
        EnhancedBlockEntityRegistry.register(Blocks.ENDER_CHEST, BlockEntityRenderCondition.CHEST, null);

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

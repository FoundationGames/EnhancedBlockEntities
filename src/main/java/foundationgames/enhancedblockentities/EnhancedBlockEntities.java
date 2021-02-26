package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.model.DynamicModelProvider;
import foundationgames.enhancedblockentities.client.model.DynamicUnbakedModel;
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.model.ModelSelector;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class EnhancedBlockEntities implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DynamicModelProvider.MODELS.put(
                new Identifier("builtin", "chest_center"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.CHEST_CENTER,
                                ModelIdentifiers.CHEST_CENTER_TRUNK
                        },
                        ModelSelector.CHEST
                )
        );
        DynamicModelProvider.MODELS.put(
                new Identifier("builtin", "chest_left"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.CHEST_LEFT,
                                ModelIdentifiers.CHEST_LEFT_TRUNK
                        },
                        ModelSelector.CHEST
                )
        );
        DynamicModelProvider.MODELS.put(
                new Identifier("builtin", "chest_right"),
                () -> new DynamicUnbakedModel(
                        new Identifier[] {
                                ModelIdentifiers.CHEST_RIGHT,
                                ModelIdentifiers.CHEST_RIGHT_TRUNK
                        },
                        ModelSelector.CHEST
                )
        );
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvider());
    }
    //MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, getCachedState(), state, 1);
}

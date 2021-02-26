package foundationgames.enhancedblockentities.client.modeldep;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class SwappableBlockUnbakedModel implements UnbakedModel {
    private final Block block;
    private final ModelBakeSettings settings;

    public SwappableBlockUnbakedModel(Block block, ModelBakeSettings settings) {
        this.block = block;
        this.settings = settings;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptyList();
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Collections.emptyList();
    }

    @Override
    public @Nullable BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        SwappableModelRegistry.Entry<?> entry = SwappableModelRegistry.ENTRIES.get(block);
        return new SwappableBlockBakedModel(entry.models.apply(MinecraftClient.getInstance().getBakedModelManager()), entry.model, entry.transformation);
    }
}

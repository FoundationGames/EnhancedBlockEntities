package foundationgames.enhancedblockentities.client.model.misc;

import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.model.ModelSelector;
import foundationgames.enhancedblockentities.util.duck.AppearanceStateHolder;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.minecraft.block.BlockState;
import net.minecraft.block.DecoratedPotPattern;
import net.minecraft.block.DecoratedPotPatterns;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class DecoratedPotModelSelector extends ModelSelector {
    public static final int BUILTIN_MODEL_COUNT = 2;

    public static final int IDX_EMPTY = 0;
    public static final int IDX_BASE_POT = 1;

    private final List<RegistryKey<DecoratedPotPattern>> potteryPatterns;

    public DecoratedPotModelSelector() {
        super(5);

        this.potteryPatterns = new ArrayList<>(Registries.DECORATED_POT_PATTERN.getKeys());
    }

    public ExtraModelKey<BlockStateModel>[] createModelIDs() {
        ModelIdentifiers.refreshPotteryPatterns();

        var ids = new ExtraModelKey[BUILTIN_MODEL_COUNT + potteryPatterns.size() * 4];
        ids[IDX_EMPTY] = ModelIdentifiers.DECORATED_POT_SHAKING_KEY;
        ids[IDX_BASE_POT] = ModelIdentifiers.DECORATED_POT_BASE_KEY;

        int idIndex = BUILTIN_MODEL_COUNT;
        for (int dirIndex = 0; dirIndex < 4; dirIndex++) {
            for (var pattern : this.potteryPatterns) {
                ids[idIndex] = ModelIdentifiers.POTTERY_PATTERNS.get(pattern)[dirIndex];

                idIndex++;
            }
        }

        return ids;
    }

    @Override
    public void writeModelIndices(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rand, int[] indices) {
        final int patternCount = potteryPatterns.size();

        indices[0] = IDX_BASE_POT;
        if (view.getBlockEntity(pos) instanceof DecoratedPotBlockEntity pot) {
            if (pot instanceof AppearanceStateHolder ms && ms.getModelState() > 0) {
                Arrays.fill(indices, IDX_EMPTY);
                return;
            }

            var sherds = pot.getSherds();

            indices[1] = BUILTIN_MODEL_COUNT + getPatternIndex(sherds.back(), patternCount);
            indices[2] = BUILTIN_MODEL_COUNT + getPatternIndex(sherds.left(), patternCount) + patternCount;
            indices[3] = BUILTIN_MODEL_COUNT + getPatternIndex(sherds.right(), patternCount) + patternCount * 2;
            indices[4] = BUILTIN_MODEL_COUNT + getPatternIndex(sherds.front(), patternCount) + patternCount * 3;

            return;
        }

        for (int i = 0; i < 4; i++) {
            indices[1 + i] = BUILTIN_MODEL_COUNT + patternCount * i;
        }
    }

    private int getPatternIndex(Optional<Item> sherd, int max) {
        return MathHelper.clamp(this.potteryPatterns.indexOf(sherd.map(DecoratedPotPatterns::fromSherd).orElse(DecoratedPotPatterns.BLANK)), 0, max - 1);
    }
}

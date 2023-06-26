package foundationgames.enhancedblockentities.client.model.misc;

import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.model.ModelSelector;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.DecoratedPotPatterns;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DecoratedPotModelSelector extends ModelSelector {
    private final List<RegistryKey<String>> potteryPatterns;

    public DecoratedPotModelSelector() {
        super(5);

        this.potteryPatterns = new ArrayList<>(Registries.DECORATED_POT_PATTERN.getKeys());
    }

    public Identifier[] createModelIDs() {
        ModelIdentifiers.refreshPotteryPatterns();

        var ids = new Identifier[1 + potteryPatterns.size() * 4];
        ids[0] = ModelIdentifiers.DECORATED_POT_BASE;

        int idIndex = 1;
        for (int dirIndex = 0; dirIndex < 4; dirIndex++) {
            for (var pattern : this.potteryPatterns) {
                ids[idIndex] = ModelIdentifiers.POTTERY_PATTERNS.get(pattern)[dirIndex];

                idIndex++;
            }
        }

        return ids;
    }

    @Override
    public void writeModelIndices(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rand, @Nullable RenderContext ctx, int[] indices) {
        final int patternCount = potteryPatterns.size();

        indices[0] = 0;
        if (view.getBlockEntity(pos) instanceof DecoratedPotBlockEntity pot) {
            var sherds = pot.getSherds();

            indices[1] = 1 + getPatternIndex(sherds.back(), patternCount);
            indices[2] = 1 + getPatternIndex(sherds.left(), patternCount) + patternCount;
            indices[3] = 1 + getPatternIndex(sherds.right(), patternCount) + patternCount * 2;
            indices[4] = 1 + getPatternIndex(sherds.front(), patternCount) + patternCount * 3;

            return;
        }

        for (int i = 0; i < 4; i++) {
            indices[1 + i] = 1 + patternCount * i;
        }
    }

    private int getPatternIndex(Item sherd, int max) {
        return MathHelper.clamp(this.potteryPatterns.indexOf(DecoratedPotPatterns.fromSherd(sherd)), 0, max - 1);
    }
}

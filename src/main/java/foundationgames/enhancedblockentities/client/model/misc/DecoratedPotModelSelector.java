package foundationgames.enhancedblockentities.client.model.misc;

import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.model.ModelSelector;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.DecoratedPotPatterns;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
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

        this.potteryPatterns = new ArrayList<>(Registries.DECORATED_POT_PATTERNS.getKeys());
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
        if (view.getBlockEntity(pos) instanceof DecoratedPotBlockEntity pot && pot.getShards().size() == 4) {
            for (int i = 0; i < 4; i++) {
                var pattern = DecoratedPotPatterns.fromShard(pot.getShards().get(i));
                int patternIndex = MathHelper.clamp(this.potteryPatterns.indexOf(pattern), 0, patternCount - 1);

                indices[1 + i] = 1 + patternIndex + patternCount * i;
            }

            return;
        }

        for (int i = 0; i < 4; i++) {
            indices[1 + i] = 1 + patternCount * i;
        }
    }
}

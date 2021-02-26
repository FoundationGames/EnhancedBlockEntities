package foundationgames.enhancedblockentities.client.modeldep;

import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class SwappableModelRegistry {
    public static final Map<Block, Entry<?>> ENTRIES = new HashMap<>();

    public static void register(Block block, Entry<?> entry) {

    }

    public static class Entry<T extends BlockEntity> {
        public final ModelSupplier model;
        public final Function<BakedModelManager, BakedModel[]> models;
        public final TransformationSupplier transformation;
        public final Predicate<T> blockEntityRenderPredicate;

        public Entry(ModelSupplier model, Function<BakedModelManager, BakedModel[]> models, TransformationSupplier transformation, Predicate<T> blockEntityRenderPredicate) {
            this.model = model;
            this.models = models;
            this.transformation = transformation;
            this.blockEntityRenderPredicate = blockEntityRenderPredicate;
        }
    }

    public static void registerDefaults() {
        ENTRIES.put(Blocks.CHEST, new Entry<ChestBlockEntity>(
                (view, pos, state) -> {
                    if(view.getBlockEntity(pos) instanceof ChestBlockEntity) {
                        ChestBlockEntity be = (ChestBlockEntity)view.getBlockEntity(pos);
                        if(be.getAnimationProgress(1) > 0) {
                            return 1;
                        }
                    }
                    return 0;
                },
                models -> new BakedModel[] {

                },
                state -> { return null; },
                entity -> entity.getAnimationProgress(0) > 0
        ));
    }
}

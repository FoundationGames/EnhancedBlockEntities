package foundationgames.enhancedblockentities.client.model.item;

import com.mojang.serialization.MapCodec;
import foundationgames.enhancedblockentities.util.DateUtil;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record EBEIsChristmasProperty() implements BooleanProperty {
    public static final MapCodec<EBEIsChristmasProperty> CODEC = MapCodec.unit(new EBEIsChristmasProperty());

    @Override
    public MapCodec<EBEIsChristmasProperty> getCodec() {
        return CODEC;
    }

    @Override
    public boolean test(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        return DateUtil.isChristmas();
    }
}
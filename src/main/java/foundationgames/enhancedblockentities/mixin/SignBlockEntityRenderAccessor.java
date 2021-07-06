package foundationgames.enhancedblockentities.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SignBlockEntityRenderer.class)
public interface SignBlockEntityRenderAccessor {
    @Invoker("getColor")
    static int getColor(SignBlockEntity sign) {
        throw new AssertionError("Mixin has severely broken, seek help");
    }

    @Accessor("RENDER_DISTANCE")
    static int getRenderDistance() {
        throw new AssertionError("Mixin has severely broken, seek help");
    }
}

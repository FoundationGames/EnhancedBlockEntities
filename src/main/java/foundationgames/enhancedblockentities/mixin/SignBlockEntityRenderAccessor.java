package foundationgames.enhancedblockentities.mixin;

import net.minecraft.block.entity.SignText;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SignBlockEntityRenderer.class)
public interface SignBlockEntityRenderAccessor {
    @Invoker("renderText")
    void enhanced_bes$renderText(BlockPos pos, SignText signText, MatrixStack matrices, VertexConsumerProvider verticesProvider, int light, int lineHeight, int lineWidth, boolean front);
    // void enhanced_bes$renderText(SignBlockEntity blockEntity, MatrixStack matrices, VertexConsumerProvider verticesProvider, int light, float scale);

    @Accessor("RENDER_DISTANCE")
    static int enhanced_bes$getRenderDistance() {
        throw new AssertionError("Mixin has severely broken, seek help");
    }
}

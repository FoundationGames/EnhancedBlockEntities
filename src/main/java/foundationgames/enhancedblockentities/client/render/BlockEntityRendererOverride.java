package foundationgames.enhancedblockentities.client.render;

import foundationgames.enhancedblockentities.event.EBEEvents;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;

import java.util.Random;

public abstract class BlockEntityRendererOverride {
    public static final BlockEntityRendererOverride NO_OP = new BlockEntityRendererOverride() {
        @Override
        public void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {}
    };

    private static final Random dummy = new Random();

    public BlockEntityRendererOverride() {
        EBEEvents.RELOAD_MODELS.register((loader, manager, profiler) -> this.onModelsReload());
    }

    public abstract void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay);

    public void onModelsReload() {}

    public static void renderBakedModel(VertexConsumer vertices, MatrixStack matrices, BakedModel model, int light, int overlay, Direction lightDirection) {
        float l;
        for (int i = 0; i <= 6; i++) {
            for(BakedQuad q : model.getQuads(null, ModelHelper.faceFromIndex(i), dummy)) {
                l = getLight(q.getFace(), lightDirection);
                vertices.quad(matrices.peek(), q, l, l, l, light, overlay);
            }
        }
    }

    private static float getLight(Direction face, Direction facing) {
        switch(rotate(face, facing)) {
            case NORTH:
            case SOUTH:
                return 0.75f;
            case EAST:
            case WEST:
                return 0.6f;
            case DOWN:
                return 0.5f;
            default:
                return 1.0f;
        }
    }

    private static Direction rotate(Direction face, Direction facing) {
        if(face == Direction.UP || face == Direction.DOWN || facing == Direction.UP || facing == Direction.DOWN) return face;
        int rotations = 0;
        switch(facing) {
            case EAST:
                rotations = 1;
                break;
            case SOUTH:
                rotations = 2;
                break;
            case WEST:
                rotations = 3;
                break;
        }
        Direction r = face;
        for (int i = 0; i < rotations; i++) {
            r = r.rotateYCounterclockwise();
        }
        return r;
    }
}

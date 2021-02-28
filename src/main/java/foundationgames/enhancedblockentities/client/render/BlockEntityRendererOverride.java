package foundationgames.enhancedblockentities.client.render;

import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BlockEntityRendererOverride {
    private static final Random dummy = new Random();

    public abstract void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay);

    public static BlockEntityRendererOverride chest(Supplier<BakedModel[]> lidModels, Function<BlockEntity, Integer> selector) {
        return new BlockEntityRendererOverride() {
            private BakedModel[] models = null;
            private final float yPiv = 9f/16;
            private final float zPiv = 15f/16;
            @Override
            public void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
                if(models == null) models = lidModels.get();
                if(blockEntity instanceof ChestAnimationProgress) {
                    matrices.push();
                    matrices.translate(0.5f, 0, 0.5f);
                    Direction dir = blockEntity.getCachedState().get(ChestBlock.FACING);
                    matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180 - dir.asRotation()));
                    matrices.translate(-0.5f, 0, -0.5f);
                    matrices.translate(0, yPiv, zPiv);
                    float rot = ((ChestAnimationProgress)blockEntity).getAnimationProgress(tickDelta);
                    rot = 1f - rot;
                    rot = 1f - (rot * rot * rot);
                    matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(rot * 90));
                    matrices.translate(0, -yPiv, -zPiv);
                    renderBakedModel(vertexConsumers.getBuffer(RenderLayer.getSolid()), matrices, models[selector.apply(blockEntity)], light, overlay, dir);
                    matrices.pop();
                }
            }
        };
    }

    public static void renderBakedModel(VertexConsumer vertices, MatrixStack matrices, BakedModel model, int light, int overlay, Direction facing) {
        float l;
        for (int i = 0; i <= 6; i++) {
            for(BakedQuad q : model.getQuads(null, ModelHelper.faceFromIndex(i), dummy)) {
                l = getLight(q.getFace(), facing);
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
                return 0.4f;
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

package foundationgames.enhancedblockentities.client.render;

import foundationgames.enhancedblockentities.event.EBEEvents;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.Direction;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BlockEntityRendererOverride {
    private static final Random dummy = new Random();

    public BlockEntityRendererOverride() {
        EBEEvents.RELOAD_MODELS.register((loader, manager, profiler) -> this.onModelsReload());
    }

    public abstract void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay);

    public void onModelsReload() {}

    public static BlockEntityRendererOverride chest(Supplier<BakedModel[]> lidModels, Function<BlockEntity, Integer> selector) {
        BlockEntityRendererOverride r = new BlockEntityRendererOverride() {
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

            public void onModelsReload() {
                this.models = null;
            }
        };
        return r;
    }

    public static BlockEntityRendererOverride sign() {
        BlockEntityRendererOverride r = new BlockEntityRendererOverride() {
            @Override
            public void render(BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
                if(blockEntity instanceof SignBlockEntity) {
                    matrices.push();
                    SignBlockEntity be = (SignBlockEntity)blockEntity;
                    float signAngle;
                    matrices.translate(0.5D, 0.5D, 0.5D);
                    if (be.getCachedState().getBlock() instanceof SignBlock) {
                        signAngle = - ((float)(be.getCachedState().get(SignBlock.ROTATION) * 360) / 16);
                        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(signAngle));
                    } else {
                        signAngle = - be.getCachedState().get(WallSignBlock.FACING).asRotation();
                        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(signAngle));
                        matrices.translate(0.0D, -0.3125, -0.4375);
                    }
                    matrices.translate(0.0, 0.333333, 0.0466666);
                    matrices.scale(0.01f, -0.01f, 0.01f);
                    TextRenderer tr = MinecraftClient.getInstance().textRenderer;
                    int color = be.getTextColor().getSignColor();
                    int tColor = NativeImage.getAbgrColor(0,
                            (int)(NativeImage.getBlue(color) * 0.4),
                            (int)(NativeImage.getGreen(color) * 0.4),
                            (int)(NativeImage.getRed(color) * 0.4)
                    );
                    for(int i = 0; i < 4; i++) {
                        OrderedText text = be.getTextBeingEditedOnRow(i, (t) -> {
                            List<OrderedText> texts = tr.wrapLines(t, 90);
                            return texts.isEmpty() ? OrderedText.EMPTY : texts.get(0);
                        });
                        if (text != null) {
                            float t = (float)(-tr.getWidth(text) / 2);
                            tr.draw(text, t, (float)(i * 10 - 20), tColor, false, matrices.peek().getModel(), vertexConsumers, false, 0, light);
                        }
                    }
                    matrices.pop();
                }
            }
        };
        return r;
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

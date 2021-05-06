package foundationgames.enhancedblockentities.util;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.util.Identifier;

public enum ResourceUtil {;
    private static RuntimeResourcePack PACK;

    public static final String CHEST_ITEM_MODEL_RESOURCE = "{\"parent\":\"block/chest_center\",\"overrides\":[{\"predicate\":{\"is_christmas\":1},\"model\": \"item/christmas_chest\"}]}";

    public static void addSingleChestModels(String texture, String chestName, RuntimeResourcePack pack) {
        pack.addModel(JModel.model().parent("block/template_chest_center").textures(JModel.textures().var("chest", texture)), new Identifier("block/" + chestName + "_center"));
        pack.addModel(JModel.model().parent("block/template_chest_center_lid").textures(JModel.textures().var("chest", texture)), new Identifier("block/" + chestName + "_center_lid"));
        pack.addModel(JModel.model().parent("block/template_chest_center_trunk").textures(JModel.textures().var("chest", texture)), new Identifier("block/" + chestName + "_center_trunk"));
    }

    public static void addDoubleChestModels(String leftTex, String rightTex, String chestName, RuntimeResourcePack pack) {
        pack.addModel(JModel.model().parent("block/template_chest_left").textures(JModel.textures().var("chest", leftTex)), new Identifier("block/" + chestName + "_left"));
        pack.addModel(JModel.model().parent("block/template_chest_left_lid").textures(JModel.textures().var("chest", leftTex)), new Identifier("block/" + chestName + "_left_lid"));
        pack.addModel(JModel.model().parent("block/template_chest_left_trunk").textures(JModel.textures().var("chest", leftTex)), new Identifier("block/" + chestName + "_left_trunk"));
        pack.addModel(JModel.model().parent("block/template_chest_right").textures(JModel.textures().var("chest", rightTex)), new Identifier("block/" + chestName + "_right"));
        pack.addModel(JModel.model().parent("block/template_chest_right_lid").textures(JModel.textures().var("chest", rightTex)), new Identifier("block/" + chestName + "_right_lid"));
        pack.addModel(JModel.model().parent("block/template_chest_right_trunk").textures(JModel.textures().var("chest", rightTex)), new Identifier("block/" + chestName + "_right_trunk"));
    }

    public static void addChestBlockStates(String chestName, RuntimeResourcePack pack) {
        pack.addBlockState(
                JState.state(
                        JState.variant()
                                .put("facing=east,type=single", JState.model("builtin:"+chestName+"_center").y(90))
                                .put("facing=north,type=single", JState.model("builtin:"+chestName+"_center"))
                                .put("facing=south,type=single", JState.model("builtin:"+chestName+"_center").y(180))
                                .put("facing=west,type=single", JState.model("builtin:"+chestName+"_center").y(270))
                                .put("facing=east,type=left", JState.model("builtin:"+chestName+"_left").y(90))
                                .put("facing=north,type=left", JState.model("builtin:"+chestName+"_left"))
                                .put("facing=south,type=left", JState.model("builtin:"+chestName+"_left").y(180))
                                .put("facing=west,type=left", JState.model("builtin:"+chestName+"_left").y(270))
                                .put("facing=east,type=right", JState.model("builtin:"+chestName+"_right").y(90))
                                .put("facing=north,type=right", JState.model("builtin:"+chestName+"_right"))
                                .put("facing=south,type=right", JState.model("builtin:"+chestName+"_right").y(180))
                                .put("facing=west,type=right", JState.model("builtin:"+chestName+"_right").y(270))
                ), new Identifier(chestName)
        );
    }

    public static void addSingleChestOnlyBlockStates(String chestName, RuntimeResourcePack pack) {
        pack.addBlockState(
                JState.state(
                        JState.variant()
                                .put("facing=east", JState.model("builtin:"+chestName+"_center").y(90))
                                .put("facing=north", JState.model("builtin:"+chestName+"_center"))
                                .put("facing=south", JState.model("builtin:"+chestName+"_center").y(180))
                                .put("facing=west", JState.model("builtin:"+chestName+"_center").y(270))
                ), new Identifier(chestName)
        );
    }

    public static void addSignModels(String texture, String signName, String wallSignName, RuntimeResourcePack pack) {
        pack.addModel(JModel.model().parent(signAOSuffix("block/template_sign_0")).textures(JModel.textures().var("sign", texture)), new Identifier("block/" + signName + "_0"));
        pack.addModel(JModel.model().parent(signAOSuffix("block/template_sign_22_5")).textures(JModel.textures().var("sign", texture)), new Identifier("block/" + signName + "_22_5"));
        pack.addModel(JModel.model().parent(signAOSuffix("block/template_sign_45")).textures(JModel.textures().var("sign", texture)), new Identifier("block/" + signName + "_45"));
        pack.addModel(JModel.model().parent(signAOSuffix("block/template_sign_67_5")).textures(JModel.textures().var("sign", texture)), new Identifier("block/" + signName + "_67_5"));
        pack.addModel(JModel.model().parent(signAOSuffix("block/template_wall_sign")).textures(JModel.textures().var("sign", texture)), new Identifier("block/" + wallSignName));
    }

    private static String signAOSuffix(String model) {
        if (EnhancedBlockEntities.CONFIG.signAO) model += "_ao";
        return model;
    }

    public static void addSignBlockStates(String signName, String wallSignName, RuntimeResourcePack pack) {
        pack.addBlockState(
                JState.state(
                        JState.variant()
                                .put("rotation=0", JState.model("block/"+signName+"_0").y(180))
                                .put("rotation=1", JState.model("block/"+signName+"_67_5").y(270))
                                .put("rotation=2", JState.model("block/"+signName+"_45").y(270))
                                .put("rotation=3", JState.model("block/"+signName+"_22_5").y(270))
                                .put("rotation=4", JState.model("block/"+signName+"_0").y(270))
                                .put("rotation=5", JState.model("block/"+signName+"_67_5"))
                                .put("rotation=6", JState.model("block/"+signName+"_45"))
                                .put("rotation=7", JState.model("block/"+signName+"_22_5"))
                                .put("rotation=8", JState.model("block/"+signName+"_0"))
                                .put("rotation=9", JState.model("block/"+signName+"_67_5").y(90))
                                .put("rotation=10", JState.model("block/"+signName+"_45").y(90))
                                .put("rotation=11", JState.model("block/"+signName+"_22_5").y(90))
                                .put("rotation=12", JState.model("block/"+signName+"_0").y(90))
                                .put("rotation=13", JState.model("block/"+signName+"_67_5").y(180))
                                .put("rotation=14", JState.model("block/"+signName+"_45").y(180))
                                .put("rotation=15", JState.model("block/"+signName+"_22_5").y(180))
                ), new Identifier(signName)
        );
        pack.addBlockState(
                JState.state(
                        JState.variant()
                                .put("facing=north", JState.model("block/"+wallSignName))
                                .put("facing=west", JState.model("block/"+wallSignName).y(270))
                                .put("facing=south", JState.model("block/"+wallSignName).y(180))
                                .put("facing=east", JState.model("block/"+wallSignName).y(90))
                ), new Identifier(wallSignName)
        );
    }

    public static void resetPack() {
        PACK = RuntimeResourcePack.create("ebe:pack");
    }

    public static RuntimeResourcePack getPack() {
        return PACK;
    }

    static {
        resetPack();
    }
}

/*    */ package foundationgames.enhancedblockentities.mixin;
/*    */ import foundationgames.enhancedblockentities.util.WorldUtil;
/*    */ import foundationgames.enhancedblockentities.util.duck.AppearanceStateHolder;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_2487;
/*    */ import net.minecraft.class_8172;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({class_8172.class})
/*    */ public class DecoratedPotBlockEntityMixin implements AppearanceStateHolder {
/*    */   @Unique
/* 17 */   private int enhanced_bes$modelState = 0; @Unique
/* 18 */   private int enhanced_bes$renderState = 0;
/*    */   
/*    */   @Inject(method = {"readNbt"}, at = {@At("TAIL")})
/*    */   private void enhanced_bes$updateChunkOnPatternsLoaded(class_2487 nbt, class_8541 registryAccess, CallbackInfo ci) {
/* 22 */     class_8172 self = (class_8172)this;
/*    */     
/* 24 */     if (self.method_10997() != null && self.method_10997().method_8608()) {
/* 25 */       WorldUtil.rebuildChunk(self.method_10997(), self.method_11016());
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"onSyncedBlockEvent"}, at = {@At(value = "RETURN", shift = At.Shift.BEFORE, ordinal = 0)})
/*    */   private void enhanced_bes$updateOnWobble(int type, int data, CallbackInfoReturnable<Boolean> cir) {
/* 31 */     class_8172 self = (class_8172)this;
/* 32 */     class_1937 world = self.method_10997();
/*    */     
/* 34 */     if (self.field_46662 == null) {
/*    */       return;
/*    */     }
/*    */     
/* 38 */     updateAppearanceState(1, world, self.method_11016());
/*    */     
/* 40 */     WorldUtil.scheduleTimed(world, self.field_46661 + self.field_46662.field_46666, () -> {
/*    */           if (self.method_10997().method_8510() >= self.field_46661 + self.field_46662.field_46666) {
/*    */             updateAppearanceState(0, world, self.method_11016());
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getModelState() {
/* 50 */     return this.enhanced_bes$modelState;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setModelState(int state) {
/* 55 */     this.enhanced_bes$modelState = state;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderState() {
/* 60 */     return this.enhanced_bes$renderState;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRenderState(int state) {
/* 65 */     this.enhanced_bes$renderState = state;
/*    */   }
/*    */ }


/* Location:              D:\我的世界\.minecraft\versions\1.21.8生电包\mods\enhancedblockentities-0.11.3 1.21.4.jar!\foundationgames\enhancedblockentities\mixin\DecoratedPotBlockEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
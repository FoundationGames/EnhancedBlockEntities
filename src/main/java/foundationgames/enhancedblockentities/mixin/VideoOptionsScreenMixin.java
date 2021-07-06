package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.config.gui.option.ConfigButtonOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.VideoOptionsScreen;
import net.minecraft.client.options.Option;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(VideoOptionsScreen.class)
public abstract class VideoOptionsScreenMixin extends Screen {
    protected VideoOptionsScreenMixin(Text title) {
        super(title);
    }

    @ModifyArg(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/widget/ButtonListWidget;addAll([Lnet/minecraft/client/options/Option;)V"
            ),
            index = 0
    )
    private Option[] enhanced_bes$addEBEOptionButton(Option[] old) {
        Option[] options = new Option[old.length + 1];
        System.arraycopy(old, 0, options, 0, old.length);
        options[options.length - 1] = new ConfigButtonOption(this);
        return options;
    }
}

package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.config.gui.option.ConfigButtonOption;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(VideoSettingsScreen.class)
public abstract class VideoOptionsScreenMixin extends Screen {
    protected VideoOptionsScreenMixin(Component title) {
        super(title);
    }

    @ModifyArg(
            method = "addOptions",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/OptionsList;addSmall([Lnet/minecraft/client/OptionInstance;)V"
            ),
            index = 0
    )
    private OptionInstance<?>[] enhanced_bes$addEBEOptionButton(OptionInstance<?>[] old) {
        var options = new OptionInstance<?>[old.length + 1];
        System.arraycopy(old, 0, options, 0, old.length);
        options[options.length - 1] = ConfigButtonOption.getOption(this);
        return options;
    }
}

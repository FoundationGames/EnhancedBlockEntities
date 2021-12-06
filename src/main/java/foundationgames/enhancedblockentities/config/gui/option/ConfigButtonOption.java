package foundationgames.enhancedblockentities.config.gui.option;

import foundationgames.enhancedblockentities.config.gui.screen.EBEConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.TranslatableText;

public class ConfigButtonOption extends Option {
    private final Screen parent;

    public ConfigButtonOption(Screen parent) {
        super("option.ebe.config");
        this.parent = parent;
    }

    @Override
    public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
        return new ButtonWidget(x, y, width, 20, new TranslatableText("option.ebe.config"), b -> {
            MinecraftClient.getInstance().setScreen(new EBEConfigScreen(parent));
        });
    }
}

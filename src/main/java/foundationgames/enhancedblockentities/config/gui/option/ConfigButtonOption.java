package foundationgames.enhancedblockentities.config.gui.option;

import com.mojang.serialization.Codec;
import foundationgames.enhancedblockentities.config.gui.screen.EBEConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ConfigButtonOption {
    public static SimpleOption<?> getOption(Screen parent) {
        return new SimpleOption<>(
            "option.ebe.config",
            SimpleOption.emptyTooltip(),
            (title, object) -> title,
            new ConfigButtonCallbacks<>(parent),
            Optional.empty(),
            value -> {
            }
        );
    }

    private record ConfigButtonCallbacks<T>(Screen parent) implements SimpleOption.Callbacks<T> {
        @Override
        public Function<SimpleOption<T>, ClickableWidget> getWidgetCreator(SimpleOption.TooltipFactory<T> tooltipFactory, GameOptions gameOptions, int x, int y, int width, Consumer<T> changed) {
            return (option) -> ButtonWidget.builder(Text.translatable("option.ebe.config"), b -> {
                MinecraftClient.getInstance().setScreen(new EBEConfigScreen(parent));
            }).dimensions(x, y, width, 20).build();
        }

        @Override
        public Optional<T> validate(T value) {
            return Optional.empty();
        }

        @Override
        public Codec<T> codec() {
            return null;
        }
    }
}

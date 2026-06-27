package foundationgames.enhancedblockentities.config.gui.option;

import com.mojang.serialization.Codec;
import foundationgames.enhancedblockentities.config.gui.screen.EBEConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ConfigButtonOption {
    public static OptionInstance<?> getOption(Screen parent) {
        return new OptionInstance<>(
            "option.ebe.config",
            OptionInstance.noTooltip(),
            (title, object) -> title,
            new ConfigButtonCallbacks<>(parent),
            Optional.empty(),
            value -> {
            }
        );
    }

    private record ConfigButtonCallbacks<T>(Screen parent) implements OptionInstance.Enum<T> {
        @Override
        public Function<OptionInstance<T>, AbstractWidget> createButton(OptionInstance.TooltipSupplier<T> tooltipFactory, Options gameOptions, int x, int y, int width, Consumer<T> changed) {
            return (option) -> Button.builder(Component.translatable("option.ebe.config"), b -> {
                Minecraft.getInstance().setScreen(new EBEConfigScreen(parent));
            }).bounds(x, y, width, 20).build();
        }

        @Override
        public Optional<T> validateValue(T value) {
            return Optional.empty();
        }

        @Override
        public Codec<T> codec() {
            return null;
        }
    }
}

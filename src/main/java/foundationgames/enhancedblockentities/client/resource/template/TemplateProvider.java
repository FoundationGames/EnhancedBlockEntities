package foundationgames.enhancedblockentities.client.resource.template;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class TemplateProvider {
    private final TemplateLoader loader;
    private final TemplateDefinitions.Impl definitions = new TemplateDefinitions.Impl();
    private final Deque<String> loaded = new ArrayDeque<>();

    public TemplateProvider(TemplateLoader loader) {
        this.loader = loader;
    }

    public String load(String templatePath, Consumer<TemplateDefinitions> definitions) throws IOException {
        this.definitions.push();
        definitions.accept(this.definitions);

        try {
            var substitutions = new HashMap<String, String>();
            for (var entry : this.definitions) {
                substitutions.put(entry.getKey(), entry.getValue().getAndApplyTemplate(this));
            }

            var templateRaw = this.loader.getOrLoadRaw(templatePath);
            var matcher = Pattern.compile("!\\[(" + String.join("|", substitutions.keySet()) + ")]")
                    .matcher(templateRaw);

            var result = new StringBuilder();
            while (matcher.find()) {
                matcher.appendReplacement(result, substitutions.get(matcher.group(1)));
            }
            matcher.appendTail(result);

            this.definitions.pop();
            return result.toString();
        } catch (IOException ex) {
            this.definitions.pop();
            throw ex;
        }
    }

    @FunctionalInterface
    public interface TemplateApplyingFunction {
        String getAndApplyTemplate(TemplateProvider templates) throws IOException;
    }
}

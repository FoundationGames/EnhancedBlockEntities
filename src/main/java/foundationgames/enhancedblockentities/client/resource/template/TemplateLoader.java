package foundationgames.enhancedblockentities.client.resource.template;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TemplateLoader {
    private Path rootPath;

    private final Map<String, String> loadedTemplates = new HashMap<>();

    public TemplateLoader() {
    }

    public void setRoot(Path path) {
        this.rootPath = path;
    }

    public String getOrLoadRaw(String path) throws IOException {
        if (this.rootPath == null) {
            return "";
        }

        if (this.loadedTemplates.containsKey(path)) {
            return this.loadedTemplates.get(path);
        }

        var file = rootPath.resolve(path);
        try (var in = Files.newInputStream(file)) {
            var templateRaw = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            this.loadedTemplates.put(path, templateRaw);

            return templateRaw;
        }
    }
}

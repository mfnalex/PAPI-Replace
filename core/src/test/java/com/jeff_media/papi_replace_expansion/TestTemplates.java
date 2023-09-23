package com.jeff_media.papi_replace_expansion;

import com.jeff_media.papi_replace_expansion.templates.Template;
import com.jeff_media.papi_replace_expansion.templates.TemplateList;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTemplates {

    private static YamlConfiguration config;

    @BeforeAll
    public static void setup() {
        File file = new File("src/test/resources/config.yml");
        config = YamlConfiguration.loadConfiguration(file);

        if (!"success".equals(config.getString("test"))) {
            throw new RuntimeException("Test config failed to load");
        }

    }

    @Test
    public void template_simple() {
        System.out.println(config.saveToString());
        TemplateList templates = TemplateList.of(config.getConfigurationSection("replace.templates"));
        Template template = templates.get("simple");
        assert template != null;
        String string = "foo foo bar bar";
        assertEquals("bar bar bar bar", template.applyAll(string));
    }
}

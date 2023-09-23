package com.jeff_media.papi_replace_expansion.templates;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TemplateList implements Iterable<Template> {

    public static final TemplateList EMPTY = new TemplateList(Collections.emptyList());

    private final Map<String, Template> templates = new HashMap<>();

    public TemplateList(@NotNull final List<Template> templates) {
        for (Template template : templates) {
            if (this.templates.containsKey(template.getName())) {
                throw new IllegalArgumentException("Duplicate template name: " + template.getName());
            }
            this.templates.put(template.getName(), template);
        }
    }

    public static TemplateList of(@NotNull final ConfigurationSection configuration) {
        Objects.requireNonNull(configuration, "configuration cannot be null");
        List<Template> templates = new ArrayList<>();
        for (String name : configuration.getKeys(false)) {
            templates.add(Template.fromConfigurationSection(configuration, name));
        }
        return new TemplateList(templates);
    }

    @Nullable
    public Template get(String name) {
        return templates.get(name);
    }

    @NotNull
    @Override
    public Iterator<Template> iterator() {
        return templates.values().iterator();
    }
}

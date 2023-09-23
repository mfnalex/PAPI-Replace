package com.jeff_media.papi_replace_expansion.templates;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Template {

    private final String name;
    private final List<Replacement> replacements;

    public Template(final @NotNull String name, final @NotNull List<Replacement> list) {
        this.name = name;
        this.replacements = Collections.unmodifiableList(list);
    }

    public static Template fromConfigurationSection(ConfigurationSection section, String name) {
        if(!section.isList(name)) {
            throw new IllegalArgumentException("Invalid template: " + name);
        }

        return new Template(name, section.getMapList(name)
                .stream()
                .map(Replacement::of)
                .collect(Collectors.toList())
        );
    }

    /**
     * Gets the name of the this template
     * @return The name of this template
     */
    public String getName() {
        return name;
    }

    public String applyAll(@NotNull String input) {
        for(Replacement replacement : replacements) {
            input = replacement.apply(input);
        }
        return input;
    }
}

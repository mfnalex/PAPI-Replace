package com.jeff_media.papi_replace_expansion;

import com.jeff_media.papi_replace_expansion.parsing.NaiveParser;
import com.jeff_media.papi_replace_expansion.parsing.Parser;
import com.jeff_media.papi_replace_expansion.templates.Template;
import com.jeff_media.papi_replace_expansion.templates.TemplateList;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * PlaceholderAPI expansion to replace strings
 */
public class ReplacePlaceholderExpansion extends PlaceholderExpansion {

    //private static final Pattern INNER_PLACEHOLDER = Pattern.compile("^.*(?<inner>\\{[^}]*}).*$");
    // private static final Pattern INNER_PLACEHOLDER = Pattern.compile("\\{[^{]*?}");
    private static final int MAX_NESTED_PLACEHOLDERS = 20;
    private static final String version;
    private static final List<String> PLACEHOLDERS = new ArrayList<>();

    static {
        String tmpVersion = "<unknown>";
        try (InputStream stream = ReplacePlaceholderExpansion.class.getResourceAsStream(
                "/papi-replace-extension.version");
             InputStreamReader reader = new InputStreamReader(stream);
             BufferedReader bufferedReader = new BufferedReader(reader);) {
            tmpVersion = bufferedReader.readLine();
        } catch (IOException ignored) {

        }
        version = tmpVersion;
    }

    private final String CONFIG_PATH = "expansions.replace";
    private final String CONFIG_TEMPLATES_PATH = CONFIG_PATH + ".templates";
    private final Parser replacer = new NaiveParser();
    private final TemplateList templates;

    {
        FileConfiguration papiConfig = getPlaceholderAPI().getConfig();
        if (!papiConfig.isConfigurationSection(CONFIG_TEMPLATES_PATH)) {
            saveDefaultTemplates();
        }

        templates = TemplateList.of(getPlaceholderAPI().getConfig().getConfigurationSection(CONFIG_TEMPLATES_PATH));

        addAutoReplacements();

    }

    private static String replaceInnerPlaceholders(OfflinePlayer player, String input) {
        int nestedPlaceholders = 0;
        while (PlaceholderAPI.containsBracketPlaceholders(input)) {
            //System.out.println("Found inner placeholder, replacing...");
            //System.out.println("Input: " + input);
            String output = PlaceholderAPI.setBracketPlaceholders(player, input);
            //System.out.println("Output: " + output);
            if (output.equals(input)) {
                //System.out.println("Although there is an inner place, it hasn't changed, so we're breaking out of the loop");
                break;
            }
            input = output;
            nestedPlaceholders++;
            if (nestedPlaceholders >= MAX_NESTED_PLACEHOLDERS) {
                //System.out.println("Reached max nested placeholders, breaking out of the loop");
                break;
            }
        }
        //System.out.println("No more placeholders found, or broke out because see above ^");
        return input;
    }

    private void addAutoReplacements() {
        PLACEHOLDERS.add("%replace_<search>_<replace>_<text>%");
        PLACEHOLDERS.add("%replace_`<search>`_`<replace>`_<text>%");
        for(Template template : templates) {
            PLACEHOLDERS.add("%replace_template_" + template.getName() + "_<text>%");
        }
    }

    private void saveDefaultTemplates() {
        try (InputStream inputStream = ReplacePlaceholderExpansion.class.getResourceAsStream("/templates.yml");
             InputStreamReader reader = new InputStreamReader(inputStream);) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(reader);
            ConfigurationSection section = yaml.getConfigurationSection(CONFIG_PATH);
            FileConfiguration papiConfig = getPlaceholderAPI().getConfig();
            papiConfig.set(CONFIG_PATH, section);
            getPlaceholderAPI().saveConfig();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public @NotNull String getIdentifier() {
        return "replace";
    }

    @Override
    public @NotNull String getAuthor() {
        return "mfnalex";
    }

    @Override
    public @NotNull String getVersion() {
        return version;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        return parseAndReplace(player, params);
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return parseAndReplace(player, params);
    }

    private String parseAndReplace0(OfflinePlayer player, final String input) {
        return replacer.parseAndReplace(replaceInnerPlaceholders(player, input));
    }

    private String parseAndReplace(@Nullable OfflinePlayer player, String input) {
        if (input.startsWith("template_")) {
            return replaceTemplates(player, input.substring("template_".length()));
        }
        return parseAndReplace0(player, input);
    }

    private String replaceTemplates(final @Nullable OfflinePlayer player, String substring) {
        substring = PlaceholderAPI.setBracketPlaceholders(player, substring);
        int endOfTemplateName = substring.indexOf('_');
        if (endOfTemplateName == -1) {
            return null;
        }
        String templateName = substring.substring(0, endOfTemplateName);
        String text = "";
        if (endOfTemplateName != substring.length() - 1) {
            text = substring.substring(endOfTemplateName + 1);
        }

        Template template = templates.get(templateName);
        if (template == null) {
            return null;
        }
        return template.applyAll(text);

    }

    @Override
    public @NotNull List<String> getPlaceholders() {
        return PLACEHOLDERS;
    }
}

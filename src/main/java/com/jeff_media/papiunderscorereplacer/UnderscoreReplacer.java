package com.jeff_media.papiunderscorereplacer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnderscoreReplacer extends PlaceholderExpansion {

    private final Plugin plugin;

    public UnderscoreReplacer(PAPIUnderscoreReplacer plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "replaceunderscores";
    }

    @Override
    public @NotNull String getAuthor() {
        return "mfnalex";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        String[] split = params.split("_", 2);
        if (split.length != 2) {
            //System.out.println("Split length is " + split.length + " instead of 2");
            return null;
        }
        String replace = split[0];
        String message = split[1];
        return message.replace("_", replace);
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return this.onRequest(player, params);
    }
}

package com.jeff_media.papiunderscorereplacer;

import org.bukkit.plugin.java.JavaPlugin;

public class PAPIUnderscoreReplacer extends JavaPlugin {

    @Override
    public void onEnable() {
        new ReplacePlaceholderExpansion(this).register();
    }

}

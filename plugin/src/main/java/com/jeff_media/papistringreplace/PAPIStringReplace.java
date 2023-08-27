package com.jeff_media.papistringreplace;

import org.bukkit.plugin.java.JavaPlugin;

public class PAPIStringReplace extends JavaPlugin {

    @Override
    public void onEnable() {
        new ReplacePlaceholderExpansion(this).register();
    }

}

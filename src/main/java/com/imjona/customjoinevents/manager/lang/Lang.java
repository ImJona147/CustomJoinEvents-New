package com.imjona.customjoinevents.manager.lang;

import com.imjona.customjoinevents.utils.UtilsPlugin;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Lang {
    private final File file;

    private HashMap<String, String> translations;

    public Lang(File file) {
        this.file = file;
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        load();
    }

    public void load() {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.file);
        this.translations = new HashMap<>();
        if (!this.file.getName().equalsIgnoreCase("en.yml") && (
                new File(this.file.getParentFile().getAbsolutePath(), "en.yml")).exists()) {
            YamlConfiguration cfg2 = YamlConfiguration.loadConfiguration(new File(this.file.getParentFile().getAbsolutePath(), "en.yml"));
            loadConfigurationSection("translations", Objects.requireNonNull(cfg2.getConfigurationSection("translations")), cfg2);
        }
        loadConfigurationSection("translations", Objects.requireNonNull(cfg.getConfigurationSection("translations")), cfg);
    }

    public void save() {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.file);
        for (String key : this.translations.keySet())
            cfg.set(key, this.translations.get(key));
        try {
            cfg.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfigurationSection(String keyPre, ConfigurationSection section, YamlConfiguration cfg) {
        for (String key : section.getKeys(false)) {
            if (cfg.get(keyPre + "." + key) instanceof YamlConfiguration) {
                loadConfigurationSection(keyPre + "." + key, Objects.<ConfigurationSection>requireNonNull(cfg.getConfigurationSection(keyPre + "." + key)), cfg);
                continue;
            }
            this.translations.put(keyPre + "." + key, cfg.getString(keyPre + "." + key));
        }
    }

    public String getStringc(String name) {
        name = "translations." + name;
        if (this.translations.containsKey(name))
            return this.translations.get(name);
        return name;
    }

    public String getString(String message) {
        return UtilsPlugin.fixColorMessages(getStringc(message));
    }

    public String getLanguageName() {
        return this.file.getName();
    }

    public HashMap<String, String> getTranslations() {
        return this.translations;
    }
}

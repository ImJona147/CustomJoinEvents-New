package com.imjona.customjoinevents.manager.lang;

import com.imjona.customjoinevents.CustomJoinEvents;
import com.imjona.customjoinevents.utils.UtilsPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class LangManager {
    private static final String[] languages = new String[] { "en", "es" };

    private final CustomJoinEvents plugin;

    private String selectedLang;

    private Lang lang;

    public LangManager(CustomJoinEvents plugin, String selectedLang) {
        this.plugin = plugin;
        this.selectedLang = selectedLang;
        loadLang();
    }

    private void loadLang() {
        if (!this.plugin.getDataFolder().exists())
            this.plugin.getDataFolder().mkdirs();
        File dir = new File(this.plugin.getDataFolder().getAbsoluteFile() + "/translations/");
        if (!dir.exists())
            dir.mkdirs();
        UtilsPlugin.sendMessageToConsole("&7Loading language &b" + this.selectedLang + ".yml &7...");
        for (String lang : languages) {
            File file = new File(dir.getAbsolutePath(), lang + ".yml");
            if (!file.exists())
                FileUtils.insertData("translations/" + lang + ".yml", dir.getAbsoluteFile() + "/" + lang + ".yml", this.plugin);
            updateFile(file);
        }
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.getName().equals(this.selectedLang + ".yml"))
                this.lang = new Lang(file);
        }
        if (this.lang == null) {
            UtilsPlugin.sendMessageToConsole("&7Language &b" + this.selectedLang + "&7 not found! Using english!");
            this.lang = new Lang(new File(dir.getAbsolutePath(), "en.yml"));
        }
        UtilsPlugin.sendMessageToConsole("&7Language set to &b" + this.lang.getLanguageName() + "&7!");
    }

    private void updateFile(File file) {
        Reader reader = new InputStreamReader(Objects.requireNonNull(this.plugin.getResource("translations/" + file.getName())));
        YamlConfiguration newCfg = YamlConfiguration.loadConfiguration(reader);
        YamlConfiguration oldCfg = YamlConfiguration.loadConfiguration(file);
        if (newCfg.getConfigurationSection("translations") != null)
            for (String key : Objects.requireNonNull(newCfg.getConfigurationSection("translations")).getKeys(true)) {
                if (!oldCfg.contains("translations." + key))
                    oldCfg.set("translations." + key, newCfg.get("translations." + key));
            }
        try {
            oldCfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Lang getLanguage() {
        return this.lang;
    }

    public void setSelectedLang(String selectedLang) {
        this.selectedLang = selectedLang;
    }
}

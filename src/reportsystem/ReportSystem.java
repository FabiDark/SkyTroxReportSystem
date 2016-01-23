package reportsystem;

import de.fabidark.reportsystem.sql.SQL;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Fabian on 22.01.2016.
 */

public class ReportSystem extends Plugin {

    @Override
    public void onEnable() {
        createsqlfileandsetup();
    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }

    private void createsqlfileandsetup() {
        File file = new File(getDataFolder().getPath(), "mysql.yml");
        try {
            if(!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            if(!file.exists()) {
                file.createNewFile();
                Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                cfg.set("Host", "HOST");
                cfg.set("Username", "USERNAME");
                cfg.set("Password", "PASSWORD");
                cfg.set("Database", "DATABASE");
                cfg.set("Port", "3306");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, file);
            }
            Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            SQL.host = cfg.getString("Host");
            SQL.username = cfg.getString("Username");
            SQL.database = cfg.getString("Database");
            SQL.password = cfg.getString("Password");
            SQL.port = cfg.getString("Port");
            SQL.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

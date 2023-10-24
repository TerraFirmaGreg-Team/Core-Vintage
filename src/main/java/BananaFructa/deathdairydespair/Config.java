package BananaFructa.deathdairydespair;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    public static Configuration config;

    public static int respawnHungerLevel;
    public static int respawnThirstLevel;

    public static void load(File configDir) {

        config = new Configuration(new File(configDir,"deathdairydespair.cfg"));

        respawnHungerLevel = config.getInt("respawn_hunger","general",100,0,100,"The hunger value with which a player respawns.");
        respawnThirstLevel = config.getInt("respawn_thirst","general",100,0,100,"The thirst value with which a player respawns.");

        if (config.hasChanged()) config.save();
    }

}

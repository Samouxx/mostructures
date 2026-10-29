package io.github.frqnny.mostructures.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.github.frqnny.mostructures.MoStructures;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MoStructuresConfig {
    // Same file as the Omega Config versions of the mod, so existing settings are kept
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("mostructures-config-v5.json5");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String HEADER = """
            // Welcome to Mo'Structures Config!
            //
            //   Take a break, rest a while, enjoy the scenery! :)
            //
            //   Using this config, you can prevent structures from generating by de-activating them.
            //
            //   You can modify the biomes these structures spawn in, and how far apart they are, through datapacks
            //   (worldgen/structure_set files).
            //
            """;

    protected Map<String, StructureConfigEntry> structureConfigEntries = new HashMap<>();

    public static MoStructuresConfig load() {
        MoStructuresConfig config = null;
        if (Files.exists(FILE)) {
            try (Reader reader = Files.newBufferedReader(FILE)) {
                // parseReader is lenient: it accepts the // comments of the .json5 file
                config = GSON.fromJson(JsonParser.parseReader(reader), MoStructuresConfig.class);
            } catch (IOException | JsonParseException e) {
                // Keep the broken file so the player can fix it, and play with every structure activated
                MoStructures.LOGGER.error("Could not read {}, using the default settings", FILE, e);
                return new MoStructuresConfig().withDefaults();
            }
        }
        if (config == null || config.structureConfigEntries == null) {
            config = new MoStructuresConfig();
        }
        config.withDefaults().save();
        return config;
    }

    public StructureConfigEntry get(String configKey) {
        return structureConfigEntries.get(configKey);
    }

    //places all our structures's entries in case they do not exist
    private MoStructuresConfig withDefaults() {
        for (String configKey : new String[]{"abandoned_church", "air_balloon", "barn_house", "big_pyramid", "ice_tower",
                "jungle_pyramid", "killer_bunny_castle", "lighthouse", "moai", "pillager_factory", "pillager_mines",
                "pirate_ship", "tavern", "the_castle_in_the_sky", "villager_market", "villager_tower", "sphinx"}) {
            structureConfigEntries.putIfAbsent(configKey, new StructureConfigEntry());
        }
        return this;
    }

    private void save() {
        try {
            Files.writeString(FILE, HEADER + GSON.toJson(this));
        } catch (IOException e) {
            MoStructures.LOGGER.error("Could not save {}", FILE, e);
        }
    }
}

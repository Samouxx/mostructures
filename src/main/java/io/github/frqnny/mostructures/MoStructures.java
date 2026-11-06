package io.github.frqnny.mostructures;

import io.github.frqnny.mostructures.config.MoStructuresConfig;
import io.github.frqnny.mostructures.init.ProcessorTypes;
import io.github.frqnny.mostructures.init.Structures;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MoStructures {
    public static final String MOD_ID = "mostructures";
    // Before CONFIG: loading the config may log an error
    public static final Logger LOGGER = LoggerFactory.getLogger("MoStructures");
    public static final MoStructuresConfig CONFIG = MoStructuresConfig.load();

    public static void init() {
        ProcessorTypes.init();
        Structures.init();
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}

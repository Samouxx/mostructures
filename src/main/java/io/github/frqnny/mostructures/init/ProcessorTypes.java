package io.github.frqnny.mostructures.init;

import io.github.frqnny.mostructures.MoStructures;
import io.github.frqnny.mostructures.processor.SimpleCobblestoneProcessor;
import io.github.frqnny.mostructures.processor.SimpleStoneStructureProcessor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ProcessorTypes {

    // Since 26.3, processor types are registered directly as codecs
    public static void init() {
        Registry.register(BuiltInRegistries.STRUCTURE_PROCESSOR, MoStructures.id("jungle_rot_processor"), SimpleStoneStructureProcessor.CODEC);
        Registry.register(BuiltInRegistries.STRUCTURE_PROCESSOR, MoStructures.id("simple_cobblestone"), SimpleCobblestoneProcessor.CODEC);
    }

}

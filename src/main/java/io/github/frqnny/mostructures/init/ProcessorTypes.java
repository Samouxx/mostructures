package io.github.frqnny.mostructures.init;

import io.github.frqnny.mostructures.MoStructures;
import io.github.frqnny.mostructures.processor.SimpleCobblestoneProcessor;
import io.github.frqnny.mostructures.processor.SimpleStoneStructureProcessor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class ProcessorTypes {
    public static StructureProcessorType<SimpleStoneStructureProcessor> SIMPLE_STONE;
    public static StructureProcessorType<SimpleCobblestoneProcessor> SIMPLE_COBBLESTONE;

    public static void init() {
        SIMPLE_STONE = Registry.register(BuiltInRegistries.STRUCTURE_PROCESSOR, MoStructures.id("jungle_rot_processor"), () -> SimpleStoneStructureProcessor.CODEC);
        SIMPLE_COBBLESTONE = Registry.register(BuiltInRegistries.STRUCTURE_PROCESSOR, MoStructures.id("simple_cobblestone"), () -> SimpleCobblestoneProcessor.CODEC);
    }

}

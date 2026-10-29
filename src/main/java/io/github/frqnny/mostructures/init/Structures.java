package io.github.frqnny.mostructures.init;

import io.github.frqnny.mostructures.MoStructures;
import io.github.frqnny.mostructures.structure.ModStructure;
import io.github.frqnny.mostructures.structure.ModStructurePlacement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class Structures {
    public static final TagKey<Structure> NO_LAKES = TagKey.create(Registries.STRUCTURE, MoStructures.id("no_lakes"));

    public static StructureType<ModStructure> GENERIC;

    public static void init() {
        GENERIC = Registry.register(BuiltInRegistries.STRUCTURE_TYPE, MoStructures.id("generic"), () -> ModStructure.CODEC);
        // Since 26.3, structure placement types are registered directly as codecs
        Registry.register(BuiltInRegistries.STRUCTURE_PLACEMENT, MoStructures.id("type"), ModStructurePlacement.CODEC);
    }
}

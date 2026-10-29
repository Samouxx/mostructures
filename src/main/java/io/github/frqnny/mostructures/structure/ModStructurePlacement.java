package io.github.frqnny.mostructures.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.frqnny.mostructures.MoStructures;
import io.github.frqnny.mostructures.config.StructureConfigEntry;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.AbstractSpreadingStructurePlacement.FrequencyReductionMethod;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;
import java.util.Optional;

public class ModStructurePlacement extends RandomSpreadStructurePlacement {
    public static final MapCodec<ModStructurePlacement> CODEC = RecordCodecBuilder.<ModStructurePlacement>mapCodec(instance -> instance.group(
                    Codec.STRING.fieldOf("config_key").forGetter(ModStructurePlacement::getConfigKey),
                    Vec3i.offsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(ModStructurePlacement::locateOffset),
                    FrequencyReductionMethod.CODEC.optionalFieldOf("frequency_reduction_method", FrequencyReductionMethod.DEFAULT).forGetter(ModStructurePlacement::frequencyReductionMethod),
                    Codec.floatRange(0.0f, 1.0f).optionalFieldOf("frequency", 1.0f).forGetter(ModStructurePlacement::frequency),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt").forGetter(ModStructurePlacement::salt),
                    RegistryFileCodec.create(Registries.STRUCTURE_SET, StructureSet.DIRECT_CODEC, false).listOf().fieldOf("structure_set_to_avoid").orElse(List.of()).forGetter(config -> config.structureSetToAvoid),
                    Codec.intRange(0, 4096).fieldOf("spacing").forGetter(ModStructurePlacement::spacing),
                    Codec.intRange(0, 4096).fieldOf("separation").forGetter(ModStructurePlacement::separation),
                    RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(ModStructurePlacement::spreadType))
            .apply(instance, ModStructurePlacement::new)).validate(ModStructurePlacement::validate);
    public final List<Holder<StructureSet>> structureSetToAvoid;
    private final String configKey;
    private boolean activated = true;

    public ModStructurePlacement(String configKey, Vec3i locateOffset, FrequencyReductionMethod frequencyReductionMethod, float frequency, int salt, List<Holder<StructureSet>> structureSetToAvoid, int spacing, int separation, RandomSpreadType spreadType) {
        super(locateOffset, frequencyReductionMethod, frequency, salt, Optional.empty(), spacing, separation, spreadType);
        this.configKey = configKey;
        this.structureSetToAvoid = structureSetToAvoid;
    }

    private static DataResult<ModStructurePlacement> validate(ModStructurePlacement p) {
        String configKey = p.configKey;
        StructureConfigEntry entry = MoStructures.CONFIG.get(configKey);
        if (entry == null) {
            return DataResult.error(() -> "ModStructurePlacement with config key: " + configKey + " does not have specified key in the config!");
        }

        if (!entry.activated) {
            MoStructures.LOGGER.info("Disabled {} structure as requested by config!", configKey);
        }

        p.activated = entry.activated;

        return DataResult.success(p);
    }

    public static boolean isStructureSetNearby(ChunkGeneratorStructureState calculator, Holder<StructureSet> structureSetEntry, int centerChunkX, int centerChunkZ, int chunkCount) {

        for (int i = centerChunkX - chunkCount; i <= centerChunkX + chunkCount; ++i) {
            for (int j = centerChunkZ - chunkCount; j <= centerChunkZ + chunkCount; ++j) {
                var placement = structureSetEntry.value().placement();
                //avoid looping exclusion checks with our structure placement
                //if vanilla's, simply use their method
                if (placement instanceof ModStructurePlacement structurePlacement) {
                    if (structurePlacement.shouldGenerateNoExclusionCheck(calculator, i, j)) {
                        return true;
                    }
                } else {
                    if (placement.isStructureChunk(calculator, i, j)) {
                        return true;
                    }
                }

            }
        }

        return false;
    }

    public String getConfigKey() {
        return configKey;
    }

    @Override
    public boolean isStructureChunk(ChunkGeneratorStructureState calculator, int chunkX, int chunkZ) {
        return this.activated && super.isStructureChunk(calculator, chunkX, chunkZ) && exclusionZoneCheck(calculator, chunkX, chunkZ);
    }

    public boolean shouldGenerateNoExclusionCheck(ChunkGeneratorStructureState calculator, int chunkX, int chunkZ) {
        return this.activated && super.isStructureChunk(calculator, chunkX, chunkZ);
    }

    public boolean exclusionZoneCheck(ChunkGeneratorStructureState calculator, int chunkX, int chunkZ) {
        if (!structureSetToAvoid.isEmpty()) {
            for (Holder<StructureSet> entry : structureSetToAvoid) {
                if (isStructureSetNearby(calculator, entry, chunkX, chunkZ, 3)) {
                    return false;
                }
            }
        }
        return true;
    }

    // The parent returns MapCodec<RandomSpreadStructurePlacement>, and Java generics do not allow a narrower type here
    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<RandomSpreadStructurePlacement> codec() {
        return (MapCodec<RandomSpreadStructurePlacement>) (MapCodec<?>) CODEC;
    }
}

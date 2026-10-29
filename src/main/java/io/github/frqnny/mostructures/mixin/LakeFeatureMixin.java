package io.github.frqnny.mostructures.mixin;

import io.github.frqnny.mostructures.init.Structures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("unused")
@Mixin(LakeFeature.class)
public class LakeFeatureMixin {

    @Inject(at = @At("HEAD"), method = "place", cancellable = true)
    private void mostructures_lakeFix(WorldGenLevel level, ChunkGenerator generator, RandomSource random, BlockPos origin, CallbackInfoReturnable<Boolean> info) {
        ChunkAccess chunk = level.getChunk(origin);

        Registry<Structure> registry = level.registryAccess().lookupOrThrow(Registries.STRUCTURE);
        StructureManager structureAccessor = level.getLevel().structureManager();

        for (Holder<Structure> entry : registry.getTagOrEmpty(Structures.NO_LAKES)) {
            StructureStart startForFeature = structureAccessor.getStartForStructure(entry.value(), chunk);
            if (startForFeature != null && startForFeature.isValid()) {
                info.setReturnValue(false);
                return;
            }
        }
    }
}

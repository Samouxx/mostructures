package io.github.frqnny.mostructures.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.frqnny.mostructures.init.ProcessorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class SimpleCobblestoneProcessor extends StructureProcessor {
    public static final MapCodec<SimpleCobblestoneProcessor> CODEC = RecordCodecBuilder.mapCodec((proc) -> proc.group(
            Codec.FLOAT.fieldOf("mossiness").forGetter(processor -> processor.mossiness)
    ).apply(proc, SimpleCobblestoneProcessor::new));
    private final float mossiness;

    public SimpleCobblestoneProcessor(float mossiness) {
        this.mossiness = mossiness;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader worldView, BlockPos pos, BlockPos blockPos, StructureTemplate.StructureBlockInfo structureBlockInfo, StructureTemplate.StructureBlockInfo structureBlockInfo2, StructurePlaceSettings structurePlacementData) {
        RandomSource random = structurePlacementData.getRandom(structureBlockInfo2.pos());
        BlockState blockState = structureBlockInfo2.state();
        BlockPos blockPos2 = structureBlockInfo2.pos();
        BlockState blockState2 = null;

        if (blockState.is(Blocks.COBBLESTONE)) {
            blockState2 = this.processBlocks(random);
        }

        return blockState2 != null ? new StructureTemplate.StructureBlockInfo(blockPos2, blockState2, structureBlockInfo2.nbt()) : structureBlockInfo2;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ProcessorTypes.SIMPLE_COBBLESTONE;
    }

    @Nullable
    private BlockState processBlocks(RandomSource random) {
        if (random.nextFloat() >= 0.5F) {
            return null;
        } else {
            return random.nextFloat() < this.mossiness ? Blocks.COBBLESTONE.defaultBlockState() : Blocks.MOSSY_COBBLESTONE.defaultBlockState();

        }
    }
}

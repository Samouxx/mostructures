package io.github.frqnny.mostructures.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class SimpleStoneStructureProcessor implements StructureProcessor {
    public static final MapCodec<SimpleStoneStructureProcessor> CODEC = RecordCodecBuilder.mapCodec((proc) -> proc.group(
            Codec.FLOAT.fieldOf("mossiness").forGetter(processor -> processor.mossiness)
    ).apply(proc, SimpleStoneStructureProcessor::new));
    private final float mossiness;

    public SimpleStoneStructureProcessor(float mossiness) {
        this.mossiness = mossiness;
    }

    private static BlockState randomStairProperties(RandomSource random, Block stairs) {
        return stairs.defaultBlockState().setValue(StairBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)).setValue(StairBlock.HALF, Half.values()[random.nextInt(Half.values().length)]);
    }

    private static BlockState randomState(RandomSource random, BlockState[] states) {
        return states[random.nextInt(states.length)];
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader worldView, BlockPos pos, BlockPos blockPos, BlockPos originalPos, StructureTemplate.StructureBlockInfo structureBlockInfo2, StructurePlaceSettings structurePlacementData) {
        RandomSource random = structurePlacementData.getRandom(structureBlockInfo2.pos());
        BlockState blockState = structureBlockInfo2.state();
        BlockPos blockPos2 = structureBlockInfo2.pos();
        BlockState blockState2 = null;
        if (blockState.is(Blocks.STONE_BRICKS) || blockState.is(Blocks.STONE) || blockState.is(Blocks.CHISELED_STONE_BRICKS)) {
            blockState2 = this.processBlocks(random);
        }


        return blockState2 != null ? new StructureTemplate.StructureBlockInfo(blockPos2, blockState2, structureBlockInfo2.nbt()) : structureBlockInfo2;
    }

    @Nullable
    private BlockState processBlocks(RandomSource random) {
        if (random.nextFloat() >= 0.5F) {
            return null;
        } else {
            BlockState[] blockStates = new BlockState[]{Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), randomStairProperties(random, Blocks.STONE_BRICK_STAIRS)};
            BlockState[] blockStates2 = new BlockState[]{Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), randomStairProperties(random, Blocks.MOSSY_STONE_BRICK_STAIRS)};
            return this.process(random, blockStates, blockStates2);
        }
    }

    private BlockState process(RandomSource random, BlockState[] regularStates, BlockState[] mossyStates) {
        return random.nextFloat() < this.mossiness ? randomState(random, mossyStates) : randomState(random, regularStates);
    }

    @Override
    public MapCodec<SimpleStoneStructureProcessor> codec() {
        return CODEC;
    }


}

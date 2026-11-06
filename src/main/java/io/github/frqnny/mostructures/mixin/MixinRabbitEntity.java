package io.github.frqnny.mostructures.mixin;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Rabbit.class)
public class MixinRabbitEntity {

    @Inject(at = @At("HEAD"), method = "finalizeSpawn", cancellable = true)
    public void rabbitPlsWork(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData, CallbackInfoReturnable<SpawnGroupData> info) {
        if (spawnReason == EntitySpawnReason.STRUCTURE) {
            info.setReturnValue(new Rabbit.RabbitGroupData(Rabbit.Variant.EVIL));
        }
    }
}

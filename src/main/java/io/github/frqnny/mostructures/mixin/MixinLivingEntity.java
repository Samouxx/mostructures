package io.github.frqnny.mostructures.mixin;

import io.github.frqnny.mostructures.util.ItemUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {


    @SuppressWarnings("UnreachableCode")
    @Inject(method = "die", at = @At("TAIL"))
    public void dropItems(DamageSource source, CallbackInfo info) {

        if (((LivingEntity) (Object) this) instanceof Rabbit rabbit) {
            if (rabbit.getVariant() == Rabbit.Variant.EVIL) {
                ItemStack diamonds = new ItemStack(Items.DIAMOND);
                diamonds.setCount(Math.max(8, rabbit.getRandom().nextInt(10)));
                ItemUtils.spawnStack(rabbit.level(), rabbit.getX(), rabbit.getY() + 0.5, rabbit.getZ(), diamonds);

                ItemStack emeralds = new ItemStack(Items.EMERALD);
                emeralds.setCount(Math.max(10, rabbit.getRandom().nextInt(12)));
                ItemUtils.spawnStack(rabbit.level(), rabbit.getX(), rabbit.getY() + 0.5, rabbit.getZ(), emeralds);

                ItemStack ironIngots = new ItemStack(Items.IRON_INGOT);
                ironIngots.setCount(Math.max(18, rabbit.getRandom().nextInt(20)));
                ItemUtils.spawnStack(rabbit.level(), rabbit.getX(), rabbit.getY() + 0.5, rabbit.getZ(), ironIngots);

                ItemStack goldIngots = new ItemStack(Items.IRON_INGOT);
                goldIngots.setCount(Math.max(16, rabbit.getRandom().nextInt(18)));
                ItemUtils.spawnStack(rabbit.level(), rabbit.getX(), rabbit.getY() + 0.5, rabbit.getZ(), goldIngots);
            }
        }
    }
}

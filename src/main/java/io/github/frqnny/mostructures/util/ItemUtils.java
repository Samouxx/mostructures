package io.github.frqnny.mostructures.util;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemUtils {
    public static void spawnStack(Level world, double x, double y, double z, ItemStack stack) {
        ItemEntity item = new ItemEntity(world, x, y, z, stack);
        item.setDefaultPickUpDelay();
        world.addFreshEntity(item);
    }

}

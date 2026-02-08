package com.ethan.meanmobs.mixin;

import com.ethan.meanmobs.ai.SeekShelterGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chicken.class)
public abstract class ChickenMixin extends Animal {

    protected ChickenMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void meanmobs$registerGoals(CallbackInfo ci) {
        // run faster.
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 24.0F, 1.3D, 2.0D));
        // find dry spot.
        this.goalSelector.addGoal(2, new SeekShelterGoal(this, 1.3D));
    }
}

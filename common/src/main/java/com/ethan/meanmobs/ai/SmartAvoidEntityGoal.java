package com.ethan.meanmobs.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

import java.util.function.Predicate;

public class SmartAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {

    private final PathfinderMob mob;

    public SmartAvoidEntityGoal(PathfinderMob mob, Class<T> entityClassToAvoid, float maxDist, double walkSpeedModifier, double sprintSpeedModifier) {
        super(mob, entityClassToAvoid, maxDist, walkSpeedModifier, sprintSpeedModifier);
        this.mob = mob;
    }

    public SmartAvoidEntityGoal(PathfinderMob mob, Class<T> entityClassToAvoid, float maxDist, double walkSpeedModifier, double sprintSpeedModifier, Predicate<LivingEntity> targetPredicate) {
        super(mob, entityClassToAvoid, maxDist, walkSpeedModifier, sprintSpeedModifier, targetPredicate);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        // if angry, fight not flight.
        if (mob.getTarget() != null) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        // stop if they slap us back.
        if (mob.getTarget() != null) {
            return false;
        }
        return super.canContinueToUse();
    }
}

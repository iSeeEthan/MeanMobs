package com.ethan.meanmobs.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SeekShelterGoal extends Goal {

    private final PathfinderMob mob;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private final double speedModifier;
    private int nextStartTick;

    public SeekShelterGoal(PathfinderMob mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // optimization: don't check every tick. check every 2-4 seconds.
        // also only if it is actually raining.
        long time = this.mob.level().getGameTime();
        if (time < this.nextStartTick) {
            return false;
        }
        
        // set next check time.
        this.nextStartTick = (int) (time + 40 + this.mob.getRandom().nextInt(40));

        if (!this.mob.level().isRaining()) {
            return false;
        }

        // if already covered, chill.
        if (!this.mob.level().canSeeSky(this.mob.blockPosition())) {
            return false;
        }

        return this.findPossibleShelter();
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.shelterX, this.shelterY, this.shelterZ, this.speedModifier);
    }

    private boolean findPossibleShelter() {
        BlockPos mobPos = this.mob.blockPosition();
        
        // limit checks to 10 spots to save cpu.
        for (int i = 0; i < 10; ++i) {
            BlockPos randomPos = mobPos.offset(
                    this.mob.getRandom().nextInt(20) - 10,
                    this.mob.getRandom().nextInt(10) - 5,
                    this.mob.getRandom().nextInt(20) - 10
            );
            
            // check for roof and solid ground.
            if (!this.mob.level().canSeeSky(randomPos) 
                && this.mob.level().getBlockState(randomPos).isAir()
                && !this.mob.level().getBlockState(randomPos.below()).isAir()) {
                
                this.shelterX = randomPos.getX() + 0.5D;
                this.shelterY = randomPos.getY();
                this.shelterZ = randomPos.getZ() + 0.5D;
                return true;
            }
        }
        return false;
    }
}

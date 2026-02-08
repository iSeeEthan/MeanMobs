package com.ethan.meanmobs.mixin;

import com.ethan.meanmobs.ai.SeekShelterGoal;
import com.ethan.meanmobs.ai.SmartAvoidEntityGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Pig.class)
public abstract class PigMixin extends Animal {

    protected PigMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void meanmobs$registerGoals(CallbackInfo ci) {
        // fight back.
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        // run away.
        this.goalSelector.addGoal(2, new SmartAvoidEntityGoal<>(this, Player.class, 24.0F, 1.3D, 1.8D));
        // hide from rain.
        this.goalSelector.addGoal(3, new SeekShelterGoal(this, 1.3D));
        
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
    }

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void meanmobs$createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        // more damage.
        cir.setReturnValue(cir.getReturnValue().add(Attributes.ATTACK_DAMAGE, 2.0D));
    }
}

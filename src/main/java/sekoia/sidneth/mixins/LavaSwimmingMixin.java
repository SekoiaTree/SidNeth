package sekoia.sidneth.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public abstract class LavaSwimmingMixin {
    @Shadow public abstract boolean isSwimming();

    @Shadow public abstract void setSwimming(boolean swimming);

    @Shadow public abstract boolean isSprinting();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    @Shadow public abstract boolean hasVehicle();

    @Shadow public abstract boolean isInLava();

    @Shadow public abstract boolean isTouchingWater();

    @Shadow public abstract boolean isSubmergedInWater();

    @Inject(method = "updateSwimming", at = @At("TAIL"))
    private void allowLavaSwimming(CallbackInfo ci) {
        if (this.isSwimming()) {
            this.setSwimming(this.isSprinting() && ((this.isInLava() && isWearingNetheriteLeggingsAndBoots((LivingEntity)(Object)this))|| this.isTouchingWater()) && !this.hasVehicle());
        } else {
            this.setSwimming(this.isSprinting() && ((this.isInLava() && isWearingNetheriteLeggingsAndBoots((LivingEntity)(Object)this))|| this.isSubmergedInWater()) && !this.hasVehicle());
        }
    }

    @SuppressWarnings("MethodNameSameAsClassName")
    @Mixin(Entity.class)
    private static abstract class shouldNotLeaveSwimmingPose {
        @Shadow public abstract boolean isInLava();

        @Shadow public abstract boolean isInSwimmingPose();

        @Inject(method = "shouldLeaveSwimmingPose", cancellable = true, at = @At("HEAD"))
        private void shouldNotLeaveSwimmingPose(CallbackInfoReturnable<Boolean> cir) {
            if (isInSwimmingPose() && isInLava()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Mixin(ClientPlayerEntity.class)
    private static abstract class shouldNotLeaveSwimmingPoseClient extends PlayerEntity {

        @Shadow public Input input;

        public shouldNotLeaveSwimmingPoseClient(World world, BlockPos pos, float yaw, GameProfile profile) {
            super(world, pos, yaw, profile);
        }

        @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;setSprinting(Z)V"), slice = @Slice(
                from = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;horizontalCollision:Z"),
                to = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;isFlyingLocked()Z")
        ))
        private void resetSprintingIfNotInLava(ClientPlayerEntity clientPlayerEntity, boolean sprinting) {
            boolean bl5 = (float)this.getHungerManager().getFoodLevel() > 6.0F || this.abilities.allowFlying;
            boolean bl8 = !this.input.hasForwardMovement() || !bl5;
            boolean bl7 = bl8 || this.horizontalCollision || this.isTouchingWater() && !this.isSubmergedInWater();
            if (!this.onGround && !this.input.sneaking && bl8 || (!clientPlayerEntity.isInLava()) || bl7) {
                clientPlayerEntity.setSprinting(sprinting);
            }
        }
    }
    private boolean isWearingNetheriteLeggingsAndBoots(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.FEET).getItem() == Items.NETHERITE_BOOTS && entity.getEquippedStack(EquipmentSlot.LEGS).getItem() == Items.NETHERITE_LEGGINGS;
    }
}

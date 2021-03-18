package sekoia.sidneth.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sekoia.sidneth.SidnethInit;

public class SkystoneElytraBoostMixin {

    @Mixin(ServerPlayNetworkHandler.class)
    private static final class SkystoneElytraBoostServerMixin {
        @Shadow
        public ServerPlayerEntity player;

        @Inject(method = "onClientCommand", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;checkFallFlying()Z", shift = At.Shift.AFTER))
        private void giveBoost(ClientCommandC2SPacket packet, CallbackInfo ci) {
            float multiplier = 0;
            if (this.player.getEquippedStack(EquipmentSlot.HEAD).getItem() == SidnethInit.skystoneItems[3]) {
                multiplier += 0.4;
            }
            if (this.player.getEquippedStack(EquipmentSlot.LEGS).getItem() == SidnethInit.skystoneItems[2]) {
                multiplier += 0.4;
            }
            if (this.player.getEquippedStack(EquipmentSlot.FEET).getItem() == SidnethInit.skystoneItems[1]) {
                multiplier += 0.4;
            }
            Vec3d rot = this.player.getRotationVector();
            Vec3d vel = this.player.getVelocity();
            this.player.setVelocity(vel.add(rot.multiply(multiplier)));
        }
    }

    @Mixin(ClientPlayerEntity.class)
    private static final class SkystoneElytraBoostClientMixin extends AbstractClientPlayerEntity {
        public SkystoneElytraBoostClientMixin(ClientWorld world, GameProfile profile) {
            super(world, profile);
        }

        @Inject(method = "tickMovement", at = @At(value = "INVOKE",
                target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"),
                slice = @Slice(
                        from = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"),
                        to = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isFallFlying()Z"))
        )
        private void giveBoost(CallbackInfo ci) {
            float multiplier = 0;
            if (this.getEquippedStack(EquipmentSlot.HEAD).getItem() == SidnethInit.skystoneItems[3]) {
                multiplier += 0.4;
            }
            if (this.getEquippedStack(EquipmentSlot.LEGS).getItem() == SidnethInit.skystoneItems[2]) {
                multiplier += 0.4;
            }
            if (this.getEquippedStack(EquipmentSlot.FEET).getItem() == SidnethInit.skystoneItems[1]) {
                multiplier += 0.4;
            }
            Vec3d rot = this.getRotationVector();
            Vec3d vel = this.getVelocity();
            this.setVelocity(vel.add(rot.multiply(multiplier)));
        }
    }
}


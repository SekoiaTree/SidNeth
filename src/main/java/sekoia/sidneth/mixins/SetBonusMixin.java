package sekoia.sidneth.mixins;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sekoia.sidneth.ArmorSet;
import sekoia.sidneth.SidnethInit;
import sekoia.sidneth.setBonusInterface;

@Mixin(LivingEntity.class)
public class SetBonusMixin implements setBonusInterface {

    ArmorSet currentSet;

    @Override
    public @Nullable ArmorSet getCurrentArmorSet() {
        return currentSet;
    }

    @Override
    public void setCurrentArmorSet(ArmorSet set) {
        currentSet = set;
    }

    @Mixin(PlayerEntity.class)
    private static class PlayerEntityEquipStackMixin {
        @Inject(method = "equipStack", at = @At("HEAD"))
        private void checkForSet(EquipmentSlot slot, ItemStack stack, CallbackInfo ci) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                ((setBonusInterface)this).setCurrentArmorSet(ArmorSet.getMatchingArmorSet((LivingEntity)(Object) this));
                ServerSidePacketRegistry.INSTANCE.sendToPlayer((PlayerEntity)(Object)this, SidnethInit.SYNC_ARMOR_BONUS_PACKET_ID, new PacketByteBuf(Unpooled.buffer()));
            }
        }
    }

    @Mixin(MobEntity.class)
    private static class MobEntityEquipStackMixin {
        @Inject(method = "equipStack", at = @At("HEAD"))
        private void checkForSet(EquipmentSlot slot, ItemStack stack, CallbackInfo ci) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) ((setBonusInterface)this).setCurrentArmorSet(ArmorSet.getMatchingArmorSet((LivingEntity)(Object) this));
        }
    }

    @Mixin(ArmorStandEntity.class)
    private static class ArmorStandEntityEquipStackMixin {
        @Inject(method = "equipStack", at = @At("HEAD"))
        private void checkForSet(EquipmentSlot slot, ItemStack stack, CallbackInfo ci) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) ((setBonusInterface)this).setCurrentArmorSet(ArmorSet.getMatchingArmorSet((LivingEntity)(Object) this));
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "method_30122", at = @At("HEAD"))
    private void checkForSetMethod30122(EquipmentSlot equipmentSlot, ItemStack itemStack, CallbackInfo ci) {
        currentSet = ArmorSet.getMatchingArmorSet((LivingEntity)(Object) this);
        if ((LivingEntity)(Object)this instanceof PlayerEntity) {
            ServerSidePacketRegistry.INSTANCE.sendToPlayer((PlayerEntity)(Object)this, SidnethInit.SYNC_ARMOR_BONUS_PACKET_ID, new PacketByteBuf(Unpooled.buffer()));
        }
    }
}

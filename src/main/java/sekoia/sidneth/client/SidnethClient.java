package sekoia.sidneth.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import sekoia.sidneth.ArmorSet;
import sekoia.sidneth.SidnethInit;
import sekoia.sidneth.setBonusInterface;

@Environment(EnvType.CLIENT)
public class SidnethClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //noinspection ConstantConditions
        ClientSidePacketRegistry.INSTANCE.register(SidnethInit.SYNC_ARMOR_BONUS_PACKET_ID,
                (packetContext, attachedData) -> packetContext.getTaskQueue().execute(() -> ((setBonusInterface)MinecraftClient.getInstance().player).setCurrentArmorSet(ArmorSet.getMatchingArmorSet(MinecraftClient.getInstance().player))));
    }
}

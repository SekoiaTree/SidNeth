package sekoia.sidneth;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

public class ArmorSet {
    public static final Registry<ArmorSet> armorSets =
            FabricRegistryBuilder.createSimple(ArmorSet.class, new Identifier("sidneth", "armor_sets")).buildAndRegister();

    public static final ArmorSet AQUANITE = Registry.register(armorSets, "sidneth:aquanite", new ArmorSet(SidnethInit.aquaniteItems));
    public static final ArmorSet NETHERITE = Registry.register(armorSets, "netherite",new ArmorSet(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS));
    public static final ArmorSet SKYSTONE = Registry.register(armorSets, "sidneth:skystone", new ArmorSet(SidnethInit.skystoneItems[3], Items.ELYTRA, SidnethInit.skystoneItems[2], SidnethInit.skystoneItems[1]));

    private final Item helmet;
    private final Item chestplate;
    private final Item leggings;
    private final Item boots;

    public ArmorSet(Item helmet, Item chestplate, Item leggings, Item boots) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }
    public ArmorSet(LivingEntity entity) {
        this.helmet = entity.getEquippedStack(EquipmentSlot.HEAD).getItem();
        this.chestplate = entity.getEquippedStack(EquipmentSlot.CHEST).getItem();
        this.leggings = entity.getEquippedStack(EquipmentSlot.LEGS).getItem();
        this.boots = entity.getEquippedStack(EquipmentSlot.FEET).getItem();
    }

    public ArmorSet(Item[] items) {
        this.helmet = items[4];
        this.chestplate = items[3];
        this.leggings = items[2];
        this.boots = items[1];
    }

    public boolean isWearingSet(LivingEntity entity) {
        return this.helmet == entity.getEquippedStack(EquipmentSlot.HEAD).getItem() &&
        this.chestplate == entity.getEquippedStack(EquipmentSlot.CHEST).getItem() &&
        this.leggings == entity.getEquippedStack(EquipmentSlot.LEGS).getItem() &&
        this.boots == entity.getEquippedStack(EquipmentSlot.FEET).getItem();
    }

    @Nullable
    public static ArmorSet getMatchingArmorSet(LivingEntity entity) {
        for (ArmorSet i : armorSets) {
            if (i.isWearingSet(entity)) {
                return i;
            }
        }
        return null;
    }
}

package sekoia.sidneth.abstractions;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;

public abstract class AbstractArmorMaterial implements ArmorMaterial {

    private static final int[] BASE_DURABILITY = new int[] {407, 592, 555, 481};
    private static final int[] PROTECTION_VALUES = new int[] {3, 6, 8, 3};
    private static final int DURABILITY_MULTIPLIER = 37;
    private final String name;
    private Item materialItem;

    public AbstractArmorMaterial(String name) {
        this.name = name;
    }

    public AbstractArmorMaterial(String name, Item materialItem) {
        this.name = name;
        this.materialItem = materialItem;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * DURABILITY_MULTIPLIER;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_VALUES[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return Integer.MAX_VALUE;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(materialItem);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return 3.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0F;
    }

    public void register() {
        Item material = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
        materialItem = Registry.register(Registry.ITEM, "sidneth:" + name, material);
    }
}


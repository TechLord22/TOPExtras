package topextras.providers;

import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import topextras.Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MinecartInfoProvider implements IProbeInfoEntityProvider {

    private final List<ItemStack> stacks = new ArrayList<>();
    private final Set<Item> foundItems = new HashSet<>();

    @Override
    public String getID() {
        return Utilities.getProviderId("minecart");
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {
        // hopper and chest minecarts
        if (entity instanceof EntityMinecartContainer) {
            int maxSlots;
            if (entity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
                IItemHandler capability = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (capability == null) {
                    stacks.clear();
                    foundItems.clear();
                    return;
                }

                maxSlots = capability.getSlots();
                for (int i = 0; i < maxSlots; i++) {
                    Utilities.addItemStack(stacks, foundItems, capability.getStackInSlot(i));
                }
            } else {
                IInventory inventory = (IInventory) entity;
                maxSlots = inventory.getSizeInventory();
                for (int i = 0; i < maxSlots; i++) {
                    Utilities.addItemStack(stacks, foundItems, inventory.getStackInSlot(i));
                }
            }
            if (!stacks.isEmpty()) Utilities.showChestContents(probeInfo, stacks, mode);
            stacks.clear();
            foundItems.clear();
        }
    }
}

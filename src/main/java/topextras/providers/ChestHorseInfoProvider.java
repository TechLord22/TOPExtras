package topextras.providers;

import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import topextras.Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChestHorseInfoProvider implements IProbeInfoEntityProvider {

    private final List<ItemStack> stacks = new ArrayList<>();
    private final Set<Item> foundItems = new HashSet<>();

    @Override
    public String getID() {
        return Utilities.getProviderId("chest_horse");
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {
        // hopper and chest minecarts
        if (entity instanceof AbstractChestHorse && ((AbstractChestHorse) entity).hasChest()) {
            int maxSlots;
            if (entity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
                IItemHandler capability = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (capability == null) {
                    stacks.clear();
                    foundItems.clear();
                    return;
                }

                maxSlots = capability.getSlots();
                // start at 3 to ignore armor/saddle
                for (int i = 3; i < maxSlots; i++) {
                    Utilities.addItemStack(stacks, foundItems, capability.getStackInSlot(i));
                }
            } else {
                NBTTagCompound compound = entity.writeToNBT(new NBTTagCompound());
                if (!compound.hasKey("Items")) {
                    stacks.clear();
                    foundItems.clear();
                    return;
                }

                NBTTagList list = compound.getTagList("Items", 10);
                for (int i = 0; i < list.tagCount(); i++) {
                    NBTTagCompound tagCompound = list.getCompoundTagAt(i);
                    int slot = tagCompound.getByte("Slot") & 255;

                    // start at 3 to ignore armor/saddle
                    if (slot > 2) Utilities.addItemStack(stacks, foundItems, new ItemStack(tagCompound));
                }
            }
            if (!stacks.isEmpty()) Utilities.showChestContents(probeInfo, stacks, mode);
            stacks.clear();
            foundItems.clear();
        }
    }
}

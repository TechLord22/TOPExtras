package topextras;


import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;
import mcjty.theoneprobe.config.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

public class Utilities {

    public static final String MODID_DYNAMIC_TREES = "dynamictrees";
    public static final String MODID_APOTHEOSIS = "apotheosis";

    public static final DecimalFormat FORMAT = new DecimalFormat("#,###.#");

    @Nonnull
    public static String getProviderId(@Nonnull String name) {
        return String.format("%s:%s_provider", TopExtras.MODID, name);
    }

    public static void addItemStack(@Nonnull List<ItemStack> stacks, @Nonnull Set<Item> foundItems, @Nonnull ItemStack stack) {
        if (stack.isEmpty()) return;
        if (foundItems.contains(stack.getItem())) {
            for (ItemStack s : stacks) {
                if (ItemHandlerHelper.canItemStacksStack(s, stack)) {
                    s.grow(stack.getCount());
                    return;
                }
            }
        }
        // If we come here we need to append a new stack
        stacks.add(stack.copy());
        foundItems.add(stack.getItem());
    }

    public static void showChestContents(@Nonnull IProbeInfo probeInfo, @Nonnull List<ItemStack> stacks, @Nonnull ProbeMode mode) {
        IProbeInfo vertical = probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(Config.chestContentsBorderColor).spacing(0));
        int rows = 0;
        int idx = 0;

        if (Tools.show(mode, Config.getRealConfig().getShowChestContentsDetailed()) && stacks.size() <= Config.showItemDetailThresshold) {
            for (ItemStack stackInSlot : stacks) {
                vertical.horizontal(new LayoutStyle().spacing(10).alignment(ElementAlignment.ALIGN_CENTER))
                        .item(stackInSlot, new ItemStyle().width(16).height(16))
                        .text(TextStyleClass.INFO + stackInSlot.getDisplayName());
            }
        } else {
            IProbeInfo horizontal = null;
            for (ItemStack stackInSlot : stacks) {
                if (idx % 10 == 0) {
                    horizontal = vertical.horizontal(new LayoutStyle().spacing(0));
                    rows++;
                    if (rows > 4) break;
                }
                horizontal.item(stackInSlot);
                idx++;
            }
        }
    }
}

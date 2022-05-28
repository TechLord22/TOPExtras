package topextras.providers;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import topextras.Utilities;

import javax.annotation.Nonnull;
import java.util.Collections;

public class CauldronInfoProvider implements IProbeInfoProvider {

    private static final ItemStack WATER_BUCKET = new ItemStack(Items.WATER_BUCKET);
    private static final ItemStack WATTER_BOTTLE = Items.POTIONITEM.getDefaultInstance();
    private static final ItemStack BUCKET = new ItemStack(Items.BUCKET);

    @Override
    public String getID() {
        return Utilities.getProviderId("cauldron");
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, @Nonnull IBlockState blockState, IProbeHitData data) {
        if (blockState.getBlock() instanceof BlockCauldron) {
            for (IProperty<?> property : blockState.getProperties().keySet()) {
                if (!"level".equals(property.getName())) continue;
                if (property.getValueClass() == Integer.class) {
                    //noinspection unchecked
                    IProperty<Integer> integerProperty = (IProperty<Integer>) property;
                    int fill = blockState.getValue(integerProperty);
                    int maxFill = Collections.max(integerProperty.getAllowedValues());

                    IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));

                    if (fill > 0) {
                        horizontalPane.item((fill == maxFill) ? WATER_BUCKET : WATTER_BOTTLE);
                        horizontalPane.text(TextStyleClass.LABEL + "" + fill + ((fill == 1) ? " {*topextras.top.cauldron_fill_1*}" : " {*topextras.top.cauldron_fill_2*}"));
                    } else {
                        horizontalPane.item(BUCKET);
                        horizontalPane.text(TextStyleClass.LABEL + "{*topextras.top.empty*} ");
                    }
                    return;
                }
            }
        }
    }
}

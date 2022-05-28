package topextras.providers;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockStem;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import topextras.Utilities;

import javax.annotation.Nonnull;
import java.util.Collections;

public class CropInfoProvider implements IProbeInfoProvider {

    private static final ItemStack PUMPKIN = new ItemStack(Blocks.PUMPKIN);
    private static final ItemStack MELON = new ItemStack(Blocks.MELON_BLOCK);
    private static final ItemStack COCOA = new ItemStack(Items.DYE, 1, 3);

    @Override
    public String getID() {
        return Utilities.getProviderId("crop");
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, @Nonnull IBlockState blockState, IProbeHitData data) {
        Block block = blockState.getBlock();
        if (block instanceof BlockStem || block instanceof BlockCocoa) {
            for (IProperty<?> property : blockState.getProperties().keySet()) {
                if (!"age".equals(property.getName())) continue;
                if (property.getValueClass() == Integer.class) {
                    //noinspection unchecked
                    IProperty<Integer> integerProperty = (IProperty<Integer>) property;
                    int age = blockState.getValue(integerProperty);
                    int maxAge = Collections.max(integerProperty.getAllowedValues());

                    IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                    if (block == Blocks.PUMPKIN_STEM) horizontalPane.item(PUMPKIN);
                    else if (block == Blocks.MELON_STEM) horizontalPane.item(MELON);
                    else if (block == Blocks.COCOA) horizontalPane.item(COCOA);

                    if (age == maxAge) {
                        horizontalPane.text(TextStyleClass.OK + "{*topextras.top.growth_finished*}");
                    } else {
                        horizontalPane.text(TextStyleClass.LABEL + "{*topextras.top.growth*} " + TextStyleClass.WARNING + (age * 100 / maxAge) + "%");
                    }
                    return;
                }
                return;
            }
        }
    }
}

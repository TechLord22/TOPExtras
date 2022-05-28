package topextras.providers;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import topextras.Utilities;

import javax.annotation.Nonnull;

public class EnchantingPowerInfoProvider implements IProbeInfoProvider {

    private static final ItemStack ENCHANTED_BOOK = new ItemStack(Items.ENCHANTED_BOOK);

    @Override
    public String getID() {
        return Utilities.getProviderId("enchanting_power");
    }

    @Override
    public void addProbeInfo(ProbeMode mode, @Nonnull IProbeInfo probeInfo, EntityPlayer player, @Nonnull World world, @Nonnull IBlockState blockState, @Nonnull IProbeHitData data) {
        float enchantingPower = ForgeHooks.getEnchantPower(world, data.getPos());
        if (blockState.getBlock().hasTileEntity(blockState) && world.getTileEntity(data.getPos()) instanceof TileEntityEnchantmentTable) {
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(data.getPos());
            enchantingPower = 0.0F;
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if ((x != 0 || z != 0) && world.isAirBlock(pos.add(z, 0, x)) && world.isAirBlock(pos.add(z, 1, x))) {
                        enchantingPower += ForgeHooks.getEnchantPower(world, pos.add(z * 2, 0, x * 2));
                        enchantingPower += ForgeHooks.getEnchantPower(world, pos.add(z * 2, 1, x * 2));
                        if (z != 0 && x != 0) {
                            enchantingPower += ForgeHooks.getEnchantPower(world, pos.add(z * 2, 0, x));
                            enchantingPower += ForgeHooks.getEnchantPower(world, pos.add(z * 2, 1, x));
                            enchantingPower += ForgeHooks.getEnchantPower(world, pos.add(z, 0, x * 2));
                            enchantingPower += ForgeHooks.getEnchantPower(world, pos.add(z, 1, x * 2));
                        }
                    }
                }
            }
        }
        if (enchantingPower > 0.0F) {
            probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                    .item(ENCHANTED_BOOK)
                    .text(TextStyleClass.LABEL + "{*topextras.top.enchanting_power*} " + TextFormatting.LIGHT_PURPLE + Utilities.FORMAT.format(enchantingPower));
        }
    }
}

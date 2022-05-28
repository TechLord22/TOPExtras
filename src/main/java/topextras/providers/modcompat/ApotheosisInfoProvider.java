package topextras.providers.modcompat;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import shadows.ench.anvil.EnchantmentSplitting;
import shadows.ench.anvil.TileAnvil;
import shadows.spawn.TileSpawnerExt;
import topextras.Utilities;

import javax.annotation.Nonnull;

public class ApotheosisInfoProvider implements IProbeInfoProvider {

    private static final Enchantment SPLITTING = new EnchantmentSplitting();

    @Override
    public String getID() {
        return Utilities.getProviderId("apotheosis");
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, @Nonnull World world, @Nonnull IBlockState blockState, @Nonnull IProbeHitData data) {
        if (blockState.getBlock() == Blocks.MOB_SPAWNER) {
            // apotheosis mob spawners
            TileEntity tileEntity = world.getTileEntity(data.getPos());
            if (tileEntity instanceof TileSpawnerExt) {
                TileSpawnerExt spawner = (TileSpawnerExt) tileEntity;
                MobSpawnerBaseLogic logic = spawner.getSpawnerBaseLogic();

                probeInfo.text(TextStyleClass.LABEL + "{*topextras.apotheosis.delay*} " + TextStyleClass.OK +
                                logic.minSpawnDelay + "-" + logic.maxSpawnDelay + TextStyleClass.LABEL + " {*topextras.unit.ticks*}")
                        .text(TextStyleClass.LABEL + "{*topextras.apotheosis.spawn_count*} " + TextStyleClass.OK + logic.spawnCount)
                        .text(TextStyleClass.LABEL + "{*topextras.apotheosis.max_nearby*} " + TextStyleClass.OK + logic.maxNearbyEntities)
                        .text(TextStyleClass.LABEL + "{*topextras.apotheosis.player_range*} " + TextStyleClass.OK + logic.activatingRangeFromPlayer)
                        .text(TextStyleClass.LABEL + "{*topextras.apotheosis.spawn_range*} " + TextStyleClass.OK + logic.spawnRange);

                if (player.isSneaking()) {
                    if (spawner.ignoresPlayers) probeInfo.text(TextStyleClass.INFOIMP + "{*topextras.apotheosis.ignores_players*}");
                    if (spawner.ignoresConditions) probeInfo.text(TextStyleClass.INFOIMP + "{*topextras.apotheosis.ignores_conditions*}");
                    if (spawner.ignoresCap) probeInfo.text(TextStyleClass.INFOIMP + "{*topextras.apotheosis.ignores_cap*}");
                    if (spawner.redstoneEnabled) probeInfo.text(TextStyleClass.INFOIMP + "{*topextras.apotheosis.requires_redstone*}");
                }
            }
        } else if (blockState.getBlock() == Blocks.ANVIL) {
            TileEntity tileEntity = world.getTileEntity(data.getPos());
            if (tileEntity instanceof TileAnvil) {
                TileAnvil anvil = (TileAnvil) tileEntity;
                int unbreaking = anvil.getUnbreaking();
                int splitting = anvil.getSplitting();
                if (unbreaking > 0) probeInfo.text(TextStyleClass.OK + Enchantments.UNBREAKING.getTranslatedName(unbreaking));
                if (splitting > 0) probeInfo.text(TextStyleClass.OK + SPLITTING.getTranslatedName(splitting));
            }
        }
    }
}

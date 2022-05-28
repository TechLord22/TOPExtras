package topextras.providers;

import mcjty.theoneprobe.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import topextras.Utilities;

public class TNTInfoProvider implements IProbeInfoEntityProvider {

    @Override
    public String getID() {
        return Utilities.getProviderId("tnt");
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof EntityTNTPrimed) {
            probeInfo.text(TextStyleClass.LABEL + "{*topextras.top.tnt_fuse*} " + TextStyleClass.WARNING + StringUtils.ticksToElapsedTime(((EntityTNTPrimed) entity).getFuse()));
        }
    }
}

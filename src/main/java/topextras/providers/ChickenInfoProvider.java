package topextras.providers;

import mcjty.theoneprobe.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import topextras.Utilities;

public class ChickenInfoProvider implements IProbeInfoEntityProvider {

    @Override
    public String getID() {
        return Utilities.getProviderId("chicken");
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof EntityChicken) {
            EntityChicken chicken = (EntityChicken) entity;
            probeInfo.text(TextStyleClass.LABEL + "{*topextras.top.chicken_egg*} " + TextStyleClass.INFOIMP + StringUtils.ticksToElapsedTime(chicken.timeUntilNextEgg));
        }
    }
}

package topextras.providers;

import mcjty.theoneprobe.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import topextras.Utilities;

public class BreedingInfoProvider implements IProbeInfoEntityProvider {

    @Override
    public String getID() {
        return Utilities.getProviderId("breeding");
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof EntityAnimal) {
            EntityAnimal animal = (EntityAnimal) entity;
            int age = animal.getGrowingAge();

            // adult
            if (age > 0) {
                probeInfo.text(TextStyleClass.LABEL + "{*topextras.top.breeding_cooldown*} " + TextStyleClass.INFOIMP + StringUtils.ticksToElapsedTime(age));
            }
        }

    }
}

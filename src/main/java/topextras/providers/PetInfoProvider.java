package topextras.providers;

import mcjty.theoneprobe.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import topextras.Utilities;

public class PetInfoProvider implements IProbeInfoEntityProvider {

    @Override
    public String getID() {
        return Utilities.getProviderId("pet");
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof EntityTameable) {
            EntityTameable tameable = (EntityTameable) entity;

            if (tameable.isTamed()) {
                probeInfo.text(TextStyleClass.OK + "{*topextras.top.tamed*}");
                probeInfo.text(TextStyleClass.LABEL + (tameable.isSitting() ? "{*topextras.top.sitting*}" : "{*topextras.top.standing*}"));
            } else {
                probeInfo.text(TextStyleClass.LABEL + "{*topextras.top.not_tamed*}");
            }
        }
    }
}

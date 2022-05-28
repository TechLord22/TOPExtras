package topextras.providers;

import mcjty.theoneprobe.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import topextras.Utilities;

public class PaintingInfoProvider implements IProbeInfoEntityProvider {

    @Override
    public String getID() {
        return Utilities.getProviderId("painting");
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof EntityPainting) {
            NBTTagCompound compound = entity.writeToNBT(new NBTTagCompound());
            if (compound.hasKey("Motive")) {
                probeInfo.text(TextStyleClass.INFO + TextFormatting.ITALIC.toString() +
                        StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(compound.getString("Motive")), ' '));
            }
        }
    }
}

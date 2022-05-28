package topextras;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import topextras.providers.*;

import javax.annotation.Nonnull;

@Mod(name = TopExtras.NAME, modid = TopExtras.MODID, version = TopExtras.VERSION, dependencies = "required-after:theoneprobe@[1.4.8,);after:dynamictrees@[0.9.25,)")
public class TopExtras {

    public static final String MODID = "topextras";
    public static final String NAME = "Top Extras";
    public static final String VERSION = "@VERSION@";

    @Mod.EventHandler
    public static void onInit(@Nonnull FMLInitializationEvent event) {
        ITheOneProbe theOneProbe = TheOneProbe.theOneProbeImp;

        // Vanilla
        theOneProbe.registerProvider(new FurnaceInfoProvider());
        theOneProbe.registerProvider(new CropInfoProvider());
        theOneProbe.registerProvider(new JukeboxProvider());
        theOneProbe.registerProvider(new CauldronInfoProvider());
        theOneProbe.registerProvider(new EnchantingPowerInfoProvider());
        theOneProbe.registerEntityProvider(new BreedingInfoProvider());
        theOneProbe.registerEntityProvider(new PetInfoProvider());
        theOneProbe.registerEntityProvider(new TNTInfoProvider());
        theOneProbe.registerEntityProvider(new MinecartInfoProvider());
        theOneProbe.registerEntityProvider(new ChestHorseInfoProvider());
        theOneProbe.registerEntityProvider(new PaintingInfoProvider());
        theOneProbe.registerEntityProvider(new ChickenInfoProvider());

        // Mod Compat
        if (Loader.isModLoaded(Utilities.MODID_DYNAMIC_TREES)) {
            theOneProbe.registerProvider(new DynamicTreesInfoProvider());
        }
    }
}

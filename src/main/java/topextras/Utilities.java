package topextras;


import javax.annotation.Nonnull;
import java.text.DecimalFormat;

public class Utilities {

    public static final String MODID_DYNAMIC_TREES = "dynamictrees";

    public static final DecimalFormat FORMAT = new DecimalFormat("#,###.#");

    @Nonnull
    public static String getProviderId(@Nonnull String name) {
        return String.format("%s:%s_provider", TopExtras.MODID, name);
    }
}

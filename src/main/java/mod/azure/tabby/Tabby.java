package mod.azure.tabby;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Tabby.MODID)
public class Tabby {
	public static final String MODID = "tabby";

	public Tabby() {
		MinecraftForge.EVENT_BUS.register(this);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TabbyConfig.CONFIG_SPEC, "tabby_config.toml");
		TabbyConfig.loadConfig(TabbyConfig.CONFIG_SPEC, FMLPaths.CONFIGDIR.get().resolve("tabby_config.toml").toString());
	}
}

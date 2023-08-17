package mod.azure.tabby;

import java.io.File;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class TabbyConfig {
	public static final ForgeConfigSpec CONFIG_SPEC;
	public static ConfigValue<Long> maxCount;
	public static IntValue maxRows;
	public static BooleanValue adaptive;
	public static IntValue adaptiveDivisor;

	static {
		ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
		setupConfig(configBuilder);
		CONFIG_SPEC = configBuilder.build();
	}

	private static void setupConfig(ForgeConfigSpec.Builder builder) {
		maxCount = builder.comment("The maximum players that can be rendered, set to 0 for the max to always be the current online player count").define("max_count", 0L);
		maxRows = builder.comment("The maximum rows that can be rendered, this value is not used if adaptive is set to true").defineInRange("max_rows", 40, 1, 100);
		adaptive = builder.comment("Set to true for the amount of rows to adapt based off how many players there are").define("adaptive", false);
		adaptiveDivisor = builder.comment("The amount to divide the players online by to determine how many rows will be rendered when adaptive is set to true\nFormula: x / y = maxRows\nWhere x is the value of maxCount and y is the adaptiveDivisor value").defineInRange("adaptive_divisor", 5, 1, 10);
	}

	public static void loadConfig(ForgeConfigSpec config, String path) {
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
		file.load();
		config.setConfig(file);
	}
}

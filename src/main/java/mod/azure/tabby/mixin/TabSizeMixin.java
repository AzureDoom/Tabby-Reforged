package mod.azure.tabby.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import mod.azure.tabby.TabbyConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlayerTabOverlay;

@Mixin(PlayerTabOverlay.class)
public class TabSizeMixin {

	@Shadow
	@Final
	private Minecraft minecraft;

	@ModifyConstant(constant = @Constant(longValue = 80L), method = "getPlayerInfos")
	private long modifyCount(long count) {
		if (TabbyConfig.maxCount.get() <= 0) {
			return minecraft.player.connection.getOnlinePlayers().size();
		}
		return TabbyConfig.maxCount.get();
	}

	@ModifyConstant(constant = @Constant(intValue = 20), method = "render")
	private int modifyMaxRows(int MAX_ROWS) {
		if (TabbyConfig.adaptive.get()) {
			if (TabbyConfig.maxCount.get() <= 0)
				return Math.max(1, minecraft.player.connection.getOnlinePlayers().size() / TabbyConfig.adaptiveDivisor.get());
			return (int) Math.max(1, TabbyConfig.maxCount.get() / TabbyConfig.adaptiveDivisor.get());
		}
		return Math.max(1, TabbyConfig.maxRows.get());
	}
}

package mod.azure.tabby.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import mod.azure.tabby.TabbyConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;

@Mixin(PlayerTabOverlay.class)
public class TabSizeMixin {

	@Shadow
	@Final
	private Minecraft minecraft;

	@ModifyVariable(method = "render", at = @At("STORE"), ordinal = 1)
	private List<PlayerInfo> modifyCount(List<PlayerInfo> list) {
		return PlayerTabOverlay.f_94518_.sortedCopy(this.minecraft.player.connection.getOnlinePlayers()).subList(0, (int) Math.min(PlayerTabOverlay.f_94518_.sortedCopy(this.minecraft.player.connection.getOnlinePlayers()).size(), TabbyConfig.maxCount.get()));
	}

	@ModifyConstant(constant = @Constant(intValue = 20), method = "render")
	private int modifyMaxRows(int MAX_ROWS) {
		if (TabbyConfig.adaptive.get()) {
			if (TabbyConfig.maxCount.get() <= 0) {
				int onlinePlayers = minecraft.player.connection.getOnlinePlayers().size();
				return Math.max(1, onlinePlayers / TabbyConfig.adaptiveDivisor.get());
			}
			return (int) Math.max(1, TabbyConfig.maxCount.get() / TabbyConfig.adaptiveDivisor.get());
		}
		return Math.max(1, TabbyConfig.maxRows.get());
	}
}

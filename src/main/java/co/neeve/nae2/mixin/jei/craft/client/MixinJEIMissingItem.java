package co.neeve.nae2.mixin.jei.craft.client;

import appeng.integration.modules.jei.JEIMissingItem;
import com.llamalad7.mixinextras.sugar.Local;
import mezz.jei.gui.recipes.RecipeTransferButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = JEIMissingItem.class, remap = false)
public class MixinJEIMissingItem {
	@Shadow
	@Final
	private List<Integer> craftableSlots;

	@Shadow
	@Final
	private List<Integer> foundSlots;

	@SuppressWarnings("deprecation")
	@Inject(method = "showError", at = @At(
		value = "INVOKE",
		target = "Lmezz/jei/gui/TooltipRenderer;drawHoveringText(Lnet/minecraft/client/Minecraft;Ljava/util/List;II)V"
	))
	public void showError(CallbackInfo ci,
	                      @Local(name = "foundAnyCraftable") boolean foundAnyCraftable,
	                      @Local(name = "tooltipLines") List<String> tooltipLines,
	                      @Local(name = "b") RecipeTransferButton b) {
		if (foundAnyCraftable && craftableSlots.size() + foundSlots.size() == 9) {
			tooltipLines.add("");
			tooltipLines.add(I18n.translateToLocal("nae2.jei.missing.craft.1"));
			tooltipLines.add(I18n.translateToLocal("nae2.jei.missing.craft.2"));
			b.enabled = GuiScreen.isCtrlKeyDown();
		}
	}
}
package team.mixxit.allotment.interf;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IItemColorProvider {
    @OnlyIn(Dist.CLIENT)
    IItemColor getItemColor(ItemColors colors);
}
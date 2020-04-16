package neo_ores.gui;

import neo_ores.mana.PlayerManaCalcClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public class GuiNeoGameOverlay extends Gui
{
	public static final ResourceLocation MPIcon = new ResourceLocation("neo_ores:textures/gui/mana_info.png");
	protected final Minecraft mc;
	protected final GuiNewChat persistantChatGUI;
    public PlayerManaCalcClient manaCalc = new PlayerManaCalcClient();
    
	public GuiNeoGameOverlay(Minecraft minecraft) 
	{
		this.mc = minecraft;
		this.persistantChatGUI = new GuiNewChat(minecraft);
		this.renderGameOverlay();
	}
	
	@SuppressWarnings("static-access")
	public void renderGameOverlay()
	{
		ScaledResolution scaled = new ScaledResolution(mc);
		int width = scaled.getScaledWidth() / 2 + 10;
        int height = scaled.getScaledHeight() - 49;
		FontRenderer fontrenderer = this.mc.fontRenderer;
		mc.getTextureManager().bindTexture(this.MPIcon);
		
		if(mc.player != null && mc.playerController.gameIsSurvivalOrAdventure() && manaCalc.getML(mc.player) > 0)
		{	
			this.mc.mcProfiler.startSection("manaBar");
			if(mc.player.getAir() < 300 && mc.player.getAir() > 0)
			{
				height -= 20;
				
				this.drawTexturedModalRect(width, height, 0, 0, 81, 5);
				float xp_calc = 1.003F; 
				for(int i = 0;i < manaCalc.getML(mc.player);i++)
				{
					xp_calc *= 1.003F;
				}
				int xp_bar = (int)((float)manaCalc.getMXP(mc.player) / (16.0F * xp_calc) * 81.0F);
				if(xp_bar > 0)
				{
					this.drawTexturedModalRect(width, height, 0, 5, xp_bar, 5);
				}
				this.drawTexturedModalRect(width, height + 4, 0, 10, 81, 5);
				int mp_bar = (int)((float)manaCalc.getMP(mc.player) / (float)manaCalc.getMaxMP(mc.player) * 81.0F);
				if(mp_bar > 0)
				{
					this.drawTexturedModalRect(width, height + 4, 0, 15, mp_bar, 5);
				}
				
				width += 83;
				String level = I18n.translateToLocal("gui.overlay.level") + ":" + manaCalc.getML(mc.player);
				fontrenderer.drawString(level, width - 1, height, Integer.parseInt("000000", 16));
				fontrenderer.drawString(level, width + 1, height, Integer.parseInt("000000", 16));
				fontrenderer.drawString(level, width, height - 1, Integer.parseInt("000000", 16));
				fontrenderer.drawString(level, width, height + 1, Integer.parseInt("000000", 16));
				fontrenderer.drawString(level, width, height, Integer.parseInt("66AAFF", 16));
				
				height += 10;
				String mana = I18n.translateToLocal("gui.overlay.mana") + ":" + manaCalc.getMP(mc.player) + "/" + manaCalc.getMaxMP(mc.player);
				fontrenderer.drawString(mana, width - 1, height, Integer.parseInt("000000", 16));
				fontrenderer.drawString(mana, width + 1, height, Integer.parseInt("000000", 16));
				fontrenderer.drawString(mana, width, height - 1, Integer.parseInt("000000", 16));
				fontrenderer.drawString(mana, width, height + 1, Integer.parseInt("000000", 16));
				fontrenderer.drawString(mana, width , height, Integer.parseInt("66AAFF", 16));
			}
			else
			{
				this.drawTexturedModalRect(width, height, 0, 0, 81, 5);
				float xp_calc = 1.003F; 
				for(int i = 0;i < manaCalc.getML(mc.player);i++)
				{
					xp_calc *= 1.003F;
				}
				int xp_bar = (int)((float)manaCalc.getMXP(mc.player) / (16.0F * xp_calc) * 81.0F);
				if(xp_bar > 0)
				{
					this.drawTexturedModalRect(width, height, 0, 5, xp_bar, 5);
				}
				this.drawTexturedModalRect(width, height + 4, 0, 10, 81, 5);
				int mp_bar = (int)((float)manaCalc.getMP(mc.player) / (float)manaCalc.getMaxMP(mc.player) * 81.0F);
				if(mp_bar > 0)
				{
					this.drawTexturedModalRect(width, height + 4, 0, 15, mp_bar, 5);
				}
				
				width += 83;
				String level = I18n.translateToLocal("gui.overlay.level") + ":" + manaCalc.getML(mc.player);
				fontrenderer.drawString(level, width - 1, height, Integer.parseInt("000000", 16));
				fontrenderer.drawString(level, width + 1, height, Integer.parseInt("000000", 16));
				fontrenderer.drawString(level, width, height - 1, Integer.parseInt("000000", 16));
				fontrenderer.drawString(level, width, height + 1, Integer.parseInt("000000", 16));
				fontrenderer.drawString(level, width, height, Integer.parseInt("66AAFF", 16));
				
				height += 10;
				String mana = I18n.translateToLocal("gui.overlay.mana") + ":" + manaCalc.getMP(mc.player) + "/" + manaCalc.getMaxMP(mc.player);
				fontrenderer.drawString(mana, width - 1, height, Integer.parseInt("000000", 16));
				fontrenderer.drawString(mana, width + 1, height, Integer.parseInt("000000", 16));
				fontrenderer.drawString(mana, width, height - 1, Integer.parseInt("000000", 16));
				fontrenderer.drawString(mana, width, height + 1, Integer.parseInt("000000", 16));
				fontrenderer.drawString(mana, width , height, Integer.parseInt("66AAFF", 16));
			}
			this.mc.mcProfiler.endSection();
		}
	}
}

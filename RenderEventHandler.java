package com.example.playerradar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class RenderEventHandler extends Gui {

    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL || mc.currentScreen != null) {
            return;
        }

        EntityPlayerSP clientPlayer = mc.player;
        if (clientPlayer == null) return;

        int screenWidth = event.getResolution().getScaledWidth();
        int centerX = screenWidth / 2;
        int centerY = 25;

        drawRect(centerX - 120, centerY, centerX + 120, centerY + 1, 0xAAFFFFFF);

        for (EntityPlayer player : mc.world.playerEntities) {
            if (player == clientPlayer || player.isInvisible()) {
                continue;
            }

            double dx = player.posX - clientPlayer.posX;
            double dz = player.posZ - clientPlayer.posZ;
            
            float angleToPlayer = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90.0F;
            float relativeAngle = MathHelper.wrapDegrees(angleToPlayer - clientPlayer.rotationYaw);

            if (relativeAngle >= -90 && relativeAngle <= 90) {
                int iconX = centerX + (int) (relativeAngle * 1.3); 
                int distance = (int) clientPlayer.getDistance(player);

                NetworkPlayerInfo netInfo = mc.getConnection().getPlayerInfo(player.getUniqueID());
                if (netInfo != null) {
                    mc.getTextureManager().bindTexture(netInfo.getLocationSkin());
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    Gui.drawScaledCustomSizeModalRect(iconX - 6, centerY - 14, 8, 8, 8, 8, 12, 12, 64, 64);
                }

                String distStr = distance + "m";
                int textWidth = mc.fontRenderer.getStringWidth(distStr);
                mc.fontRenderer.drawStringWithShadow(distStr, iconX - (textWidth / 2), centerY + 4, 0x00FF00);
            }
        }
    }
}

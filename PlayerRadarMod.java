package com.example.playerradar;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = PlayerRadarMod.MODID, name = PlayerRadarMod.NAME, version = PlayerRadarMod.VERSION, clientSideOnly = true)
public class PlayerRadarMod {
    public static final String MODID = "playerradar";
    public static final String NAME = "Player Radar HUD";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
    }
}

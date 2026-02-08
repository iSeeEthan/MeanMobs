package com.ethan.meanmobs.neoforge;

import com.ethan.meanmobs.MeanMobs;
import net.neoforged.fml.common.Mod;

@Mod(MeanMobs.MOD_ID)
public final class MeanMobsNeoForge {
    public MeanMobsNeoForge() {
        // Run our common setup.
        MeanMobs.init();
    }
}

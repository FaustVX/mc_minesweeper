package fr.faustvx.minesweeper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.faustvx.minesweeper.blocks.Mine;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Minesweeper.MOD_ID)
public final class Minesweeper
{
    public static final String MOD_ID = "minesweeper";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Minesweeper()
    {
        LOGGER.info("{} registered", MOD_ID);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Mine.BLOCKS.register(modEventBus);
    }
}

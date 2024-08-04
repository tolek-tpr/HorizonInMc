package pl.epsi;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.epsi.blocks.HorizonBlocks;
import pl.epsi.items.HorizonItems;

public class HorizonInMc implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("horizoninmc");
	public static final String MOD_ID = "horizoninmc";

	@Override
	public void onInitialize() {
		HorizonItems.initialize();
		HorizonBlocks.initialize();
		LOGGER.info("Hello Fabric world!");
	}

}
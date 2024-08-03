package pl.epsi;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HorizonInMc implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("horizoninmc");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}

}
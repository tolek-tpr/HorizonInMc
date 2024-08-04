package pl.epsi;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.epsi.blocks.HorizonBlocks;
import pl.epsi.event.EventImpl;
import pl.epsi.events.MainMenuKeybindListener;
import pl.epsi.items.HorizonItems;

import java.util.ArrayList;

public class HorizonInMc implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("horizoninmc");
	public static final String MOD_ID = "horizoninmc";
	public ArrayList<EventImpl> events = new ArrayList<>();

	@Override
	public void onInitialize() {
		HorizonItems.initialize();
		HorizonBlocks.initialize();
		LOGGER.info("Hello Fabric world!");

		events.add(new MainMenuKeybindListener());

		events.forEach(e -> e.setEnabled(true));
	}

}
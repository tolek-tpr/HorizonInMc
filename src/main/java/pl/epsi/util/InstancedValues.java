package pl.epsi.util;

import pl.epsi.gui.MainMenuScreenE;
import pl.epsi.gui.QuestScreen;

public class InstancedValues {

    private static InstancedValues instance;

    private InstancedValues() {}

    public static InstancedValues getInstance() {
        if (instance == null) instance = new InstancedValues();
        return instance;
    }

    public MainMenuScreenE screen;

}

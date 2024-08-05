package pl.epsi.util;

import pl.epsi.gui.MainMenuScreenE;
import pl.epsi.gui.QuestScreen;

import java.util.ArrayList;

public class InstancedValues {

    private static InstancedValues instance;

    private InstancedValues() {}

    public static InstancedValues getInstance() {
        if (instance == null) instance = new InstancedValues();
        return instance;
    }

    public MainMenuScreenE screen;
    public ArrayList<Integer> screenExclamationUpdate = new ArrayList<>();

}

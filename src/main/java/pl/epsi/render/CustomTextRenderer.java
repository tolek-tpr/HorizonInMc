package pl.epsi.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CustomTextRenderer {

    private DrawContext context;
    private Identifier textMap = new Identifier();

    public CustomTextRenderer(DrawContext context) {
        this.context = context;
    }

    public void renderText(Text text, int x, int y) {
        String stringText = text.getString();
        String[] letters = stringText.split("");


    }

}

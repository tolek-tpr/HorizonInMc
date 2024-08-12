package pl.epsi.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class CustomTextRenderer {

    private DrawContext context;
    private Identifier textMap = new Identifier("horizoninmc", "font/custom_font_1.png");

    public CustomTextRenderer(DrawContext context) {
        this.context = context;
    }

    public void renderText(String stringText, int x, int y, int spacing, int height) {
        stringText = stringText.toLowerCase();
        String[] letters = stringText.split("");
        int step = 0;

        for (String letter : letters) {
            int[] texturePosition = getPositionInfo(letter);

            if (letter.equals(" ")) {
                step += 5;
                continue;
            }
            if (texturePosition == null) continue;

            context.drawTexture(textMap, x + step, y, texturePosition[2], height,
                    texturePosition[0], texturePosition[1], texturePosition[2],
                    16, 232, 16);
            step += texturePosition[2] + spacing;
        }
    }

    public void setTextMap(Identifier textMap) {
        this.textMap = textMap;
    }

    /**
     *
     * @param letter The letter you want to get info for
     * @return Integer[], The U, V, and regionWidth for a given letter
     */
    public int[] getPositionInfo(String letter) {
        switch(letter) {
            case "a" -> { return new int[]{ 0, 0, 8 }; }
            case "b" -> { return new int[]{ 10, 0, 8 }; }
            case "c" -> { return new int[]{ 18, 0, 8 }; }
            case "d" -> { return new int[]{ 27, 0, 8 }; }
            case "e" -> { return new int[]{ 36, 0, 7 }; }
            case "f" -> { return new int[]{ 44, 0, 8 }; }
            case "g" -> { return new int[]{ 52, 0, 8 }; }
            case "h" -> { return new int[]{ 61, 0, 8 }; }
            case "i" -> { return new int[]{ 70, 0, 6 }; }
            case "j" -> { return new int[]{ 77, 0, 8 }; }
            case "k" -> { return new int[]{ 86, 0, 7 }; }
            case "l" -> { return new int[]{ 94, 0, 8 }; }
            case "m" -> { return new int[]{ 103, 0, 9 }; }
            case "n" -> { return new int[]{ 113, 0, 8 }; }
            case "o" -> { return new int[]{ 122, 0, 8 }; }
            case "p" -> { return new int[]{ 131, 0, 8 }; }
            case "q" -> { return new int[]{ 140, 0, 8 }; }
            case "r" -> { return new int[]{ 149, 0, 7 }; }
            case "s" -> { return new int[]{ 157, 0, 8 }; }
            case "t" -> { return new int[]{ 166, 0, 8 }; }
            case "u" -> { return new int[]{ 175, 0, 8 }; }
            case "w" -> { return new int[]{ 184, 0, 10 }; }
            case "v" -> { return new int[]{ 195, 0, 9 }; }
            case "x" -> { return new int[]{ 205, 0, 9 }; }
            case "y" -> { return new int[]{ 215, 0, 8 }; }
            case "z" -> { return new int[]{ 224, 0, 8 }; }
        }

        return null;
    }

}

package pl.epsi.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CustomTextRenderer {

    public static class CustomTextRendererException extends Exception {
        CustomTextRendererException(String msg) {
            super(msg);
        }
    }

    public static class FontProps {
        private String name;
        private int bitmapHeight = 16;
        private int bitmapWidth = 232;
        private int baseLine = 12;
        private int fallbackWidth = 16;
        private int fallbackHeight = 10;
        private final Map<Character, ArrayList<Integer>> characters = new TreeMap<>();
    }

    private final static Map<Identifier, CustomTextRenderer> renderers = new TreeMap<>();

    final private Identifier font;
    final private FontProps props;

    private int horizontalSpacing = 1;
    private int verticalSpacing = 1;
    private int scaling = 1;

    public enum HorizontalAlign { LEFT, RIGHT, CENTER }
    private HorizontalAlign horizontalAlign = HorizontalAlign.LEFT;

    public enum VerticalAlign { TOP, BOTTOM, CENTER }
    private VerticalAlign verticalAlign = VerticalAlign.TOP;

    private CustomTextRenderer(Identifier font, FontProps props) {
        this.font = font;
        this.props = props;
    }

    public static CustomTextRenderer of(String modId, String fontName) {
        Identifier font = new Identifier(modId, fontName + ".png");
//        if (!renderers.containsKey(font)) {
            Yaml yaml = new Yaml();
            final FontProps props = (FontProps) yaml.load(fontName + ".yml");
//            renderers.put(font, new CustomTextRenderer(font, props));
//        }
//        return renderers.get(font);
    return new CustomTextRenderer(font, props);
    }

    public CustomTextRenderer setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
        return this;
    }

    public CustomTextRenderer setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
        return this;
    }

    public CustomTextRenderer setScaling(int scaling) {
        this.scaling = scaling;
        return this;
    }

    public CustomTextRenderer setHorizontalAlign(HorizontalAlign horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
        return this;
    }

    public CustomTextRenderer setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
        return this;
    }
    public int renderLine(DrawContext context, String text, int x, int y) throws CustomTextRendererException {
        int offset = 0;
        for (int i = 0; i < text.length(); ++i) {
            final char ch = text.charAt(i);
            if (!props.characters.containsKey(ch)) throw new CustomTextRendererException("Character '" + ch + "' not found in font map");
            final ArrayList<Integer> position = props.characters.get(ch);
            final int posX = position.get(0);
            final int posY = position.get(1);
            final int width = position.get(2) == null ? props.fallbackWidth : position.get(2);
            final int height = position.get(3) == null ? props.fallbackHeight : position.get(3);
            context.drawTexture(font, x + offset, y, width, height,
                    posX, posY, width,
                    height, props.bitmapWidth, props.bitmapHeight);
            offset += Math.ceil((width + horizontalSpacing) * scaling);
        }
        return offset;
    }

}

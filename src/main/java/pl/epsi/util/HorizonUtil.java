package pl.epsi.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HorizonUtil {

    /**
     *
     * @param squareVertices Vertices of the square, in the order of x1, y1, x2, y2
     * @param posX The X position to check
     * @param posY The Y position to check
     * @return Boolean, are the position coordinates inside the square
     */
    public static boolean isCoordinateInsideSquare(int[] squareVertices, int posX, int posY) {
        return squareVertices.length >= 4 ?
                posX >= squareVertices[0] && posX <= squareVertices[2] && posY >= squareVertices[1] && posY <= squareVertices[3] : false;
    }

    public static <T> ArrayList<T> asArray(T... toAdd) {
        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, toAdd);
        return list;
    }

}

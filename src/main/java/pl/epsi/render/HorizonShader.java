package pl.epsi.render;

import org.lwjgl.opengl.GL20;
import pl.epsi.util.HorizonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HorizonShader {

    public int ID;

    public HorizonShader(File vertexFile, File fragmentFile) {
        try {
            String vCode = HorizonUtil.readFile(HorizonUtil.asInputStream(vertexFile));
            String fCode = HorizonUtil.readFile(HorizonUtil.asInputStream(fragmentFile));

            int vertex, fragment;

            vertex = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
            GL20.glShaderSource(vertex, vCode);
            GL20.glCompileShader(vertex);
            checkErrors(vertex, "SHADER");

            fragment = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
            GL20.glShaderSource(fragment, fCode);
            GL20.glCompileShader(fragment);
            checkErrors(fragment, "SHADER");

            ID = GL20.glCreateProgram();
            GL20.glAttachShader(ID, vertex);
            GL20.glAttachShader(ID, fragment);
            GL20.glLinkProgram(ID);
            checkErrors(ID, "PROGRAM");

            GL20.glDeleteShader(vertex);
            GL20.glDeleteShader(fragment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void use() {
        GL20.glUseProgram(ID);
    }

    private void checkErrors(int shader, String type) {
        if (type.equals("PROGRAM")) {
            if (GL20.glGetProgramInfoLog(shader).length() != 0) {
                //throw new RuntimeException("Program failed!");
            }
        } else {
            if (GL20.glGetShaderInfoLog(shader).length() != 0) {
                //throw new RuntimeException("Shader didn't load properly!");
            }
        }
    }

}

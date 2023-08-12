package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    int width,height;
    String title;
    private long glfwWindow;

    private static Window window = null;
    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
    }

    public static Window get(){
        if(Window.window == null){
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run(){
        System.out.println("Hello LWJGL " + Version.getVersion() + "!" );

        init();
        loop();
        
        //free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminating GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    public void init(){
        //setting up an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Intialize GLFW
        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        //configuring GLFW i.e. giving window hints
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //creating the window
        glfwWindow = glfwCreateWindow(this.width,this.height,this.title,NULL, NULL);
        if(glfwWindow == NULL){
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow,KeyListener::keyCallback);

        //make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        //Enable v-sync
        glfwSwapInterval(1);

        //making the window visible
        glfwShowWindow(glfwWindow);
        GL.createCapabilities();
    }

    public void loop(){
        while (!glfwWindowShouldClose(glfwWindow)){
            glfwPollEvents();
            glClearColor(1.0f,1.0f,1.0f,0.1f);
            glClear(GL_COLOR_BUFFER_BIT);

            if(KeyListener.isKeyPressed(GLFW_KEY_SPACE)){
                System.out.println("Space key is pressed");
            }
            glfwSwapBuffers(glfwWindow);

        }
    }


}

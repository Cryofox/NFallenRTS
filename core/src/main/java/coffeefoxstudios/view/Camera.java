package coffeefoxstudios.view;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ryder Stancescu on 4/22/2017.
 */

/**
 * Orthographic Camera for Scene.
 */
public class Camera implements ApplicationListener {

    static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;

    Vector2 cameraPosition;
    private OrthographicCamera cam;
    public void create(Vector2 startPosition)
    {
        cameraPosition = startPosition;
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 30f;
        cam.viewportHeight = 30f * height/width;
        cam.update();
    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

package coffeefoxstudios.model.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Ryder Stancescu on 4/23/2017.
 */
public class Renderer {

    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;

    public Renderer()
    {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }
    public void updateRenderers(Camera camera)
    {
        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }


    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }


}

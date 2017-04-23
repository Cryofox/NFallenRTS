package coffeefoxstudios.model.states;

import coffeefoxstudios.model.utils.SpriteVault;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.reflect.generics.tree.ClassTypeSignature;
import com.badlogic.gdx.utils.viewport.Viewport;
/**
 * Created by Ryder Stancescu on 4/22/2017.
 */
public class GameState {
    private static final Log log = LogFactory.getLog(GameState.class);
    SpriteBatch spriteBatch = null;
    OrthographicCamera gameCamera;
    Viewport viewport;

    Vector3 mousePosition = new Vector3(0,0,0);
    ShapeRenderer shapes = new ShapeRenderer();

    public GameState(SpriteBatch spriteBatch)
    {
        this.spriteBatch = spriteBatch;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        gameCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        gameCamera = new OrthographicCamera(30, 30 * (h / w));
        gameCamera.position.set(gameCamera.viewportWidth / 2f, gameCamera.viewportHeight / 2f, 0);

    }

    public void resize(int width, int height)
    {

    }

    /**
     * Update Cycle for this State
     */
    public void update()
    {
        updateInput();

        //Update the Camera (For Culling?)
        gameCamera.update();
    }

    void updateInput()
    {
//        log.info("Mouse:["+Gdx.input.getX()+","+Gdx.input.getY()+"]");
        mousePosition.set(Gdx.input.getX(),Gdx.input.getY(),0);
        //Unproject the Screen Coordinates to the "Camera's" Coordinates
        gameCamera.unproject(mousePosition);

    }



    /**
     * Render Cycle for this State
     */
    public void render ()
    {
        spriteBatch.setProjectionMatrix(gameCamera.combined);
        //Get Mouse Position (RAW)

        //Project the position to World Space (For Rendering)
        //Draw Circle where mouse is
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(1,1,0,1);
        shapes.circle(mousePosition.x,mousePosition.y,2);
        shapes.end();


        spriteBatch.begin();

//        spriteBatch.draw(SpriteVault.getInstance().getTexture("HeroPH"),10,10);
//        spriteBatch.draw(SpriteVault.getInstance().getTexture("HeroPH"),30,10);
        spriteBatch.end();
    }




}

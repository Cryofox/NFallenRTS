package coffeefoxstudios.model.states;

import coffeefoxstudios.model.Squad;
import coffeefoxstudios.model.managers.CollisionManager;
import coffeefoxstudios.model.managers.SquadManager;
import coffeefoxstudios.model.states.controllers.PlayerController;
import coffeefoxstudios.model.utils.RenderUtil;
import coffeefoxstudios.model.utils.SelectedTypes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Ryder Stancescu on 4/22/2017.
 */
public class GameState {
    private static final Log log = LogFactory.getLog(GameState.class);

    RenderUtil renderer = null;

    OrthographicCamera gameCamera;
    Vector2 cameraTranslation = new Vector2(0, 0);
    Viewport viewport;

    PlayerController playerController;
    //Screen HalfDimensions
    int halfWidth = 1;
    int halfHeight = 1;

    public GameState(SpriteBatch spriteBatch) {
        renderer = new RenderUtil();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        gameCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        gameCamera = new OrthographicCamera(30, 30 * (h / w));
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCamera);
//        viewport = new ScalingViewport(Scaling.none, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight(), gameCamera);
        viewport.apply();


        halfWidth = viewport.getScreenWidth() / 2;
        halfHeight = viewport.getScreenHeight() / 2;
        gameCamera.position.set(0, 0, 0);

        playerController = new PlayerController(this);

        Gdx.input.setInputProcessor(playerController);
        stubInitialize();
    }


    private void stubInitialize()
    {
        SquadManager.getInstance().spawnSquad(new Vector3(0,0,0));
        SquadManager.getInstance().spawnSquad(new Vector3(10,10,0));
    }



    public void resize(int width, int height) {
        viewport.update(width, height);
        halfWidth = viewport.getScreenWidth() / 2;
        halfHeight = viewport.getScreenHeight() / 2;
    }

    /**
     * Update Cycle for this State
     */
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        //Update Player Input
        updateInput(deltaTime);

        //Update Game
        SquadManager.getInstance().update(deltaTime);

        //Update the Camera (For Culling?)
        gameCamera.update();
    }

    void updateInput(float deltaTime) {

        //Update Controller
        playerController.update(deltaTime);
        updateKeyboard();
    }


    /**
     * Unprojects the Screen Position to World Space
     * @param screenPosition
     */
    public void unproject(Vector3 screenPosition)
    {
        gameCamera.unproject(screenPosition,viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
//        gameCamera.unproject(screenPosition);

    }



    /**
     * Logic for Keyboard Updates
     */
    void updateKeyboard() {
        cameraTranslation.set(0, 0);
        int translateAmount = 10;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cameraTranslation.add(0, translateAmount);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cameraTranslation.add(0, -translateAmount);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cameraTranslation.add(translateAmount, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cameraTranslation.add(-translateAmount, 0);
        }
//        gameCamera.translate(cameraTranslation);
        gameCamera.position.add(cameraTranslation.x, cameraTranslation.y, 0);
        gameCamera.update();
//        spriteBatch.setProjectionMatrix(gameCamera.combined);

        //Update RenderUtil when the camera moves.
        renderer.updateRenderers(gameCamera);
    }


    /**
     * Render Cycle for this State
     */
    public void render() {
        //Update RenderUtil by GameCamera Projection
//        renderer.updateRenderers(gameCamera);


        //Render ViewPort BackDrop
        //------------
        //Set Camera BackDropColor
//
//        shapes.begin(ShapeRenderer.ShapeType.Filled);
//        shapes.setColor(.5f, .5f, 0.5f, 1);
//        shapes.rect(gameCamera.position.x - halfWidth, gameCamera.position.y - halfHeight, viewport.getScreenWidth(), viewport.getScreenHeight());
//        shapes.end();
        //============

        //Render Game
        //------------
        renderGame();
        //============

        //Render Game OverLays
        //------------
        renderGameOverlay();
        //============

        //Render Game Debug OverLays
        //------------
        renderGameDebugOverlay();
        //============


        //Render GUI
        //------------
        //============

        //Render GUI Overlays ?
        //------------
        //============

        //Render GUI Debug Overlays
        //------------
        //============

    }

    void renderGame() {
        //Render Environment

        //Render Buildings

        //Render Squads
        SquadManager.getInstance().render(renderer);

//        spriteBatch.begin();
//        spriteBatch.draw(SpriteVault.getInstance().getTexture("HeroPH"), 10, 10);
//        spriteBatch.draw(SpriteVault.getInstance().getTexture("HeroPH"), 30, 10);
//        spriteBatch.end();
    }

    void renderGameOverlay() {
        playerController.render(renderer);
    }
    void renderGameDebugOverlay() {
        //Render Environment

        //Render Buildings

        //Render Squads
        SquadManager.getInstance().renderDebug(renderer);
    }
    void renderGUI() {
    }
    void renderGUIOverlay() {
    }
    void renderGUIDebugOverlay() {
    }
}

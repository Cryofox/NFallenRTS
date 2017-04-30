package coffeefoxstudios.model.states;

import coffeefoxstudios.model.managers.SquadManager;
import coffeefoxstudios.model.states.controllers.PlayerController;
import coffeefoxstudios.model.utils.RenderUtil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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


    Vector2 cameraTranslation = new Vector2(0, 0);



    PlayerController playerController;


    OrthographicCamera gameCamera,uiCamera;
    Viewport gameViewport, uiViewport;
    //UI View
//    GameView uiView;

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
        uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        gameCamera = new OrthographicCamera(30, 30 * (h / w));
        gameViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCamera);
//        viewport = new ScalingViewport(Scaling.none, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight(), gameCamera);
        uiViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCamera);

        gameViewport.apply();
        uiViewport.apply();
        halfWidth = gameViewport.getScreenWidth() / 2;
        halfHeight = gameViewport.getScreenHeight() / 2;
        gameCamera.position.set(0, 0, 0);

        playerController = new PlayerController(this);

        Gdx.input.setInputProcessor(playerController);

//        uiView = new GameView(uiCamera);

        stubInitialize();
    }


    private void stubInitialize() {
        SquadManager.getInstance().spawnSquad(new Vector3(0, 0, 0));
        SquadManager.getInstance().spawnSquad(new Vector3(0, 0, 0));
        SquadManager.getInstance().spawnSquad(new Vector3(0, 0, 0));
        SquadManager.getInstance().spawnSquad(new Vector3(0, 0, 0));
        SquadManager.getInstance().spawnSquad(new Vector3(0, 0, 0));
        SquadManager.getInstance().spawnSquad(new Vector3(0, 0, 0));
        SquadManager.getInstance().spawnSquad(new Vector3(0, 0, 0));
        SquadManager.getInstance().spawnSquad(new Vector3(0, 0, 0));

        SquadManager.getInstance().spawnSquad(new Vector3(30, 30, 0));
        SquadManager.getInstance().spawnSquad(new Vector3(100, 100, 0));
        SquadManager.getInstance().spawnSquad(new Vector3(300, 300, 0));
    }


    public void resize(int width, int height) {
        gameViewport.update(width, height);
        uiViewport.update(width,height);

        halfWidth = gameViewport.getScreenWidth() / 2;
        halfHeight = gameViewport.getScreenHeight() / 2;

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

        //UI View
//        uiView.update(deltaTime);
    }

    void updateInput(float deltaTime) {
        //Update Controller
        playerController.update(deltaTime);
        updateKeyboard();
    }


    /**
     * Unprojects the Screen Position to World Space
     *
     * @param screenPosition
     */
    public void unproject(Vector3 screenPosition) {
        gameCamera.unproject(screenPosition, gameViewport.getScreenX(), gameViewport.getScreenY(), gameViewport.getScreenWidth(), gameViewport.getScreenHeight());
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
        ShapeRenderer shapes = renderer.getShapeRenderer();
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.5f, .5f, 0.5f, 1);
        shapes.rect(gameCamera.position.x - halfWidth*gameCamera.zoom, gameCamera.position.y - halfHeight*gameCamera.zoom, gameViewport.getScreenWidth()*gameCamera.zoom, gameViewport.getScreenHeight()*gameCamera.zoom);
        shapes.end();
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
        uiView.render();
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

    int maxZoom = 5;
    int minZoom = 1;
    public void zoomCamera(float amount)
    {
        float zoom = gameCamera.zoom -amount;
        if(zoom < minZoom) zoom = minZoom;

        zoom = (zoom<minZoom)? minZoom: zoom;
        zoom = (zoom>maxZoom)? maxZoom: zoom;
        gameCamera.zoom= zoom;
        //Update the Camera (For Culling?)
        gameCamera.update();
    }
}

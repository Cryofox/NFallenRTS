package coffeefoxstudios.model.states.controllers;


import coffeefoxstudios.model.Building;
import coffeefoxstudios.model.Squad;
import coffeefoxstudios.model.managers.CollisionManager;
import coffeefoxstudios.model.states.GameState;
import coffeefoxstudios.model.states.PlayerState;
import coffeefoxstudios.model.utils.RenderUtil;
import coffeefoxstudios.model.utils.Renderable;

import coffeefoxstudios.model.utils.SelectedTypes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;

public class PlayerController implements InputProcessor {
    private static final Log log = LogFactory.getLog(PlayerController.class);

    Renderable highlightedInteractable;
    //Can't Select Units and Buildings.

    //4 Ways to Select Units:
    // Box Select
    // Double Click unit (selects all units of same Character Type in view)
    // Single Click unit (selects only the specified unit)
    // Shift Click (adds the selected unit to the list of Units.

    //3 Ways to Select Buildings
    // Double Click Building (selects all Buildings of same Type in view)
    // Single Click Building (selects only the specified Building)
    // Shift Click (adds the selected Building to the list of Buildings.

    List<Squad> selectedUnits;
    List<Building> selectedBuildings;

    PlayerState currentState = PlayerState.Default;


    //Mouse Values
    Vector3 startMouseDownPosition = new Vector3(0, 0, 0);
    Vector3 mousePosition = new Vector3(-99, -99, 0);

    boolean leftMouseJustPressed = false;
    MouseButtonState leftMouseState = MouseButtonState.Up;

    Rectangle boxSelect= new Rectangle();
    GameState gameState =null;
    //MouseState
    public PlayerController(GameState gameState) {
        this.gameState = gameState;
        highlightedInteractable = null;
        selectedUnits = new ArrayList<>();
        selectedBuildings= new ArrayList<>();
    }

    public void update(float deltaTime) {
        //Building State is where we are placing a Building down, no Highlighting should occur when doing this.

        //Toggle Highlighted Entity
        if (highlightedInteractable != null) {
            //Set the selected Type to None
            highlightedInteractable.setSelectedType(SelectedTypes.None);
            //Check if the old Highlighted Entity is a Squad
            if (highlightedInteractable instanceof Squad) {
                Squad squad = (Squad) highlightedInteractable;
                //Check if it's in our selected List
                if (selectedUnits.contains(squad)) {
                    squad.setSelectedType(SelectedTypes.Selected);
                }
            }
        }


        if (currentState != PlayerState.Building) {
            highlightedInteractable = CollisionManager.getInstance().getRenderable(mousePosition);
            if(highlightedInteractable!=null)
            {
                highlightedInteractable.setSelectedType(SelectedTypes.Highlighted);
            }
        }
    }

    public void render(RenderUtil renderer) {
        //Draw a Selection Box
//        log.info("MouseState:"+ leftMouseState.toString());
        ShapeRenderer shapes = renderer.getShapeRenderer();
        shapes.begin(ShapeRenderer.ShapeType.Line);
        if(leftMouseState == MouseButtonState.HeldDown)
        {
            shapes.setColor(1,1,1,1);
            shapes.rect(boxSelect.x,boxSelect.y, boxSelect.width,boxSelect.height);
//            log.info("MPS:"+startMouseDownPosition);
//            log.info("MP:"+mousePosition);
//            log.info("Rectangle:" + rectangle.getX() +","+rectangle.getY()+","+rectangle.getWidth()+","+rectangle.getHeight());
            shapes.setColor(0,1,0,1);
            shapes.circle(startMouseDownPosition.x,startMouseDownPosition.y,5);
        }
        shapes.setColor(1,0,0,1);
        shapes.circle(mousePosition.x,mousePosition.y,5);


        shapes.end();;
    }

    /**
     * Select all Units inside BoxSelect. Remember only UNITS can be selected via box select
     */
    private void boxSelectUnits()
    {
        //Clear Selections
        clearSelections();

        //Grab new SelectedUnits (if any)
        selectedUnits = CollisionManager.getInstance().getSelectedUnits(boxSelect);
        if(selectedUnits!=null)
        {
            for(Squad squad: selectedUnits)
            {
                squad.setSelectedType(SelectedTypes.Selected);
            }
        }
        log.info("SelectedUnits:"+selectedUnits.size());
    }


    private void clearSelections()
    {
        if(selectedUnits!=null)
        {
            for(Squad squad: selectedUnits)
            {
                squad.setSelectedType(SelectedTypes.None);
            }
        }
        if(selectedBuildings!=null)
        {
            for(Building building: selectedBuildings)
            {
                building.setSelectedType(SelectedTypes.None);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        updateMousePosition();
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        updateMousePosition();
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        updateMousePosition();
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Process Left Mouse Button
        if (button == Input.Buttons.LEFT) {
            if (leftMouseState == MouseButtonState.Up) {
                leftMouseJustPressed = true;
                leftMouseState = MouseButtonState.Down;
                startMouseDownPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                //Unproject the mouse Start
                gameState.unproject(startMouseDownPosition);
//                log.info("SETTING MOUSEDOWN START");
            } else {
                leftMouseJustPressed = false;
            }
        }
        return true;
    }


    private void updateBoxSelect()
    {
        float minX = Math.min(startMouseDownPosition.x, mousePosition.x);
        float maxX = Math.max(startMouseDownPosition.x, mousePosition.x);
        float minY = Math.min(startMouseDownPosition.y, mousePosition.y);
        float maxY = Math.max(startMouseDownPosition.y, mousePosition.y);
        boxSelect.set(minX, minY, maxX-minX, maxY-minY);
    }



    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            leftMouseJustPressed = false; //Reset LeftMouseState
            //Selection Logic
            boxSelectUnits();
            leftMouseState = MouseButtonState.Up;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        updateMousePosition();
        if(leftMouseState == MouseButtonState.Down)
        {
            leftMouseState = MouseButtonState.HeldDown;
            //Update BoxSelect
            updateBoxSelect();
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        updateMousePosition();
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    private void updateMousePosition()
    {
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        //Unproject the MousePosition
        gameState.unproject(mousePosition);
        updateBoxSelect();
    }
}


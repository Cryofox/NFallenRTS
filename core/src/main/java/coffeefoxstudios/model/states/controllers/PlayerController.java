package coffeefoxstudios.model.states.controllers;


import coffeefoxstudios.model.Building;
import coffeefoxstudios.model.Squad;
import coffeefoxstudios.model.managers.CollisionManager;
import coffeefoxstudios.model.states.GameState;
import coffeefoxstudios.model.states.PlayerState;
import coffeefoxstudios.model.utils.RenderUtil;
import coffeefoxstudios.model.utils.Renderable;

import coffeefoxstudios.model.utils.SelectedTypes;
import coffeefoxstudios.view.Camera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    Vector3 startMouseDownPositionLeft = new Vector3(0, 0, 0);
    Vector3 mousePosition = new Vector3(0, 0, 0);

    boolean leftMouseJustPressed = false;
    MouseButtonState leftMouseState = MouseButtonState.Up;


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


    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Process Left Mouse Button
        if (button == Input.Buttons.LEFT) {
            if (leftMouseState == MouseButtonState.Up) {
                leftMouseJustPressed = true;
                leftMouseState = MouseButtonState.Down;
                startMouseDownPositionLeft.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            } else {
                leftMouseJustPressed = false;
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            leftMouseJustPressed = false; //Reset LeftMouseState
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
//        log.info("MousePosition:" + mousePosition.toString());
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        //Unproject the MousePosition
        gameState.unproject(mousePosition);

        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

package coffeefoxstudios.model.states.controllers;


import coffeefoxstudios.model.actors.Actor;
import coffeefoxstudios.model.actors.buildings.Building;
import coffeefoxstudios.model.actors.squads.Squad;
import coffeefoxstudios.model.managers.CollisionManager;
import coffeefoxstudios.model.managers.SquadManager;
import coffeefoxstudios.model.states.GameState;
import coffeefoxstudios.model.states.PlayerState;
import coffeefoxstudios.model.actors.squads.commands.SquadOrder;
import coffeefoxstudios.model.actors.squads.commands.SquadOrderCommand;
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

import java.util.ArrayList;
import java.util.List;

public class PlayerController implements InputProcessor {
    private static final Log log = LogFactory.getLog(PlayerController.class);
    private static final Vector3 DEFAULT = new Vector3(-999, -999, -999);
    Renderable highlightedInteractable;
    //Can't Select Units and Buildings. You can only select One or the other as a group.

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
    MouseButtonState rightMouseState = MouseButtonState.Up;
    Rectangle boxSelect = new Rectangle();
    GameState gameState = null;
    SquadOrderCommand selectedOrderType = SquadOrderCommand.Move;

    //MouseState
    public PlayerController(GameState gameState) {
        this.gameState = gameState;
        highlightedInteractable = null;
        selectedUnits = new ArrayList<>();
        selectedBuildings = new ArrayList<>();
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
        //Only Set Highlighted Unit if we are not trying to place down a structure.
        if (currentState != PlayerState.Building) {
            highlightedInteractable = CollisionManager.getInstance().getRenderable(mousePosition);
            if (highlightedInteractable != null) {
                highlightedInteractable.setSelectedType(SelectedTypes.Highlighted);
            }
        }
    }

    public void render(RenderUtil renderer) {
        //Draw a Selection Box
        ShapeRenderer shapes = renderer.getShapeRenderer();
        shapes.begin(ShapeRenderer.ShapeType.Line);
        if (leftMouseState == MouseButtonState.HeldDown) {
            shapes.setColor(1, 1, 1, 1);
            shapes.rect(boxSelect.x, boxSelect.y, boxSelect.width, boxSelect.height);
            shapes.setColor(0, 1, 0, 1);
            shapes.circle(startMouseDownPosition.x, startMouseDownPosition.y, 5);
        }
        //Draw where the Mouse Currently Is
        shapes.setColor(1, 0, 0, 1);
        shapes.circle(mousePosition.x, mousePosition.y, 5);
        shapes.end();
    }

    /**
     * Select all Units inside BoxSelect. Remember only UNITS can be selected via box select
     * Set using mouseStart(TouchDown), Called once TouchUp event is called from LeftMouse
     */
    private void boxSelectUnits() {
        //Clear Selections
        clearSelections();

        //Grab new SelectedUnits (if any)
        selectedUnits = CollisionManager.getInstance().getSelectedUnits(boxSelect);
        if (selectedUnits != null && selectedUnits.size() > 0) {
            for (Squad squad : selectedUnits) {
                squad.setSelectedType(SelectedTypes.Selected);
            }
            currentState = PlayerState.Commanding;
        } else {
            currentState = PlayerState.Default;
        }
    }


    private void clearSelections() {
        if (selectedUnits != null) {
            for (Squad squad : selectedUnits) {
                squad.setSelectedType(SelectedTypes.None);
            }
        }
        if (selectedBuildings != null) {
            for (Building building : selectedBuildings) {
                building.setSelectedType(SelectedTypes.None);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {

        if (currentState == PlayerState.Commanding) {
            switch (keycode) {
                case Input.Keys.Q:
//                    order = SquadOrderCommand.AttackMove;
                    break;

                default:
                    break;
            }
        }

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
        updateMousePosition();
        //Process Left Mouse Button
        if (button == Input.Buttons.LEFT) {
            if (leftMouseState == MouseButtonState.Up) {
                //Just Pressed
                leftMouseJustPressed = true;
                leftMouseState = MouseButtonState.Down;
                startMouseDownPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                //Unproject the mouse Start
                gameState.unproject(startMouseDownPosition);
                updateBoxSelect();
            }
        }
        if (button == Input.Buttons.RIGHT) {
            //Right Mouse was JUST Pressed
            if (rightMouseState == MouseButtonState.Up) {
                //Just Pressed
                rightMouseState = MouseButtonState.Down;
                if (selectedUnits.size() > 0) {
                    //If shift is pressed we QUEUE the order, otherwise we Set It.
                    SquadOrder order = null;

                    //Create the Squad Order
                    switch (selectedOrderType) {
                        case Move:
                        case AttackMove:
                            order = new SquadOrder(selectedOrderType, mousePosition); //Location
                            break;
                        case AttackTarget:
                        case GaurdTarget:
                            //CollisionManager should return an Actor not a Squad,
                            Actor target = CollisionManager.getInstance().getActor(mousePosition);
                            order = new SquadOrder(selectedOrderType, target); //Target
                            break;
                        case HoldFire:
                            order = new SquadOrder(selectedOrderType); //Self
                            break;
                    }
                    if (order != null) {
                        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                            SquadManager.getInstance().queueOrder(selectedUnits, order);
                        } else {
                            SquadManager.getInstance().setOrder(selectedUnits, order);
                        }
                    }
                }
            }
        }
        return true;
    }


    private void updateBoxSelect() {
        float minX = Math.min(startMouseDownPosition.x, mousePosition.x);
        float maxX = Math.max(startMouseDownPosition.x, mousePosition.x);
        float minY = Math.min(startMouseDownPosition.y, mousePosition.y);
        float maxY = Math.max(startMouseDownPosition.y, mousePosition.y);
        boxSelect.set(minX, minY, maxX - minX, maxY - minY);
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            leftMouseJustPressed = false; //Reset LeftMouseState
            //Selection Logic
            boxSelectUnits();
            leftMouseState = MouseButtonState.Up;
        }
        if (button == Input.Buttons.RIGHT) {
            //Just Pressed
            rightMouseState = MouseButtonState.Up;
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        updateMousePosition();
        if (leftMouseState == MouseButtonState.Down) {
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
        log.info("Scroll Amount:"+ amount);
        if(gameState!=null) {
            gameState.zoomCamera(-(float)amount*0.1f);

        }
        return true;
    }


    private void updateMousePosition() {
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        //Unproject the MousePosition
        gameState.unproject(mousePosition);
        updateBoxSelect();
    }
}


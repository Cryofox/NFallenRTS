package coffeefoxstudios.model.managers;

import coffeefoxstudios.model.actors.Actor;
import coffeefoxstudios.model.actors.squads.Squad;
import coffeefoxstudios.model.utils.Renderable;
import coffeefoxstudios.model.utils.Tuple;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Ryder Stancescu on 4/23/2017.
 */
public class CollisionManager {
    private final static CollisionManager instance = new CollisionManager();

    public static CollisionManager getInstance() {
        return instance;
    }

    private List<Renderable> squads;
    private List<Renderable> projectiles;
    private List<Renderable> buildings;
    private List<Renderable> misc;

    private CollisionManager() {
        squads = new ArrayList<>();
    }

    /**
     * Add Object that is interactable in the game's world.
     *
     * @param entity
     */
    public void addSquad(Squad entity) {
        squads.add(entity);

    }
    //Add Projectile


    public boolean collides(Renderable entity1, Renderable entity2) {
        Rectangle rect1 = entity1.getBoundingBox();
        Rectangle rect2 = entity2.getBoundingBox();
        return collides(rect1, rect2);
    }


    public List<Squad> getSelectedUnits(Rectangle boxSelect) {
        List<Squad> squadList = new ArrayList<>();
        for (Renderable squad : squads) {
            if (collides(boxSelect, squad.getBoundingBox()))
                squadList.add((Squad) squad);
        }
        return squadList;
    }

    private boolean collides(Rectangle rect1, Rectangle rect2) {
        if (rect1.contains(rect2) || rect2.contains(rect1)
                || rect1.overlaps(rect2)) {
            return true;
        }
        return false;
    }

    private boolean collides(Circle circle, Vector2 vector) {
        return (circle.contains(vector) || circle.contains(vector));
    }

    private boolean collides(Circle circle1, Circle circle2) {
        return (circle1.contains(circle2) || circle2.contains(circle1)
                || circle1.overlaps(circle2));

    }

    private boolean collides(Rectangle rect1, Vector3 point) {
        return (rect1.contains(point.x, point.y));
    }

    private boolean collides(Rectangle rect1, Vector2 point) {
        return (rect1.contains(point));
    }

    /**
     * Gets the Squad that is highlighted over the position.
     *
     * @param point
     * @return
     */
    public Actor getActor(Vector2 point) {
        for (Renderable renderable : squads) {
            if (renderable.getBoundingBox().contains(point)) {
                return (Actor) renderable;
            }
        }
        return null;
    }

    public Actor getActor(Vector3 point) {
        for (Renderable renderable : squads) {
            if (renderable.getBoundingBox().contains(point.x, point.y)) {
                return (Actor) renderable;
            }
        }
        return null;
    }

    /**
     * Checks ALL Render Lists
     *
     * @param point
     * @return
     */
    public Renderable getRenderable(Vector3 point) {
        for (Renderable renderable : squads) {
            if (renderable.getBoundingBox().contains(point.x, point.y)) {
                return renderable;
            }
        }
        return null;
    }

    public List<Tuple<Squad, Vector3>> getFinalPosition(List<Squad> squads, Vector3 target) {
        if (squads == null || target == null)
            return null;

        List<Squad> remainingSquads = new ArrayList<>();
        remainingSquads.addAll(squads);

        List<Tuple<Squad, Vector3>> finalSquadPositions = new ArrayList<>();

        //Increment.
        Set<Vector2> closedSet = new HashSet<>();

        List<Vector2> positions = new ArrayList<>();
        positions.add(new Vector2(target.x, target.y));

        Vector2 position = null;
        boolean collides = false;
        while (remainingSquads.size() > 0 && positions.size() > 0) {
            //Get position
            position = positions.get(0);
            positions.remove(0);
            //If the closedSet does not already have this position
            if (!closedSet.contains(position)) {
                //Append to Visiting/Closed Set
                closedSet.add(position);

                //Get Neighbours and append to List
                addGridNeighbours(positions, closedSet, position);

                //Check if this position is valid for our Circle
                collides = false;
                for (Tuple<Squad, Vector3> stuple : finalSquadPositions) {
                    //Circle is better  than rect due to last positioning.
                    Circle circleCur = new Circle();
                    circleCur.setX(position.x);
                    circleCur.setY(position.y);
                    circleCur.setRadius(remainingSquads.get(0).getPersonalSpaceRadius()+1);

                    Circle circlePlaced = new Circle();
                    circlePlaced.setX(stuple.getElement2().x);
                    circlePlaced.setY(stuple.getElement2().y);
                    circlePlaced.setRadius(stuple.getElement1().getPersonalSpaceRadius()+1);

                    if (collides(circleCur, circlePlaced)) {
                        collides = true;
                        break;
                    }
                }
                if (collides == false) {
                    //Create Tuple
                    Tuple<Squad, Vector3> tuple = new Tuple<Squad, Vector3>();
                    tuple.setElement1(remainingSquads.get(0));
                    tuple.setElement2(new Vector3(position.x, position.y, 0));
                    //Store Tuple
                    finalSquadPositions.add(tuple);
                    //Remove from Remaining Squads
                    remainingSquads.remove(0);
                }
            }
        }
        return finalSquadPositions;
    }

    void addGridNeighbours(List<Vector2> positionList, Set<Vector2> closedSet, Vector2 position) {
        //U
        Vector2 vector = new Vector2(position.x, position.y + 1);
        if (!closedSet.contains(vector))
            positionList.add(vector);
        //L
        vector = (new Vector2(position.x - 1, position.y));
        if (!closedSet.contains(vector))
            positionList.add(vector);

        //D
        vector = (new Vector2(position.x, position.y - 1));
        if (!closedSet.contains(vector))
            positionList.add(vector);
        //R
        vector = (new Vector2(position.x + 1, position.y));
        if (!closedSet.contains(vector))
            positionList.add(vector);


        //TR
        vector = (new Vector2(position.x + 1, position.y + 1));
        if (!closedSet.contains(vector))
            positionList.add(vector);
        //BL
        vector = (new Vector2(position.x - 1, position.y - 1));
        if (!closedSet.contains(vector))
            positionList.add(vector);
        //BR
        vector = (new Vector2(position.x + 1, position.y - 1));
        if (!closedSet.contains(vector))
            positionList.add(vector);
        //TL
        vector = (new Vector2(position.x - 1, position.y + 1));
        if (!closedSet.contains(vector))
            positionList.add(vector);

    }

}

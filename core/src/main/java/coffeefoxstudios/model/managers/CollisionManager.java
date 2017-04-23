package coffeefoxstudios.model.managers;

import coffeefoxstudios.model.Squad;
import coffeefoxstudios.model.utils.Renderable;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ryder Stancescu on 4/23/2017.
 */
public class CollisionManager {
    private final static CollisionManager instance = new CollisionManager();
    public static CollisionManager getInstance()
    {
        return instance;
    }

    private List<Renderable> squads;
    private List<Renderable> projectiles;
    private List<Renderable> buildings;
    private List<Renderable> misc;

    private CollisionManager()
    {
        squads = new ArrayList<>();
    }

    /**
     * Add Object that is interactable in the game's world.
     * @param entity
     */
    public void addSquad(Squad entity)
    {
        squads.add(entity);
    }
    //Add Projectile


    public boolean collides(Renderable entity1, Renderable entity2)
    {
        Rectangle rect1 = entity1.getBoundingBox();
        Rectangle rect2 = entity2.getBoundingBox();
        if(rect1.contains(rect2) || rect2.contains(rect1)
                || rect1.overlaps(rect2))
        {   return true;}
        return false;
    }


    /**
     * Gets the Squad that is highlighted over the position.
     * @param point
     * @return
     */
    public Squad getSquad(Vector2 point)
    {
        for(Renderable renderable : squads)
        {
            if(renderable.getBoundingBox().contains(point))
            {
                return (Squad)renderable;
            }
        }
        return null;
    }
}

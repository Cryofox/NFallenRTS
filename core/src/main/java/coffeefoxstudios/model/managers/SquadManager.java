package coffeefoxstudios.model.managers;

import coffeefoxstudios.model.Squad;
import coffeefoxstudios.model.utils.RenderUtil;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryder Stancescu on 4/23/2017.
 */
public class SquadManager  {
    private final static SquadManager instance = new SquadManager();
    public static SquadManager getInstance()
    {
        return instance;
    }

    List<Squad> squadList;

    private SquadManager()
    {
        squadList = new ArrayList<>();
    }


    public Squad spawnSquad(Vector3 position)
    {
        Squad squad = new Squad();
        squad.setPosition(position);

        //Store Squad in Squadlist for Update Cycle
        squadList.add(squad);

        //Add to CollisionManager
        CollisionManager.getInstance().addSquad(squad);

        return squad;
    }





    public void update(float deltaTime)
    {
        //Update ALL squads regardless of visibility
        for(Squad squad : squadList)
        {
            squad.update(deltaTime);
        }
    }


    public void render(RenderUtil renderer) {

        for(Squad squad : squadList)
        {
            squad.render(renderer);
        }
    }


    public void renderDebug(RenderUtil renderer) {
        for(Squad squad : squadList)
        {
            squad.renderDebug(renderer);
        }
    }


}

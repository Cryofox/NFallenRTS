package coffeefoxstudios.model.states.commands;

/**
 * Created by Ryder Stancescu on 4/23/2017.
 */
public enum SquadOrderCommand {
    //Location Based Orders
    Move,
    AttackMove,
    Patrol,

    //Target Based Orders
    AttackTarget,
    GaurdTarget,
    HoldFire

}

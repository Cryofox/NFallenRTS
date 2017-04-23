package coffeefoxstudios.model.states;

/**
 * Created by Ryder Stancescu on 4/23/2017.
 */


import com.sun.glass.ui.View;

/**
 * Class used to Determine what state we are in, and where we want to go.
 */
public class PlayerState {

    /*
        Idle  (Empty State, not doing anything specific)
        Placing a Building
        Commanding Squads (Have 1 or more squads selected and ordering commands to.
        Select Unit (Left click on unit to see info)
     */




//Player State should be seperate from UI State


    //Bottom Right Corner is reserved for Context Sensitive Commands
    /*
            4x3
     */
    // if a Unit is selected:  Re-Inforce,

    /* When Unit is highlighted
    Unit Sample
            [Attack][Guard][Patrol][HoldFire]
            [Ability1][Ability2][Ability3][Ability4]
            [Upgrade1/Misc][Upgrade2/Misc][Upgrade3/Misc][Upgrade4/Misc]
    */
    /* When No Unit is Selected
    Build Layout
        [Command Center] [Mine Ops] [ Barracks ] []
        [] [] [] []
        [] [] [] [Next ->]



     */



}

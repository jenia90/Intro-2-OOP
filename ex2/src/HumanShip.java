import oop.ex2.GameGUI;

import java.awt.*;

/**
 * Created by jenia on 13/03/2016.
 */
public class HumanShip extends SpaceShip{

    private static boolean INIT_ACCELERATION = false;

    public HumanShip(){
        reset();
    }
    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        GameGUI gui = game.getGUI();
        int turnDirection = STRAIGHT_HEADING;
        boolean acceleration = INIT_ACCELERATION;
        shieldControl(false);

        if (game.getGUI().isTeleportPressed())
            teleport();
        else if(gui.isUpPressed() || gui.isRightPressed() || gui.isLeftPressed()){
            acceleration = gui.isUpPressed();

            if(gui.isLeftPressed() && !gui.isRightPressed())
                turnDirection = LEFT_TURN;
            else if(gui.isRightPressed() && !gui.isLeftPressed())
                turnDirection = RIGHT_TURN;
        }


        else if (gui.isShieldsPressed())
            shieldControl(true);
        else if (gui.isShotPressed()) {
            fire(game);
        }
        else
            chargeEnergy();

        getPhysics().move(acceleration, turnDirection);
        updateGunCoolDown();
        updateShieldStats();
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    @Override
    public Image getImage() {
        if (isShieldOn())
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        return GameGUI.SPACESHIP_IMAGE;
    }
}

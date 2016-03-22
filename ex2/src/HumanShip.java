import oop.ex2.GameGUI;

import java.awt.*;

/**
 * This class implements the Human controlled space ship.
 */
public class HumanShip extends SpaceShip{

    private static boolean HUMANSHIP_INIT_ACCELERATION = false; // Constant value for the initial acceleration.

    /**
     * Deals with the user input to control this ship.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        GameGUI gui = game.getGUI(); // gets current GUI object
        int turnDirection = STRAIGHT_HEADING; // reset the heading direction at the beginning of each round.
        boolean acceleration = HUMANSHIP_INIT_ACCELERATION; // disable acceleration at the beginning of each round.
        shieldControl(false); // make sure shield is off at the beginning of each round

        if (game.getGUI().isTeleportPressed()) // checks if teleport button is pressed.
            teleport();

        // Checks if any of the navigation buttons pressed and navigates the ship's movement accordingly.
        if(gui.isUpPressed() || gui.isRightPressed() || gui.isLeftPressed()){
            acceleration = gui.isUpPressed();

            if(gui.isLeftPressed() && !gui.isRightPressed())
                turnDirection = LEFT_TURN;
            else if(gui.isRightPressed() && !gui.isLeftPressed())
                turnDirection = RIGHT_TURN;
        }

        if (gui.isShieldsPressed())
            shieldControl(true);

        if (gui.isShotPressed()) {
            fire(game);
        }

        getPhysics().move(acceleration, turnDirection);
        updateGunCoolDown(); // updates gun cool down timer.
        updateShieldStats(); // updates shield stats
        chargeEnergy();
    }

    /**
     * Overrides the default getImage() implementation and assigns the player images.
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

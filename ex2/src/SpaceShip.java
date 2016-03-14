import java.awt.Image;
import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game. 
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 *  a base class for the other spaceships or any other option you will choose.
 *  
 * @author oop
 */
public abstract class SpaceShip{

    private static final int INIT_ENERGY = 190;
    private static final int MAX_HEALTH = 22;
    private static final int SHOT_ENERGY_COST = 19;
    private static final int TELEPORT_ENERGY_COST = 140;

    private int healthLevel;
    private int currentEnergyLevel;
    private int maxEnergyLevel = 210;
    private SpaceShipPhysics shipPhysics;
    private boolean shieldOn = false;
    private int gunCoolDown = 0;

    /**
     * Updates gun cool down period.
     */
    public void updateGunCoolDown(){
        if(gunCoolDown > 0)
            gunCoolDown--;
    }

    /**
     * Charges energy each round.
     */
    public void chargeEnergy(){
        if(currentEnergyLevel < maxEnergyLevel)
            currentEnergyLevel++;
    }
    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip(){
        if(!isShieldOn()) {
            updateNoShieldStats();
        }

        else {
            maxEnergyLevel += 18;
            currentEnergyLevel += 18;
        }
    }

    /**
     * Updates energy and health levels in case of hits and collisions.
     */
    private void updateNoShieldStats() {
        healthLevel--;

        currentEnergyLevel -= 10;
        if(currentEnergyLevel > maxEnergyLevel)
            maxEnergyLevel = currentEnergyLevel;
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        shipPhysics = new SpaceShipPhysics();
        healthLevel = MAX_HEALTH;
        currentEnergyLevel = INIT_ENERGY;
    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return healthLevel == 0;
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics(){
        return shipPhysics;
    }


    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if(!isShieldOn())
            updateNoShieldStats();
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
    public abstract Image getImage();

    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if(currentEnergyLevel >= SHOT_ENERGY_COST && gunCoolDown == 0) {
            game.addShot(shipPhysics);
            currentEnergyLevel -= SHOT_ENERGY_COST;
            gunCoolDown = 7;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if(currentEnergyLevel >= 3 && !isShieldOn())
            shieldOn = true;
        else
            shieldOn = false;
    }

    /**
     * Returns current shield status.
     * @return current shield status.
     */
    public boolean isShieldOn(){
        return shieldOn;
    }

    /**
     * Updates shield status based on energy levels.
     */
    public void updateShield(){
        if (isShieldOn()){
            if(currentEnergyLevel >= 3)
                currentEnergyLevel -= 3;
            else
                shieldOn();
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
       if(currentEnergyLevel >= TELEPORT_ENERGY_COST){
           shipPhysics = new SpaceShipPhysics();
           currentEnergyLevel -= TELEPORT_ENERGY_COST;
       }
    }
}

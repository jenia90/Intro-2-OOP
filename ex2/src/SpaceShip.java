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

    /**
     * Constants to use in this super class only.
     */
    private static final int INIT_ENERGY = 190;
    private static final int MAX_HEALTH = 22;
    private static final int SHOT_ENERGY_COST = 19;
    private static final int TELEPORT_ENERGY_COST = 140;
    private static final int SHIELD_ENERGY_COST = 3;

    /**
     * Constats for use in this and extended classes.
     */
    protected static final int STRAIGHT_HEADING = 0;
    protected static final int LEFT_TURN = 1;
    protected static final int RIGHT_TURN = -1;

    /**
     * In game member variables.
     * @param healthLevel
     */
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
        if(!shieldOn) {
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
        maxEnergyLevel = 210;
        gunCoolDown = 0;
        shieldOn = false;
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
        if(!shieldOn)
            updateNoShieldStats();
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
    public Image getImage(){
        if(shieldOn)
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

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
        if(currentEnergyLevel >= SHIELD_ENERGY_COST && !shieldOn)
            shieldOn = true;
        else
            shieldOn = false;
    }

    /**
     * Turns on or off the shield by request.
     * @param command turns on the shield if true; turns off otherwise.
     */
    public void shieldControl(boolean command){
        if(command)
            shieldOn();
        else
            shieldOn = false;
    }

    /**
     * Gets current shield status.
     */
    public boolean isShieldOn(){
        return shieldOn;
    }

    /**
     * Reduces the current energy level whenever the shield is on.
     */
    public void updateShieldStats(){
        if(isShieldOn())
            currentEnergyLevel -= SHIELD_ENERGY_COST;
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

    /**
     * Turns the ship in the direction of the closest ship and accelerates in that direction
     * @param shipPhysics current ship's physics object
     * @param angle angle towards closest ship in radians
     */
    protected void pursuitShip(SpaceShipPhysics shipPhysics, double angle){
        if(angle > 0){
            shipPhysics.move(true, LEFT_TURN);
        } else if (angle < 0) {
            shipPhysics.move(true, RIGHT_TURN);
        } else
            shipPhysics.move(true, STRAIGHT_HEADING);
    }

    /**
     * Strategy for a ship that tries to bash other ships. When it's about to bash it turns on the shield.
     * @param game SpaceWars object representing the current game
     */
    protected void basherStrategy(SpaceWars game){
        SpaceShip closest = game.getClosestShipTo(this);
        SpaceShipPhysics physics = getPhysics();
        double angle = getPhysics().angleTo(closest.getPhysics());
        double distance = getPhysics().distanceFrom(closest.getPhysics());

        if(distance <= 0.19)
            shieldControl(true);
        else{
            shieldControl(false);
            chargeEnergy();
        }

        pursuitShip(physics, angle);
        updateShieldStats();
    }

    /**
     * Strategy for a ship that aggressively attacks other ships by firing and chasing them.
     * @param game SpaceWars object representing the current game
     */
    protected void aggressiveStrategy(SpaceWars game){
        SpaceShip closest = game.getClosestShipTo(this);
        double angle = getPhysics().angleTo(closest.getPhysics());

        if(angle < 0.21)
            fire(game);

        pursuitShip(getPhysics(), angle);
        updateGunCoolDown();
        chargeEnergy();
    }

    /**
     * Strategy for a ship that runs away from other ships and avoids collision.
     * @param game SpaceWars object representing the current game
     */
    protected void runnerStrategy(SpaceWars game){
        SpaceShip closest = game.getClosestShipTo(this);
        SpaceShipPhysics physics = getPhysics();
        double angle = getPhysics().angleTo(closest.getPhysics());
        double distance = getPhysics().distanceFrom(closest.getPhysics());

        if(distance <= 0.25 && angle <= 0.23)
            teleport();

        if(angle >= 0){
            physics.move(true, RIGHT_TURN);
        } else if (angle < 0) {
            physics.move(true, LEFT_TURN);
        }
    }
}

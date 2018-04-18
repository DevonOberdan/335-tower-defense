package model;
/**
 * 
 * @author Taite Nazifi
 */
public class Player {

	private int health;
	private int balance;
	/**
	 * 
	 * @param game
	 */
	public Player(Map game) {
		
	}
	/**
	 * 
	 * @param damage
	 * @return
	 */
	public boolean doDamage(int damage) {
		if (health <= 0) {
			return false;
		}
		this.health -= damage;
		return true;
	}
	/**
	 * 
	 * @param amt
	 */
	public void deposit(int amt) {
		this.balance += amt;
	}
	
	/**
	 * 
	 * @param amt
	 * @return
	 */
	public boolean withdraw(int amt) {
		if(this.balance <= 0)
			return false;
		this.balance -= amt;
		return true;
	}
}

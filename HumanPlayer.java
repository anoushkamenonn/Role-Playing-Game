package rpg;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents the human player and holds their units in this role-playing game.
 */
public class HumanPlayer {

	/**
	 * Human Unit 1: Falia
	 */
	Unit falia;

	/**
	 * Human Unit 2: Erom
	 */
	Unit erom;

	/**
	 * Human Unit 3: Ama
	 */
	Unit ama;

	/**
	 * A random number generator to be used for returning random levels and jobs.
	 */
	Random random = new Random();

	/**
	 * A scanner to be used for selecting moves and targets.
	 */
	Scanner scan = new Scanner(System.in);

	/**
	 * Constructs a human player.
	 */
	// Create instance of human player
	public HumanPlayer(){
		// Initialize unit per player
		this.falia = new Unit("Falia", generateLevel(),generateJob());
		this.erom = new Unit("Erom", generateLevel(),generateJob());
		this.ama = new Unit("Ama", generateLevel(),generateJob());
	}

	// Getters and Setters

	/**
	 * Returns the falia Unit.
	 * Note: This method does not take any parameters.
	 * @return falia
	 */
	public Unit getFalia(){
		return falia;
	}

	/**
	 * Returns the erom Unit.
	 * Note: This method does not take any parameters.
	 * @return erom
	 */
	public Unit getErom() {
		return erom;
	}

	/**
	 * Returns the ama Unit.
	 * Note: This method does not take any parameters.
	 * @return ama
	 */
	public Unit getAma() {
		return ama;
	}

	/**
	 * Randomly chooses a string representing the level of a unit by generating a random integer.
	 * There are three possible levels: low, medium, high.
	 * Note: This method does not take any parameters.
	 * @return String of the generated level of a human's unit
	 */
	private String generateLevel(){
		// Initiate generatedLevel variable
		String generatedLevel;

		// generate a random integer from 0 to 2
		int randomInt = this.random.nextInt(3);

		// assign generatedLevel based on randomInt value
		if(randomInt == 0){
			generatedLevel = "low";
		}
		else if(randomInt == 1){
			generatedLevel = "medium";
		}
		else{
			generatedLevel = "high";
		}
		
		// Return string with level
		return generatedLevel;
	}

	/**
	 * Randomly chooses a string representing the job of a unit by generating a random integer.
	 * There are three possible jobs: mage, knight, archer.
	 * Note: This method does not take any parameters.
	 * @return String of the generated job a human's unit will take on
	 */
	private String generateJob(){
		String generatedJob;

		// generate a random integer from 0 to 2
		int randomInt = this.random.nextInt(3);

		// assign generatedJob based on randomInt value
		if(randomInt == 0){
			generatedJob = "mage";
		}
		else if(randomInt == 1){
			generatedJob = "knight";
		}
		else{
			generatedJob = "archer";
		}

		// Return string with job
		return generatedJob;
	}

	/**
	 * Checks if the user entered a valid move string, meaning it begins with one of the following letters: 'a' 'A' 'b' 'B'
	 * Prints a friendly message to enter a valid input and returns null if the string is invalid.
	 * @param move String representing the move to be performed by a human unit, for example, "attack" or "block"
	 * @return String of "attack" or "block" or null
	 */
	public String validateMove(String move){

		// Students: your code goes here.
		
		// Make sure player enters valid move
		if(move == null || move.isEmpty()){
			System.out.println("Please enter valid move.");
			return null;
		}

		// Convert move string to lower case
		move = move.toLowerCase();
		
		// Check first letter of move & return choice
		char first = move.charAt(0);
		if(first == 'a'){ 
			return "attack"; 
		} 
		else if(first == 'b'){ 
			return "block"; 
		} 
		
		// If first letter not valid return null
		else{
			System.out.println("Please enter a valid move.");
			return null;
		}
	}


	/**
	 * Checks if the computer target selected by the human is alive and returns said target if it exists.
	 * If the target with the given name is not alive or does not exist, print a message saying so and return null.
	 * @param targetName String that should be the name of a computer unit
	 * @param computer ComputerPlayer that the human is currently playing against
	 * @return Unit representing the target belonging to the computer or null
	 */
	public Unit selectTarget(String targetName, ComputerPlayer computer){

		// Students: your code goes here.
		
		// Check if target name is valid & alive
		if(targetName == null || targetName.isEmpty()){
			System.out.println("Invalid target name.");
			return null;
		}

		// Match name with computer unit
		if(targetName.equals("Criati")){
			// Check if target HP > 0 & return object (same for remaining)
			if(computer.getCriati().getHp() > 0){
				return computer.getCriati();
			} else {
				// Print message if target already vanquished (same for remaining)
				System.out.println("Criati has already been vanquished");
				return null;
			}
		}
		else if(targetName.equals("Ledde")){
			if(computer.getLedde().getHp() > 0){
				return computer.getLedde();
			} else {
				System.out.println("Ledde has already been vanquished");
				return null;
			}
		}
		else if(targetName.equals("Tyllion")){
			if(computer.getTyllion().getHp() > 0){
				return computer.getTyllion();
			} else {
				System.out.println("Tyllion has already been vanquished");
				return null;
			}
		}
		else {
			// Print message if target does not exist
			System.out.println(targetName + " is not a member of the enemy’s forces!");
			return null;
		}

	}

	/**
	 * Determines the strength of the attacker by comparing the attacker's job and the job of the target.
	 * Mages are strong against knights, but weak against archers. Knights are strong against archers, but weak against mages.
	 * There are three possible attacker strengths: same, strong, weak.
	 * @param attacker Unit belonging to human that is attacking the target
	 * @param target Unit belonging to computer that is being attacked by the human
	 * @return String representing the strength of the attacker relative to the target
	 */
	public String determineAttackerStrength(Unit attacker, Unit target){
		String determinedStrength;

		// assign determinedStrength by comparing job of attacker with job of the target
		if(attacker.getJob().equalsIgnoreCase(target.getJob())){
			determinedStrength = "same";
		}
		else if((attacker.getJob().equalsIgnoreCase("knight") && target.getJob().equalsIgnoreCase("archer")) ||
				(attacker.getJob().equalsIgnoreCase("archer") && target.getJob().equalsIgnoreCase("mage")) ||
				(attacker.getJob().equalsIgnoreCase("mage") && target.getJob().equalsIgnoreCase("knight"))){
			determinedStrength = "strong";
		}
		else{
			determinedStrength = "weak";
		}

		return determinedStrength;
	}

	/**
	 * For the given unit, allow human player to pick between attacking a target of their choosing or blocking.
	 * This human unit will carry out the selected move during its turn.
	 * Note: This method does not return anything.
	 * @param unit Unit that is currently taking a turn
	 * @param computer ComputerPlayer that human is playing against
	 */
	public void moveUnit(Unit unit, ComputerPlayer computer){

		// Students: your code goes here.
		// Check if unit is still alive
		if(unit.getHp() <= 0){
			System.out.println(unit.getName() + " is knocked out and cannot move!");
			return;
		}

		// Ask player what move to make
		// Initialize move
		String move = null;
		while(move == null){
			// Prompt player to select move 
			System.out.print("Enter move for " + unit.getName() + " (attack/block): ");
			String userInput = scan.nextLine();
			// Call validate move
			move = validateMove(userInput);
		}

		// If move = attack
		if(move.equals("attack")){
			// Initialize target
			Unit target = null;

			// Prompt player to select target
			while(target == null){
				System.out.print("Select target (Criati, Ledde, Tyllion): ");
				// Receive user input
				String targetName = scan.nextLine();
				target = selectTarget(targetName, computer);
			}

			// Call determineAttackerStrength
			String strength = determineAttackerStrength(unit, target);

			// Calculate and apply damage
			int damage = unit.attack(strength);
			target.receiveDamage(damage);
		}

		// Activate unit block ability
		else if(move.equals("block")){
			unit.block();
		}

	}

	/**
	 * Resets temporary defensive buff of each human unit by setting temporaryDefense back to 0.
	 * Note: This method does not take any parameters and does not return anything.
	 */
	public void resetTemporaryDefense(){
		this.erom.setTemporaryDefense(0);
		this.falia.setTemporaryDefense(0);
		this.ama.setTemporaryDefense(0);
	}

	/**
	 * Determines if human player has lost or is knocked out.
	 * This is done by checking if all of its three units are knocked out.
	 * Note: This method does not take any parameters.
	 * @return boolean true if human has no units left or false
	 */
	public boolean isKnockedOut(){

		// return true if all human units have 0 HP or less
		return this.falia.getHp() <= 0 && this.erom.getHp() <= 0 && this.ama.getHp() <= 0;
	}

}
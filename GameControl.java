package rpg;
import java.util.Scanner;

/**
 * This is a simplified version of a role-playing game.
 */
public class GameControl {

	/**
	 * Creates a human player to play the game.
	 */
	HumanPlayer human = new HumanPlayer();

	/**
	 * Creates a computer player to play the game.
	 */
	ComputerPlayer computer = new ComputerPlayer();

	/**
	 * Prints the game's context and rules.
	 * Note: This method does not take any parameters and does not return anything.
	 */
	public void printInstructions(){
		System.out.println();
		System.out.println("Welcome to the final battle against enemy forces. You will be facing off against the computer.");
		System.out.println("Each of you will have 3 units with randomly generated jobs and levels.");
		System.out.println("The jobs are: mage, knight, and archer. Archers are strong against mages, but weak against knights.");
		System.out.println("Mages are strong against knights, but weak against archers. Knights are strong against archers, but weak against mages.");
		System.out.println("There are two moves: attack (deal damage to one target) and block (temporarily increase defense).");
		System.out.println("Combat is turn based; all your love units will take a turn and then all the computer's live units will take a turn.");
		System.out.println("You have 10 turns to defeat the computer. If both players still have units standing, you only win ");
		System.out.println("if the combined HP of your units exceeds the computer's.");
		System.out.println();
	}

	/**
	 * Prints the current status of all human units and all computer units.
	 * Note: This method does not take any parameters and does not return anything.
	 */
	public void printStatus(){
		System.out.println();
		System.out.println("Your units:");
		this.human.getFalia().printCurrentStatus();
		this.human.getErom().printCurrentStatus();
		this.human.getAma().printCurrentStatus();
		System.out.println();
		System.out.println("Computer units:");
		this.computer.getCriati().printCurrentStatus();
		this.computer.getLedde().printCurrentStatus();
		this.computer.getTyllion().printCurrentStatus();
		System.out.println();
	}

	/**
	 * Takes the human player's turn by calling moveUnit on each of the human player's three units: Falia, Erom, and Ama.
	 * Prints the unit's job and level before moving it. Checks if there is no winner before proceeding to the next move.
	 * If there is a winner between the first and second unit's turn or between the second and third unit's turn,
	 * then return out of the method to end the human turn.
	 * Resets any computer temporary defense after all human units have made their move.
	 * Note: This method does not return anything.
	 * @param turn int representing the current turn that the game is on.
	 */
	public void takeHumanTurn(int turn){

		// Students: your code goes here.
		Scanner input = new Scanner(System.in);
		
		// Initialize humanUnits & computerUnits for loop
		Unit[] humanUnits = {human.getFalia(), human.getErom(), human.getAma()};
		Unit[] computerUnits = {computer.getCriati(), computer.getLedde(), computer.getTyllion()};

		// Move through human players
		for (Unit u : humanUnits) {
			if (u.getHp() <= 0) continue;

			// Announce player
			System.out.println(u.getName() + " (Job: " + u.getJob() + 
					", Level: " + u.getLevel() + ") taking action");

			// Ask human for move
			String move;
			while (true) {
				// Choose attack or block
				System.out.print("Choose move for " + u.getName() + " (attack/block): ");
				move = input.nextLine();
				// exit & move to next portion if valid choice
				if (move.equals("attack") || move.equals("block")) break;
				System.out.println("Please choose 'attack' or 'block'.");
			}

			Unit target = null;
			if (move.equals("attack")) {
				// Select target
				while (true) {
					System.out.print("Select target (criati/ledde/tyllion): ");
					String t = input.nextLine();
					// Identify which computer target 
					if (t.equalsIgnoreCase("criati")) target = computer.getCriati();
					else if (t.equalsIgnoreCase("ledde")) target = computer.getLedde();
					else if (t.equalsIgnoreCase("tyllion")) target = computer.getTyllion();
					// Check if target exists & has HP & move to next 
					if (target != null && target.getHp() > 0) break;
					System.out.println("Invalid target or target already knocked out.");
				}

				// Call attackerStrength from ComputerPlayer & store 
				String attackerStrength = computer.determineAttackerStrength(target, u);
				// Apply strength of attack to target's HP
				target.receiveDamage(u.attack(attackerStrength));
				if (target.getHp() < 0) target.setHp(0);
				// Check & announce HP of target 
				System.out.println(target.getName() + " has " + target.getHp() + " HP remaining.");
			} else {
				// If human chooses to block instead of attack
				u.block();
				System.out.println(u.getName() + " is blocking this turn.");
			}

			// stop if game ended
			if (getWinner(turn) != null) return; 
		}

		// Reset computer temporary defense (call from ComputerPlayer)
		computer.resetTemporaryDefense();

	}

	/**
	 * Takes the computer player's turn and resets any human temporary defense after the computer has made its moves.
	 * Note: This method does not take any parameters and does not return anything.
	 */
	public void takeComputerTurn(){

		// Students: your code goes here.
		// Call computer strategy from ComputerPlayer
		computer.strategy(human.getFalia(), human.getErom(), human.getAma());
		
		// Reset human temporary defense (call from HumanPlayer)
		human.resetTemporaryDefense();
	}

	/**
	 * Gets the winner of the game based on the turn parameter and whether one of the players has been knocked out.
	 * If the turn is less than 10, return null if both players are alive, otherwise return the winner if the opposing player is knocked out.
	 * If both players still have living units after 10 turns, then the player with the greatest sum of HP wins, otherwise it is a tie.
	 * @param turn int representing the current turn that the game is on.
	 * @return String representing who won the game ("human" or "computer") or "tie" if there is a tie.
	 * Return null if both players are still alive and the current turn is less than 10.
	 */
	public String getWinner(int turn){

		// Students: your code goes here.
		// Check both players still alive
		boolean humanAlive = !human.isKnockedOut();
		boolean computerAlive = !computer.isKnockedOut();

		// Check if current turn parameter is less than 10
		if (turn < 10) {
			// Check status of human & computer players when turn < 10
			if (!humanAlive) return "computer";
			if (!computerAlive) return "human";
			// If either > 0 HP no winner yet
			return null;
		}

		// Turn 10 reached
		// Calculate final HP
		int humanHp = human.getFalia().getHp() + human.getErom().getHp() + human.getAma().getHp();
		int computerHp = computer.getCriati().getHp() + computer.getLedde().getHp() + computer.getTyllion().getHp();

		// Return winner or tie
		if (humanHp > computerHp) return "human";
		else if (computerHp > humanHp) return "computer";
		else return "tie";
	}

	/**
	 * Creates an instance of GameControl and contains the flow of this role-playing game.
	 * Note: This method does not return anything.
	 * @param args Not used.
	 */
	public static void main(String[] args){

		// Students: your code goes here.

		//TODO: create GameControl object and print the game instructions
		// Create instance of GameControl
		GameControl game = new GameControl();
		game.printInstructions();

		//TODO: initialize a String variable to keep track of the winner
		String winner = null;

		//TODO: initialize a boolean variable to keep track of whether someone has won within 10 turns
		boolean gameOver = false;

		/*
		 *  TODO: Create a loop that runs 10 times or exits if there is a winner. In each iteration:
		 *    - print the current turn number
		 *    - print the current status of all units. Hint: printStatus() is given to you in this class
		 *    - take the human player's turn
		 *    - check for a winner and update your String variable and boolean variable accordingly
		 *    - print the current status of all units. Hint: printStatus() is given to you in this class
		 *    - take the computer player's turn
		 *    - check for a winner and update your String variable and boolean variable accordingly
		 */
		// Game loop runs 10 turns
		for (int turn = 1; turn <= 10 && !gameOver; turn++) {
			// Print turn #
			System.out.println("Turn:" + turn);
			// Call printStatus
			game.printStatus();

			// Human turn
			game.takeHumanTurn(turn);
			// Check for a winner, if not null then end game (break)
			winner = game.getWinner(turn);
			if (winner != null) {
				gameOver = true;
				break;
			}

			// Show updated status after human turn
			game.printStatus();

			// Computer turn
			game.takeComputerTurn();
			// Check for a winner, if not null then end game (break)
			winner = game.getWinner(turn);
			if (winner != null) {
				gameOver = true;
				break;
			}
		}

		//TODO: Find the winner if there wasn't one determined within the 10 turns
		if (winner == null) winner = game.getWinner(10);

		//TODO: Print the end result of the game
		System.out.println("GAME OVER!!!");
		if (winner.equals("tie")) System.out.println("TIE GAME.");
		else System.out.println("WINNER: " + winner);
	}
}
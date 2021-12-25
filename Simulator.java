import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Simulator {
	Robot[] robots;
	Alliance[] alliances;
	
	int[][] winScores, lossScores; //sandstorm, teleop, end, total
	
	public Simulator(int totalRobots) {
		if(totalRobots % 3 != 0) {
			totalRobots -= totalRobots % 3;
		}
		robots = new Robot[totalRobots];
		alliances = new Alliance[totalRobots/3];
		int matches = (int) (1/2.0 * totalRobots/3 * (totalRobots/3 - 1));
		winScores = new int[4][matches];
		lossScores = new int[4][matches];
	}
	
	public void createRobots() {
		int startLevel, cycles, panels, cargo, endLevel;
		for(int i = 0; i < robots.length; i++) {
			startLevel = (int) (Math.random() * 3);
			cycles = (int) (Math.random() * 13 + 1);
			panels = (int) (Math.random() * cycles + 1);
			cargo = Math.max((cycles - Math.max(panels, 3)), 0);
			endLevel = (int) (Math.random() * 4);
			robots[i] = new Robot(i, startLevel, cycles, panels, cargo, endLevel);
		}
	}
	
	public void createAlliances() {
		Robot robot1, robot2, robot3;
		for(int i = 0; i < alliances.length; i++) {
			robot1 = robots[i*3];
			robot2 = robots[i*3+1];
			robot3 = robots[i*3+2];
			alliances[i] = new Alliance(i, robot1, robot2, robot3);
		}
	}
	
	public void runMatches() {
		Alliance alliance1, alliance2;
		int matchNum = 0;
		for(int a = 0; a < alliances.length; a++) {
			alliance1 = alliances[a];
			for(int b = a+1; b < alliances.length; b++) {
				alliance2 = alliances[b];
				if(alliance1.totalScore >= alliance2.totalScore) {
					winScores[0][matchNum] = alliance1.sandstormScore; lossScores[0][matchNum] = alliance2.sandstormScore;
					winScores[1][matchNum] = alliance1.teleopScore; lossScores[1][matchNum] = alliance2.teleopScore;
					winScores[2][matchNum] = alliance1.endgameScore; lossScores[2][matchNum] = alliance2.endgameScore;
					winScores[3][matchNum] = alliance1.totalScore; lossScores[3][matchNum] = alliance2.totalScore;
				} else {
					winScores[0][matchNum] = alliance2.sandstormScore; lossScores[0][matchNum] = alliance1.sandstormScore;
					winScores[1][matchNum] = alliance2.teleopScore; lossScores[1][matchNum] = alliance1.teleopScore;
					winScores[2][matchNum] = alliance2.endgameScore; lossScores[2][matchNum] = alliance1.endgameScore;
					winScores[3][matchNum] = alliance2.totalScore; lossScores[3][matchNum] = alliance1.totalScore;
				}
				matchNum++;
			}
		}
	}
	
	public void analyzeMatches() {
		//System.out.println(Arrays.toString(winScores[3]));
		//System.out.println(Arrays.toString(lossScores[3]));
		double avgWinScore = Arrays.stream(winScores[3]).sum() / (double) winScores[3].length;
		double avgLossScore = Arrays.stream(lossScores[3]).sum() / (double) lossScores[3].length;
		int maxScore = Arrays.stream(winScores[3]).max().getAsInt();
		int minScore = Arrays.stream(lossScores[3]).min().getAsInt();
		int maxComponent = Arrays.stream(winScores[0]).max().getAsInt() +
						   Arrays.stream(winScores[1]).max().getAsInt() +
						   Arrays.stream(winScores[2]).max().getAsInt();
		
		System.out.println();
		System.out.println("Alliances Created      | " + alliances.length);
		System.out.println("Matches Played         | " + winScores[3].length);
		System.out.println("Average Winning Score  | " + avgWinScore);
		System.out.println("Average Losing Score   | " + avgLossScore);
		System.out.println("Maximum Score          | " + maxScore);
		System.out.println("Minimum Score          | " + minScore);
		System.out.println("Maximum Combined Score | " + maxComponent);
		System.out.println();
	}
	
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		while(true) {
			int robots = 0;
			while(robots < 6) {
				System.out.print("Enter number of robots (>= 6): ");
				try {
					robots = Integer.parseInt(scnr.nextLine());
				} catch(Exception e) {}
			}
			Simulator monteCarlo = new Simulator(robots);
			monteCarlo.createRobots();
			monteCarlo.createAlliances();
			monteCarlo.runMatches();
			monteCarlo.analyzeMatches();
		}
	}
}
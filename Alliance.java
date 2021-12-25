public class Alliance {
	int number;
	Robot[] robots;
	
	int[] startLevels = new int[3];
	int cycleSum, panelSum, cargoSum;
	int[] endLevels = new int[4];
	
	int sandstormScore, teleopScore, endgameScore;
	int totalScore;
	
	public Alliance(int number, Robot robot1, Robot robot2, Robot robot3) {
		this.number = number;
		Robot[] robots = {robot1, robot2, robot3};
		
		for(Robot robot : robots) {
			startLevels[robot.startLevel]++;
			cycleSum += robot.cycles;
			panelSum += robot.panels;
			cargoSum += robot.cargo;
			endLevels[robot.endLevel]++;
		}
		
		for(int i = 1; i < 3; i++) {
			sandstormScore += startLevels[i] * i * 3;
		}
		teleopScore = panelSum * 2 + cargoSum * 3;
		for(int i = 1; i < 4; i++) {
			endgameScore += endLevels[i] * Math.pow(2,i-1) * 3;
		}
		
		totalScore = sandstormScore + teleopScore + endgameScore;
	}
}
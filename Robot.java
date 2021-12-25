public class Robot {
	public int team, startLevel, cycles, panels, cargo, endLevel;
	
	public Robot(int team, int startLevel, int cycles, 
			     int panels, int cargo, int endLevel) {
		this.team = team;
		this.startLevel = startLevel;
		this.cycles = cycles;
		this.panels = panels;
		this.cargo = cargo;
		this.endLevel = endLevel;
	}
}
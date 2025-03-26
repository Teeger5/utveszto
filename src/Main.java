public class Main {
	public static void main(String[] args) {
		System.out.println("Hello, World!");

		// Example grid initialization
		int[][] exampleGrid = {
				{1, 2, 0, 3},
				{6, 5, 0, 4},
				{0, 0, 0, 10},
				{7, 8, 0, 9}
		};

		var utveszto = new Utveszto(exampleGrid);

		// Start from the cell containing '1'
		if (utveszto.utkereses()) {
			System.out.println("Találtam egy útvonalat");
			utveszto.printPath();
		} else {
			System.out.println("Nem találtam útvonalat");
		}
		System.out.println("Összes megtett lépés: " + utveszto.getAllSteps());
	}
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
/**
 * CSPPathFinder egy program, amely egy hálózaton belüli útvonalat keres
 * korlátozások alapján, CSP (Constraint Satisfaction Problem) megközelítéssel.
 * A cél olyan útvonal megtalálása, amely:
 * - Növekvő számozott mezőket érint.
 * - Áthalad nem számozott mezőkön is (0).
 * - Nem halad átlósan.
 * - Minden mezőt legfeljebb egyszer érint.
 */
public class Utveszto {

	private static final int MEGLATOGATOTT = -1;
	private static final int URES = 0;
	private static final int KERESZTEZODES = -2;

	/**
	 * A hálózatot reprezentáló mátrix. Az egyes cellák értéke:
	 * - Egy szám, amelyet növekvő sorrendben kell meglátogatni.
	 * - 0, ha a mező nem számozott, de át kell haladni rajta.
	 * - -1, ha a mező már meglátogatott.
	 * Erre vannak a MEGLATOGATOTT és az URES nevű konstansok
	 */
	private final int[][] board;

	/**
	 * Az útvonal koordinátái, amelyek a megtalált útvonalat reprezentálják.
	 * Minden int[] két elemet tartalmaz: {sor, oszlop}.
	 */
	private final List<int[]> path = new ArrayList<>();

	/**
	 * A hálózat sorainak száma.
	 */
	private final int rows;

	/**
	 * A hálózat oszlopainak száma.
	 */
	private final int cols;

	/**
	 * A legnagyobb sszám, amit el kell érni
	 */
	private final int top;

	private int allSteps = 0;

	/**
	 * Inicializálja a hálózatot, és beállítja a problématér paramétereit.
	 *
	 * @param inputGrid Egy 2D mátrix, amely a hálózatot reprezentálja,
	 *                  ahol az útvonalat meg kell találni.
	 */
	public Utveszto(int[][] inputGrid) {
		board = inputGrid;
		rows = board.length;
/*		for (var x : board) {
			if (x.length != board[0].length) {
				throw new RuntimeException("A tábla sorai nem mind egyenlő szélesek");
			}
		}*/
		cols = board[0].length;

		top = getMax(board);
		System.out.println("top: " + top);
	}

	public boolean utkereses () {
		int startX = 0, startY = 0, min = board[0][0];
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] != URES && board[y][x] < min) {
					min = board[y][x];
					startX = x;
					startY = y;
				}
			}
		}
		return findPath(startX, startY, 0, 1);
	}

	/**
	 * Rekurzívan keresi a megfelelő útvonalat a hálózatban egy adott
	 * kezdő cellából. Az útvonal:
	 * - Növekvő sorrendben érinti a számozott cellákat.
	 * - Áthalad nem számozott (0) mezőkön.
	 * - Nem halad átlósan.
	 * - Nem lép vissza már meglátogatott cellára.
	 *
	 * @param x A jelenlegi cella sor indexe.
	 * @param y A jelenlegi cella oszlop indexe.
	 * @param steps A következő szám, amelyet meg kell látogatni.
	 * @return true, ha sikerült érvényes útvonalat találni, false, ha nem.
	 */
	private boolean findPath(int x, int y, int steps, int nextLvl) {
		allSteps++;
		if (x < 0 || y < 0 || x >= rows || y >= cols) return false;

		if (board[y][x] == MEGLATOGATOTT) {
			return false;
		}

		var nextLevel = nextLvl;
		if (board[y][x] == nextLvl) {
			nextLevel = board[y][x] + 1;
		}

		printBoard(x, y, steps, nextLvl);

		if (steps == (rows * cols) - 1) {
			var result = board[y][x] != URES; // == top;
			System.out.println("Minden mező érintve, csúcson vagyunk? " + result);
			return result;
		}

/*		if (board[y][x] == top) {
			var temp = board[y][x];
			board[y][x] = MEGLATOGATOTT;
			var max = getMax(board);
			printBoard(x, y, steps, max);
			System.out.println("Legnagyobb érték elérve, tábla max: " + max);
			board[y][x] = temp;
			return max == MEGLATOGATOTT;
		}*/

		if (board[y][x] > nextLvl) {
			return false;
		}

		path.add(new int[]{y, x});
		int temp = board[y][x];
		board[y][x] = MEGLATOGATOTT;

		steps++;
		if (
				findPath(x + 1, y, steps, nextLevel)
						|| findPath(x - 1, y, steps, nextLevel)
						|| findPath(x, y + 1, steps, nextLevel)
						|| findPath(x, y - 1, steps, nextLevel)) {
			return true;
		}

		board[y][x] = temp;
		path.removeLast();
		return false;
	}

	/**
	 * Kiírja a megtalált útvonal koordinátáit. Minden lépést
	 * sor-oszlop párosként jelenít meg.
	 */
	public void printPath() {
		for (int[] cell : path) {
			System.out.println("Step: " + cell[0] + ", " + cell[1]);
		}
	}

	/**
	 * Kiírja a tábla tartalmát, vizualizálva a jelenlegi állapotát
	 * X a jelenlegi pozíciót jelöli
	 * --- az eddig megtett utat jelöli
	 * @param posX jelenlegi X pozíció
	 * @param posY jelenlegi Y pozíció
	 * @param steps az út során eddig megtett lépések száma
	 * @param nextLvl a következő keresett szám a mezők között
	 */
	private void printBoard (int posX, int posY, int steps, int nextLvl) {
		for (int s = 0; s < steps; s++) {
			System.out.print("   ");
		}
		System.out.println("Step: " + steps + ", field: " + board[posX][posY] + ", nextLevel: " + nextLvl);
		for (int y = 0; y < board.length; y++) {
			for (int s = 0; s < steps; s++) {
				System.out.print("   ");
			}
			System.out.print("|");
			for (int x = 0; x < board[y].length; x++) {
				if (x == posX && y == posY) {
					System.out.print(" X ");
				}
				else {
					switch (board[y][x]) {
						case MEGLATOGATOTT: System.out.print("---"); break;
						case URES: System.out.print("   "); break;
						case KERESZTEZODES: System.out.print(" + "); break;
						default: System.out.printf(" %-2d", board[y][x]); break;
					}
				}
			}
			System.out.println("|");
		}
		System.out.println();
	}

	/**
	 * Megkeresi a legnagyobb értéket egy int mátrixban
	 * @param matrix a mátrix
	 * @return a legnagyobb érték a mátrixban
	 */
	public int getMax (int[][] matrix) {
		return Arrays.stream(matrix)
				.map(z -> Arrays.stream(z).max().getAsInt())
				.max(Comparator.naturalOrder()).get();
	}

	/**
	 * Visszaadja az összes megtett lépés számát (azaz hányszor futott a findPath metódus)
	 * @return összes megtett lépés száma
	 */
	public int getAllSteps() {
		return allSteps;
	}
}

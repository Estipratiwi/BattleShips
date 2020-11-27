
public class BattleShips {
    public static int numRows = 6;
    public static int numCols = 6;
    public static int playerShips;
    public static int computerShips;
    public static String[][] grid = new String[numRows][numCols];
    public static int[][] missedGuesses = new int[numRows][numCols];

    public static void main(String[] args){
        System.out.println("**** Selamat datang di game Battle Ships ****");
        System.out.println("Saat ini, Laut kosong");

        createOceanMap();

        deployPlayerShips();

        deployComputerShips();

        do {
            Battle();
        }while(BattleShips.playerShips != 0 && BattleShips.computerShips != 0);

        gameOver();
    }

    public static void createOceanMap(){

        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();


        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " ";
                if (j == 0)
                    System.out.print(i + "|" + grid[i][j]);
                else if (j == grid[i].length - 1)
                    System.out.print(grid[i][j] + "|" + i);
                else
                    System.out.print(grid[i][j]);
            }
            System.out.println();
        }


        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }

    public static void deployPlayerShips(){
        Scanner input = new Scanner(System.in);

        System.out.println("\nKerahkan kapal mu:");

        BattleShips.playerShips = 5;
        for (int i = 1; i <= BattleShips.playerShips; ) {
            System.out.print("Masukkan kordinat X untuk kapal mu " + i + " ship: ");
            int x = input.nextInt();
            System.out.print("Masukkan kordinat Y untuk kapal mu " + i + " ship: ");
            int y = input.nextInt();

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
            {
                grid[x][y] =   "@";
                i++;
            }
            else if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y] == "@")
                System.out.println("Anda tidak dapat menempatkan dua kapal di tempat yang sama");
            else if((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                System.out.println("Anda tidak dapat menempatkan kapal di luar " + numRows + " by " + numCols + " grid");
        }
        printOceanMap();
    }

    public static void deployComputerShips(){
        System.out.println("Komputer sedang menyebarkan kapal");

        BattleShips.computerShips = 5;
        for (int i = 1; i <= BattleShips.computerShips; ) {
            int x = (int)(Math.random() * 10);
            int y = (int)(Math.random() * 10);

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
            {
                grid[x][y] =   "x";
                System.out.println(i + ". Kapal dikerahkan");
                i++;
            }
        }
        printOceanMap();
    }

    public static void Battle(){
        playerTurn();
        computerTurn();

        printOceanMap();

        System.out.println();
        System.out.println("Kapal China: " + BattleShips.playerShips + " | Kapal Amerika: " + BattleShips.computerShips);
        System.out.println();
    }

    public static void playerTurn(){
        System.out.println("\nGiliran China");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Masukkan kordinat X: ");
            x = input.nextInt();
            System.out.print("Masukkan kodinat Y: ");
            y = input.nextInt();

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols))
            {
                if (grid[x][y] == "x")
                {
                    System.out.println("Boom! Anda menembak kapal musuh!");
                    grid[x][y] = "!"; //Hit mark
                    --BattleShips.computerShips;
                }
                else if (grid[x][y] == "@") {
                    System.out.println("Oh tidak, Anda menembak kapal sendiri :(");
                    grid[x][y] = "x";
                    --BattleShips.playerShips;
                    ++BattleShips.computerShips;
                }
                else if (grid[x][y] == " ") {
                    System.out.println("Oh tidak, meleset");
                    grid[x][y] = "-";
                }
            }
            else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                System.out.println("Anda tidak dapat menempatkan kapal anda diluar " + numRows + " by " + numCols + " grid");
        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));
    }

    public static void computerTurn(){
        System.out.println("\nGiliran amerika");

        int x = -1, y = -1;
        do {
            x = (int)(Math.random() * 6);
            y = (int)(Math.random() * 6);

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols))
            {
                if (grid[x][y] == "@")
                {
                    System.out.println("Amerika menenggelamkan kapal nya sendiri!");
                    grid[x][y] = "x";
                    --BattleShips.playerShips;
                    ++BattleShips.computerShips;
                }
                else if (grid[x][y] == "x") {
                    System.out.println("Amerika menenggelamkan kapal China");
                    grid[x][y] = "!";
                }
                else if (grid[x][y] == " ") {
                    System.out.println("Kapal Amerika meleset");

                    if(missedGuesses[x][y] != 1)
                        missedGuesses[x][y] = 1;
                }
            }
        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));
    }

    public static void gameOver(){
        System.out.println("Kapal China: " + BattleShips.playerShips + " | Kapal Amerika: " + BattleShips.computerShips);
        if(BattleShips.playerShips > 0 && BattleShips.computerShips <= 0)
            System.out.println("\n" + "Hore! Anda memenangkan pertempuran :)");
        else
            System.out.println("\n" + "Maaf, Anda kalah dalam pertempuran");
        System.out.println();
    }

    public static void printOceanMap(){
        System.out.println();

        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();


        for(int x = 0; x < grid.length; x++) {
            System.out.print(x + "|");

            for (int y = 0; y < grid[x].length; y++){
                System.out.print(grid[x][y]);
            }

            System.out.println("|" + x);
        }


        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }
}

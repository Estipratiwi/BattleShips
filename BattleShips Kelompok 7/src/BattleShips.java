import java.util.Scanner;

public class BattleShips {
    //untuk membuat map / ( createOceanMap dan printOcean Map )
    // numRows, numCols, AmerikaShips, ChinaShips, grid, dan missedGuesses yaitu Enkapulasi
    public static int numRows = 6;
    public static int numCols = 6;
    public static int AmerikaShips;
    public static int ChinaShips;
    public static String[][] grid = new String[numRows][numCols];
    public static int[][] missedGuesses = new int[numRows][numCols];

    //tampilan awal game battleship
    //dimana laut sedang kosong karena belum mengimputkan titik kordinat
    //membuat beberapa method
    public static void main(String[] args){   //menampung banyak method
        System.out.println("** Selamat datang di game Battleships **");
        System.out.println("=== Sekarang, laut sedang kosong ===\n");

        //Step 1 – Membuat method createOceanMap
        createOceanMap();

        //Step 2 – Membuat method deployAmerikaShips
        deployAmerikaShips();

        //Step 3 - Membuat method deployChinaShips
        deployChinaShips();

        //Step 4 Membuat method Battle
        do {
            Battle();
        }while(BattleShips.AmerikaShips != 0 && BattleShips.ChinaShips != 0);

        //Step 5 - Game over / Membuat method gameOver
        gameOver();
    }

    public static void createOceanMap(){
        //First section of Ocean Map / Bagian pertama dari method createOceanMap
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

        //Middle section of Ocean Map / Bagian tengah dari method OceanMap
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

        //Last section of Ocean Map / Bagian terakhir dari method OceanMap
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }

    //proses penyebaran kapal Amerika
    //menampilkan info kapal yang sedang disebar/dikerahkan
    //dengan memasukkan terlebih dahulu titik koordinat X Y
    public static void deployAmerikaShips(){
        Scanner input = new Scanner(System.in);

        System.out.println("\nAmerika (@) sedang menyebarkan kapal :");
        //Mengerahkan 5 kapal untuk Amerika
        BattleShips.AmerikaShips = 5;
        for (int i = 1; i <= BattleShips.AmerikaShips; ) {
            System.out.print("Masukkan kordinat X untuk kapal " + i + "  anda: ");
            int x = input.nextInt();
            System.out.print("Masukkan kordinat Y untuk kapal " + i + "  anda: ");
            int y = input.nextInt();

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
            {
                grid[x][y] =   "@";
                i++;
            }
            //kapal tidak dapat berada di titik koordinat yang sudah terpakai sehingga muncul info
            else if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y] == "@")
                System.out.println("anda tidak dapat menempatkan dua kapal atau lebih di lokasi yang sama");
            //kapal tidak dapat di tempat kan di luar titik koordinat yang sudah ditetapkan sehingga muncul info
            else if((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                System.out.println("Anda tidak dapat menempatkan kapal diluar dan titik koordinat " + " X = " + numRows
                        + " dengan " + " titik kordinat Y = " +numCols );
        }
        printOceanMap();
    }

    //proses penyebaran kapal China
    //menampilkan info kapal yang disebar/dikerahkan
    public static void deployChinaShips(){
        System.out.println("\nChina (x) sedang menyebarkan kapal");
        //Mengerahkan 5 kapal untuk China
        BattleShips.ChinaShips = 5;
        for (int i = 1; i <= BattleShips.ChinaShips; ) {
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

    //method battle yang berarti proses menembak antara Amerika dan China
    // dan menampilkan sisa kapal amerika dan China
    public static void Battle(){
        AmerikaTurn();
        ChinaTurn();

        printOceanMap();

        System.out.println();
        System.out.println("Kapal Amerika (@) : " + BattleShips.AmerikaShips + " | Kapal China (x) : " +
                BattleShips.ChinaShips);
        System.out.println();
    }

    // proses/giliran Amerika menembak China
    //menginput titik kordinat X Y ke kapal yang akan di tembak yaitu China
    //dan menampilkan info ketika salah menginput koordinat
    //menampilkan informasi ketika selesai menembak
    public static void AmerikaTurn(){
        System.out.println("\nGiliran Amerika (@) menembak");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Masukkan titik kordinat X: ");
            x = input.nextInt();
            System.out.print("Masukkan titik kordinat Y: ");
            y = input.nextInt();

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
            {
                if (grid[x][y] == "x") //if computer ship is already there; computer loses ship
                {
                    System.out.println("Boom! Amerika menenggelamkan kapal China (x)!");
                    grid[x][y] = "!"; //Hit mark
                    --BattleShips.ChinaShips;
                }
                else if (grid[x][y] == "@") {
                    System.out.println("Oh tidak, Amerika menenggelamkan kapal nya sendiri (@) :(");
                    grid[x][y] = "x";
                    --BattleShips.AmerikaShips;
                }
                else if (grid[x][y] == " ") {
                    System.out.println("Tembakan Amerika meleset");
                    grid[x][y] = "-";
                }
            }
            else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))  //invalid guess
                System.out.println("Anda tidak dapat menempatkan kapal diluar Map");
        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
    }

    //proses/giliran China menembak Amerika
    //menginput titik kordinat X Y ke kapal yang akan di tembak yaitu Amerika
    //dan menampilkan info ketika salah menginput koordinat
    //menampilkan informasi ketika selesai menembak
    public static void ChinaTurn(){
        System.out.println("\nGiliran China (x) menembak");
        //Guess co-ordinates
        int x = -1, y = -1;
        do {
            x = (int)(Math.random() * 6);
            y = (int)(Math.random() * 6);

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
            {
                if (grid[x][y] == "@") //if player ship is already there; player loses ship
                {
                    System.out.println("China menenggelamkan salah satu kapal Amerika (@)!");
                    grid[x][y] = "x";
                    --BattleShips.AmerikaShips;
                }
                else if (grid[x][y] == "x") {
                    System.out.println("China menenggelamkan salah satu kapal nya sendiri (x)");
                    grid[x][y] = "!";
                }
                else if (grid[x][y] == " ") {
                    System.out.println("Tembakan China meleset");
                    //Saving missed guesses for computer
                    if(missedGuesses[x][y] != 1)
                        missedGuesses[x][y] = 1;
                }
            }
        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
    }


    //menampilkan sisa kapal saat permainan selesai atau gameover
    //menampilkan siapa pemenangnya dan yang dikalah
    public static void gameOver(){
        System.out.println("Kapal Amerika: " + BattleShips.AmerikaShips + " | Kapal China: " + BattleShips.ChinaShips);
        if(BattleShips.AmerikaShips > 0 && BattleShips.ChinaShips <= 0)
            System.out.println("Hore! Amerika memenangkan pertarungan :)");
        else
            System.out.println("Sorry, Amerika kalah dalam pertempuran");
        System.out.println();
    }

    public static void printOceanMap(){
        System.out.println();
        //Bagian pertama dari method printOceanMap
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

        //Bagian tengah dari method printOceanMap
        for(int x = 0; x < grid.length; x++) {
            System.out.print(x + "|");

            for (int y = 0; y < grid[x].length; y++){
                System.out.print(grid[x][y]);
            }

            System.out.println("|" + x);
        }

        //Bagian terakhir dari method printOceanMap
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }
}
//Klasa ne vijim paraqet Algortmin e "Backtracking" per ta zgjedhur Tabelen e Sudokut
public class Sudoku_Backtracking implements Sudoku_Methods
{

    private static int BOXSIZE;
    private static int steps = 0;   
    private static int BOARDSIZE;                
    private static final String NAME = "Backtracking";
    
    
    private Integer[][] solution;


    public Sudoku_Backtracking(int gameBoxSize)
     {
       BOXSIZE = gameBoxSize;
       BOARDSIZE = BOXSIZE * BOXSIZE;
     }



     //Metoda ne vjiim e merr nje Tabele e ruan nga inputi dhe fillon duke i provuar zgjidhjet ne qdo iteracion duke e ruajtur zgjidhjen e duhur
    //Rikthen zgjidhjen ose tabele boshe nese nuk ka gjetur ndonje zgjidhje
    public Integer[][] getSolution(Integer[][] userInputPuzzle)
    {
        this.solution = userInputPuzzle;

        //Rikthejme zgjidhjen nese e kemi gjetur ate
        if(solveSudoku())
         {
           return solution;
         } 
        else{return new Integer[BOARDSIZE][BOARDSIZE];} //Rikthejme tabelen boshe nese nuk ka zgjidhje
    }





     //Algoritmi ne vijim e perdore 'kerkimin rekursive' me te gjitha vlerat e  mundshme te domenes te cilat i vendos ne Tabele nga upper-left e deri
    //tek bottom right (duke e llogaritur nje rresht para se te kaloj tek tjetri rresht). Domena E {1,2, ... ,9}
    //Rikthen 'true' nese : secila qeli e Tabeles e ka nje vlere te ruajtur aty 
    private boolean solveSudoku()
    {
        //Ruajme rreshtin tjeter boshe te Tabeles per t'ia caktuar vlerat
        int[] nextCell = getNextEmptyCell();

         //Ky 'if' shikon nese rreshti i thene nuk eshte 'boshe' (pra null) qe dmth kemi nje zgjidhje dhe pastaj e printon ne Console nr. e hapave qe u deshen
        //per te arritur deri tek zgjidhja
        if(solution[nextCell[0]][nextCell[1]] != null)
         {
           System.out.println("Steps = " + steps);
           return true;
         }

        //Kryejme 'loop' per secilen vlere brenda Domenes (1->9) 
        for(int value = 1; value < BOARDSIZE+1; value++)
        {
          steps++;
          
          //Nese vlera e shqyrtuar nuk eshte perdore ne te njejtin "rresht", "shtylle" apo "katrore" at. vazhdo
          if(isSafe(value, nextCell, solution))
           {
                solution[nextCell[0]][nextCell[1]] = value;

                if(solveSudoku())
                 {return true;}

                //Urdheri "BackTracking" e largon vleren e shqyrtuar ne qeline e caktuar dhe e provon vleren tjeter
                solution[nextCell[0]][nextCell[1]] = null;
           }
        }
        return false; //e luan rolin si 'trigger' per backtracking   
    }



    //E gjen qelizen tjeter boshe brenda rreshit te Tables 2D dhe e rikthen ate si Array 1D (indexi [0] --> rreshti i qelizes dhe indexi [1] --> shtylla e qelizes)
    private int[] getNextEmptyCell()
     {
        int[] cell = new int[2];

        for(int i = 0; i < BOARDSIZE; i++)
         {
            for(int j = 0; j < BOARDSIZE; j++)
             {
                if(solution[i][j] == null)
                {   
                    //Nese qeliza ne vijim nuk ka asnje vlere atehere riktheje ate
                    cell[0] = i;
                    cell[1] = j;
                    return cell;
                }
             }
         }
        //Rikthejme Ko-Ordinatat e 1st cell
        return cell;
     }



    //Rikthen true apo false varesisht se vlera 'value' a gjendet ne Rreshtin 'row' te Tabeles se cekur 'board'
    private boolean isSafeToUseInRow(int value, int row, Integer[][] board)
     {
        for(int i = 0; i < BOARDSIZE; i++)
        {
            if(board[row][i] == null)
            {}
            else if (board[row][i] == value) //Nese qeliza e ka nje vlere dhe ajo vlere eshte vlera e selektuar at. rikthe false sepse ajo po perdoret (already)
             {return false;}
        }
        //Rikthjeme true nese vlera e selektuar nuk eshte perdorur ende (at. kjo dmth. qe ajo mund te perdoret safely)
        return true;
     }



    //Rikthen true apo false varesisht se vlera 'value' a gjendet ne Shtyllen 'column' te Tabeles se cekur 'board'
    private boolean isSafeToUseInColumn(int value, int column, Integer[][] board)
     {
        for(int i = 0; i < BOARDSIZE; i++)
        {
            if(board[i][column] == null)
            {}
            else if (board[i][column] == value) //Nese qeliza e ka nje vlere dhe ajo vlere eshte vlera e selektuar at. rikthe false sepse ajo po perdoret (already)
             {return false;}
        }
        //Rikthjeme 'true' nese vlera e selektuar nuk eshte perdorur ende (at. kjo dmth. qe ajo mund te perdoret safely)        
        return true;
     }




    //Rikthen true apo false varesisht se vlera 'value' a gjendet ne qelizen 'cell' te Tabeles se cekur 'board' [false nese eshte ne perdorim]    
    private boolean isSafeToUseInBox(int value, int[] cell, Integer[][] board)
     {
        //Caktojme 'Rreshtin Fillues' te Box te shqyrtuar nga kordinata e qelise 'x' ne modulo madhesine e box
        int boxRow = cell[0] - (cell[0] % BOXSIZE);

        //Caktojme 'Rreshtin Fillues' te Box te shqyrtuar nga kordinata e qelise 'y' ne modulo madhesine e box
        int boxCol = cell[1] - (cell[1] % BOXSIZE);


        for(int i = 0; i < BOXSIZE; i++)
        {
            for (int j = 0; j < BOXSIZE; j++) 
            {
                if(board[i + boxRow][j + boxCol] == null)
                {}
                else if (board[i + boxRow][j + boxCol] == value) //Nese qeliza e ka nje vlere dhe ajo vlere eshte vlera e selektuar at. rikthe false sepse ajo po perdoret (ende)
                 {return false;}
               
            }
        }
        //Rikthjeme 'true' nese vlera e selektuar nuk eshte perdorur ende (at. kjo dmth. qe ajo mund te perdoret sigurt)        
        return true;
     }



     //Rikthen true apo false varesisht se vlera 'value' a gjendet ne qelizen 'cell' apo Rresht 'row' dhe Shtylle 'column' 
    //te Tabeles se cekur 'board' [false nese eshte ne perdorim]        
    private boolean isSafe(int value, int[] cell, Integer[][] board)
     {return isSafeToUseInRow(value, cell[0], board) && isSafeToUseInColumn(value, cell[1], board) && isSafeToUseInBox(value, cell, board);}


    public String getName()
     {return NAME;}
}
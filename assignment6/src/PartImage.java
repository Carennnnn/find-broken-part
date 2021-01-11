import java.io.*;

public class PartImage {
    private boolean[][]	pixels;
    private boolean[][]	visited;
    private int	rows;
    private int	cols;
    private int perimeter = 0;
    private int numOfPieces = 0;
    private boolean[][] pixels3;
    private boolean[][] pixels1;



    //Creates a new, blank PartImage with the given rows (r) and columns (c)
    public PartImage(int r, int c) {
        rows = r;
        cols = c;
        visited = new boolean[r][c];
        pixels = new boolean[r][c];
    }

    //Creates a new PartImage containing rw rows and cl columns
    //Initializes the 2D boolean pixel array based on the provided byte data
    //A 0 in the byte data is treated as false, a 1 is treated as true
    public PartImage(int rw, int cl, byte[][] data) {
        this(rw,cl);
        for (int r=0; r<10; r++) {
            for (int c=0; c<10; c++) {
                if (data[r][c] == 1)
                    pixels[r][c] = true;
                else
                    pixels[r][c]= false;
            }
        }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean getPixel(int r, int c) { return pixels[r][c]; }

    public void print() {
        for(int i = 0; i < getRows(); i++){
            for(int j = 0; j < getCols(); j++){
                if(pixels[i][j]){
                    System.out.print("*");
                }else{
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }


    public Point2D findStart() {
        for(int i = 0; i < getRows(); i++){
            for(int j = 0; j < getCols(); j++){
                if(pixels[i][j]){
                    Point2D start = new Point2D(i, j);
                    return start;
                }
            }
        }
        return null;
    }

    public Point2D findStartEachTime(boolean[][] pixels3) {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                if (pixels3[i][j]) {
                    Point2D start = new Point2D(i, j);
                    return start;
                }
            }
        }return null;
    }


    public int partSize() {
        int size = 0;
        for(int i = 0; i < getRows(); i++){
            for(int j = 0; j < getCols(); j++){
                if(pixels[i][j]){
                    size++;
                }
            }
        }
        return size;
    }

    public int partSize1() {
        int size = 0;
        for(int i = 0; i < getRows(); i++){
            for(int j = 0; j < getCols(); j++){
                if(pixels1[i][j]){
                    size++;
                }
            }
        }
        return size;
    }


    public boolean[][] copyPixels(){
        boolean[][] newPixel = new boolean[getRows()][getCols()];
        for(int i = 0; i < getRows(); i++){
            for(int j = 0; j < getCols(); j++){
                newPixel[i][j] = pixels[i][j];
            }
        }return newPixel;
    }

    private void expandFrom(int r, int c){
        pixels1 = copyPixels();
        expandFrom(r, c, pixels1);
    }

    private void expandFrom(int r, int c, boolean[][] pixels1) {

        //set this point to white

        pixels1[r][c] = false;


        //check upper pixel(r-1, c)
        if(r-1 >= 0 && r-1 <= 9 && c >= 0 && c <= 9){
            if(pixels1[r-1][c]){
                expandFrom(r-1, c, pixels1);
            }
        }

        //check left pixel(r, c-1)
        if(r >= 0 && r <= 9 && c-1 >= 0 && c-1 <= 9){
            if(pixels1[r][c-1]){
                expandFrom(r, c-1, pixels1);
            }
        }

        //check below pixel(r+1, c)
        if(r+1 >= 0 && r+1 <= 9 && c >= 0 && c <= 9){
            if(pixels1[r+1][c]){
                expandFrom(r+1, c, pixels1);
            }
        }

        //check right pixel(r, c+1)
        if(r >= 0 && r <= 9 && c+1 >= 0 && c+1 <= 9){
            if(pixels1[r][c+1]){
                expandFrom(r, c+1, pixels1);
            }
        }

    }


    private int numNextTo(int r, int c){

        int num = 0;

        if(r-1 >= 0 && r-1 <= 9 && c >= 0 && c <= 9){
            if(pixels[r-1][c]){
                num++;
            }
        }


        //check left pixel(r, c-1)
        if(r >= 0 && r <= 9 && c-1 >= 0 && c-1 <= 9){
            if(pixels[r][c-1]){
                num++;
            }
        }

        //check below pixel(r+1, c)
        if(r+1 >= 0 && r+1 <= 9 && c >= 0 && c <= 9){
            if(pixels[r+1][c]){
                num++;
            }
        }

        //check right pixel(r, c+1)
        if(r >= 0 && r <= 9 && c+1 >= 0 && c+1 <= 9){
            if(pixels[r][c+1]){
                num++;
            }
        }
        return num;
    }

    private int perimeterOf(int r, int c){
        boolean[][] pixels2 = copyPixels();

        return perimeterOf(r, c, pixels2);
    }


    private int perimeterOf(int r, int c, boolean[][] pixels2) {
        //set this point to white

        pixels2[r][c] = false;
        visited[r][c] = true;

        int number = numNextTo(r, c);

        if(number == 3){
            perimeter++;
        }else if(number == 2){
            perimeter += 2;
        }else if(number == 1){
            perimeter += 3;
        }else if(number == 0){
            perimeter += 4;
        }


        //check upper pixel(r-1, c)
        if(r-1 >= 0 && r-1 <= 9 && c >= 0 && c <= 9){
            if(pixels2[r-1][c]){
                perimeterOf(r-1, c, pixels2);
            }
        }

        //check left pixel(r, c-1)
        if(r >= 0 && r <= 9 && c-1 >= 0 && c-1 <= 9){
            if(pixels2[r][c-1]){
                perimeterOf(r, c-1, pixels2);
            }
        }

        //check below pixel(r+1, c)
        if(r+1 >= 0 && r+1 <= 9 && c >= 0 && c <= 9){
            if(pixels2[r+1][c]){
                perimeterOf(r+1, c, pixels2);
            }
        }

        //check right pixel(r, c+1)
        if(r >= 0 && r <= 9 && c+1 >= 0 && c+1 <= 9){
            if(pixels2[r][c+1]){
                perimeterOf(r, c+1, pixels2);
            }
        }
        return perimeter;
    }

    public boolean isBroken(){
        Point2D p = findStart();
        expandFrom((int)p.getX(), (int)p.getY());
        return (partSize1() != 0);
    }

    public int perimeter() {
        Point2D p = findStart();
        return perimeterOf((int)p.getX(), (int)p.getY());
    }

    public int countPieces() {
        pixels3 = copyPixels();
        return countPieces(pixels3);
    }

    public int countPieces(boolean[][] pixels3){

        Point2D point = findStartEachTime(pixels3);

        if(point != null) {

            countPieces(point.getX(), point.getY(), pixels3);

            numOfPieces++;

            return countPieces(pixels3);

        }
        return numOfPieces;
    }

    public void countPieces(int r, int c, boolean[][] pixels3){

        pixels3[r][c] = false;

        //check upper pixel(r-1, c)
        if(r-1 >= 0 && r-1 <= 9 && c >= 0 && c <= 9){
            if(pixels3[r-1][c]){
                countPieces(r-1, c, pixels3);
            }
        }

        //check left pixel(r, c-1)
        if(r >= 0 && r <= 9 && c-1 >= 0 && c-1 <= 9){
            if(pixels3[r][c-1]){
                countPieces(r, c-1, pixels3);
            }
        }

        //check below pixel(r+1, c)
        if(r+1 >= 0 && r+1 <= 9 && c >= 0 && c <= 9){
            if(pixels3[r+1][c]){
                countPieces(r+1, c, pixels3);
            }
        }

        //check right pixel(r, c+1)
        if(r >= 0 && r <= 9 && c+1 >= 0 && c+1 <= 9){
            if(pixels3[r][c+1]){
                countPieces(r, c+1, pixels3);
            }
        }
    }

    public static PartImage readFromFile(String fileName) throws InvalidPartImageException{
        try{
            BufferedReader file1;
            file1 = new BufferedReader(new FileReader(fileName));
            int numberOfRows = 0;
            int numOfElememt = 0;


            while(file1.ready()){
                String stringInARow = file1.readLine();
                numberOfRows++;
                String[] rowInArray = stringInARow.split(",");
                numOfElememt = rowInArray.length;
                for(int i = 0; i < numOfElememt; i++){
                    if(!(rowInArray[i].equals("0") || rowInArray[i].equals("1"))){
                        file1.close();
                        throw new InvalidPartImageException(fileName);
                    }
                }
            }
            file1.close();

            BufferedReader file2;
            file2 = new BufferedReader(new FileReader(fileName));

            while(file2.ready()){
                String stringInThisRow = file2.readLine();
                String[] rowInThisArray = stringInThisRow.split(",");
                int numElementInThisRow = rowInThisArray.length;
                if(numElementInThisRow != numOfElememt){
                    file2.close();
                    throw new InvalidPartImageException(fileName);
                }
            }
            file2.close();


            byte[][] imageInByte = new byte[numberOfRows][numOfElememt];
            BufferedReader file3;
            file3 = new BufferedReader(new FileReader(fileName));
            int rowNum = 0;

            while(file3.ready()){
                String stringInRow3 = file3.readLine();
                String[] rowInArray3 = stringInRow3.split(",");
                for(int i = 0; i < numOfElememt; i++){
                    if(rowInArray3[i].equals("0")){
                        imageInByte[rowNum][i] = 0;
                    }else if(rowInArray3[i].equals("1")){
                        imageInByte[rowNum][i] = 1;
                    }
                }
                rowNum++;
            }
            file3.close();

            PartImage result = new PartImage(numberOfRows, numOfElememt, imageInByte);
            return result;

        }
        catch (FileNotFoundException e){
            System.out.println("Error: Cannot open the file");
            return null;
        }catch (IOException e){
            System.out.println("Error: Cannot read from file");
            return null;
        }
    }
}
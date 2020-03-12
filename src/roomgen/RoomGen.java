
package roomgen;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class RoomGen {

    int[][][][] roomMap = new int[5][5][10][10];
    Random rand = new Random();
    
    
    public static void main(String[] args) {
        RoomGen rg = new RoomGen();
    }
    
    RoomGen(){
        generate();
    }
    
    
    
    void printRooms(){
        for (int w = 0; w < 5; w++) {
            for (int h = 0; h < 5; h++) {
                printRoom(w, h); //will mess up drawing
            }
        }
    }
    
    void printRoom(int startX, int startY){
                        for (int w = 0; w < 10; w++) {
            for (int h = 0; h < 10; h++) {
                System.out.print(roomMap[startY][startX][h][w]);
            }
                    System.out.println("");
        }
    }

    private void generate() {
        int startX = rand.nextInt(4)+1;
        int startY = rand.nextInt(4)+1;
        fillFloor(startX, startY);
        generateRoom(startX, startY);
    }

    private void generateRoom(int startX, int startY) {
        
        

                
        //Randomises directions for the cells
//        Direction[] neighbourDirections = Direction.values();
//        Collections.shuffle(Arrays.asList(neighbourDirections));
//
//        for (Direction d : neighbourDirections) {
//            if(roomMap[startY+d.shiftY][startX+d.shiftX] == null){ // no room generated in this direction
//              generate floor in new room using fillFloor                
//get door position in wall
                int doorPos = rand.nextInt(3)+1;
//                //add door in right wall of currentRoom
//                if(d.getDirection() == Direction.RIGHT){
                    roomMap[startY][startX][doorPos+2][9] = 3; //draw door in wall of original room
                    //use dShift to draw opposite wall of next door
                    //recursive call to next room room using dshift
//                }
//            }
//        }

        printRoom(startX, startY);
    }

    private void fillFloor(int startX, int startY) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if(x==0 || y==0 || x==9 || y==9){ //wall
                    roomMap[startY][startX][y][x] = 1;
                }
                else{
                    roomMap[startY][startX][y][x] = 0;
                }
            }
        }
        
        //randomly place obstacles in 7*7 square
        for (int i = 0; i < 10; i++) {
            int x = rand.nextInt(7)+1;
            int y = rand.nextInt(7)+1;
            roomMap[startY][startX][y][x] = 2;
        }
        
    }


private enum Direction {
        UP(-1, 0, 0), RIGHT(1, 0, 1), DOWN(0, 1, 2), LEFT(-1, 0, 3);

        private int shiftX, shiftY; //Links to all the first parameters of the constant
        private int neighbourOpposingPosition; //Flips the direction to break the other wall
        private Direction opposite;

        static {
            UP.opposite = DOWN;
            DOWN.opposite = UP;
            LEFT.opposite = RIGHT;
            RIGHT.opposite = LEFT;
        }

        private Direction(int shiftX, int shiftY, int neighbourPosition) {
            this.shiftX = shiftX;
            this.shiftY = shiftY;
            
            this.neighbourOpposingPosition = neighbourPosition;
        }
        
        public Direction getDirection(){
            return opposite.opposite;
        }
    }
}
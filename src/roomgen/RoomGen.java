package roomgen;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class RoomGen {

    int[][][][] roomMap = new int[5][5][][];
    Random rand = new Random();

    public static void main(String[] args) {
        RoomGen rg = new RoomGen();

    }

    RoomGen() {
        generate();
    }

    void printRooms() {
        for (int w = 0; w < 5; w++) {
            for (int h = 0; h < 5; h++) {
                printRoom(w, h); //will mess up drawing
            }
        }
    }

    void printRoom(int startX, int startY) {
        for (int w = 0; w < 10; w++) {
            for (int h = 0; h < 10; h++) {
                System.out.print(roomMap[startY][startX][h][w]);
            }
            System.out.println("");
        }
    }

    private void generate() {
//        for (int i = 0; i < 5; i++) {  //create null rooms - not needed any more
//            for (int j = 0; j < 5; j++) {
//                            roomMap[i][j]=null;
//            }
//
//        }
        int startX = rand.nextInt(4);
        int startY = rand.nextInt(4);
        //fillFloor(startX, startY);
        generateRoom(startX, startY);
        //printRoom(startX, startY);
    }

    private void generateRoom(int startX, int startY) {

//        Randomises directions for the cells
        Direction[] neighbourDirections = Direction.values();
        //System.out.println("Before:" + Arrays.toString(neighbourDirections));
        Collections.shuffle(Arrays.asList(neighbourDirections));
        System.out.println("After:" + Arrays.toString(neighbourDirections));
        roomMap[startY][startX] = new int[10][10];
        fillFloor(startX, startY);
        printRoom(startX, startY);

        for (Direction d : neighbourDirections) {
            int doorPos = rand.nextInt(3) + 1;
            System.out.println("shiftY" + d.shiftY + " shiftX " + d.shiftX);
            int newRoomY = startY + d.shiftY;
            int newRoomX = startX + d.shiftX;
            if (validRoom(newRoomX, newRoomY)) {

                if (roomMap[newRoomY][newRoomX] == null) { // no room generated in this direction
                    System.out.println("no Room in " + newRoomX + " " + newRoomY);
                    //roomMap[startY][startX + d.shiftX][doorPos + 2][0] = 3;
//              generate floor in new room using fillFloor                
//get door position in wall
//        add door in right wall of currentRoom
                    if (d.getDirection() == Direction.RIGHT) {
                        System.out.println("RIGHT");
                        roomMap[startY][startX][doorPos][9] = 3; //draw door in wall of original room
                        //roomMap[startY][startX+1][doorPos + 2][0] = 3;
                        //use dShift to draw opposite wall of next door recursive call to next room room using dshift
                        //roomMap[startY + d.shiftY][startX + d.shiftX][doorPos + 2][0] = 3;
                    }
                    if (d.getDirection() == Direction.LEFT) {
                        roomMap[startY][startX][doorPos + 2][0] = 3;
                        //roomMap[startY][startX-1][doorPos + 2][0] = 3;
                    }
                    if (d.getDirection() == Direction.UP) {
                        roomMap[startY][startX][0][doorPos + 2] = 3;
                        //roomMap[startY+1][startX][0][doorPos + 2] = 3;
                    }
                    if (d.getDirection() == Direction.DOWN) {
                        roomMap[startY][startX][9][doorPos + 2] = 3;
                        //roomMap[startY-1][startX][0][doorPos + 2] = 3;
                    }

                }

            }

        }

    }

    private void fillFloor(int startX, int startY) {

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (x == 0 || y == 0 || x == 9 || y == 9) { //wall
                    roomMap[startY][startX][y][x] = 1;
                } else {
                    roomMap[startY][startX][y][x] = 0;
                }
            }
        }

        //randomly place obstacles in 7*7 square
        for (int i = 0; i < 10; i++) {
            int x = rand.nextInt(7) + 1;
            int y = rand.nextInt(7) + 1;
            roomMap[startY][startX][y][x] = 2;
        }

    }

    private boolean validRoom(int newRoomX, int newRoomY) {
        if ((newRoomX < 0 || newRoomX > 4) && (newRoomY < 0 || newRoomY > 4)) {
            return false;
        }
        return true;
    }

    private enum Direction {
        UP(-1, 0, 0), RIGHT(0, 1, 1), DOWN(1, 0, 2), LEFT(0, -1, 3);

        private int shiftX, shiftY; //Links to all the first parameters of the constant
        private int neighbourOpposingPosition; //Flips the direction to break the other wall
        private Direction opposite;

        static {
            UP.opposite = DOWN;
            DOWN.opposite = UP;
            LEFT.opposite = RIGHT;
            RIGHT.opposite = LEFT;
        }

        private Direction(int shiftY, int shiftX, int neighbourPosition) {
            this.shiftX = shiftX;
            this.shiftY = shiftY;

            this.neighbourOpposingPosition = neighbourPosition;
        }

        public Direction getDirection() {
            return opposite.opposite;
        }
    }
}

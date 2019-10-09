package byog;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room {
    private Posit posit;
    private int width;
    private int height;

    public Room(Posit p, int w, int h) {
        this.posit = p;
        this.width = w;
        this.height = h;
    }

    public Posit posit() {
        return this.posit;
    }

    public int height() {
        return this.height;
    }

    public int width() {
        return this.width;
    }

    public static void addRoom(TETile[][] world, Posit p, int w, int h) {
        for (int x = 0; x < w; x += 1) {
            //System.out.println("addRoomFunction: xPos:"+p.xPos+",ypos:"+p.yPos);
            world[p.xPos() + x][p.yPos()] = Tileset.WALL;
            world[p.xPos() + x][p.yPos() + h - 1] = Tileset.WALL;
        }
        for (int y = 0; y < h; y += 1) {
            world[p.xPos()][p.yPos() + y] = Tileset.WALL;
            world[p.xPos() + w - 1][p.yPos() + y] = Tileset.WALL;
        }
    }

    public static Posit innerRand(Room r, WorldGenerateParam wgp) {
        Random rand = new Random(wgp.seed());
        int innerX = rand.nextInt(r.width - 2) + r.posit.xPos() + 1;
        int innerY = rand.nextInt(r.height - 2) + r.posit.yPos() + 1;
        Posit innerPosit = new Posit(innerX, innerY);
        return innerPosit;
    }

    private static void fillRoom(TETile[][] world, Room r) {
        for (int x = 1; x < r.width - 1; x += 1) {
            for (int y = 1; y < r.height - 1; y += 1) {
                world[r.posit.xPos() + x][r.posit.yPos() + y] = Tileset.FLOOR;
            }
        }
    }

    public static void fillRoomList(TETile[][] world, ArrayList<Room> roomList) {
        for (int i = 0; i < roomList.size(); i += 1) {
            fillRoom(world, roomList.get(i));
        }
    }

    public static ArrayList<Room> sortRoomList(ArrayList<Room> roomList) {
        ArrayList<Room> newRoomList = new ArrayList<>();
        int roomListSize = roomList.size();
        for (int i = 0; i < roomListSize; i += 1) {
            int minRoom = smallestRoom(roomList);
            newRoomList.addLast(roomList.remove(minRoom));
        }
        return newRoomList;
    }
     /**
    public static ArrayList<Room> sortRoomList(ArrayList<Room> roomList) {
        //ArrayList<Room> rooms = new ArrayList<>();
        //rooms.addLast(roomList.get(0));
        double min = 10000.0;
        for(int i = 0; i < roomList.size() - 1; i += 1) {
            int k = i + 1;
            for(int j = i + 1; j < roomList.size(); j += 1) {
                double d = distance(roomList.get(i), roomList.get(j));
                if (d < min) {
                    min = d;
                    k = j;
                }
            }
            //rooms.addLast(roomList.get(k));
            roomList = switchValue(i, k, roomList);
        }
        return roomList;
    }
    public static ArrayList<Room> switchValue(int index1, int index2, ArrayList<Room> roomList) {
        ArrayList<Room> rooms = new ArrayList<>();
        if (index1 > index2) {
            index1 = index2;
            int temp = index1;
            index2 = temp;
        }
        if (index1 == 0) {
            rooms.addLast(roomList.get(index2));
            if(index2 - index1 == 1) {
                rooms.addLast(roomList.get(index1));
                for (int i = 2; i < roomList.size(); i += 1) {
                    rooms.addLast(roomList.get(i));
                }
            }
            else {
                for (int i = 1; i < index2; i += 1) {
                    rooms.addLast(roomList.get(i));
                }
                rooms.addLast(roomList.get(index1));
                if (index2 == roomList.size() - 1) {
                    return rooms;
                }
                else {
                    for (int i = index2 + 1; i < roomList.size(); i += 1) {
                        rooms.addLast(roomList.get(i));
                    }
                    return rooms;
                }
            }
        }
        else {
            for (int i = 0; i < index1; i += 1) {
                rooms.addLast(roomList.get(i));
            }
            rooms.addLast(roomList.get(index2));

            if (index2 - index1 == 1) {
                rooms.addLast(roomList.get(index1));
                for (int i = index2 + 1; i < roomList.size(); i += 1) {
                    rooms.addLast(roomList.get(i));
                }
            }
            else {
                for (int i = index1 + 1; i < index2; i += 1){
                    rooms.addLast(roomList.get(i));
                }
                rooms.addLast(roomList.get(index1));
                if (index2 == roomList.size() - 1) {
                    return rooms;
                }
                else {
                    for (int i = index2 + 1; i < roomList.size(); i += 1) {
                        rooms.addLast(roomList.get(i));
                    }
                    return rooms;
                }
            }
        }
        return rooms;
    }
*/
    public static int smallestRoom(ArrayList<Room> roomList) {
        int min = 110; int minIndex = 0;
        for (int i = 0; i < roomList.size(); i += 1) {
            int positSum = roomList.get(i).posit.xPos() + roomList.get(i).posit.yPos();
            if (positSum < min) {
                min = positSum;
                minIndex = i;
            }
        }
        return minIndex;
    }

    public boolean isCross(Room rp2) {
        // cross rectangular point
        int rp1x0 = this.posit.xPos();
        int rp1y0 = this.posit.yPos();
        int rp1x1 = rp1x0 + this.width;
        int rp1y1 = rp1y0 + this.height;

        int rp2x0 = rp2.posit.xPos();
        int rp2y0 = rp2.posit.yPos();
        int rp2x1 = rp2x0 + rp2.width;
        int rp2y1 = rp2y0 + rp2.height;
        int minx = Math.max(rp1x0, rp2x0);
        int miny = Math.max(rp1y0, rp2y0);
        int maxx = Math.min(rp1x1, rp2x1);
        int maxy = Math.min(rp1y1, rp2y1);
        return minx < maxx && miny < maxy;
    }

    public boolean isCrossWithRoomList(ArrayList<Room> rooms) {
        for(int i = 0; i < rooms.size(); i += 1) {
            if (this.isCross(rooms.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static double distance(Room rp1, Room rp2) {
        int a = rp1.posit().xPos() - rp2.posit().xPos();
        int b = rp1.posit().yPos() - rp2.posit().yPos();
        return Math.sqrt( a * a + b * b);
    }

}

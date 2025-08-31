package player;

import room.Room;
import inventory.Inventory;

public class Player {
    private String name;
    private Room currentRoom;
    private Inventory inventory;
    private boolean alive;

    public Player(String name, Room startRoom) {
        this.name = name;
        this.currentRoom = startRoom;
        this.inventory = new Inventory();
        this.alive = true;
    }

    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void moveTo(Room room) {
        this.currentRoom = room;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void die() {
        this.alive = false;
        System.out.println("\n⚠️ " + name + "님은 좀비에게 잡혀버렸습니다... 게임 오버!");
    }
}

package player;
import room.Room;
import inventory.Inventory;
import item.Item;

public class Player {
    private String name; //수정한 부분
    private Room currentRoom;
    private boolean dead = false;
    private Inventory inventory;

    public Player(String name, Room startingRoom) { //수정한 부분
        this.name=name;
        this.currentRoom = startingRoom;
        this.inventory = new Inventory();
    }

    public String getName(){ //수정한 부분
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void moveTo(Room nextRoom) {
        if (nextRoom != null) {
            this.currentRoom = nextRoom;
            System.out.println(nextRoom.getName() + "으(로) 이동했습니다.");
        }
    }

    public boolean useTool(String tool, Item itemChecker) {
        if (inventory.hasItem(tool)) {
            boolean success = itemChecker.isCorrectTool(tool);
            if (success) {
                System.out.println(tool + "을(를) 성공적으로 사용했습니다!");
                inventory.removeItem(tool);
            } else {
                System.out.println(tool + "은(는) 효과가 없습니다... 살인자가 다가옵니다!");
                this.die();
            }
            return success;
        } else {
            System.out.println(tool + "을(를) 가지고 있지 않습니다.");
            return false;
        }
    }

    public boolean useToolWithDeath(String tool, Item itemChecker) {
        if (inventory.hasItem(tool)) {
            boolean success = itemChecker.isCorrectTool(tool);
            if (success) {
                System.out.println(tool + "을(를) 성공적으로 사용했습니다!");
                inventory.removeItem(tool);
            } else {
                System.out.println(tool + "은(는) 효과가 없습니다... 살인자가 다가옵니다!");
                this.die();
            }
            return success;
        } else {
            System.out.println(tool + "을(를) 가지고 있지 않습니다.");
            return false;
        }
    }

    public boolean useKey(String key, Item itemChecker) {
        if (inventory.hasItem(key)) {
            boolean success = itemChecker.isCorrectKey(key);
            if (success) {
                System.out.println(key + "로 문이 열렸습니다!");
                inventory.removeItem(key);
            } else {
                System.out.println(key + "는 맞지 않습니다...");
            }
            return success;
        } else {
            System.out.println(key + "를 가지고 있지 않습니다.");
            return false;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void die() {
        dead = true;
        System.out.println("\n*** 게임 오버 ***");
        System.out.println("살인자에게 발각되어 죽었습니다...");
    }

    public boolean isDead() {
        return dead;
    }
}

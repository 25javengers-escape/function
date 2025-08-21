package room;

public class Room {
    private String name;
    private String description;
    private Room nextRoom;

    // 복도에서 선택할 수 있는 방 목록 (지하실, 서재, 거실)
    private static Room[] hidingRooms;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Room getNextRoom() {
        return nextRoom;
    }

    public void setNextRoom(Room nextRoom) {
        this.nextRoom = nextRoom;
    }

    // 선택 가능한 방들 등록
    public static void setHidingRooms(Room[] rooms) {
        hidingRooms = rooms;
    }

    // 선택 가능한 방 목록 반환
    public static Room[] getHidingRooms() {
        return hidingRooms.clone();
    }

    // 방 목록 출력
    public static void showHidingRooms() {
        System.out.println("숨을 수 있는 방 목록:");
        for (int i = 0; i < hidingRooms.length; i++) {
            System.out.println((i + 1) + ". " + hidingRooms[i].getName()
                    + " - " + hidingRooms[i].getDescription());
        }
    }
}

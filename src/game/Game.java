package game;

import room.Room;

import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private Scanner scanner;

    public Game() {
        scanner = new Scanner(System.in);
        setupRooms();
    }

    private void setupRooms() {
        Room room1 = new Room("1번 방", "의문의 방. 밧줄에 묶여 있다. 근처에 가위가 있다.");
        Room hallway = new Room("복도", "여러 개의 방이 보인다. 살인자의 기척이 느껴진다.");

        room1.setNextRoom(hallway);
        this.currentRoom = room1;
    }

    public void start() {
        System.out.println("===== 방탈출 게임 시작 =====");
        System.out.println("당신은 눈을 떠보니 낯선 저택의 방 안, 밧줄에 묶여 있습니다...");
        System.out.println(currentRoom.getDescription());

        // 1단계: 밧줄 자르기
        while (true) {
            System.out.print("\n>> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.contains("가위")) {
                System.out.println("당신은 가위를 사용해 밧줄을 풀었습니다.");
                break;
            } else {
                System.out.println("인기척이 느껴진다.. 빨리 탈출해야돼!' (힌트: 가위 사용)");
            }
        }

        // 2단계: 힌트 찾기
        System.out.println("\n문이 잠겨 있습니다. 방 안을 탐색해 비밀번호 힌트를 찾아야 합니다.");
        findHint();

        // 3단계: 힌트 입력하여 문 열기
        enterPassword();

        // 4단계: 복도로 이동
        moveToNextRoom();
    }

    private void findHint() {
        while (true) {
            System.out.println("\n어디를 조사하시겠습니까?");
            System.out.println("1. 책상 서랍");
            System.out.println("2. 벽면 탐색");
            System.out.println("3. 침대 아래");
            System.out.print("선택 >> ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("서랍 안은 비어 있습니다.");
                    break;
                case "2":
                    System.out.println("벽면의 그림 뒤에서 쪽지를 발견했습니다!");
                    System.out.println("→↓↓←↑↑");
                    return; // 힌트를 찾았으니 탐색 종료
                case "3":
                    System.out.println("침대 아래에는 먼지만 가득합니다.");
                    break;
                default:
                    System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }

    private void enterPassword() {
        System.out.println("\n힌트를 이용해 문을 여세요.");
        System.out.println("비밀번호를 입력하세요. (힌트: →↓↓←↑↑)");
        while (true) {
            System.out.print("비밀번호 >> ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                System.out.println("문이 열렸습니다!");
                break;
            } else {
                System.out.println("비밀번호가 틀렸습니다.");
            }
        }
    }

    private void moveToNextRoom() {
        if (currentRoom.getNextRoom() != null) {
            currentRoom = currentRoom.getNextRoom();
            System.out.println("\n[이동] " + currentRoom.getName());
            System.out.println(currentRoom.getDescription());
            hallwayEvent();
        } else {
            System.out.println("이동할 수 없습니다.");
        }
    }

    private void hallwayEvent() {
        System.out.println("살인자의 발소리가 들립니다! 어디로 숨을까요?");
        System.out.println("1. 지하실  2. 서재  3. 거실");

        while (true) {
            System.out.print("선택 >> ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.println("지하실로 향합니다...");
                    break;
                case "2":
                    System.out.println("서재로 향합니다...");
                    break;
                case "3":
                    System.out.println("거실로 향합니다...");
                    break;
                default:
                    System.out.println("올바른 번호를 선택해주세요.");
            }
        }
    }
}

package game;

import room.Room;
import player.Player;
import item.Item;
import puzzle.Quiz;
import puzzle.BaseBallGame;
import java.util.Scanner;
import java.util.Random;

public class Game {
    private Player player;
    private Scanner scanner;
    private Item gameItems;
    private Quiz quiz;
    private BaseBallGame baseBallGame;

    // 게임에 사용될 방들
    private Room startRoom;
    private Room hallway;
    private Room basement;
    private Room library;
    private Room livingRoom;
    private Room entrance;

    public Game() {
        scanner = new Scanner(System.in);
        setupRooms();
        setupGame();
    }

    private void setupRooms() {
        startRoom = new Room("감금된 방", "의문의 방. 밧줄에 묶여 있다. 근처에 여러 도구들이 보인다.");
        hallway = new Room("복도", "'여러 개의 방이 보인다. 살인자의 기척이 느껴진다.'");
        basement = new Room("지하실", "어둡고 축축한 지하실. 숨기에는 좋지만 퀴즈를 풀어야 나갈 수 있다.");
        library = new Room("서재", "책들이 가득한 서재. 퀴즈를 풀면 안전하게 이동할 수 있을 것 같다.");
        livingRoom = new Room("거실", "넓은 거실. 살인자에게 발각되기 쉬운 곳이다.");
        entrance = new Room("현관", "저택의 현관. 디지털 자물쇠가 있는 문이 보인다.");

        // 방 연결 설정
        startRoom.setNextRoom(hallway);
        basement.setNextRoom(entrance);
        library.setNextRoom(entrance);

        // 복도에서 선택 가능한 방 등록
        Room.setHidingRooms(new Room[]{ basement, library, livingRoom });
    }

    private void setupGame() { //수정한 부분-player 생성자 제거
        gameItems = new Item();
        quiz = new Quiz();
        baseBallGame = new BaseBallGame();
    }

    public void start() {
        System.out.println("===== 방탈출 게임 시작 =====");
        System.out.print("플레이어 이름을 입력하세요: "); //수정된 부분
        String playerName=scanner.nextLine().trim(); //수정된 부분

        player=new Player(playerName,startRoom); //수정된 부분

        System.out.println(player.getName()+"님, 눈을 떠보니 낯선 저택의 방 안, 밧줄에 묶여 있습니다..."); //수정된 부분
        System.out.println("살인자에게 감금된 것 같습니다. 빨리 탈출해야 합니다!");
        System.out.println();

        if (!stage1_EscapeRope(2)) return;       // 1단계
        if (!stage2_FindKey()) return;           // 2단계
        if (!stage3_ChooseHidingPlace()) return; // 3단계
        if (!stage4_SolvePuzzle()) return;       // 4단계
        stage5_FinalEscape();                    // 5단계
    }

    // 1단계: 밧줄 자르기
    private boolean stage1_EscapeRope(int correctTool) {
        System.out.println("=== 1단계: 밧줄 자르기 ===");
        System.out.println(player.getCurrentRoom().getDescription());

        // 도구들을 인벤토리에 추가
        for (String tool : gameItems.getTools()) {
            player.getInventory().addItem(tool);
        }

        System.out.println("\n밧줄을 자를 도구를 선택하세요:");
        gameItems.showTools();

        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            System.out.print("사용할 도구 번호를 입력하세요 (1-4): ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= 4) {
                    String selectedTool = gameItems.getTools()[choice - 1];

                    if (choice == correctTool) {
                        System.out.println(selectedTool + "을(를) 성공적으로 사용했습니다!");
                        System.out.println("밧줄이 잘렸습니다! 이제 자유롭게 움직일 수 있습니다.");
                        player.getInventory().removeItem(selectedTool);
                        return true;
                    } else {
                        attempts++;
                        if (attempts < maxAttempts) {
                            System.out.println(selectedTool + "은(는) 효과가 없습니다... 다른 것을 사용해봅시다.");
                            System.out.println("남은 기회: " + (maxAttempts - attempts) + "번");
                        } else {
                            System.out.println(selectedTool + "은(는) 효과가 없습니다... 살인자가 다가옵니다!");
                            player.die();
                            return false;
                        }
                    }
                } else {
                    System.out.println("1-4 사이의 번호를 입력하세요.");
                }
            } catch (Exception e) {
                System.out.println("올바른 번호를 입력하세요.");
                scanner.nextLine();
            }
        }
        return false;
    }

    private boolean stage2_FindKey() {
        System.out.println("\n=== 2단계: 방 탈출 ===");
        System.out.println("문이 잠겨 있습니다. 열쇠를 찾아야 합니다.");

        boolean[] searched = new boolean[3];
        int foundKeys = 0;

        // Item에서 전체 키 배열 가져오기
        String[] keys=gameItems.getKeys();

        while (foundKeys < 3) {
            System.out.println("\n어디를 조사하시겠습니까?");
            System.out.println("1. 책상 서랍");
            System.out.println("2. 벽면 탐색");
            System.out.println("3. 침대 아래");
            System.out.print("선택 >> ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (!searched[0]) {
                        System.out.println("서랍 안에서 " + keys[0] + "를 발견했습니다!");
                        player.getInventory().addItem(keys[0]); // Inventory에 추가
                        searched[0] = true;
                        foundKeys++;
                    } else {
                        System.out.println("이미 조사했습니다.");
                    }
                    break;
                case "2":
                    if (!searched[1]) {
                        System.out.println("벽면의 그림 뒤에서 " + keys[2] + "를 발견했습니다!");
                        player.getInventory().addItem(keys[2]);
                        searched[1] = true;
                        foundKeys++;
                    } else {
                        System.out.println("이미 조사했습니다.");
                    }
                    break;
                case "3":
                    if (!searched[2]) {
                        System.out.println("침대 아래에서 " + keys[1] + "를 발견했습니다!");
                        player.getInventory().addItem(keys[1]);
                        searched[2] = true;
                        foundKeys++;
                    } else {
                        System.out.println("이미 조사했습니다.");
                    }
                    break;
                default:
                    System.out.println("올바른 번호를 입력하세요.");
            }
        }

        // showKeys() 사용
        System.out.println("\n획득한 열쇠 목록:");
        gameItems.showKeys();

        // 열쇠 사용 단계
        System.out.println("\n열쇠를 사용해 문을 열어보세요:");
        while (true) {
            System.out.print("사용할 열쇠를 입력하세요 (예: 1번 열쇠): ");
            String selectedKey = scanner.nextLine().trim();

            if (gameItems.isCorrectKey(selectedKey)) {
                System.out.println("정답! 문이 열렸습니다!");
                return true;
            } else {
                System.out.println("열쇠가 맞지 않습니다. 다시 시도하세요.");
            }
        }
    }

    // 3단계: 복도 이동
    private boolean stage3_ChooseHidingPlace() {
        System.out.println("\n=== 3단계: 복도 ===");
        player.moveTo(hallway);
        System.out.println(player.getCurrentRoom().getDescription());
        System.out.println("살인자의 발소리가 들립니다! 어디로 숨을까요?");

        Room.showHidingRooms();

        while (true) {
            System.out.print("선택 >> ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                Room[] hidingRooms = Room.getHidingRooms();

                if (choice >= 1 && choice <= hidingRooms.length) {
                    Room selectedRoom = hidingRooms[choice - 1];
                    player.moveTo(selectedRoom);

                    if (selectedRoom.getName().equals("거실")) {
                        System.out.println("거실로 향합니다...");
                        System.out.println("거실은 숨을 곳이 없습니다!");
                        System.out.println("살인자에게 발각되었습니다!");
                        player.die();
                        return false;
                    } else {
                        System.out.println(selectedRoom.getName() + "로 향합니다...");
                        return true;
                    }
                } else {
                    System.out.println("올바른 번호를 선택해주세요 (1-" + hidingRooms.length + ").");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    // 4단계: 퍼즐 풀기
    private boolean stage4_SolvePuzzle() {
        System.out.println("\n=== 4단계: 퍼즐 해결 ===");
        System.out.println("현재 위치: " + player.getCurrentRoom().getName());

        if (player.getCurrentRoom().getName().equals("지하실")) {
            System.out.println("지하실에 숨었습니다. 하지만 나가려면 퍼즐을 풀어야 합니다.");
            return quiz.solveQuiz(scanner);
        } else if (player.getCurrentRoom().getName().equals("서재")) {
            System.out.println("서재에 숨었습니다. 책 사이에서 퍼즐을 발견했습니다.");
            return quiz.solveQuiz(scanner);
        }

        return false;
    }

    // 5단계: 최종 탈출
    private boolean stage5_FinalEscape() {
        System.out.println("\n=== 5단계: 최종 탈출 ===");
        player.moveTo(entrance);
        System.out.println("마침내 현관에 도착했습니다!");

        if (baseBallGame.playBaseBallGame(scanner)) {
            System.out.println("\n🎉 게임 클리어! 🎉");
            System.out.println(player.getName() + "님, 성공적으로 살인자의 저택에서 탈출했습니다!");
            return true;
        } else {
            player.die();
            return false;
        }
    }
}

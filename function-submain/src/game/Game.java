package game;

import room.Room;
import player.Player;
import item.Item;
import puzzle.Quiz;
import puzzle.BaseballGame;
import java.util.Scanner;

public class Game {
    private Player player;
    private Scanner scanner;
    private Item gameItems;
    private Quiz quiz;
    private BaseballGame baseballGame;

    // 연구소 방들
    private Room startRoom;
    private Room hallway;
    private Room lab;
    private Room dataRoom;
    private Room lounge;
    private Room exitGate;

    public Game() {
        scanner = new Scanner(System.in);
        setupRooms();
        setupGame();
    }

    private void setupRooms() {
        startRoom = new Room("격리실", "실험체가 갇혀 있던 방. 제어 장치에 묶여 있다.");
        hallway = new Room("연구소 복도", "군데군데 피가 묻은 복도. 좀비들의 그림자가 보인다.");
        lab = new Room("실험실", "파괴된 장비와 시약들이 널려 있다. 시약들로 인해 폭발 위험이 있다.");
        dataRoom = new Room("데이터실", "연구 기록이 담긴 방. 보안 퍼즐이 있다.");
        lounge = new Room("휴게실", "한때 연구원들이 쉬던 공간. 지금은 좀비들이 어슬렁거린다.");
        exitGate = new Room("출구 게이트", "연구소의 최종 출구. 디지털 보안 패널이 작동 중이다.");

        // 방 연결
        startRoom.setNextRoom(hallway);
        lab.setNextRoom(exitGate); // 실험실 선택 시 바로 출구
        dataRoom.setNextRoom(exitGate);

        // 복도에서 선택 가능한 방 등록
        Room.setHidingRooms(new Room[]{ lab, dataRoom, lounge });
    }

    private void setupGame() {
        gameItems = new Item();
        quiz = new Quiz();
        baseballGame = new BaseballGame();
    }

    public void start() {
        System.out.println("===== 🧟‍♂️ 좀비 연구소 탈출 게임 🧟‍♂️ =====");
        System.out.print("플레이어 이름을 입력하세요: ");
        String playerName = scanner.nextLine().trim();

        player = new Player(playerName, startRoom);

        System.out.println(player.getName() + "님, 당신은 연구소의 좀비 바이러스 항체를 보유한 실험체입니다.");
        System.out.println("눈을 떠보니 연구소의 격리실 안, 제어 장치에 묶여 있습니다...");
        System.out.println("좀비가 퍼진 연구소에서 살아남기 위해 탈출해야 합니다!");
        System.out.println();
        scanner.nextLine();

        if (!stage1_EscapeRestraint()) return;
        if (!stage2_FindKey()) return;
        if (!stage3_ChooseHidingPlace()) return;
        if (!stage4_SolvePuzzle()) return;
        stage5_FinalEscape();
    }

    // 1단계: 제어 장치 해제
    private boolean stage1_EscapeRestraint() {
        System.out.println("=== 1단계: 제어 장치 해제 ===");
        System.out.println(player.getCurrentRoom().getDescription());

        for (String tool : gameItems.getTools()) {
            player.getInventory().addItem(tool);
        }

        System.out.println("\n제어 장치를 해제할 도구를 선택하세요.");
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

                    // Item 클래스 메서드 사용
                    if (gameItems.isCorrectTool(selectedTool)) {
                        player.getInventory().removeItem(selectedTool);
                        System.out.println("제어 장치가 해제되었습니다! 이제 자유롭게 움직일 수 있습니다.");
                        return true;
                    } else {
                        attempts++;
                        if (attempts < maxAttempts) {
                            System.out.println(selectedTool + "은(는) 효과가 없습니다... 다른 것을 사용해봅시다.");
                            System.out.println("남은 기회: " + (maxAttempts - attempts) + "번");
                        } else {
                            System.out.println(selectedTool + "은(는) 효과가 없습니다... 좀비들이 몰려옵니다!");
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

    // 2단계: 보안 카드 찾기
    private boolean stage2_FindKey() {
        System.out.println("\n=== 2단계: 보안 카드 찾기 ===");
        System.out.println("출구 게이트로 나가려면 보안 카드를 찾아야 합니다.");

        boolean[] searched = new boolean[3];
        int foundKeys = 0;
        String[] keys = gameItems.getKeys();

        while (foundKeys < 3) {
            System.out.println("\n어디를 조사하시겠습니까?");
            System.out.println("1. 책상 서랍");
            System.out.println("2. 벽면 패널");
            System.out.println("3. 침대 아래");
            System.out.println("4. 옷장 안");
            System.out.print("선택 >> ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (!searched[0]) {
                        System.out.println("서랍 안에서 " + keys[0] + "를 발견했습니다!");
                        player.getInventory().addItem(keys[0]);
                        searched[0] = true;
                        foundKeys++;
                    } else {
                        System.out.println("이미 조사했습니다.");
                    }
                    break;
                case "2":
                    if (!searched[1]) {
                        System.out.println("벽면 패널 뒤에서 " + keys[2] + "를 발견했습니다!");
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
                case "4":
                    System.out.println("옷장 문을 열자, 안에서 갑자기 좀비가 튀어나옵니다!");
                    player.die();
                    return false;
                default:
                    System.out.println("올바른 번호를 입력하세요.");
            }
        }

        System.out.println("\n보안 카드를 사용해 문을 열어보세요.");
        while (true) {
            System.out.print("사용할 카드 이름을 입력하세요 (예: C): ");
            String selectedKey = scanner.nextLine().trim();

            if (gameItems.isCorrectKey(selectedKey)) {
                System.out.println("정답! 문이 열렸습니다!");
                return true;
            } else {
                System.out.println("보안 카드가 맞지 않습니다. 다시 시도하세요.");
            }
        }
    }

    // 3단계: 복도 이동
    private boolean stage3_ChooseHidingPlace() {
        System.out.println("\n=== 3단계: 연구소 복도 ===");
        player.moveTo(hallway);
        System.out.println(player.getCurrentRoom().getDescription());
        System.out.println("좀비들의 신음소리밖에 들리지 않고 연구원들은 모두 좀비로 변했습니다..");
        System.out.println("발전실에서 불길이 치솟고 있습니다...");
        System.out.println("불 근처 좀비들의 움직임이 눈에 띄게 느려집니다.");
        System.out.println("어디로 숨을까요?");

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

                    if (selectedRoom.getName().equals("휴게실")) {
                        System.out.println("휴게실로 향합니다...");
                        System.out.println("휴게실은 안전하지 않습니다!");
                        System.out.println("좀비들에게 발각되었습니다!");
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

    // 4단계: 퍼즐 풀기 (실험실 선택 시 바로 출구)
    private boolean stage4_SolvePuzzle() {
        System.out.println("\n=== 4단계: 퍼즐 해결 ===");
        System.out.println("현재 위치: " + player.getCurrentRoom().getName());

        if (player.getCurrentRoom().getName().equals("실험실")) {
            System.out.println("실험실에 들어왔습니다. 파괴된 장비와 시약들로 인해 폭발 위험이 있습니다!");
            System.out.println("조심히 출구 게이트로 이동합니다...");
            player.moveTo(exitGate); // 바로 출구 게이트로 이동
            return true; // 퍼즐 없이 통과
        } else if (player.getCurrentRoom().getName().equals("데이터실")) {
            System.out.println("데이터실에 숨었습니다. 보안 장치 퍼즐이 작동 중입니다.");
            return quiz.solveQuiz(scanner, player.getName());
        }

        return false;
    }

    // 5단계: 최종 탈출
    private boolean stage5_FinalEscape() {
        System.out.println("\n=== 5단계: 최종 탈출 ===");
        player.moveTo(exitGate);
        System.out.println("마침내 출구 게이트에 도착했습니다!");

        if (baseballGame.start(scanner, player.getName())) {
            System.out.println("\n🎉 게임 클리어! 🎉");
            System.out.println(player.getName() +"님, 좀비로 뒤덮힌 세상 속에서 살아갈 인류의 미래가 보입니다!");
            return true;
        } else {
            player.die();
            return false;
        }
    }
}
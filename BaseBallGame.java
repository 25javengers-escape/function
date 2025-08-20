package puzzle;
import java.util.*;

public class BaseBallGame {
    private int[] secretNumber = new int[4];

    private void generateSecretNumber() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(0,8,1,5));
        for (int i = 0; i < 4; i++) {
            numbers.add(i);
        }

        for (int i = 0; i < 4; i++) {
            secretNumber[i] = numbers.get(i);
        }
    }

    public boolean playBaseBallGame(Scanner scanner) {
        System.out.println("\n[5단계] 당신은 현관문에 도착했습니다!");
        System.out.println("디지털 자물쇠에는 4자리 숫자를 입력해야 합니다.");
        System.out.println("숫자 야구 게임으로 비밀번호를 맞춰보세요!");
        System.out.println("*0~9 숫자 4개, 중복 없음");
        System.out.println();

        generateSecretNumber();
        int attempts = 0;
        int maxAttempts = 10;

        while (attempts < maxAttempts) {
            System.out.print("네 자리 숫자를 입력하세요 (예: 1 2 3 4): ");
            int[] inputNumbers = new int[4];
            boolean validInput = true;

            try {
                for (int i = 0; i < 4; i++) {
                    inputNumbers[i] = scanner.nextInt();
                    if (inputNumbers[i] < 0 || inputNumbers[i] > 9) {
                        validInput = false;
                        break;
                    }
                }
                scanner.nextLine();

                if (validInput) {
                    for (int i = 0; i < 4; i++) {
                        for (int j = i + 1; j < 4; j++) {
                            if (inputNumbers[i] == inputNumbers[j]) {
                                validInput = false;
                                break;
                            }
                        }
                        if (!validInput) break;
                    }
                }

                if (!validInput) {
                    System.out.println("잘못된 입력입니다. 0~9 사이의 서로 다른 숫자 4개를 입력하세요.");
                    continue;
                }

            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 숫자만 입력하세요.");
                scanner.nextLine();
                continue;
            }

            attempts++;
            int strike = 0;
            int ball = 0;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (inputNumbers[i] == secretNumber[j]) {
                        if (i == j) {
                            strike++;
                        } else {
                            ball++;
                        }
                    }
                }
            }

            if (strike == 4) {
                System.out.println("\n4 스트라이크! 문이 열렸습니다!");
                System.out.println("축하합니다! 저택에서 탈출에 성공했습니다!");
                return true;
            } else if (strike == 0 && ball == 0) {
                System.out.println("아웃! (남은 시도: " + (maxAttempts - attempts) + "번)");
            } else {
                System.out.println("스트라이크: " + strike + " / 볼: " + ball + " (남은 시도: " + (maxAttempts - attempts) + "번)");
            }

            if (attempts >= maxAttempts) {
                System.out.println("\n시간이 너무 오래 걸렸습니다...");
                System.out.println("살인자가 현관문 앞에 도착했습니다!");
                return false;
            }
        }
        return false;
    }
    
}
package puzzle;
import java.util.*;

public class BaseballGame {
    private int[] answer;

    public BaseballGame() {
        answer = new int[]{8, 1, 5}; //퀴즈 정답 수정 가능
    }

    public boolean start(Scanner scanner, String playerName) {
        System.out.println("\n출구 게이트 앞에 도착했습니다.");
        System.out.println("특수 보안 패드가 작동 중입니다. 정확한 3자리 코드를 입력해야 합니다.");
        System.out.println("보안 패드는 야구 게임처럼 숫자와 위치를 맞춰야 열립니다.");
        System.out.println("숫자와 위치가 정확할수록 게이트 해제에 가까워집니다...");

        System.out.println("(설명: Strike = 숫자,위치 모두 일치, Ball = 숫자만 존재, Out = 숫자 없음)\n");

        int attempts = 0;
        int maxAttempts = 15; // 최대 시도 횟수 15회

        while (attempts < maxAttempts) {
            System.out.print("보안 코드 입력 (예: 123): ");
            String input = scanner.nextLine();

            if (input.length() != 3 || !input.matches("\\d{3}")) {
                System.out.println("3자리 숫자를 입력하세요!");
                continue;
            }

            int[] guess = new int[3];
            for (int i = 0; i < 3; i++) {
                guess[i] = input.charAt(i) - '0';
            }

            int strike = 0, ball = 0;
            for (int i = 0; i < 3; i++) {
                if (guess[i] == answer[i]) {
                    strike++;
                } else if (contains(answer, guess[i])) {
                    ball++;
                }
            }

            if (strike == 3) {
                System.out.println("\n보안 패드 해제 성공! 게이트가 열립니다!");
                return true;
            } else {
                attempts++;
                int out = 3 - strike - ball;
                System.out.println("\n" + strike + " Strike (숫자+위치 일치)");
                System.out.println(ball + " Ball (숫자만 존재)");
                System.out.println(out + " Out (숫자 없음)");
                System.out.println("남은 기회: " + (maxAttempts - attempts) + "번\n");
            }
        }

        System.out.println("기회를 모두 소진했습니다! 좀비들이 접근합니다...");
        return false;
    }

    private boolean contains(int[] arr, int num) {
        for (int n : arr) {
            if (n == num) return true;
        }
        return false;
    }
}
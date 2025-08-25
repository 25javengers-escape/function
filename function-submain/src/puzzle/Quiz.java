package puzzle;
import java.util.Scanner;

public class Quiz {

    public boolean solveQuiz(Scanner scanner, String playerName) {
        System.out.println("\n" + playerName + "님은 데이터실에 들어왔습니다...");
        System.out.println("다음 구역으로 이동하려면 보안 잠금 장치를 풀어야 합니다...");
        System.out.println("벽에 연구원들의 메모가 적혀 있습니다:");
        System.out.println();
        System.out.println("[좀비는 인간의 뇌를 노린다. 그러나 연구소에서 밝혀낸 유일한 취약점은 무엇일까?]");
        System.out.println();

        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            System.out.print("정답을 입력하세요: ");
            String answer = scanner.nextLine().trim();

            if (answer.equals("불")) {
                System.out.println("\n정답입니다!");
                System.out.println("보안 장치가 해제되며 다음 구역으로 이동합니다.");
                return true;
            } else {
                attempts++;
                if (attempts == 1) {
                    System.out.println("\n틀렸습니다...");
                    System.out.println("연구원 메모에 '이것은 좀비를 태우는 유일한 방법'이라는 기록이 있다.");
                    System.out.println("좀비들의 신음소리가 점점 가까워집니다!");
                    System.out.println("남은 기회: " + (maxAttempts - attempts) + "번\n");
                } else if (attempts == 2) {
                    System.out.println("\n틀렸습니다...");
                    System.out.println("바닥에 화염방사기가 떨어져 있는 것 같다...");
                    System.out.println("좀비들의 신음소리가 더 가까워집니다!");
                    System.out.println("남은 기회: " + (maxAttempts - attempts) + "번\n");
                } else {
                    System.out.println("\n틀렸습니다...");
                    System.out.println("좀비 떼에게 발각되었습니다!");
                    return false;
                }
            }
        }
        return false;
    }
}
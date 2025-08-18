package puzzle;
import java.util.Scanner;

public class Quiz {

    public boolean solveQuiz(Scanner scanner) {
        System.out.println("\n당신은 숨은 방에 들어왔습니다...");
        System.out.println("그러나 또 다른 문 앞에 자물쇠가 걸려 있습니다...");
        System.out.println("벽에는 이렇게 적혀 있습니다:");
        System.out.println();
        System.out.println("[나는 항상 너와 함께 있지만, 결코 잡을 수는 없다. 낮에는 길고 밤에는 짧다. 나는 무엇일까?]");
        System.out.println();

        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            System.out.print("정답을 입력하세요: ");
            String answer = scanner.nextLine().trim();

            if (answer.equals("그림자")) {
                System.out.println("\n정답입니다!");
                System.out.println("자물쇠가 풀리면서 문이 열렸습니다. 다음 단계로 이동합니다.");
                return true;
            } else {
                attempts++;
                if (attempts < maxAttempts) {
                    System.out.println("\n틀렸습니다... 살인자의 발소리가 가까워집니다!");
                    System.out.println("남은 기회: " + (maxAttempts - attempts) + "번\n");
                } else {
                    System.out.println("\n틀렸습니다... 살인자에게 발각되었습니다!");
                    return false;
                }
            }
        }
        return false;
    }
}
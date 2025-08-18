package item;

public class Item {
    private String[] tools = {"가위", "나이프", "톱", "망치"};
    private String[] keys = {"1번 열쇠", "2번 열쇠", "3번 열쇠", "4번 열쇠"};
    private String correctTool = "가위";
    private String correctKey = "3번 열쇠";

    public boolean isCorrectTool(String tool) {
        return tool.equals(correctTool);
    }

    public boolean isCorrectKey(String key) {
        return key.equals(correctKey);
    }

    public String[] getTools() {
        return tools.clone();
    }

    public String[] getKeys() {
        return keys.clone();
    }

    public String getCorrectTool() {
        return correctTool;
    }

    public String getCorrectKey() {
        return correctKey;
    }

    public void showTools() {
        System.out.println("사용할 수 있는 도구들:");
        for (int i = 0; i < tools.length; i++) {
            System.out.println((i + 1) + ". " + tools[i]);
        }
    }

    public void showKeys() {
        System.out.println("발견한 열쇠들:");
        for (int i = 0; i < keys.length; i++) {
            System.out.println((i + 1) + ". " + keys[i]);
        }
    }
}
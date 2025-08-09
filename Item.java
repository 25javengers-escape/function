package project_2;

// 게임 내 등장하는 모든 아이템

public class Item {

    String[] tools = {"가위", "나이프", "톱", "망치"};
    String[] keys = {"1번", "2번", "3번", "4번"};
    private String correctTool = "가위";
    private String correctkey = "3번";

    public boolean isCorrectTools(String tool) {
        return tool.equals(correctTool);
    }

    public boolean isCorrectKeys(String key) {
        return key.equals(correctkey);
    }

    public String[] getTools() {
        return tools;
    }

    public String[] getKeys() {
        return keys;
    }

}


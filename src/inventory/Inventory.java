package inventory;
import java.util.*;

public class Inventory {
    private List<String> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(String item) {
        if (!items.contains(item)) {
            items.add(item);
            System.out.println(item + "을(를) 획득했습니다!");
        }
    }

    public void removeItem(String item) {
        if (items.remove(item)) {
            System.out.println(item + "을(를) 사용했습니다.");
        }
    }

    public boolean hasItem(String item) {
        return items.contains(item);
    }

    public void showInventory() {
        if (items.isEmpty()) {
            System.out.println("인벤토리가 비어있습니다.");
        } else {
            System.out.println("보유 아이템:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
        }
    }

    public int size() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }
}
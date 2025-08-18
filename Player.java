package project_2;

import Room; //수정
import project_2.Item;
import Puzzle; //수정

//플레이어의 상태 추적, 아이템 사용 행위 제어

public class Player {
    private Room currentRoom; // 현재 방
    private boolean dead=false; // 사망 여부

    //시작 방 설정
    public Player(Room startingRoom) {
        this.currentRoom = startingRoom;
    }

    //현재 방 반환
    public Room getCurrentRoom() {
        return currentRoom;
    }

    //방 이동
    public void moveTo(Room nextRoom) {
        if (nextRoom != null) {
            this.currentRoom = nextRoom;
        }
    }

    //도구 사용
    public boolean useTool(String tool, Item itemChecker) {
        return itemChecker.isCorrectTools(tool);
    }

    //키 사용 
    public boolean useKey(String key, Item itemChecker) {
        return itemChecker.isCorrectKeys(key);
    }

    //퍼즐 풀기
    public boolean solvePuzzle(Puzzle puzzle) {
        //return puzzle.메서드(); //수정
    }

    //사용자 사망 처리
    public void die() {
        dead = true;
    }

    //사용자 사망 여부 확인
    public boolean isDead() {
        return dead;
    }
}

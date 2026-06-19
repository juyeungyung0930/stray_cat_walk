package view;

import model.dto.FriendCatDTO;
import model.dto.RelationshipDTO;

import java.util.List;
import java.util.Scanner;

/**
 * 고양이 만남/관계 화면 출력을 담당하는 View
 */
public class FriendView {

    private Scanner scanner = new Scanner(System.in);

    /**
     * 고양이를 처음 만났을 때 출력한다
     */
    public void printMeetCat(FriendCatDTO npc) {
        System.out.println("\n🐱 고양이를 발견했다!");
        System.out.printf("  이름: %s | 색: %s | 성격: %s%n",
                npc.getName(), npc.getColor(), npc.getPersonality());
    }

    /**
     * 성격 궁합으로 결정된 관계를 출력한다
     */
    public void printRelationResult(String npcName, String relType) {
        String emoji = relType.equals("FRIEND") ? "💛" : relType.equals("RIVAL") ? "😾" : "😐";
        System.out.printf("  %s %s → %s 관계가 됐다!%n", emoji, npcName, relType);
    }

    /**
     * 전체 관계 목록을 출력한다
     */
    public void printRelationshipList(List<RelationshipDTO> list) {
        if (list.isEmpty()) {
            System.out.println("  아직 아는 고양이가 없다냥...");
            return;
        }
        System.out.println("\n[ 친구/라이벌 목록 ]");
        for (int i = 0; i < list.size(); i++) {
            RelationshipDTO rel = list.get(i);
            String emoji = rel.getRelType().equals("FRIEND") ? "💛"
                         : rel.getRelType().equals("RIVAL")  ? "😾" : "😐";
            System.out.printf("  %d. %s %s (%s) - %s%n",
                    i + 1, emoji, rel.getNpcName(), rel.getRelType(), rel.getMetAt());
        }
    }

    /**
     * 영역 침범 결과를 출력한다
     */
    public void printInvadeResult(boolean success, String locationName, String ownerName) {
        if (success) {
            System.out.printf("  😈 %s 구역 침범 성공! %s의 영역을 빼앗았다!%n", locationName, ownerName);
        } else {
            System.out.printf("  😿 침범 실패... %s한테 쫓겨났다.%n", ownerName);
        }
    }

    /**
     * 친구 고양이 위치를 출력한다
     */
    public void printFriendLocation(String friendName, String locationName) {
        System.out.printf("  📍 %s은(는) 지금 [%s]에 있다냥!%n", friendName, locationName);
    }
}

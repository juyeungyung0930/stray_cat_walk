package model.service;

import model.dao.FriendDAO;
import model.dao.RelationshipDAO;
import model.dto.FriendCatDTO;
import model.dto.RelationshipDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * 고양이 관계 관련 비즈니스 로직을 담당하는 Service
 * 성격 궁합 판정, 관계 등록/변경 처리
 */
public class FriendService {

    private FriendDAO       friendDAO       = new FriendDAO();
    private RelationshipDAO relationshipDAO = new RelationshipDAO();

    /**
     * 특정 장소에 있는 NPC 고양이 목록을 반환한다
     */
    public List<FriendCatDTO> getCatsAtLocation(int locationId) throws SQLException {
        return friendDAO.selectCatsByLocation(locationId);
    }

    /**
     * 친구 고양이의 현재 위치를 반환한다
     */
    public FriendCatDTO getFriendLocation(int npcId) throws SQLException {
        return friendDAO.selectFriendCatById(npcId);
    }

    /**
     * 내 고양이와 NPC의 성격을 비교해 관계 타입을 결정한다
     *
     * 궁합 규칙:
     * - 같은 성격 → FRIEND
     * - 도도함 vs 애교쟁이 → FRIEND (상호보완)
     * - 겁쟁이 vs 도도함 → RIVAL (위협 느낌)
     * - 나머지 → NEUTRAL
     */
    public String determineRelType(String myPersonality, String npcPersonality) {
        if (myPersonality.equals(npcPersonality)) return "FRIEND";
        if ((myPersonality.equals("도도함") && npcPersonality.equals("애교쟁이")) ||
            (myPersonality.equals("애교쟁이") && npcPersonality.equals("도도함"))) {
            return "FRIEND";
        }
        if ((myPersonality.equals("겁쟁이") && npcPersonality.equals("도도함")) ||
            (myPersonality.equals("도도함") && npcPersonality.equals("겁쟁이"))) {
            return "RIVAL";
        }
        return "NEUTRAL";
    }

    /**
     * 고양이를 처음 만났을 때 관계를 등록한다
     * 이미 아는 사이면 등록하지 않음
     */
    public String meetCat(int catId, FriendCatDTO npc, String myPersonality) throws SQLException {
        // 이미 아는 사이인지 확인
        RelationshipDTO existing = relationshipDAO.selectRelationship(catId, npc.getNpcId());
        if (existing != null) {
            return existing.getRelType(); // 이미 아는 사이면 기존 관계 반환
        }

        // 처음 만나면 성격 궁합으로 관계 결정
        String relType = determineRelType(myPersonality, npc.getPersonality());
        RelationshipDTO rel = new RelationshipDTO();
        rel.setCatId(catId);
        rel.setNpcId(npc.getNpcId());
        rel.setRelType(relType);
        relationshipDAO.insertRelationship(rel);
        return relType;
    }

    /**
     * 관계 타입을 변경한다 (영역 침범, 선물 등으로 관계 변화 시)
     */
    public void changeRelType(int catId, int npcId, String newRelType) throws SQLException {
        relationshipDAO.updateRelType(catId, npcId, newRelType);
    }

    /**
     * 내 고양이의 전체 관계 목록을 반환한다
     */
    public List<RelationshipDTO> getMyRelationships(int catId) throws SQLException {
        return relationshipDAO.selectRelationshipsByCatId(catId);
    }
}

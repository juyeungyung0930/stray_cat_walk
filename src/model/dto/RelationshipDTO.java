package model.dto;

import java.sql.Date;

/**
 * 관계 데이터를 담는 DTO
 * RELATIONSHIP 테이블과 1:1 매핑
 * 내 고양이와 NPC 고양이의 관계(친구/라이벌/중립)를 저장
 */
public class RelationshipDTO {

    private int    relId;    // 관계 ID (PK)
    private int    catId;    // 내 고양이 ID (CAT FK)
    private int    npcId;    // NPC 고양이 ID (FRIEND_CAT FK)
    private String relType;  // 관계 타입: FRIEND / RIVAL / NEUTRAL
    private Date   metAt;    // 처음 만난 날짜

    // 출력 편의를 위한 JOIN 필드 (DB 컬럼 아님)
    private String npcName;

    public RelationshipDTO() {}

    public RelationshipDTO(int relId, int catId, int npcId, String relType, Date metAt) {
        this.relId   = relId;
        this.catId   = catId;
        this.npcId   = npcId;
        this.relType = relType;
        this.metAt   = metAt;
    }

    public int    getRelId()   { return relId; }
    public void   setRelId(int relId) { this.relId = relId; }

    public int    getCatId()   { return catId; }
    public void   setCatId(int catId) { this.catId = catId; }

    public int    getNpcId()   { return npcId; }
    public void   setNpcId(int npcId) { this.npcId = npcId; }

    public String getRelType() { return relType; }
    public void   setRelType(String relType) { this.relType = relType; }

    public Date   getMetAt()   { return metAt; }
    public void   setMetAt(Date metAt) { this.metAt = metAt; }

    public String getNpcName() { return npcName; }
    public void   setNpcName(String npcName) { this.npcName = npcName; }

    @Override
    public String toString() {
        return String.format("[%s] 관계:%s | 만난날:%s", npcName, relType, metAt);
    }
}

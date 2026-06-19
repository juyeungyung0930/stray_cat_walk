package model.dto;

/**
 * NPC 고양이 데이터를 담는 DTO
 * FRIEND_CAT 테이블과 1:1 매핑
 * 만날 수 있는 길고양이들의 정보를 담음
 */
public class FriendCatDTO {

    private int    npcId;        // NPC 고양이 ID (PK)
    private String name;         // NPC 이름
    private String color;        // 털 색깔
    private String personality;  // 성격 (내 고양이 성격과 비교해 관계 결정)
    private int    locationId;   // 현재 있는 장소 (LOCATION FK)

    public FriendCatDTO() {}

    public FriendCatDTO(int npcId, String name, String color,
                        String personality, int locationId) {
        this.npcId      = npcId;
        this.name       = name;
        this.color      = color;
        this.personality = personality;
        this.locationId = locationId;
    }

    public int    getNpcId()       { return npcId; }
    public void   setNpcId(int npcId) { this.npcId = npcId; }

    public String getName()        { return name; }
    public void   setName(String name) { this.name = name; }

    public String getColor()       { return color; }
    public void   setColor(String color) { this.color = color; }

    public String getPersonality() { return personality; }
    public void   setPersonality(String personality) { this.personality = personality; }

    public int    getLocationId()  { return locationId; }
    public void   setLocationId(int locationId) { this.locationId = locationId; }

    @Override
    public String toString() {
        return String.format("[%s] 색:%s | 성격:%s", name, color, personality);
    }
}

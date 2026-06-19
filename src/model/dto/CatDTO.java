package model.dto;

/**
 * 내 고양이 캐릭터 데이터를 담는 DTO
 * CAT 테이블과 1:1 매핑
 */
public class CatDTO {

    private int    catId;        // 고양이 ID (PK)
    private String name;         // 고양이 이름
    private String color;        // 털 색깔
    private String personality;  // 성격 (예: 도도함, 애교쟁이, 겁쟁이)
    private int    hunger;       // 허기 수치 (0~100, 낮을수록 배고픔)
    private int    fatigue;      // 피로도 수치 (0~100, 높을수록 피곤)
    private int    mood;         // 기분 수치 (0~100, 높을수록 기분 좋음)
    private int    locationId;   // 현재 위치 (LOCATION FK)

    // 기본 생성자
    public CatDTO() {}

    // 전체 필드 생성자
    public CatDTO(int catId, String name, String color, String personality,
                  int hunger, int fatigue, int mood, int locationId) {
        this.catId       = catId;
        this.name        = name;
        this.color       = color;
        this.personality = personality;
        this.hunger      = hunger;
        this.fatigue     = fatigue;
        this.mood        = mood;
        this.locationId  = locationId;
    }

    // Getters & Setters
    public int    getCatId()       { return catId; }
    public void   setCatId(int catId) { this.catId = catId; }

    public String getName()        { return name; }
    public void   setName(String name) { this.name = name; }

    public String getColor()       { return color; }
    public void   setColor(String color) { this.color = color; }

    public String getPersonality() { return personality; }
    public void   setPersonality(String personality) { this.personality = personality; }

    public int    getHunger()      { return hunger; }
    public void   setHunger(int hunger) { this.hunger = hunger; }

    public int    getFatigue()     { return fatigue; }
    public void   setFatigue(int fatigue) { this.fatigue = fatigue; }

    public int    getMood()        { return mood; }
    public void   setMood(int mood) { this.mood = mood; }

    public int    getLocationId()  { return locationId; }
    public void   setLocationId(int locationId) { this.locationId = locationId; }

    @Override
    public String toString() {
        return String.format("[%s] 색:%s | 성격:%s | 허기:%d | 피로:%d | 기분:%d",
                name, color, personality, hunger, fatigue, mood);
    }
}

package model.dto;

/**
 * 장소 데이터를 담는 DTO
 * LOCATION 테이블과 1:1 매핑
 */
public class LocationDTO {

    private int    locationId;      // 장소 ID (PK)
    private String name;            // 장소 이름 (예: 골목, 공원, 편의점 앞)
    private String zoneType;        // 구역 타입 (예: ALLEY, PARK, STREET)
    private int    catAppearRate;   // 고양이 출현 확률 (0~100%)
    private int    itemFindRate;    // 아이템 발견 확률 (0~100%)

    public LocationDTO() {}

    public LocationDTO(int locationId, String name, String zoneType,
                       int catAppearRate, int itemFindRate) {
        this.locationId    = locationId;
        this.name          = name;
        this.zoneType      = zoneType;
        this.catAppearRate = catAppearRate;
        this.itemFindRate  = itemFindRate;
    }

    public int    getLocationId()    { return locationId; }
    public void   setLocationId(int locationId) { this.locationId = locationId; }

    public String getName()          { return name; }
    public void   setName(String name) { this.name = name; }

    public String getZoneType()      { return zoneType; }
    public void   setZoneType(String zoneType) { this.zoneType = zoneType; }

    public int    getCatAppearRate() { return catAppearRate; }
    public void   setCatAppearRate(int catAppearRate) { this.catAppearRate = catAppearRate; }

    public int    getItemFindRate()  { return itemFindRate; }
    public void   setItemFindRate(int itemFindRate) { this.itemFindRate = itemFindRate; }

    @Override
    public String toString() {
        return String.format("[%s] 구역:%s | 고양이출현:%d%% | 아이템발견:%d%%",
                name, zoneType, catAppearRate, itemFindRate);
    }
}

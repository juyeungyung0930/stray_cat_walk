package model.dto;

import java.sql.Date;

/**
 * 영역(마킹) 데이터를 담는 DTO
 * TERRITORY 테이블과 1:1 매핑
 * 어느 고양이가 어느 장소에 마킹했는지 저장
 */
public class TerritoryDTO {

    private int    territoryId;   // 영역 ID (PK)
    private int    locationId;    // 마킹된 장소 (LOCATION FK)
    private int    ownerCatId;    // 마킹한 고양이 ID (CAT or FRIEND_CAT FK)
    private Date   markedAt;      // 마킹한 날짜

    // 출력 편의를 위한 JOIN 필드 (DB 컬럼 아님)
    private String locationName;
    private String ownerName;

    public TerritoryDTO() {}

    public TerritoryDTO(int territoryId, int locationId, int ownerCatId, Date markedAt) {
        this.territoryId = territoryId;
        this.locationId  = locationId;
        this.ownerCatId  = ownerCatId;
        this.markedAt    = markedAt;
    }

    public int    getTerritoryId()  { return territoryId; }
    public void   setTerritoryId(int territoryId) { this.territoryId = territoryId; }

    public int    getLocationId()   { return locationId; }
    public void   setLocationId(int locationId) { this.locationId = locationId; }

    public int    getOwnerCatId()   { return ownerCatId; }
    public void   setOwnerCatId(int ownerCatId) { this.ownerCatId = ownerCatId; }

    public Date   getMarkedAt()     { return markedAt; }
    public void   setMarkedAt(Date markedAt) { this.markedAt = markedAt; }

    public String getLocationName() { return locationName; }
    public void   setLocationName(String locationName) { this.locationName = locationName; }

    public String getOwnerName()    { return ownerName; }
    public void   setOwnerName(String ownerName) { this.ownerName = ownerName; }

    @Override
    public String toString() {
        return String.format("[%s] 마킹주인:%s | 날짜:%s", locationName, ownerName, markedAt);
    }
}

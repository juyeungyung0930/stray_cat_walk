package model.dto;

/**
 * 아이템 데이터를 담는 DTO
 * ITEM 테이블과 1:1 매핑
 * 길에서 발견할 수 있는 아이템 정보 (마스터 데이터)
 */
public class ItemDTO {

    private int    itemId;         // 아이템 ID (PK)
    private String name;           // 아이템 이름 (예: 츄르, 참치캔, 생선뼈)
    private String rarity;         // 희귀도 (COMMON / RARE / EPIC)
    private int    hungerRecover;  // 허기 회복량

    public ItemDTO() {}

    public ItemDTO(int itemId, String name, String rarity, int hungerRecover) {
        this.itemId        = itemId;
        this.name          = name;
        this.rarity        = rarity;
        this.hungerRecover = hungerRecover;
    }

    public int    getItemId()        { return itemId; }
    public void   setItemId(int itemId) { this.itemId = itemId; }

    public String getName()          { return name; }
    public void   setName(String name) { this.name = name; }

    public String getRarity()        { return rarity; }
    public void   setRarity(String rarity) { this.rarity = rarity; }

    public int    getHungerRecover() { return hungerRecover; }
    public void   setHungerRecover(int hungerRecover) { this.hungerRecover = hungerRecover; }

    @Override
    public String toString() {
        return String.format("[%s] 희귀도:%s | 허기회복:%d", name, rarity, hungerRecover);
    }
}

package model.dto;

/**
 * 인벤토리 데이터를 담는 DTO
 * INVENTORY 테이블과 1:1 매핑
 * 내 고양이가 보유한 아이템 수량 관리
 */
public class InventoryDTO {

    private int invId;     // 인벤토리 ID (PK)
    private int catId;     // 내 고양이 ID (CAT FK)
    private int itemId;    // 아이템 ID (ITEM FK)
    private int quantity;  // 보유 수량

    // ItemDTO를 JOIN해서 가져올 때 편의상 사용하는 필드 (DB 컬럼 아님)
    private String itemName;
    private String itemRarity;

    public InventoryDTO() {}

    public InventoryDTO(int invId, int catId, int itemId, int quantity) {
        this.invId    = invId;
        this.catId    = catId;
        this.itemId   = itemId;
        this.quantity = quantity;
    }

    public int    getInvId()      { return invId; }
    public void   setInvId(int invId) { this.invId = invId; }

    public int    getCatId()      { return catId; }
    public void   setCatId(int catId) { this.catId = catId; }

    public int    getItemId()     { return itemId; }
    public void   setItemId(int itemId) { this.itemId = itemId; }

    public int    getQuantity()   { return quantity; }
    public void   setQuantity(int quantity) { this.quantity = quantity; }

    public String getItemName()   { return itemName; }
    public void   setItemName(String itemName) { this.itemName = itemName; }

    public String getItemRarity() { return itemRarity; }
    public void   setItemRarity(String itemRarity) { this.itemRarity = itemRarity; }

    @Override
    public String toString() {
        return String.format("[%s] %s x%d", itemName, itemRarity, quantity);
    }
}

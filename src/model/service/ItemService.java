package model.service;

import common.RandomUtil;
import model.dao.InventoryDAO;
import model.dao.ItemDAO;
import model.dto.InventoryDTO;
import model.dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * 아이템 관련 비즈니스 로직을 담당하는 Service
 * 랜덤 아이템 발견, 먹기, 선물하기 처리
 */
public class ItemService {

    private ItemDAO      itemDAO      = new ItemDAO();
    private InventoryDAO inventoryDAO = new InventoryDAO();

    /**
     * 전체 아이템 목록에서 랜덤으로 1개를 뽑아 반환한다
     * 아이템 발견 확률 판정은 WalkService에서 먼저 하고 이 메서드를 호출
     */
    public ItemDTO findRandomItem() throws SQLException {
        List<ItemDTO> items = itemDAO.selectAllItems();
        if (items.isEmpty()) return null;
        int idx = RandomUtil.randomIndex(items.size());
        return items.get(idx);
    }

    /**
     * 발견한 아이템을 인벤토리에 추가한다
     */
    public void pickUpItem(int catId, int itemId) throws SQLException {
        inventoryDAO.addItem(catId, itemId);
    }

    /**
     * 내 인벤토리 목록을 반환한다
     */
    public List<InventoryDTO> getInventory(int catId) throws SQLException {
        return inventoryDAO.selectInventoryByCatId(catId);
    }

    /**
     * 아이템을 먹는다 → 인벤토리에서 제거 후 허기 회복량을 반환
     * 실제 허기 수치 변경은 CatService.applyEatItem()에서 처리
     *
     * @return 해당 아이템의 허기 회복량 (없으면 0)
     */
    public int eatItem(int catId, int itemId) throws SQLException {
        List<ItemDTO> items = itemDAO.selectAllItems();
        ItemDTO target = items.stream()
                              .filter(i -> i.getItemId() == itemId)
                              .findFirst().orElse(null);
        if (target == null) return 0;

        inventoryDAO.removeItem(catId, itemId);
        return target.getHungerRecover();
    }

    /**
     * 아이템을 친구에게 선물한다 → 내 인벤토리에서 제거
     * 관계 변화 처리는 Controller에서 FriendService를 통해 진행
     */
    public void giftItem(int catId, int itemId) throws SQLException {
        inventoryDAO.removeItem(catId, itemId);
    }
}

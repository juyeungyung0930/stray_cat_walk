package model.dto;

import java.sql.Date;

/**
 * 산책 일지 데이터를 담는 DTO
 * EVENT_LOG 테이블과 1:1 매핑
 * 행동할 때마다 기록되어 오늘의 일지 조회에 사용됨
 */
public class WalkLogDTO {

    private int    logId;       // 로그 ID (PK)
    private int    catId;       // 내 고양이 ID (CAT FK)
    private Date   logDate;     // 게임 내 날짜 (GAME_DATE 기준)
    private String actionType;  // 행동 종류 (예: MOVE, EAT, MARK, MEET, GIFT)
    private String description; // 행동 설명 (예: "골목에서 나비를 만나 친구가 됨")

    public WalkLogDTO() {}

    public WalkLogDTO(int logId, int catId, Date logDate,
                      String actionType, String description) {
        this.logId       = logId;
        this.catId       = catId;
        this.logDate     = logDate;
        this.actionType  = actionType;
        this.description = description;
    }

    public int    getLogId()       { return logId; }
    public void   setLogId(int logId) { this.logId = logId; }

    public int    getCatId()       { return catId; }
    public void   setCatId(int catId) { this.catId = catId; }

    public Date   getLogDate()     { return logDate; }
    public void   setLogDate(Date logDate) { this.logDate = logDate; }

    public String getActionType()  { return actionType; }
    public void   setActionType(String actionType) { this.actionType = actionType; }

    public String getDescription() { return description; }
    public void   setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s", logDate, actionType, description);
    }
}

package common;

import java.util.Random;

/**
 * 랜덤 관련 유틸리티 클래스
 * 아이템 발견, 고양이 출현, 이벤트 발생 등 확률 계산에 사용
 */
public class RandomUtil {

    private static final Random random = new Random();

    /**
     * 주어진 확률(%)로 성공 여부를 반환한다
     * 예) isSuccess(30) → 30% 확률로 true
     *
     * @param percent 0~100 사이의 확률 값
     * @return 성공이면 true
     */
    public static boolean isSuccess(int percent) {
        return random.nextInt(100) < percent;
    }

    /**
     * min 이상 max 이하의 랜덤 정수를 반환한다
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 배열에서 랜덤한 요소 하나의 인덱스를 반환한다
     * 예) 아이템 목록에서 랜덤 1개 선택 시 사용
     */
    public static int randomIndex(int size) {
        return random.nextInt(size);
    }
}

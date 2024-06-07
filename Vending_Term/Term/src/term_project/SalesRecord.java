package term_project;

import java.time.LocalDate; // 로컬 날짜 클래스를 사용하기 위한 임포트
import java.util.HashMap; // 해시맵 클래스를 사용하기 위한 임포트
import java.util.Map; // 맵 인터페이스를 사용하기 위한 임포트

// 매출 기록을 관리하는 클래스
public class SalesRecord {
    private Map<LocalDate, Map<String, Integer>> dailySales; // 일별 매출 기록을 저장하는 맵
    private Map<String, Integer> monthlySales; // 월별 매출 기록을 저장하는 맵

    // 생성자: 일별 및 월별 매출 기록 맵을 초기화
    public SalesRecord() {
        dailySales = new HashMap<>(); // 일별 매출 기록을 위한 해시맵 초기화
        monthlySales = new HashMap<>(); // 월별 매출 기록을 위한 해시맵 초기화
    }

    // 판매 기록을 추가하는 메서드
    public void recordSale(String productName, int quantity) {
        LocalDate today = LocalDate.now(); // 현재 날짜를 가져옴
        dailySales.putIfAbsent(today, new HashMap<>()); // 오늘 날짜의 매출 기록이 없으면 새로운 해시맵으로 추가
        dailySales.get(today).put(productName, dailySales.get(today).getOrDefault(productName, 0) + quantity); // 오늘 날짜의 해당 제품 매출 수량을 업데이트

        String month = today.getMonth().toString(); // 현재 월을 문자열로 가져옴
        monthlySales.put(month, monthlySales.getOrDefault(month, 0) + quantity); // 월별 매출 기록을 업데이트
    }
    

    // 일별 매출 기록을 반환하는 메서드
    public Map<LocalDate, Map<String, Integer>> getDailySales() {
        return dailySales; // 일별 매출 기록 맵 반환
    }

    // 월별 매출 기록을 반환하는 메서드
    public Map<String, Integer> getMonthlySales() {
        return monthlySales; // 월별 매출 기록 맵 반환
    }
}

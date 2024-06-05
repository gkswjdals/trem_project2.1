package term_project;

import java.io.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class Admin {
    private String password; // 관리자 비밀번호
    private SalesRecord salesRecord; // 판매 기록을 관리하는 객체

    private Map<String, Integer> dailySalesMap; // 일별 판매 기록을 저장하는 맵
    private Map<String, Integer> monthlySalesMap; // 월별 판매 기록을 저장하는 맵

    // 생성자: 비밀번호를 설정하고 판매 기록 객체와 맵들을 초기화
    public Admin(String password) {
        this.password = password;
        this.salesRecord = new SalesRecord();
        this.dailySalesMap = new LinkedHashMap<>();
        this.monthlySalesMap = new LinkedHashMap<>();
    }

    // 비밀번호 확인 메서드
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // 비밀번호 변경 메서드
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // 파일에서 비밀번호를 읽어와서 확인하는 메서드
    public boolean checkPassword(String inputPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/password.txt"))) {
            String filePassword = reader.readLine().trim();
            return inputPassword.equals(filePassword);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 판매 기록을 저장하고 일별, 월별 판매 맵을 갱신하는 메서드
    public void recordSale(String productName, int quantity) {
        salesRecord.recordSale(productName, quantity);
        dailySalesMap.put(productName, dailySalesMap.getOrDefault(productName, 0) + quantity);
        monthlySalesMap.put(productName, monthlySalesMap.getOrDefault(productName, 0) + quantity);
        writeDailySales(); // 일별 판매 기록을 파일에 저장
        writeMonthlySales(); // 월별 판매 기록을 파일에 저장
    }

    // 일별 판매 기록을 파일에 쓰는 메서드
    private void writeDailySales() {
        LocalDate currentDate = LocalDate.now(); // 현재 날짜 가져오기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // 날짜 형식 지정
        String formattedDate = currentDate.format(formatter); // 날짜를 지정된 형식으로 포맷

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/daily.txt", true))) {
            writer.write(formattedDate + "\n"); // 날짜 기록
            for (String product : getProductNames()) {
                writer.write(product + "," + dailySalesMap.getOrDefault(product, 0) + "\n"); // 제품별 판매량 기록
            }
        } catch (IOException e) {
            e.printStackTrace(); // 파일 쓰기 중 예외 발생 시 스택 트레이스 출력
        }
    }

    // 월별 판매 기록을 파일에 쓰는 메서드
    private void writeMonthlySales() {
        LocalDate currentDate = LocalDate.now(); // 현재 날짜 가져오기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM"); // 월 형식 지정
        String formattedDate = currentDate.format(formatter); // 날짜를 지정된 형식으로 포맷

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/month.txt", true))) {
            writer.write(formattedDate + "\n"); // 월 기록
            for (String product : getProductNames()) {
                writer.write(product + "," + monthlySalesMap.getOrDefault(product, 0) + "\n"); // 제품별 판매량 기록
            }
        } catch (IOException e) {
            e.printStackTrace(); // 파일 쓰기 중 예외 발생 시 스택 트레이스 출력
        }
    }

    // 제품 이름 리스트를 반환하는 메서드
    private String[] getProductNames() {
        return new String[]{"물", "커피", "이온음료", "고급커피", "탄산음료", "특화음료"};
    }

    // 일별 판매 기록을 출력하는 메서드
    public void printDailySales() {
        System.out.println(getDailySalesData());
    }

    // 월별 판매 기록을 출력하는 메서드
    public void printMonthlySales() {
        System.out.println(getMonthlySalesData());
    }

    // 자판기에 재고를 채우는 메서드
    public void refillStock(VendingMachine vendingMachine, String productName, int amount) {
        vendingMachine.refillStock(productName, amount);
    }

    // 자판기의 동전 상태를 확인하는 메서드
    public void checkCoinStatus(VendingMachine vendingMachine) {
        vendingMachine.printCoinStatus();
    }

    // 자판기에서 동전을 수거하는 메서드
    public void collectCoins(VendingMachine vendingMachine) {
        vendingMachine.collectCoins();
    }

    // 자판기에 동전을 채우는 메서드
    public void refillCoins(VendingMachine vendingMachine, int denomination, int count) {
        vendingMachine.refillCoins(denomination, count);
    }

    // 자판기의 제품 정보를 변경하는 메서드
    public void changeProductDetails(VendingMachine vendingMachine, String oldProductName, String newProductName, int newPrice) {
        vendingMachine.changeProductDetails(oldProductName, newProductName, newPrice);
    }

    // 일별 판매 데이터를 문자열로 반환하는 메서드
    public String getDailySalesData() {
        StringBuilder sb = new StringBuilder(); // 문자열을 효율적으로 추가하기 위해 StringBuilder 사용
        Map<String, Map<String, Integer>> dailySales = readDailySales(); // 일별 판매 기록 읽기
        for (Map.Entry<String, Map<String, Integer>> entry : dailySales.entrySet()) {
            sb.append("날짜 : ").append(entry.getKey()).append("\n"); // 날짜 추가
            for (Map.Entry<String, Integer> productEntry : entry.getValue().entrySet()) {
                sb.append("음료: ").append(productEntry.getKey()).append("\t매출: ").append(productEntry.getValue()).append("\n"); // 제품별 매출 추가
            }
            sb.append("\n");
        }
        return sb.toString(); // 문자열 반환
    }

    // 월별 판매 데이터를 문자열로 반환하는 메서드
    public String getMonthlySalesData() {
        StringBuilder sb = new StringBuilder(); // 문자열을 효율적으로 추가하기 위해 StringBuilder 사용
        Map<String, Map<String, Integer>> monthlySales = readMonthlySales(); // 월별 판매 기록 읽기
        for (Map.Entry<String, Map<String, Integer>> entry : monthlySales.entrySet()) {
            sb.append(entry.getKey()).append("\n"); // 월 추가
            for (Map.Entry<String, Integer> productEntry : entry.getValue().entrySet()) {
                sb.append("음료: ").append(productEntry.getKey()).append("\t매출: ").append(productEntry.getValue()).append("\n"); // 제품별 매출 추가
            }
            sb.append("\n");
        }
        return sb.toString(); // 문자열 반환
    }

    // 월별 판매 금액 데이터를 문자열로 반환하는 메서드
    public String getMonthlySalesAmountData() {
        StringBuilder sb = new StringBuilder(); // 문자열을 효율적으로 추가하기 위해 StringBuilder 사용
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US); // 숫자 형식을 지정하기 위한 NumberFormat 객체
        Map<String, Map<String, Integer>> monthlySales = readMonthlySales(); // 월별 판매 기록 읽기
        for (Map.Entry<String, Map<String, Integer>> entry : monthlySales.entrySet()) {
            sb.append(entry.getKey()).append("\n"); // 월 추가
            for (Map.Entry<String, Integer> productEntry : entry.getValue().entrySet()) {
                int quantity = productEntry.getValue(); // 제품별 판매량 가져오기
                int price = getProductPrice(productEntry.getKey()); // 제품 가격 가져오기
                int total = quantity * price; // 총 매출 계산
                sb.append("음료: ").append(productEntry.getKey()).append("\t매출: ").append(numberFormat.format(total)).append("원\n"); // 제품별 매출 추가
            }
            sb.append("\n");
        }
        return sb.toString(); // 문자열 반환
    }

    // 제품 가격을 반환하는 메서드
    private int getProductPrice(String productName) {
        switch (productName) {
            case "물":
                return 450;
            case "커피":
                return 500;
            case "이온음료":
                return 550;
            case "고급커피":
                return 700;
            case "탄산음료":
                return 750;
            case "특화음료":
                return 800;
            default:
                return 0;
        }
    }

    // 일별 판매 기록을 읽어오는 메서드
    private Map<String, Map<String, Integer>> readDailySales() {
        Map<String, Map<String, Integer>> dailySales = new LinkedHashMap<>(); // 일별 판매 기록을 저장할 맵
        try (BufferedReader br = new BufferedReader(new FileReader("src/daily.txt"))) {
            String line;
            String currentDate = null; // 현재 날짜를 저장할 변수
            while ((line = br.readLine()) != null) {
                line = line.trim(); // 앞뒤 공백 제거
                if (line.isEmpty()) {
                    continue; // 빈 줄 건너뛰기
                }
                if (line.matches("\\d{8}")) { // 날짜 형식인지 확인
                    currentDate = line; // 현재 날짜 업데이트
                    dailySales.put(currentDate, new LinkedHashMap<>()); // 새로운 날짜의 판매 기록 추가
                } else if (currentDate != null) {
                    String[] parts = line.split(","); // 콤마로 문자열 분리
                    if (parts.length < 2) {
                        continue; // 잘못된 형식 건너뛰기
                    }
                    String product = parts[0]; // 제품 이름
                    int quantity;
                    try {
                        quantity = Integer.parseInt(parts[1]); // 판매량
                    } catch (NumberFormatException e) {
                        continue; // 숫자가 아닌 경우 건너뛰기
                    }
                    dailySales.get(currentDate).put(product, quantity); // 일별 판매 기록에 추가
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // 파일 읽기 중 예외 발생 시 스택 트레이스 출력
        }
        return dailySales; // 일별 판매 기록 반환
    }

    // 월별 판매 기록을 읽어오는 메서드
    private Map<String, Map<String, Integer>> readMonthlySales() {
        Map<String, Map<String, Integer>> monthlySales = new LinkedHashMap<>(); // 월별 판매 기록을 저장할 맵
        try (BufferedReader br = new BufferedReader(new FileReader("src/month.txt"))) {
            String line;
            String currentMonth = null; // 현재 월을 저장할 변수
            while ((line = br.readLine()) != null) {
                line = line.trim(); // 앞뒤 공백 제거
                if (line.isEmpty()) {
                    continue; // 빈 줄 건너뛰기
                }
                if (line.matches("[a-zA-Z]+")) { // 월 형식인지 확인
                    currentMonth = line; // 현재 월 업데이트
                    monthlySales.put(currentMonth, new LinkedHashMap<>()); // 새로운 월의 판매 기록 추가
                } else if (currentMonth != null) {
                    String[] parts = line.split(","); // 콤마로 문자열 분리
                    if (parts.length < 2) {
                        continue; // 잘못된 형식 건너뛰기
                    }
                    String product = parts[0]; // 제품 이름
                    int quantity;
                    try {
                        quantity = Integer.parseInt(parts[1]); // 판매량
                    } catch (NumberFormatException e) {
                        continue; // 숫자가 아닌 경우 건너뛰기
                    }
                    monthlySales.get(currentMonth).put(product, quantity); // 월별 판매 기록에 추가
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // 파일 읽기 중 예외 발생 시 스택 트레이스 출력
        }
        return monthlySales; // 월별 판매 기록 반환
    }
}

package term_project;

// Admin 클래스 정의
public class Admin {
    // 관리자 비밀번호를 저장하는 변수
    private String password;

    // 생성자: Admin 클래스의 인스턴스를 생성할 때 비밀번호를 설정
    public Admin(String password) {
        this.password = password; // 전달된 비밀번호로 password 변수 초기화
    }

    // 비밀번호 확인 메소드
    public boolean verifyPassword(String inputPassword) {
        // 입력된 비밀번호와 저장된 비밀번호가 일치하는지 확인
        return this.password.equals(inputPassword);
    }

    // 비밀번호 변경 메소드
    public void changePassword(String newPassword) {
        // 저장된 비밀번호를 새로운 비밀번호로 변경
        this.password = newPassword;
    }
}

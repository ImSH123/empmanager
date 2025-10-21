package kr.co.sist.emp.dto;

public class DeptDTO {
    private int deptCode;
    private String dName;

    public DeptDTO(int deptCode, String dName) {
        this.deptCode = deptCode;
        this.dName = dName;
    }

    public int getDeptCode() {
        return deptCode;
    }

    public String getDName() {
        return dName;
    }

    /**
     * JComboBox에 '이름'이 표시되도록 toString()을 오버라이드합니다.
     */
    @Override
    public String toString() {
        return dName; // "마케팅", "개발부" 등
    }
}
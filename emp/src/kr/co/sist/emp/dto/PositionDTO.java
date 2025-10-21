package kr.co.sist.emp.dto;

public class PositionDTO {
    private int posCode;
    private String pName;

    public PositionDTO(int posCode, String pName) {
        this.posCode = posCode;
        this.pName = pName;
    }

    public int getPosCode() {
        return posCode;
    }

    public String getPName() {
        return pName;
    }

    /**
     * JComboBox에 '이름'이 표시되도록 toString()을 오버라이드합니다.
     */
    @Override
    public String toString() {
        return pName; // "과장", "대리" 등
    }
}
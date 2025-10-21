package kr.co.sist.emp.design;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import kr.co.sist.emp.dto.DeptDTO;
import kr.co.sist.emp.dto.PositionDTO;
import kr.co.sist.emp.event.EmpDetailsInfoEvent;

public class EmpDetailsInfoDesign extends JFrame {

	// [수정] jtfVacationDays 변수 선언 추가
	private JTextField jtfEmpNo, jtfName, jtfTel, jtfAddress, jtfSal, jtfHireDate, jtfQuitDate, jtfVacationDays;
	
	private JComboBox<DeptDTO> jcbDept;
	private DefaultComboBoxModel<DeptDTO> dcbmDept;
	private JComboBox<PositionDTO> jcbPosition;
	private DefaultComboBoxModel<PositionDTO> dcbmPosition;
	
	private JComboBox<String> jcbAuth;
	private DefaultComboBoxModel<String> dcbmAuth;
	private JButton jbtnSave, jbtnCancel, jbtnQuit;
	private int empNo; 

	public EmpDetailsInfoDesign(int empNo) {
		super("사원 상세 정보");
		this.empNo = empNo; 
		setLayout(null);
		Font font = new Font("맑은고딕", Font.BOLD, 18);

		// [수정] 부서 (JTextField -> JComboBox)
		JLabel jlbDept = new JLabel("부서");
		jlbDept.setBounds(25, 30, 90, 35);
		jlbDept.setFont(font);
		add(jlbDept);

		dcbmDept = new DefaultComboBoxModel<>(); // [수정] NullPointerException 방지 위해 초기화
		jcbDept = new JComboBox<>(dcbmDept);
		jcbDept.setBounds(120, 30, 150, 35);
		jcbDept.setFont(font);
		add(jcbDept);
		
		// [수정] 이름 (jtfDeptName 자리를 채움)
		JLabel jlbName = new JLabel("이름");
		jlbName.setBounds(300, 30, 50, 35);
		jlbName.setFont(font);
		add(jlbName);

		jtfName = new JTextField();
		jtfName.setBounds(360, 30, 150, 35);
		jtfName.setFont(font);
		add(jtfName);
		jtfName.setEditable(false);
		
		// [수정] 사원번호 (레이아웃 변경)
		JLabel jlbEmpNo = new JLabel("사원번호");
		jlbEmpNo.setBounds(25, 80, 90, 35);
		jlbEmpNo.setFont(font);
		add(jlbEmpNo);

		jtfEmpNo = new JTextField();
		jtfEmpNo.setBounds(120, 80, 150, 35);
		jtfEmpNo.setFont(font);
		add(jtfEmpNo);
		jtfEmpNo.setEditable(false);

		// [수정] 직급 (JTextField -> JComboBox, 레이아웃 변경)
		JLabel jlbPosition = new JLabel("직급");
		jlbPosition.setBounds(300, 80, 60, 35);
		jlbPosition.setFont(font);
		add(jlbPosition);

		dcbmPosition = new DefaultComboBoxModel<>(); // [수정] NullPointerException 방지 위해 초기화
		jcbPosition = new JComboBox<>(dcbmPosition);
		jcbPosition.setBounds(360, 80, 150, 35);
		jcbPosition.setFont(font);
		add(jcbPosition);

		// [수정] 전화 (레이아웃 변경)
		JLabel jlbTel = new JLabel("전화");
		jlbTel.setBounds(25, 130, 90, 35);
		jlbTel.setFont(font);
		add(jlbTel);
		
		jtfTel = new JTextField();
		jtfTel.setBounds(120, 130, 390, 35);
		jtfTel.setFont(font);
		add(jtfTel);
		jtfTel.setEditable(true);

		// 주소
		JLabel jlbAddress = new JLabel("주소");
		jlbAddress.setBounds(25, 180, 90, 35);
		jlbAddress.setFont(font);
		add(jlbAddress);

		jtfAddress = new JTextField();
		jtfAddress.setBounds(120, 180, 390, 35);
		jtfAddress.setFont(font);
		add(jtfAddress);
		jtfAddress.setEditable(true);

		// 권한
		JLabel jlbAuth = new JLabel("권한");
		jlbAuth.setBounds(25, 230, 90, 35);
		jlbAuth.setFont(font);
		add(jlbAuth);

		dcbmAuth = new DefaultComboBoxModel<>();
		dcbmAuth.addElement("일반 직원");
		dcbmAuth.addElement("관리자");
		jcbAuth = new JComboBox<>(dcbmAuth);
		jcbAuth.setBounds(120, 230, 150, 35);
		jcbAuth.setFont(font);
		add(jcbAuth);

		// 연봉
		JLabel jlbSal = new JLabel("연봉");
		jlbSal.setBounds(300, 230, 60, 35);
		jlbSal.setFont(font);
		add(jlbSal);
		
		jtfSal = new JTextField();
		jtfSal.setBounds(360, 230, 150, 35);
		jtfSal.setFont(font);
		add(jtfSal);
		jtfSal.setEditable(false);

		// 입사일
		JLabel jlbHireDate = new JLabel("입사일");
		jlbHireDate.setBounds(25, 280, 90, 35);
		jlbHireDate.setFont(font);
		add(jlbHireDate);
		
		jtfHireDate = new JTextField();
		jtfHireDate.setBounds(120, 280, 150, 35);
		jtfHireDate.setFont(font);
		add(jtfHireDate);
		jtfHireDate.setEditable(false);

		// 퇴사일
		JLabel jlbQuitDate = new JLabel("퇴사일");
		jlbQuitDate.setBounds(300, 280, 60, 35);
		jlbQuitDate.setFont(font);
		add(jlbQuitDate);
		
		jtfQuitDate = new JTextField();
		jtfQuitDate.setBounds(360, 280, 150, 35);
		jtfQuitDate.setFont(font);
		add(jtfQuitDate);
		jtfQuitDate.setEditable(false);
		
		// [신규] 보유휴가 (y좌표: 330)
		JLabel jlbVacationDays = new JLabel("보유휴가");
		jlbVacationDays.setBounds(25, 330, 90, 35);
		jlbVacationDays.setFont(font);
		add(jlbVacationDays);
		
		jtfVacationDays = new JTextField();
		jtfVacationDays.setBounds(120, 330, 150, 35);
		jtfVacationDays.setFont(font);
		add(jtfVacationDays);
		jtfVacationDays.setEditable(true); // 수정 가능

		// [수정] 버튼류 (y좌표 340 -> 390으로 변경)
		jbtnQuit = new JButton("퇴사처리");
		jbtnQuit.setBounds(30, 390, 120, 50); // y: 340 -> 390
		jbtnQuit.setFont(font);
		jbtnQuit.setBackground(Color.RED);
		jbtnQuit.setForeground(Color.WHITE);
		add(jbtnQuit);

		jbtnSave = new JButton("저장");
		jbtnSave.setBounds(200, 390, 120, 50); // y: 340 -> 390
		jbtnSave.setFont(font);
		jbtnSave.setBackground(new Color(100, 180, 250));
		jbtnSave.setForeground(Color.WHITE);
		add(jbtnSave);

		jbtnCancel = new JButton("취소");
		jbtnCancel.setBounds(370, 390, 120, 50); // y: 340 -> 390
		jbtnCancel.setFont(font);
		jbtnCancel.setBackground(new Color(255, 130, 70));
		jbtnCancel.setForeground(Color.WHITE);
		add(jbtnCancel);

		// [수정] 프레임 높이 변경 (450 -> 500)
		setBounds(200, 200, 550, 500); 
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	// Getter 메서드들
	public int getEmpNo() { return this.empNo; }
	public JTextField getJtfEmpNo() { return jtfEmpNo; }
	public JTextField getJtfName() { return jtfName; }
	public JTextField getJtfTel() { return jtfTel; }
	public JTextField getJtfAddress() { return jtfAddress; }
	public JTextField getJtfSal() { return jtfSal; }
	public JTextField getJtfHireDate() { return jtfHireDate; }
	public JTextField getJtfQuitDate() { return jtfQuitDate; }
	
	// [신규] jtfVacationDays의 Getter 추가
	public JTextField getJtfVacationDays() { 
		return jtfVacationDays; 
	}
	
	public JComboBox<String> getJcbAuth() { return jcbAuth; }
	public JButton getJbtnSave() { return jbtnSave; }
	public JButton getJbtnCancel() { return jbtnCancel; }
	public JButton getJbtnQuit() { return jbtnQuit; }
	
	public JComboBox<DeptDTO> getJcbDept() {
		return jcbDept;
	}
	public DefaultComboBoxModel<DeptDTO> getDcbmDept() {
		return dcbmDept;
	}
	public JComboBox<PositionDTO> getJcbPosition() {
		return jcbPosition;
	}
	public DefaultComboBoxModel<PositionDTO> getDcbmPosition() {
		return dcbmPosition;
	}

	public static void main(String[] args) {
		int testEmpNo = 1001; 
		EmpDetailsInfoDesign edid = new EmpDetailsInfoDesign(testEmpNo);
		new EmpDetailsInfoEvent(edid);
		edid.setVisible(true);
	}
}// class
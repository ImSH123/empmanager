package kr.co.sist.emp.design;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.emp.dto.DeptDTO;
import kr.co.sist.emp.event.VacationMgmEvt;

@SuppressWarnings("serial")
public class VacationMgmDesign extends JFrame {

	private JTextField jtfEmpName;
	private DefaultComboBoxModel<DeptDTO> dcbmDept;
	private DefaultComboBoxModel<String> dcbmStartYear, dcbmStartMonth, dcbmStartDay, dcbmEndYear, dcbmEndMonth, dcbmEndDay;
	private JComboBox<DeptDTO> jcDept;
	private JComboBox<String> jcStartYear, jcStartMonth, jcStartDay, jcEndYear, jcEndMonth, jcEndDay;
	private JButton jbtnSearch;
	private JTable jtVacation;
	private DefaultTableModel dtmVacation;

	public VacationMgmDesign() {
		super("휴가관리");
		setLayout(null);
		
		Font font = new Font("맑은 고딕", Font.PLAIN, 14);

		// 상단 검색 패널
		JLabel jlbDept = new JLabel("부서");
		jlbDept.setBounds(30, 20, 40, 30);
		jlbDept.setFont(font);
		add(jlbDept);
		
		dcbmDept = new DefaultComboBoxModel<>();
		jcDept = new JComboBox<>(dcbmDept);
		jcDept.setBounds(70, 20, 120, 30);
		jcDept.setFont(font);
		add(jcDept);

		JLabel jlbEmpName = new JLabel("사원명");
		jlbEmpName.setBounds(210, 20, 50, 30);
		jlbEmpName.setFont(font);
		add(jlbEmpName);

		jtfEmpName = new JTextField();
		jtfEmpName.setBounds(265, 20, 100, 30);
		jtfEmpName.setFont(font);
		add(jtfEmpName);

		// 날짜 선택
		JLabel jlbStartDate = new JLabel("시작일");
		jlbStartDate.setBounds(30, 70, 50, 30);
		jlbStartDate.setFont(font);
		add(jlbStartDate);

		dcbmStartYear = new DefaultComboBoxModel<>();
		jcStartYear = new JComboBox<>(dcbmStartYear);
		jcStartYear.setBounds(80, 70, 70, 30);
		jcStartYear.setFont(font);
		add(jcStartYear);

		dcbmStartMonth = new DefaultComboBoxModel<>();
		jcStartMonth = new JComboBox<>(dcbmStartMonth);
		jcStartMonth.setBounds(160, 70, 50, 30);
		jcStartMonth.setFont(font);
		add(jcStartMonth);

		dcbmStartDay = new DefaultComboBoxModel<>();
		jcStartDay = new JComboBox<>(dcbmStartDay);
		jcStartDay.setBounds(220, 70, 50, 30);
		jcStartDay.setFont(font);
		add(jcStartDay);

		JLabel jlbEndDate = new JLabel("종료일");
		jlbEndDate.setBounds(290, 70, 50, 30);
		jlbEndDate.setFont(font);
		add(jlbEndDate);

		dcbmEndYear = new DefaultComboBoxModel<>();
		jcEndYear = new JComboBox<>(dcbmEndYear);
		jcEndYear.setBounds(340, 70, 70, 30);
		jcEndYear.setFont(font);
		add(jcEndYear);

		dcbmEndMonth = new DefaultComboBoxModel<>();
		jcEndMonth = new JComboBox<>(dcbmEndMonth);
		jcEndMonth.setBounds(420, 70, 50, 30);
		jcEndMonth.setFont(font);
		add(jcEndMonth);

		dcbmEndDay = new DefaultComboBoxModel<>();
		jcEndDay = new JComboBox<>(dcbmEndDay);
		jcEndDay.setBounds(480, 70, 50, 30);
		jcEndDay.setFont(font);
		add(jcEndDay);
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		for(int i = currentYear - 5; i < currentYear + 5; i++) {
			dcbmStartYear.addElement(String.valueOf(i));
			dcbmEndYear.addElement(String.valueOf(i));
		}
		for(int i = 1; i <= 12; i++) {
			dcbmStartMonth.addElement(String.format("%02d", i));
			dcbmEndMonth.addElement(String.format("%02d", i));
		}
		for(int i = 1; i <= 31; i++) {
			dcbmStartDay.addElement(String.format("%02d", i));
			dcbmEndDay.addElement(String.format("%02d", i));
		}
		jcStartYear.setSelectedItem(String.valueOf(currentYear));
		jcEndYear.setSelectedItem(String.valueOf(currentYear));

		jbtnSearch = new JButton("조회");
		jbtnSearch.setBounds(550, 70, 70, 30);
		jbtnSearch.setFont(font);
		add(jbtnSearch);
		
		String[] columnNames = {"신청ID", "사원번호", "사원명", "부서", "직급", "휴가유형", "사유", "시작일", "종료일", "승인상태"};
		dtmVacation = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtVacation = new JTable(dtmVacation);
		JScrollPane jsp = new JScrollPane(jtVacation);
		jsp.setBounds(30, 120, 590, 400);
		add(jsp);
		
		jtVacation.getColumn("승인상태").setCellRenderer(new StatusColumnCellRenderer());
		
		// JTable 컬럼 너비 설정
		jtVacation.getColumn("신청ID").setMinWidth(0);
		jtVacation.getColumn("신청ID").setMaxWidth(0);
		jtVacation.getColumn("신청ID").setWidth(0);
		jtVacation.getColumn("사원번호").setPreferredWidth(60);
		jtVacation.getColumn("사원명").setPreferredWidth(70);
		jtVacation.getColumn("부서").setPreferredWidth(80);
		jtVacation.getColumn("직급").setPreferredWidth(60);
		jtVacation.getColumn("휴가유형").setPreferredWidth(70);
		jtVacation.getColumn("사유").setPreferredWidth(150);
		jtVacation.getColumn("시작일").setPreferredWidth(100);
		jtVacation.getColumn("종료일").setPreferredWidth(100);
		jtVacation.getColumn("승인상태").setPreferredWidth(70);

		jtVacation.setFont(font);
		jtVacation.getTableHeader().setFont(font);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 1; i < jtVacation.getColumnCount(); i++) {
			jtVacation.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		jtVacation.getColumn("승인상태").setCellRenderer(new StatusColumnCellRenderer());


		setBounds(100, 100, 670, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
	}

	// Getters
	public JTextField getJtfEmpName() { return jtfEmpName; }
	public DefaultComboBoxModel<DeptDTO> getDcbmDept() { return dcbmDept; }
	public JComboBox<DeptDTO> getJcDept() { return jcDept; }
	public JComboBox<String> getJcStartYear() { return jcStartYear; }
	public JComboBox<String> getJcStartMonth() { return jcStartMonth; }
	public JComboBox<String> getJcStartDay() { return jcStartDay; }
	public JComboBox<String> getJcEndYear() { return jcEndYear; }
	public JComboBox<String> getJcEndMonth() { return jcEndMonth; }
	public JComboBox<String> getJcEndDay() { return jcEndDay; }
	public JButton getJbtnSearch() { return jbtnSearch; }
	public JTable getJtVacation() { return jtVacation; }
	public DefaultTableModel getDtmVacation() { return dtmVacation; }
	
	public static void main(String[] args) {
		VacationMgmDesign vmd = new VacationMgmDesign();
		new VacationMgmEvt(vmd);
		vmd.setVisible(true);
	}
	
	/**
	 * [수정] "승인상태" 컬럼 렌더링을 위한 내부 클래스.
	 * 값에 따라 배경색을 변경합니다.
	 */
	private class StatusColumnCellRenderer extends DefaultTableCellRenderer {
		
		// [신규] 가독성을 위해 연한(Pastel) 배경색 정의
		private final Color COLOR_GREEN = new Color(204, 255, 204);
		private final Color COLOR_RED = new Color(255, 204, 204);
		private final Color COLOR_YELLOW = new Color(255, 255, 204); // (요청/보류)

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, 
		                                             boolean isSelected, boolean hasFocus, int row, int column) {
			
			// 1. 부모 클래스의 메서드를 먼저 호출 (선택, 포커스 등 기본 스타일 적용)
			//    이 시점에 isSelected == true라면 c의 배경색은 이미 '선택색'(파란색)으로 설정됨
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			String status = (value == null) ? "" : value.toString();

			// 2. 텍스트 설정 ("보류" -> "요청")
			if ("보류".equals(status)) {
				setText("요청");
			} else {
				setText(status);
			}

			// 3. [수정] 셀이 '선택되지 않았을 때'만 배경색을 커스텀하게 변경
			if (!isSelected) {
				// 글씨색은 기본값(검정)을 사용
				c.setForeground(Color.BLACK); 
				
				if ("승인".equals(status)) {
					c.setBackground(COLOR_GREEN);
				} else if ("반려".equals(status)) {
					c.setBackground(COLOR_RED);
				} else if ("보류".equals(status)) {
					c.setBackground(COLOR_YELLOW);
				} else {
					// 그 외 (빈 값 등)는 JTable 기본 배경색 사용
					c.setBackground(table.getBackground());
				}
			}
			// (셀이 선택된(isSelected == true) 경우에는 1번 단계에서 설정된
			//  JTable의 기본 선택 배경색(파란색)이 그대로 유지됨)
			
			// 4. 가운데 정렬
			setHorizontalAlignment(SwingConstants.CENTER);
			
			return c; // 스타일이 적용된 컴포넌트(보통 JLabel)를 반환
		}
	}
}
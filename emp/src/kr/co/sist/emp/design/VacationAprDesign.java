package kr.co.sist.emp.design;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class VacationAprDesign extends JDialog {

	private JRadioButton jrbConfirm;
	private JRadioButton jrbDeny;
	private JRadioButton jrbSuspend;
	private JButton jbtnConfirm;
	private JButton jbtnCancel;

	public VacationAprDesign(JFrame parent, String currentStatus) {
		super(parent, "휴가승인", true);
		setLayout(null);
		
		Font font = new Font("맑은 고딕", Font.PLAIN, 14);

		JLabel jlbTitle = new JLabel("휴가를 승인하시겠습니까?");
		jlbTitle.setBounds(30, 20, 250, 30);
		jlbTitle.setFont(font);
		add(jlbTitle);

		jrbConfirm = new JRadioButton("승인");
		jrbConfirm.setBounds(30, 60, 60, 30);
		jrbConfirm.setFont(font);
		add(jrbConfirm);

		jrbDeny = new JRadioButton("반려");
		jrbDeny.setBounds(100, 60, 60, 30);
		jrbDeny.setFont(font);
		add(jrbDeny);

		jrbSuspend = new JRadioButton("보류");
		jrbSuspend.setBounds(170, 60, 60, 30);
		jrbSuspend.setFont(font);
		add(jrbSuspend);

		ButtonGroup bg = new ButtonGroup();
		bg.add(jrbConfirm);
		bg.add(jrbDeny);
		bg.add(jrbSuspend);
		
		if("승인".equals(currentStatus)) {
			jrbConfirm.setSelected(true);
		} else if ("반려".equals(currentStatus)) {
			jrbDeny.setSelected(true);
		} else {
			jrbSuspend.setSelected(true);
		}

		jbtnConfirm = new JButton("확인");
		jbtnConfirm.setBounds(30, 110, 80, 30);
		jbtnConfirm.setFont(font);
		add(jbtnConfirm);

		jbtnCancel = new JButton("취소");
		jbtnCancel.setBounds(120, 110, 80, 30);
		jbtnCancel.setFont(font);
		add(jbtnCancel);
		
		setBounds(parent.getX() + 200, parent.getY() + 200, 270, 200);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// setVisible(true)는 Event 클래스에서 호출
	}

	// Getters
	public JRadioButton getJrbConfirm() {
		return jrbConfirm;
	}
	public JRadioButton getJrbDeny() {
		return jrbDeny;
	}
	public JRadioButton getJrbSuspend() {
		return jrbSuspend;
	}
	public JButton getJbtnConfirm() {
		return jbtnConfirm;
	}
	public JButton getJbtnCancel() {
		return jbtnCancel;
	}
}
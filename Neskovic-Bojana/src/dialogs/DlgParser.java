package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import strategy.LogFile;

public class DlgParser extends JDialog{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel;
	private JScrollPane scrollPane;
	private JList<String> activityLog;
	private DefaultListModel<String> log;
	private LogFile fileLog;
	
	public static void main(String[] args) {
		try {
			DlgParser dialog = new DlgParser();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgParser() {
		setBounds(100, 100, 600, 400);
		setModal(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setTitle("Log parser");
		getContentPane().setLayout(new BorderLayout());
		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
		}
		{
			activityLog = new JList<String>();
			log = new DefaultListModel<>();
			activityLog.setModel(log);
			activityLog.setVisibleRowCount(20);
			activityLog.setEnabled(false);
			activityLog.setBackground(Color.WHITE);
			activityLog.setFont(new Font("Lucida Console", Font.BOLD, 12));
			scrollPane.setViewportView(activityLog);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Execute");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent click) {
						if (fileLog != null) fileLog.readLine(log.getElementAt(log.size() - 1));
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent click) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	

	public void addCommand(String command) {
		log.addElement(command);
	}


	public void closeDialog() {
		dispose();
	}
	
	public void setFileLog(LogFile fileLog) {
		this.fileLog = fileLog;
	}

}
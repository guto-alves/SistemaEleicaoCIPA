package view;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;

public class RecordingVotePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JPanel recordingPanel;
	private JPanel endPanel;

	private JProgressBar progressBar;
	private int progress;

	Timer timer = new Timer(25, this);

	private JFrame frame;
	private Component verticalStrut;

	public RecordingVotePanel(JFrame frame) {
		this.frame = frame;
		setLayout(new BorderLayout(0, 0));

		recordingPanel = new JPanel();
		recordingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		recordingPanel.setBackground(Color.WHITE);
		add(recordingPanel, BorderLayout.CENTER);
		recordingPanel.setLayout(new BoxLayout(recordingPanel, BoxLayout.Y_AXIS));

		verticalStrut = Box.createVerticalStrut(190);
		recordingPanel.add(verticalStrut);
		progressBar = new JProgressBar();
		recordingPanel.add(progressBar);
		progressBar.setStringPainted(true);

		JLabel lblGravando = new JLabel("Gravando");
		lblGravando.setBorder(new EmptyBorder(10, 0, 0, 0));
		recordingPanel.add(lblGravando);
		lblGravando.setAlignmentX(0.5f);
		lblGravando.setHorizontalAlignment(SwingConstants.CENTER);
		lblGravando.setFont(new Font("Arial Black", Font.BOLD, 16));

		endPanel = new JPanel();
		endPanel.setBackground(Color.WHITE);
		endPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblFim = new JLabel("FIM");
		lblFim.setFont(new Font("Tahoma", Font.BOLD, 64));
		lblFim.setHorizontalAlignment(SwingConstants.CENTER);
		endPanel.add(lblFim);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_1.setBackground(Color.WHITE);
		endPanel.add(panel_1, BorderLayout.SOUTH);

		JLabel lblNewLabel_1 = new JLabel("VOTOU");
		lblNewLabel_1.setBackground(Color.BLACK);
		lblNewLabel_1.setForeground(Color.GRAY);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_1.add(lblNewLabel_1);

		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (++progress == 100) {
			timer.stop();
			recordingPanel.setVisible(false);
			remove(recordingPanel);
			add(endPanel);
			repaint();
			revalidate();

			new Thread() {
				public void run() {
					try {
						Thread.sleep(1200);
					} catch (InterruptedException e) {
						Thread.interrupted();
						e.printStackTrace();
					}
					MainFrame.changeTo(frame, RecordingVotePanel.this, new VotingPanel(frame));
				};
			}.start();
		} else
			progressBar.setValue(progress);
	}

}

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class About_us {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About_us window = new About_us();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public About_us() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("About the Devlopers");
		frame.getContentPane().setBackground(SystemColor.controlDkShadow);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEasyLibrarySolutions = new JLabel("EASY LIBRARY SOLUTIONS");
		lblEasyLibrarySolutions.setForeground(Color.CYAN);
		lblEasyLibrarySolutions.setHorizontalAlignment(SwingConstants.CENTER);
		lblEasyLibrarySolutions.setFont(new Font("Likhan", Font.BOLD, 18));
		lblEasyLibrarySolutions.setBounds(395, 40, 579, 58);
		frame.getContentPane().add(lblEasyLibrarySolutions);
		
		JLabel lblProjectThirdYear = new JLabel("Project Third Year Computer Science");
		lblProjectThirdYear.setFont(new Font("Likhan", Font.BOLD, 18));
		lblProjectThirdYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblProjectThirdYear.setForeground(Color.WHITE);
		lblProjectThirdYear.setBounds(467, 110, 436, 16);
		frame.getContentPane().add(lblProjectThirdYear);
		
		JLabel lblSahakarMaharshiBhausaheb = new JLabel("Sahakar Maharshi Bhausaheb Santuji Thorat College of Arts, Science and Commerce Sangamner");
		lblSahakarMaharshiBhausaheb.setForeground(Color.LIGHT_GRAY);
		lblSahakarMaharshiBhausaheb.setFont(new Font("Likhan", Font.BOLD, 18));
		lblSahakarMaharshiBhausaheb.setBounds(284, 138, 801, 16);
		frame.getContentPane().add(lblSahakarMaharshiBhausaheb);
		
		JLabel lblNewLabel = new JLabel("Design & Develop By");
		lblNewLabel.setFont(new Font("Likhan", Font.BOLD, 18));
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setBounds(190, 233, 201, 35);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblMangeshBhausahebKanawade = new JLabel("Mangesh Bhausaheb Kanawade");
		lblMangeshBhausahebKanawade.setFont(new Font("Likhan", Font.BOLD, 18));
		lblMangeshBhausahebKanawade.setForeground(Color.GREEN);
		lblMangeshBhausahebKanawade.setBounds(361, 292, 434, 29);
		frame.getContentPane().add(lblMangeshBhausahebKanawade);
		
		JLabel lblThirdYearComputer = new JLabel("Third Year Computer Science");
		lblThirdYearComputer.setFont(new Font("Likhan", Font.BOLD, 18));
		lblThirdYearComputer.setForeground(Color.WHITE);
		lblThirdYearComputer.setBounds(361, 321, 434, 29);
		frame.getContentPane().add(lblThirdYearComputer);
		
		JLabel lblTusharBajiraoKawade = new JLabel("Tushar Bajirao Kawade");
		lblTusharBajiraoKawade.setForeground(Color.GREEN);
		lblTusharBajiraoKawade.setFont(new Font("Likhan", Font.BOLD, 18));
		lblTusharBajiraoKawade.setBounds(837, 292, 410, 29);
		frame.getContentPane().add(lblTusharBajiraoKawade);
		
		JLabel label = new JLabel("Third Year Computer Science");
		label.setFont(new Font("Likhan", Font.BOLD, 18));
		label.setForeground(Color.WHITE);
		label.setBounds(837, 323, 410, 25);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setFont(new Font("Likhan", Font.PLAIN, 18));
		label_1.setBounds(284, 406, 107, 35);
		frame.getContentPane().add(label_1);
		
		JLabel lblMkanawadegmailcom = new JLabel("mkanawade84@gmail.com");
		lblMkanawadegmailcom.setFont(new Font("Likhan", Font.BOLD, 18));
		lblMkanawadegmailcom.setForeground(Color.CYAN);
		lblMkanawadegmailcom.setBounds(361, 349, 434, 29);
		frame.getContentPane().add(lblMkanawadegmailcom);
		
		JLabel lblTusharkawadegmailcom = new JLabel("tusharkawade65@gmail.com");
		lblTusharkawadegmailcom.setFont(new Font("Likhan", Font.BOLD, 18));
		lblTusharkawadegmailcom.setForeground(Color.CYAN);
		lblTusharkawadegmailcom.setBounds(837, 346, 410, 35);
		frame.getContentPane().add(lblTusharkawadegmailcom);
		
		JLabel lblHttpswwwlinkdincomintusharkawadeaa = new JLabel("https://www.linkdin.com/in/tushar-kawade-a14a01184");
		lblHttpswwwlinkdincomintusharkawadeaa.setFont(new Font("Likhan", Font.BOLD, 18));
		lblHttpswwwlinkdincomintusharkawadeaa.setForeground(Color.WHITE);
		lblHttpswwwlinkdincomintusharkawadeaa.setBounds(837, 380, 410, 29);
		frame.getContentPane().add(lblHttpswwwlinkdincomintusharkawadeaa);
		
		JLabel lblHttpswwwlinkdincominmangeshkanawadeb = new JLabel("https://www.linkdin.com/in/mangesh-kanawade-87214b171");
		lblHttpswwwlinkdincominmangeshkanawadeb.setFont(new Font("Likhan", Font.BOLD, 18));
		lblHttpswwwlinkdincominmangeshkanawadeb.setForeground(Color.WHITE);
		lblHttpswwwlinkdincominmangeshkanawadeb.setBounds(361, 380, 434, 29);
		frame.getContentPane().add(lblHttpswwwlinkdincominmangeshkanawadeb);
		frame.setBounds(0, 0, 1365,730);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View {

	JLabel _labelResult;
	public JTextField _nb;
	private JFrame _frame;
	private Container _conteneur;
	public JButton _bouton;
	private JPanel _panelNord;
	private JPanel _panelSud;

	public View() {

		_frame = new JFrame();
		_conteneur = _frame.getContentPane();
		_panelNord = new JPanel();
		_bouton = new JButton("Tester");
		_nb = new JTextField();
		_nb.setColumns(10);
		_nb.setMargin(new Insets(5, 5, 5, 5));
		
		_bouton.setActionCommand("bouton");
//		System.out.println(_bouton.getActionCommand());
		_panelNord.add(_nb);
		_panelNord.add(_bouton);

		_panelSud = new JPanel();
		_labelResult = new JLabel("");
		_panelSud.add(_labelResult);

		_conteneur.add(_panelNord, BorderLayout.NORTH);
		_conteneur.add(_panelSud, BorderLayout.SOUTH);

		_frame.setSize(250, 100);
		_frame.setVisible(true);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setLocationRelativeTo(null);

	}
}

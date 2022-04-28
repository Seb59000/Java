import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fenetre implements ActionListener {

	private JFrame _frame;
	private Container _conteneur;
	private JLabel _labelN;
	private JLabel _labelC;
	private JButton _bouton;
	private JPanel _panelNord;
	private JPanel _panelCentre;
	
	public Fenetre() {
		_frame = new JFrame();
		_conteneur = _frame.getContentPane();
		_panelNord = new JPanel();
		_panelNord.setLayout(new FlowLayout());
		_panelCentre = new JPanel();
		_panelCentre.setLayout(new FlowLayout());
		_labelN = new JLabel("Un message nord");
		_panelNord.add(_labelN);
		_labelC = new JLabel("Un message centre");
		_bouton = new JButton("Click");
		_bouton.addActionListener(this);
		_panelCentre.add(_bouton);
		
		_conteneur.add(_panelNord, BorderLayout.NORTH);
		_conteneur.add(_panelCentre, BorderLayout.CENTER);
		
		_frame.setSize(800, 600);
		_frame.setVisible(true);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("youpi j'ai cliqué");
		
	}
}

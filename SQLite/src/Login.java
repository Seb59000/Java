import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

public class Login {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	private JTable table;
	private JButton btnAjouter;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnSupprimer;

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection = SQLiteConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 318, 379);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		table = new JTable();

		JButton btnNewButton = new JButton("Afficher la table des employ\u00E9s");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "select * from employeeInfo";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));

				} catch (Exception e) {
				}
			}
		});

		btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "insert into employeeInfo  (name,password) values (?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textField.getText());
					pst.setString(2, textField_1.getText());

					pst.execute();
					
					JOptionPane.showConfirmDialog(null, "sauvegarde effectuée.");
				} catch (Exception e) {
					JOptionPane.showConfirmDialog(null, e);
				}

			}
		});

		JLabel lblNom = new JLabel("nom");

		JLabel lblMotDePasse = new JLabel("Mot de passe");

		textField = new JTextField();
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "delete from employeeInfo  where name=? and password=?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textField.getText());
					pst.setString(2, textField_1.getText());

					pst.execute();
					
					JOptionPane.showConfirmDialog(null, "Mise à jour effectuée.");
				} catch (Exception e) {
					JOptionPane.showConfirmDialog(null, e);
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnNewButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(table, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(45)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(btnSupprimer, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnAjouter, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblMotDePasse)
										.addComponent(lblNom))
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(textField, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
										.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))))))
					.addGap(360))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNom)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMotDePasse))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAjouter)
						.addComponent(btnSupprimer))
					.addGap(18)
					.addComponent(table, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(btnNewButton)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}

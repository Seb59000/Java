import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{
	private Model m;
	private View v;
	
	Controller(Model _m, View _v){
		
		this.m=_m;
		this.v=_v;
//		this.actionPerformed(null);
		listen();
		
	}
	
	private void listen() {
		v._bouton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		System.out.println("coucou");
//		System.out.println(e.getActionCommand());
		System.out.println(v._nb.getText());
//		if (e.getActionCommand().equals("bouton")) {
//			int nb = Integer.parseInt(v._nb.getText());
//			if (m.prime(nb)==true) {
//				 v._labelResult.setText("Nombre premier détecté.");
//			} else {
//				 v._labelResult.setText("Nombre premier non détecté.");
//			}
//		}	
	}
}

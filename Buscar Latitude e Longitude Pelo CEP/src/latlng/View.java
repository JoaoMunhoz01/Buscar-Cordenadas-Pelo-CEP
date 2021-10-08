package latlng;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class View {

	private JFrame jFrame;

	public View() {

	}

	public void initialInterface() {
		jFrame = new JFrame();
		jFrame.setTitle("Latitude e Longitude por CEP");
		JPanel jPanel = new JPanel();
		JLabel jLabel = new JLabel("Olá, Me informe o seu CEP para consulta!");
		JButton jButton = new JButton("OK");
		ActionListener action = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					buscarCEPWindow();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		};
		jButton.addActionListener(action);
		jPanel.add(jLabel);
		jPanel.add(jButton);
		jFrame.setLocationRelativeTo(null);
		jFrame.setSize(300, 100);
		jFrame.setContentPane(jPanel);
		jFrame.setVisible(true);

	}

	private void buscarCEPWindow() throws Exception {
		double[] array = { 0, 0 };

		String cep = JOptionPane.showInputDialog(jFrame, "Informe o seu CEP", null, JOptionPane.QUESTION_MESSAGE);
		String key = JOptionPane.showInputDialog(jFrame, "Informe a Sua Key", null, JOptionPane.QUESTION_MESSAGE);
		;

		if (cep == null || key == null) {
			cep = "";
			key = "";
			JOptionPane.showMessageDialog(jFrame, "Houve um Erro", null, JOptionPane.INFORMATION_MESSAGE);
		} else {
			URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + cep + "&key=" + key);

			Functions functions = new Functions(cep, url);
			array = functions.printLatLng(functions.isValid(url), url);
			if (array[0] == 0.0 && array[1] == 00) {
				JOptionPane.showMessageDialog(jFrame, "Houve um Erro", null, JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(jFrame,
						"A sua Latitude é: " + array[0] + " e a sua Longitude é: " + array[1], null,
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
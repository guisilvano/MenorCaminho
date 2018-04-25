import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class GrafoWindow extends JFrame{

	private JLabel lblOrigem, lblDestino, lblKm, lblCodigoOrigem, lblCodigoDestino;
	private JTextField txfOrigem, txfDestino, txfKm, txfCodigoOrigem, txfCodigoDestino;
	private JButton btnSalvar, btnLimpar, btnProcessar, btnPlus, btnMinus;

	private JTable tblDados;
	private static DefaultTableModel tblModel;
	private JScrollPane spn;

	public GrafoWindow() {

		setSize(500,500);
		setLayout(null);
		setResizable(false);
		setTitle("Pesquisa por menor caminho");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		ComponentesCriar();

	}

	//Abre o arquivo com os dados
	private static void abrirArquivo() {

		try {
			InputStream is = new FileInputStream(System.getProperty("user.home")
					+System.getProperty("file.separator")+"tabela.csv");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String linha;
			
			while ((linha = br.readLine()) != null) {				
				String s[] = linha.split(",");			
				tblModel.addRow(s);	
			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();

		} 
	}

	//Salva o arquivo com os dados
	private static void salvarArquivo() {
		String savePath = JOptionPane.showInputDialog("Salvar como...", System.getProperty("user.home")
				+System.getProperty("file.separator")+"tabela.csv");

		OutputStream os;
		try {
			os = new FileOutputStream(savePath);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			String modelString = "";

			for (int i = 0; i < tblModel.getRowCount(); i++) {
				for (int j = 0; j < tblModel.getColumnCount(); j++) {
					modelString = modelString.concat(tblModel.getValueAt(i, j)+",");

				}
				modelString = modelString.concat("\n");
			}
			//System.out.println(modelString);

			bw.write(modelString);
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	//Percorre toda tblDados, checa o numero de destinos e gera o grafo usando Dijkstra.inserirAresta
	private static void menorCaminho() {
		
		int numDestinos = 0;
		
		//Checa número de destinos disponíveis no arquivo
		for (int i = 0; i < tblModel.getRowCount(); i++) {
			if (Integer.parseInt((String) tblModel.getValueAt(i, 0)) > numDestinos) {
				numDestinos = Integer.parseInt((String) tblModel.getValueAt(i, 0));
			}
			
			if (Integer.parseInt((String) tblModel.getValueAt(i, 2)) > numDestinos) {
				numDestinos = Integer.parseInt((String) tblModel.getValueAt(i, 2));
			}
		}
		
		try {
			Dijkstra dij = new Dijkstra(numDestinos);
			
			System.out.println(numDestinos);
			
			//Popula a matriz de destinos e calcula a rota mais próxima
			for (int i = 0; i < tblModel.getRowCount(); i++) {
				
				int codOrigem = Integer.parseInt((String) tblModel.getValueAt(i, 0)) - 1;
				int codDestino = Integer.parseInt((String) tblModel.getValueAt(i, 2)) - 1;
				int distancia = Integer.parseInt((String) tblModel.getValueAt(i, 4));

				dij.inserirAresta(i, i, 0);
				dij.inserirAresta(codOrigem, codDestino, distancia);
				dij.inserirAresta(codDestino, codOrigem, distancia);
				
				System.out.print("\n");
			}
			
			dij.menorCaminhoEncontrar(0, 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
	//Cria os componentes e os posiciona na janela
	private void ComponentesCriar() {

		//ORIGEM
		lblOrigem = new JLabel("Origem: ");
		lblOrigem.setBounds(10, 10, 100, 25);
		getContentPane().add(lblOrigem);

		txfOrigem = new JTextField();
		txfOrigem.setBounds(80, 10, 100, 25);
		getContentPane().add(txfOrigem);

		lblCodigoOrigem = new JLabel("Cod. origem: ");
		lblCodigoOrigem.setBounds(200, 10, 100, 25);
		getContentPane().add(lblCodigoOrigem);

		txfCodigoOrigem = new JTextField();
		txfCodigoOrigem.setBounds(290, 10, 100, 25);
		getContentPane().add(txfCodigoOrigem);
		//FIM ORIGEM

		//DESTINO
		lblDestino = new JLabel("Destino: ");
		lblDestino.setBounds(10, 35, 100, 25);
		getContentPane().add(lblDestino);

		txfDestino = new JTextField();
		txfDestino.setBounds(80, 35, 100, 25);
		getContentPane().add(txfDestino);

		txfCodigoDestino = new JTextField();
		txfCodigoDestino.setBounds(290, 35, 100, 25);
		getContentPane().add(txfCodigoDestino);

		lblCodigoDestino = new JLabel("Cod destino: ");
		lblCodigoDestino.setBounds(200, 35, 100, 25);
		getContentPane().add(lblCodigoDestino);
		//FIM DESTINO

		//KM
		lblKm = new JLabel("Km: ");
		lblKm.setBounds(10, 60, 100, 25);
		getContentPane().add(lblKm);

		txfKm = new JTextField();
		txfKm.setBounds(80, 60, 100, 25);
		getContentPane().add(txfKm);
		//FIM KM

		//BOTÕES
		btnSalvar = new JButton();
		btnSalvar.setAction(new AbstractAction("Salvar") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				salvarArquivo();

			}
		});
		btnSalvar.setBounds(10, 445, 100, 25);
		getContentPane().add(btnSalvar);

		btnProcessar = new JButton("Processar");
		btnProcessar.setBounds(101, 445, 140, 25);
		getContentPane().add(btnProcessar);

		btnLimpar = new JButton();
		btnLimpar.setAction(new AbstractAction("Limpar") {		

			@Override
			public void actionPerformed(ActionEvent e) {
				while (tblModel.getRowCount() > 0) {
					tblModel.removeRow(0);
				}
			}
		});
		btnLimpar.setBounds(390, 445, 100, 25);
		getContentPane().add(btnLimpar);

		btnPlus = new JButton();
		btnPlus.setAction(new AbstractAction("+") {		

			@Override
			public void actionPerformed(ActionEvent e) {
				tblModel.addRow(new Object[] {txfCodigoOrigem.getText(), txfOrigem.getText(),
						txfCodigoDestino.getText(), txfDestino.getText(), txfKm.getText()});
			}
		});
		btnPlus.setBounds(290, 65, 50, 20);
		getContentPane().add(btnPlus);

		btnMinus = new JButton();
		btnMinus.setAction(new AbstractAction("-") {			

			@Override
			public void actionPerformed(ActionEvent e) {
				tblModel.removeRow(tblDados.getSelectedRow());
			}
		});
		btnMinus.setBounds(340, 65, 50, 20);
		getContentPane().add(btnMinus);
		//FIM BOTÕES

		//TABELA
		tblModel = new DefaultTableModel();
		tblModel.addColumn("Cod. origem");
		tblModel.addColumn("Origem");
		tblModel.addColumn("Cod. destino");
		tblModel.addColumn("Destino");
		tblModel.addColumn("Km");		
		tblDados = new JTable(tblModel);

		spn = new JScrollPane(tblDados);
		spn.setBounds(10, 100, 480, 330);
		getContentPane().add(spn);
		//FIM TABELA
	}

	public static void main(String[] args) {

		new GrafoWindow().setVisible(true);
		abrirArquivo();
	
		menorCaminho();

	}


}

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlanoDeVooApp {
    // Update the field declarations
    private static JTextField identaeronaveField;
    private static JCheckBox[] regrasField;
    @SuppressWarnings("rawtypes")
    private static JComboBox tipoDeVooField;
    private static JTextField numaeronaveField;
    private static JTextField tipoaeronaveField;
    @SuppressWarnings("rawtypes")
    private static JComboBox catetField;
    @SuppressWarnings("rawtypes")
    private static JComboBox equipamentoField;
    @SuppressWarnings("rawtypes")
    private static JComboBox equipamentoVField;
    private static JTextField aerodromoptField;
    private static JTextField horaeobtField;
    private static JTextField velocidadeField;
    private static JTextField nivelDeVooField;
    private static JTextField rotaField;
    private static JTextField aerodromodestinoField;
    private static JTextField duracaototalvooField;
    private static JTextField aerodromoalternativaField;
    private static JTextArea observacoesField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlanoDeVooApp::criarJanelaInicial);
    }

    
    private static void criarJanelaInicial() {
        JFrame frame = new JFrame("Envio de Plano de Voo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Envio de Plano de Voo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(17, 2, 10, 10));

        identaeronaveField = criarCampoFormulario(formPanel, "Identificação da Aeronave:*");
        regrasField = criarCampoFormulariobox(formPanel, "Regras:*", new String[] {"I", "V", "Y", "Z"});
        tipoDeVooField = criarCampoFormulariocombobox(formPanel, "Tipo de Voo (VFR/IFR):*", new String[] {"G", "S", "N", "M", "X"});
        numaeronaveField = criarCampoFormulario(formPanel, "Numero da Aeronave:*");
        tipoaeronaveField = criarCampoFormulario(formPanel, "Tipo de Aeronave:*");
        catetField = criarCampoFormulariocombobox(formPanel, "Categoria da Esteira de Turbulência:*", new String[] {"L", "M", "H", "J"});
        equipamentoField = criarCampoFormulariocombobox(formPanel, "Equipamento:*", new String[] {"N", "S", "A", "B", "C", "D", "E1", "E2", "E3", "F", "G", "H", "I"});
        equipamentoVField = criarCampoFormulariocombobox(formPanel, "Equipamento de Vigilância:*", new String[] {"N", "S", "A", "B", "C", "D", "E1", "E2", "E3", "F", "G", "H", "I"});
        aerodromoptField = criarCampoFormulario(formPanel, "Aeródromo de Partida:*");
        horaeobtField = criarCampoFormulario(formPanel, "Hora EOBT:*");
        velocidadeField = criarCampoFormulario(formPanel, "Velocidade de Cruzeiro:*");
        nivelDeVooField = criarCampoFormulario(formPanel, "Nivel de Cruzeiro:*");
        rotaField = criarCampoFormulario(formPanel, "Rota:*");
        aerodromodestinoField = criarCampoFormulario(formPanel, "Aeródromo de Destino:*");
        duracaototalvooField = criarCampoFormulario(formPanel, "Duração Prevista do Voo:*");
        aerodromoalternativaField = criarCampoFormulario(formPanel, "Aeródromo de Alternativa:*");
        observacoesField = new JTextArea();
        observacoesField.setFont(new Font("Verdana", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(observacoesField);
        formPanel.add(new JLabel("Observações:", SwingConstants.RIGHT));
        formPanel.add(scrollPane);


        mainPanel.add(formPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton enviarButton = new JButton("Enviar");
        enviarButton.setFont(new Font("Verdana", Font.BOLD, 14));
        enviarButton.setBackground(new Color(34, 139, 34));
        enviarButton.setForeground(Color.WHITE);
        enviarButton.addActionListener(e -> enviarPlano());
        buttonPanel.add(enviarButton);

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.setFont(new Font("Verdana", Font.BOLD, 14));
        cancelarButton.setBackground(new Color(178, 34, 34));
        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.addActionListener(e -> frame.dispose());
        buttonPanel.add(cancelarButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private static JCheckBox[] criarCampoFormulariobox(JPanel painel, String texto, String[] opcoes) {
        JLabel label = new JLabel(texto, SwingConstants.RIGHT);
        label.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(label);
    
        JPanel checkBoxPanel = new JPanel(new GridLayout(1, 0)); // vertical layout
        JCheckBox[] checkBoxes = new JCheckBox[opcoes.length];
        for (int i = 0; i < opcoes.length; i++) {
            checkBoxes[i] = new JCheckBox(opcoes[i]);
            checkBoxes[i].setFont(new Font("Verdana", Font.PLAIN, 14));
            checkBoxPanel.add(checkBoxes[i]);
        }
        painel.add(checkBoxPanel);
    
        return checkBoxes;
    }
    private static JTextField criarCampoFormulario(JPanel painel, String texto) {
        JLabel label = new JLabel(texto, SwingConstants.RIGHT);
        label.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(label);

        JTextField textField = new JTextField();
        textField.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(textField);

        return textField;
    }
    private static JComboBox<String> criarCampoFormulariocombobox(JPanel painel, String texto, String[] opcoes) {
        JLabel label = new JLabel(texto, SwingConstants.RIGHT);
        label.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(label);
    
        String[] opcoesComVazio = new String[opcoes.length + 1];
        opcoesComVazio[0] = ""; // add an empty string as the first element
        System.arraycopy(opcoes, 0, opcoesComVazio, 1, opcoes.length);
    
        JComboBox<String> comboBox = new JComboBox<>(opcoesComVazio);
        comboBox.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(comboBox);
    
        return comboBox;
    }

    private static boolean validarEntrada(String texto, String tipo) {
        switch (tipo) {
            case "letras":
                return texto.matches("[a-zA-Z]+");
            case "numeros":
                return texto.matches("\\d+");
            case "horario":
                return texto.matches("\\d{2}\\d{2}");
            default:
                return true;
        }
    }

    private static boolean validarFormulario() {
        if ((identaeronaveField.getText().isEmpty())) {
            mostrarErroValidacao("Identificação de Aeronave deve ser preenchido!");
            return false;
        }
        if (regrasField == null || regrasField.length == 0) {
            mostrarErroValidacao("Regras deve ser marcado!");
            return false;
        }
        boolean regraSelecionada = false;
        for (JCheckBox checkBox : regrasField) {
            if (checkBox.isSelected()) {
                regraSelecionada = true;
                break;
            }
        }
        if (!regraSelecionada) {
            mostrarErroValidacao("Regras deve ser marcado!");
            return false;
        }
        if (tipoDeVooField.getSelectedIndex() == 0) {
            mostrarErroValidacao("Tipo de Voo deve ser selecionado!");
            return false;
        }
        if ((numaeronaveField.getText().isEmpty())) {
            mostrarErroValidacao("Número de Aeronave deve ser preenchido!");
            return false;
        }
        if (!validarEntrada(numaeronaveField.getText(), "numeros")) {
            mostrarErroValidacao("Número de Aeronave deve conter apenas numeros.");
            return false;
        }
        if ((tipoaeronaveField.getText().isEmpty())) {
            mostrarErroValidacao("Número de Aeronave deve ser preenchido!");
            return false;
        }
        if (catetField.getSelectedIndex() == 0) {
            mostrarErroValidacao("Categoria de Esteira de Turbulência deve ser selecionado!");
            return false;
        }
        if (equipamentoField.getSelectedIndex() == 0) {
            mostrarErroValidacao("Equipamento deve ser selecionado!");
            return false;
        }
        if (equipamentoVField.getSelectedIndex() == 0) {
            mostrarErroValidacao("Equipamento de Vigilância deve ser selecionado!");
            return false;
        }
        if ((aerodromoptField.getText().isEmpty())) {
            mostrarErroValidacao("Aeródromo de Partida deve ser preenchido!");
            return false;
        }
        if (!validarEntrada(aerodromoptField.getText(), "Letras")) {
            mostrarErroValidacao("Aeródromo de partida deve conter apenas letras.");
            return false;
        }
        if (!validarEntrada(horaeobtField.getText(), "horario")) {
            mostrarErroValidacao("Horário deve ser preenchido no formato HHMM!");
            return false;
        }
        if ((velocidadeField.getText().isEmpty())) {
            mostrarErroValidacao("Velocidade de Cruzeiro deve ser preenchido!");
            return false;
        }
        if ((nivelDeVooField.getText().isEmpty())) {
            mostrarErroValidacao("Nível de Cruzeiro deve ser preenchido!");
            return false;
        }
        if ((rotaField.getText().isEmpty())) {
            mostrarErroValidacao("Rota deve ser preenchido!");
            return false;
        }
        if ((aerodromodestinoField.getText().isEmpty())) {
            mostrarErroValidacao("Aeródromo de Destino deve ser preenchido!");
            return false;
        }
        if (!validarEntrada(aerodromodestinoField.getText(), "letras")) {
            mostrarErroValidacao("Aeródromo de Destino deve conter apenas letras.");
            return false;
        }
        if ((duracaototalvooField.getText().isEmpty())) {
            mostrarErroValidacao("Duração Prevista do Voo deve ser preenchido!");
            return false;
        }
        if (!validarEntrada(duracaototalvooField.getText(), "numeros")) {
            mostrarErroValidacao("Duração prevista do Voo deve ser preenchida apenas com numeros.");
            return false;
        }
        if ((aerodromoalternativaField.getText().isEmpty())) {
            mostrarErroValidacao("Aerodromo de Alternativa deve ser preenchido!");
            return false;
        }
        if (!validarEntrada(aerodromoalternativaField.getText(), "letras")) {
            mostrarErroValidacao("Aeródromo de Alternativa deve conter apenas letras.");
            return false;
        }
        // Adicione outras validações conforme necessário
        return true;
    }

    private static void mostrarErroValidacao(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    }

    private static void enviarPlano() {
        if (!validarFormulario()) {
            return;
        }
            String regras = "";
            for (JCheckBox checkBox : regrasField) {
                if (checkBox.isSelected()) {
                    regras += checkBox.getText() + ", ";
                }
            }
            regras = regras.trim();
        
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exemplobd", "root", "root")) {
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO plano_voo (Identificação_da_Aeronave, Regras, Tipo_de_Voo, Número_de_Aeronave, Tipo_de_Aeronave, Catet, Equipamento, Equipamento_Vigilancia, Aerodromo_PT, Hora_EOB, Velocidade, Nível_de_Voo, Rota, Aerodromo_Destino, Duração_Total_do_Voo, Aerodromo_Alternativa, Observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, identaeronaveField.getText());
                pstmt.setString(2, regras);
                pstmt.setString(3, (String) tipoDeVooField.getSelectedItem());
                pstmt.setString(4, numaeronaveField.getText());
                pstmt.setString(5, tipoaeronaveField.getText());
                pstmt.setString(6, (String) catetField.getSelectedItem());
                pstmt.setString(7, (String) equipamentoField.getSelectedItem());
                pstmt.setString(8, (String) equipamentoVField.getSelectedItem());
                pstmt.setString(9, aerodromoptField.getText());
                pstmt.setString(10, horaeobtField.getText());
                pstmt.setString(11, velocidadeField.getText());
                pstmt.setString(12, nivelDeVooField.getText());
                pstmt.setString(13, rotaField.getText());
                pstmt.setString(14, aerodromodestinoField.getText());
                pstmt.setString(15, duracaototalvooField.getText());
                pstmt.setString(16, aerodromoalternativaField.getText());
                pstmt.setString(17, observacoesField.getText());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Plano de voo enviado com sucesso!");
                limparCampos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao enviar plano de voo: " + e.getMessage());
            }
        }
        private static void limparCampos() {
            for (JCheckBox checkBox : regrasField) {
                checkBox.setSelected(false);
            }
            numaeronaveField.setText("");
            tipoaeronaveField.setText("");
            identaeronaveField.setText("");
            aerodromoptField.setText("");
            horaeobtField.setText("");
            velocidadeField.setText("");
            nivelDeVooField.setText("");
            rotaField.setText("");
            aerodromodestinoField.setText("");
            duracaototalvooField.setText("");
            aerodromoalternativaField.setText("");
            observacoesField.setText("");
            regrasField = new JCheckBox[regrasField.length];
            tipoDeVooField.setSelectedIndex(0);
            catetField.setSelectedIndex(0);
            equipamentoField.setSelectedIndex(0);
            equipamentoVField.setSelectedIndex(0);
        }
}

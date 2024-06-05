import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlanoDeVooApp {

    private static JTextField aeronaveField;
    private static JTextField prefixoField;
    private static JTextField tipoDeVooField;
    private static JTextField rotaField;
    private static JTextField horarioPrevistoField;
    private static JTextField nivelDeVooField;
    private static JTextField velocidadeDeVooField;
    private static JTextField pilotoComandanteField;
    private static JTextField copilotoField;
    private static JTextField aviaoField;
    private static JTextArea observacoesField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlanoDeVooApp::criarJanelaInicial);
    }

    private static void criarJanelaInicial() {
        JFrame frame = new JFrame("Envio de Plano de Voo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Envio de Plano de Voo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(11, 2, 10, 10));

        aeronaveField = criarCampoFormulario(formPanel, "Aeronave:*");
        prefixoField = criarCampoFormulario(formPanel, "Prefixo:*");
        tipoDeVooField = criarCampoFormulario(formPanel, "Tipo de Voo (VFR/IFR):*");
        rotaField = criarCampoFormulario(formPanel, "Rota:*");
        horarioPrevistoField = criarCampoFormulario(formPanel, "Horário Previsto:*");
        nivelDeVooField = criarCampoFormulario(formPanel, "Nível de Voo:*");
        velocidadeDeVooField = criarCampoFormulario(formPanel, "Velocidade de Voo:*");
        pilotoComandanteField = criarCampoFormulario(formPanel, "Piloto Comandante:*");
        copilotoField = criarCampoFormulario(formPanel, "Copiloto:*");
        aviaoField = criarCampoFormulario(formPanel, "Avião:*");
        observacoesField = new JTextArea();
        observacoesField.setFont(new Font("Verdana", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(observacoesField);
        formPanel.add(new JLabel("Observações:", SwingConstants.RIGHT));
        formPanel.add(scrollPane);

        mainPanel.add(formPanel, BorderLayout.EAST);

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

    private static JTextField criarCampoFormulario(JPanel painel, String texto) {
        JLabel label = new JLabel(texto, SwingConstants.RIGHT);
        label.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(label);

        JTextField textField = new JTextField();
        textField.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(textField);

        return textField;
    }

    private static boolean validarEntrada(String texto, String tipo) {
        switch (tipo) {
            case "letras":
                return texto.matches("[a-zA-Z]+");
            case "numeros":
                return texto.matches("\\d+");
            case "horario":
                return texto.matches("\\d{2}\\d{2}");
            case "prefixo":
                return texto.matches("[a-zA-Z]+");
            default:
                return true;
        }
    }

    private static boolean validarFormulario() {
        if (!validarEntrada(aeronaveField.getText(), "letras")) {
            mostrarErroValidacao("Aeronave deve conter apenas letras.");
            return false;
        }
        if (!validarEntrada(prefixoField.getText(), "prefixo")) {
            mostrarErroValidacao("Prefixo não pode conter números.");
            return false;
        }
        if (!validarEntrada(tipoDeVooField.getText(), "letras")) {
            mostrarErroValidacao("Tipo de Voo deve conter apenas letras.");
            return false;
        }
        if ((rotaField.getText().isEmpty())) {
            mostrarErroValidacao("Rota deve ser preenchido!");
            return false;
        }
        if (!validarEntrada(horarioPrevistoField.getText(), "horario")) {
            mostrarErroValidacao("Horário Previsto deve estar no formato HHMM.");
            return false;
        }
        if ((nivelDeVooField.getText().isEmpty())) {
            mostrarErroValidacao("Nivel de Voo deve ser preenchido!");
            return false;
        }
        if ((velocidadeDeVooField.getText().isEmpty())) {
            mostrarErroValidacao("Velocidade de Voo deve ser preenchido!");
            return false;
        }
        if ((pilotoComandanteField.getText().isEmpty())) {
            mostrarErroValidacao("Piloto comandante deve ser preenchido!");
            return false;
        }
        if ((copilotoField.getText().isEmpty())) {
            mostrarErroValidacao("Copiloto deve ser preenchido!");
            return false;
        }
        if (!validarEntrada(aviaoField.getText(), "letras")) {
            mostrarErroValidacao("Avião deve conter apenas letras.");
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

        String sql = "INSERT INTO plano_voo (Aeronave, Prefixo, Tipo_de_Voo, Rota, Horario_Previsto, Nivel_de_Voo, Velocidade_de_Voo, Piloto_Comandante, Copiloto, Observacoes) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exemplobd", "root", "root")) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, aeronaveField.getText());
            pstmt.setString(2, prefixoField.getText());
            pstmt.setString(3, tipoDeVooField.getText());
            pstmt.setString(4, rotaField.getText());
            pstmt.setString(5, horarioPrevistoField.getText());
            pstmt.setString(6, nivelDeVooField.getText());
            pstmt.setString(7, velocidadeDeVooField.getText());
            pstmt.setString(8, pilotoComandanteField.getText());
            pstmt.setString(9, copilotoField.getText());
            pstmt.setString(10, observacoesField.getText());

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Plano de voo enviado com sucesso!");

            limparCampos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao enviar plano de voo: " + e.getMessage());
        }
    }

    private static void limparCampos() {
        aeronaveField.setText("");
        prefixoField.setText("");
        tipoDeVooField.setText("");
        rotaField.setText("");
        horarioPrevistoField.setText("");
        nivelDeVooField.setText("");
        velocidadeDeVooField.setText("");
        pilotoComandanteField.setText("");
        copilotoField.setText("");
        aviaoField.setText("");
        observacoesField.setText("");
    }
}
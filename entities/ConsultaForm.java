package entities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConsultaForm extends JFrame {
    private JTextField dataField;
    private JTextField horarioField;
    private JTextField medicoField;
    private JTextField cpfPacienteField;
    private JButton submitButton;

    public ConsultaForm() {
        // Configurações do JFrame
        setTitle("Cadastro de Consulta");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        // Campos do formulário
        JLabel dataLabel = new JLabel("Data (AAAA-MM-DD):");
        dataField = new JTextField();
        
        JLabel horarioLabel = new JLabel("Horário (HH:MM):");
        horarioField = new JTextField();
        
        JLabel medicoLabel = new JLabel("Código do Médico:");
        medicoField = new JTextField();
        
        JLabel cpfPacienteLabel = new JLabel("CPF do Paciente:");
        cpfPacienteField = new JTextField();

        submitButton = new JButton("Cadastrar");

        // Adicionando componentes ao JFrame
        add(dataLabel);
        add(dataField);
        
        add(horarioLabel);
        add(horarioField);
        
        add(medicoLabel);
        add(medicoField);
        
        add(cpfPacienteLabel);
        add(cpfPacienteField);
        
        add(new JLabel()); // Placeholder
        add(submitButton);

        // Action Listener para o botão de submissão
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarConsulta();
            }
        });
    }

    private void cadastrarConsulta() {
        try {
            // Coletando os dados do formulário
            String dataStr = dataField.getText();
            String horarioStr = horarioField.getText();
            String medicoStr = medicoField.getText();
            String cpfPacienteStr = cpfPacienteField.getText();

            // Convertendo os dados para os tipos apropriados
            LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime horario = LocalTime.parse(horarioStr, DateTimeFormatter.ofPattern("HH:mm"));
            int medico = Integer.parseInt(medicoStr);

            // Criando o objeto Consulta
            Consulta consulta = new Consulta(data, horario, medico, cpfPacienteStr);

            // Aqui você pode adicionar código para salvar a consulta em um arquivo, banco de dados, etc.
            System.out.println("Consulta cadastrada com sucesso: " + consulta);

            // Limpando os campos do formulário
            dataField.setText("");
            horarioField.setText("");
            medicoField.setText("");
            cpfPacienteField.setText("");
            
            JOptionPane.showMessageDialog(this, "Consulta cadastrada com sucesso!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar consulta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Executando o formulário
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConsultaForm().setVisible(true);
            }
        });
    }
}

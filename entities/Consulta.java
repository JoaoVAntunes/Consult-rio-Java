package entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Consulta implements Serializable{
    private static final long serialVersionUID = 1L;
    private LocalDate data;
    private LocalTime horario;

    private int medico;
    private String cpfPaciente;

    //constructor
    public Consulta(LocalDate data, LocalTime horario, int medico, String cpfPaciente){
        this.data = data;
        this.horario = horario;
        this.medico = medico;
        this.cpfPaciente = cpfPaciente;
    }
    //getters
    public LocalDate getData(){
        return data;
    }
    
    public LocalTime getHorario() {
        return horario;
    }

    public int getMedico() {
        //Medico name = name.findMedicoByCodigo(, this.medico);
        return medico;
    }
    public String getCpfPaciente() {
        return cpfPaciente;
    }
    public void setConsulta(int cod_medico, String cpfPaciente, LocalDate data, LocalTime horario) {
        this.medico = cod_medico;
        this.cpfPaciente = cpfPaciente;
        this.data = data;
        this.horario = horario;
    }

    //this method will print the followings appointments
    public static void imprimirConsultasFuturasPorPaciente(List<Consulta> consultas, List<Medico> medicos, String cpfPaciente) {
        LocalDate dataAtual = LocalDate.now();

        System.out.println("Consultas agendadas para o Paciente com CPF " + cpfPaciente + ":");

        for (Consulta consulta : consultas) {
            if (consulta.getCpfPaciente().equals(cpfPaciente) && consulta.getData().isAfter(dataAtual)) {
                String nomeMedico = Medico.getMedicoNomePorCodigo(medicos, consulta);
                if (nomeMedico != null) {
                    System.out.println("- Data: " + consulta.getData() + ", Horário: " + consulta.getHorario() +
                            ", Médico: " + nomeMedico);
                }
            }
        }
    }

     // Method to read a list of consultations from a file
     @SuppressWarnings("unchecked")
    public static List<Consulta> abrirConsultas() throws IOException, ClassNotFoundException {
        List<Consulta> consultas = null;
        FileInputStream arquivo = new FileInputStream("consultas.bin");
        ObjectInputStream restaurador = new ObjectInputStream(arquivo);

        consultas = (List<Consulta>)restaurador.readObject();
        
        restaurador.close();
        arquivo.close();
        
        return consultas;
    }

    // Função para buscar a consulta de acordo com os campos inseridos e atualizar caso encontre a consulta
    public static Boolean getAtualizarConsulta(List<Consulta> consultas, int cod_medico, String cpfPaciente, LocalDate data, LocalTime horario) throws Exception{
        //LocalDate dataAtual = LocalDate.now();

        String cpf;
        int codigo;
        LocalDate data_consulta;
        LocalTime horario_consulta;
        boolean result = false;

        for (Consulta consulta : consultas) {

            cpf = consulta.getCpfPaciente();
            codigo = consulta.getMedico();
            data_consulta = consulta.getData();
            horario_consulta = consulta.getHorario();

            if (cpf.equals(cpfPaciente) 
                && codigo == cod_medico
                && data_consulta == data
                && horario_consulta == horario) 
                {
                consulta.setConsulta(cod_medico, cpfPaciente, data, horario);
                result = true;
                }
        }
        return result;
    }
    
    public static void salvarListaDeConsultas(List<Consulta> consultas) throws IOException, Exception{

        String nome_do_arquivo = "consultas.bin";
        FileOutputStream arquivo = new FileOutputStream(nome_do_arquivo);
        ObjectOutputStream gravador = new ObjectOutputStream(arquivo);
        gravador.writeObject(consultas);
        
        gravador.close();
        arquivo.close();
    }

    // Esta função consulta as consultas em determinada data e adiciona em uma lista, para a sua futura exibição
    // Foi colocado um "throws", pois é possível que o usuário digite a data num formato inadequado, por exemplo
    public static List<Consulta> getConsultasPorData(List<Consulta> consultas, LocalDate data) throws Exception{
    
    List<Consulta> consultasBuscadas = new ArrayList<>(); // Inicializa a lista

    for (Consulta consulta : consultas) {
        if (consulta.getData().equals(data)) {
            consultasBuscadas.add(consulta);
        }
    }
    return consultasBuscadas;
    }
}


/* 

public class AtualizarJogador {
    public static void main(String[] args) {
        String nome_arquivo = "BlueJedi.ser";
        try {
        Jogador jogador = Jogador.abrir(nome_arquivo);
        System.out.println("Jogador recuperado com sucesso!");
        jogador.exibir();
        jogador.posicionar(20.0f);
        jogador.pontuar();
        jogador.exibir();
        jogador.salvar(nome_arquivo);
        System.out.println("Jogador salvo com sucesso!");
        } catch (IOException e) {
        System.out.println("Excecao de I/O");
        e.printStackTrace();
        } catch (ClassNotFoundException e) {
        System.out.println("Excecao de classe desconhecida");
        e.printStackTrace();
        }
    }
}

*/

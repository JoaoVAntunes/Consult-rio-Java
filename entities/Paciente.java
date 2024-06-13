package entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import readers.readersCSV.DadosCSVReaderConsulta;
import readers.readersCSV.DadosCSVReaderPaciente;


public class Paciente implements Serializable{
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;
    private List<Consulta> consultas;
    
    //constructor
    public Paciente(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
        this.consultas = new ArrayList<>();
    }
    //getters
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }
    //setter
    public void adicionarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }
    // method to return the pacient's doctors it has had an appointment with
    public List<Medico> getMedicosConsultados(List<Medico> medicos) {
        List<Medico> medicosConsultados = new ArrayList<>();

        for (Consulta consulta : consultas) {
            int codigoMedico = consulta.getMedico();
            for (Medico medico : medicos) {
                if (medico.getCodigo() == codigoMedico && !medicosConsultados.contains(medico)) {
                    medicosConsultados.add(medico);
                    break; //To avoid adding the same doctor more than once
                }
            }
        }

        return medicosConsultados;
    }
    
    //method to return the pacient's appointments it has had an appointment with
    public List<Consulta> getConsultasComMedico(Medico medico) {
        
        List<Consulta> consultasComMedico = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (consulta.getMedico() == medico.getCodigo()) {
                consultasComMedico.add(consulta);
            }
        }
        return consultasComMedico;
    }

    //this method will be returning the appointments before than a certain date from a specific pacient
    public boolean ultimaConsultaAntesDe(LocalDate dataLimite) {
        if (consultas.isEmpty()) {
            return false; // Se não houver consultas, considerar como não ocorrida
        }

        LocalDate ultimaConsulta = consultas.get(consultas.size() - 1).getData();
        return ultimaConsulta.isBefore(dataLimite);
    }
    //method to get the pacients name by it's cpf
    public static String getPacienteNomePorCPF(String cpf) {
        List<Consulta> consultas = DadosCSVReaderConsulta.lerConsultasDoCSV();
        List<Paciente> pacientes = DadosCSVReaderPaciente.lerPacientesDoCSV(consultas);
        for (Paciente paciente : pacientes) {
            if (paciente.getCpf().equals(cpf)) {
                return paciente.getNome();
            }
        }
        return "Paciente não encontrado";
    }
    //method to find a pacient thorugh it's code
    public static Paciente findPacienteByCPF(List<Paciente> pacientes, String cpf) {
        for (Paciente paciente : pacientes) {
            if (paciente.getCpf().equals(cpf)) {
                return paciente;
            }
        }
        System.out.println("Paciente não encontrado, redirecionando de volta...");
        return null; // Retorna null se o paciente não for encontrado
    }

    //method to find a inative patient from a certain doctor
    public static boolean isPacienteInativo(Paciente paciente, List<Consulta> consultas, int mesesInatividade, LocalDate dataAtual) {
        for (Consulta consulta : consultas) {
            if (consulta.getCpfPaciente().equals(paciente.getCpf())) {
                LocalDate dataConsulta = consulta.getData();
                // Verifica se a consulta foi há mais de 'mesesInatividade' meses
                if (dataConsulta.isBefore(dataAtual.minusMonths(mesesInatividade))) {
                    return true; // Paciente está inativo há mais tempo do que o especificado
                }
            }
        }
        return false; // Paciente não está inativo há mais de 'mesesInatividade' meses
    }

     // Method to read a list of consultations from a file
     @SuppressWarnings("unchecked")
    public static List<Paciente> abrirPacientes() throws IOException, ClassNotFoundException {
        List<Paciente> pacientes = null;
        FileInputStream arquivo = new FileInputStream("pacientes.bin");
        ObjectInputStream restaurador = new ObjectInputStream(arquivo);

        pacientes = (List<Paciente>)restaurador.readObject();
        
        restaurador.close();
        arquivo.close();
        
        return pacientes;
    }

    public static void salvarListaDePacientes(List<Paciente> paciente) throws IOException, Exception{

        FileOutputStream arquivo = new FileOutputStream("pacientes.bin");
        ObjectOutputStream gravador = new ObjectOutputStream(arquivo);
        gravador.writeObject(paciente);
        
        gravador.close();
        arquivo.close();
    }

    public static String getPacienteNomePorCPF(List<Paciente> pacientes, String cpf) {

        for (Paciente paciente : pacientes) {
            if (paciente.getCpf().equals(cpf)) {
                return paciente.getNome();
            }
        }
        return "Paciente não encontrado";
    }
}
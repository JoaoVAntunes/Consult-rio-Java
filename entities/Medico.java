package entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.util.*;
import readers.readersCSV.DadosCSVReaderConsulta;
import readers.readersCSV.DadosCSVReaderPaciente;

public class Medico implements Serializable{
    private String nome;
    private int codigo;
    private List<Paciente> pacientes;

    //constructor
    public Medico(String nome, int codigo){
        this.nome  = nome;
        this.codigo = codigo; 
        this.pacientes = new ArrayList<>();
    }
    //getters
    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }
    
    //setter
    public void adicionarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    //method to return consults in a given period of time
    public List<Consulta> getConsultasNoPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        List<Consulta> consultasNoPeriodo = new ArrayList<>();
        List<Consulta> consultas = DadosCSVReaderConsulta.lerConsultasDoCSV();
        
        for (Consulta consulta : consultas) {
            if (consulta.getData().isAfter(dataInicial) && consulta.getData().isBefore(dataFinal)) {
                consultasNoPeriodo.add(consulta);
            }
        }
        consultasNoPeriodo.sort(Comparator.comparing(Consulta::getHorario));
        return consultasNoPeriodo;
    }
    //methos to find a doctor through it's code
    public static Medico findMedicoByCodigo(List<Medico> medicos, int codigo) {
        for (Medico medico : medicos) {
            if (medico.getCodigo() == codigo) {
                return medico;
            }
        }
        System.out.println("Médico não encontrado, redirecionando de volta...");
        return null; // Médico não encontrado
 
    }
    // Method to return doctor name based on appointment code (for future appointments only)
    public static String getMedicoNomePorCodigo(List<Medico> medicos, Consulta consulta) {
        int codigoMedico = consulta.getMedico();

        for (Medico medico : medicos) {
            if (medico.getCodigo() == codigoMedico) {
                return medico.getNome();
            }
        }
        return null; // Retorna null se o médico não for encontrado
    }

    public static Integer getCodigoMedicoPorNome(List<Medico> medicos, String nomeMedico) {
        for (Medico medico : medicos) {
            if (medico.getNome().equals(nomeMedico)) {
                return medico.getCodigo();
            }
        }
        return null; // Retorna null se o médico não for encontrado
    }

    //method to get the inative pacients
    public List<Paciente> getPacientesInativos(int mesesInatividade) {
        List<Consulta> consultas = DadosCSVReaderConsulta.lerConsultasDoCSV();
        List<Paciente> pacientes = DadosCSVReaderPaciente.lerPacientesDoCSV(consultas);
        
        List<Paciente> pacientesInativos = new ArrayList<>();
        LocalDate dataAtual = LocalDate.now();
    
        for (Paciente paciente : pacientes) {
            if (Paciente.isPacienteInativo(paciente, consultas, mesesInatividade, dataAtual)) {
                pacientesInativos.add(paciente);
            }
        }
    
        return pacientesInativos;
    }

    //from here on the persistance of the objects will be done
    public void salvar(String nome_arquivo) throws IOException {
        FileOutputStream arquivo = new FileOutputStream(nome_arquivo);
        ObjectOutputStream gravador = new ObjectOutputStream(arquivo);
        gravador.writeObject(this);
        gravador.close();
        arquivo.close();
    }
    public static Time abrir(String nome_arquivo) throws IOException, ClassNotFoundException {
        Time time = null;
        FileInputStream arquivo = new FileInputStream(nome_arquivo);
        ObjectInputStream restaurador = new ObjectInputStream(arquivo);
        time = (Time) restaurador.readObject();
        restaurador.close();
        arquivo.close();
        return time;
    }

        // Method to read a list of consultations from a file
    @SuppressWarnings("unchecked")
    public static List<Medico> abrirMedicos() throws IOException, ClassNotFoundException {
        List<Medico> medicos = null;
        FileInputStream arquivo = new FileInputStream("medicos.bin");
        ObjectInputStream restaurador = new ObjectInputStream(arquivo);

        medicos = (List<Medico>)restaurador.readObject();
        
        restaurador.close();
        arquivo.close();
        
        return medicos;
    }

    public static void salvarListaDeMedicos(List<Medico> medicos) throws IOException, Exception{

        FileOutputStream arquivo = new FileOutputStream("medicos.bin");
        ObjectOutputStream gravador = new ObjectOutputStream(arquivo);
        gravador.writeObject(medicos);
        
        gravador.close();
        arquivo.close();
    }

    // Método para retornar o nome do médico a partir do seu código (passado como parâmetro)
    public static String getNomeMedicoPorCodigo(List<Medico> medicos, int codigoMedico) {

        for (Medico medico : medicos) {
            if (medico.getCodigo() == codigoMedico) {
                return medico.getNome();
            }
        }
        return null; // Retorna null se o médico não for encontrado
    }
}
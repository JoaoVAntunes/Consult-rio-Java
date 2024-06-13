package application;

import entities.*;
import java.io.IOException;
import java.util.*;
import readers.readersCSV.DadosCSVReaderConsulta;
import readers.readersCSV.DadosCSVReaderMedico;
import readers.readersCSV.DadosCSVReaderPaciente;

public class SalvarBinario{
    public static void main(String[] args){

        List<Consulta> consultas = DadosCSVReaderConsulta.lerConsultasDoCSV();
        // Passo 2: Ler pacientes e associar consultas
        List<Paciente> pacientes = DadosCSVReaderPaciente.lerPacientesDoCSV(consultas);
        // Passo 3: Ler médicos e associar pacientes
        List<Medico> medicos = DadosCSVReaderMedico.lerMedicosDoCSV(pacientes);

        // Salva os dados lidos no CSV no arquivo binário
        try {
            Consulta.salvarListaDeConsultas(consultas);
            System.out.println("Consultas salvas em binário com successo!");

            Paciente.salvarListaDePacientes(pacientes);
            System.out.println("Pacientes salvos em binário com successo!");

            Medico.salvarListaDeMedicos(medicos);
            System.out.println("Médicos salvos em binário com successo!");

        } catch (IOException e) {
            System.out.println("Excecao de I/O");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


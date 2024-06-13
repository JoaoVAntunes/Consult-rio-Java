package readers.readersCSV;

import entities.Consulta;
import entities.Paciente;
import java.io.*;
import java.util.*;

public class DadosCSVReaderPaciente {
    public static List<Paciente> lerPacientesDoCSV(List<Consulta> consultas) {
		// String caminho_arquivo = "C:\\Users\\joaov_er3oeo2\\OneDrive - Grupo Marista\\Nova pasta\\Desktop\\Workspace\\java\\ProjetoJava\\informacoes\\pacientes.csv";
		// String caminho_arquivo = "C:\\Users\\Samsung\\OneDrive - Grupo Marista\\3º Período\\Programação Orientada a Objetos\\Trabalho RA3\\Consult-rio-Java-POO-3\\informacoes\\pacientes.csv";
		String caminho_arquivo = "informacoes/pacientes.csv";

		Map<String, Paciente> mapaPacientes = new HashMap<>();
		

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(caminho_arquivo), "UTF-8"))) {
			
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				
				String[] vect = line.split(",");
				String nome = vect[0];
				String cpf = vect[1];
				
				Paciente paciente = mapaPacientes.getOrDefault(cpf, new Paciente(nome, cpf));
            	mapaPacientes.putIfAbsent(cpf, paciente);
				
				line = br.readLine();
			}
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		// Associar consultas aos pacientes
		for (Consulta consulta : consultas) {
			Paciente paciente = mapaPacientes.get(consulta.getCpfPaciente());
			if (paciente != null) {
				paciente.adicionarConsulta(consulta);
			}
		}
	
		return new ArrayList<>(mapaPacientes.values());
    }
	
}
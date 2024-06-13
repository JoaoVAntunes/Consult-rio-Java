package application;

import entities.*;
import java.io.IOException;
import java.util.*;

public class InterfaceUser{
    public static void main(String[] args) throws IOException{

        // Recupera os dados salvos no arquivo binário de cada classe (Consulta, Paciente e Medico)
        try{
            List<Consulta> consultas = Consulta.abrirConsultas();
            List<Paciente> pacientes = Paciente.abrirPacientes();
            List<Medico> medicos = Medico.abrirMedicos();

            System.out.println("Consultas recuperadas com successo!");
            System.out.println("Pacientes recuperados com successo!");
            System.out.println("Médicos recuperados com successo!");

            // Instaciando um objeto da classe Menu
            Menu menu = new Menu();

            // Passando como parâmetro para o método as listas lidas dos binários
            menu.criar_interface(consultas, pacientes, medicos);

            // --=-=-=-=-=-=-=-=- TESTE -=-=-=-=-=-=-=-=-=-=
            /* -=-=- Para testar se a leitura e o armazenamento em listas está correto -=-=-
            // Criando um array de Strings para armazenar os nomes dos médicos
            String[] nomesMedicos = new String[medicos.size()];

            // Percorrendo a lista de médicos e preenchendo o array de Strings
            for (int i = 0; i < medicos.size(); i++) {
                nomesMedicos[i] = medicos.get(i).getNome();
            }

            // Imprimindo o array de nomes para verificar
            for (String nome : nomesMedicos) {
                System.out.println(nome);
            }
            */


        } 
        catch (IOException e) {
            System.out.println("Excecao de I/O");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Excecao de classe desconhecida");
            e.printStackTrace();
        }
    }           
}
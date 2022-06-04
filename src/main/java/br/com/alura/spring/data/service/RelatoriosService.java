package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private Boolean system;
	private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final FuncionarioRepository funcionarioRepository;

	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {
		system = true;
		while (system == true) {

			System.out.println("Qual ação em relatórios deseja executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca todos funcionários");
			System.out.println("2 - Busca funcionário por nome");
			System.out.println("3 - Busca funcionário por nome, salário e data");
			System.out.println("4 - Busca funcionário por data");
			System.out.println("5 - Pesquisa funcionário / salário");
			Integer action = scanner.nextInt();

			switch (action) {
			case 1:
				buscaTodos(scanner);
				break;
			case 2:
				buscaFuncionarioPorNome(scanner);
				break;
			case 3:
				buscaFuncionarioPorNomeSalarioData(scanner);
				break;
			case 4:
				buscaFuncionarioPorData(scanner);
				break;
			case 5:
				buscaFuncionarioSalario();
				break;
			default:
				system = false;
				break;
			}
		}
	}

	private void buscaFuncionarioSalario() {
		List<FuncionarioProjecao> funcionariosSalario = funcionarioRepository.findFuncionarioSalario();
		funcionariosSalario.forEach(f -> System.out.println(
		"Id: "+f.getId()+
		". Nome: "+f.getNome()+
		". Salário: R$"+f.getSalario()));
	}

	private void buscaFuncionarioPorData(Scanner scanner) {
		System.out.println("Digite a data de contratação para pesquisa");
		String data = scanner.next();
		LocalDate dataContratacao = LocalDate.parse(data, format);
		List<Funcionario> funcionarios = funcionarioRepository.findFuncionarioPorData(dataContratacao);
		funcionarios.forEach(System.out::println);
	}

	private void buscaTodos(Scanner scanner) {
		funcionarioRepository.findAll().forEach(System.out::println);;
	}

	private void buscaFuncionarioPorNomeSalarioData(Scanner scanner) {
		System.out.println("Digite o nome do funcionário");
		String nome = scanner.next();
		System.out.println("Digite o teto salarial da pesquisa");
		Double salario = scanner.nextDouble();
		System.out.println("Digite a data final de contratação para pesquisa");
		String data = scanner.next();
		LocalDate dataContratacao = LocalDate.parse(data, format);
		List<Funcionario> funcionarios = funcionarioRepository.findNomeSalarioData(nome, salario, dataContratacao);
		funcionarios.forEach(System.out::println);
	}

	private void buscaFuncionarioPorNome(Scanner scanner) {
		System.out.println("Digite o nome do funcionário");
		String nome = scanner.next();
		List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);
		funcionarios.forEach(System.out::println);
	}
}

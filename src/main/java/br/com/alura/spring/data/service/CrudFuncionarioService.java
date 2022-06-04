package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
	private Boolean system;

	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository,
									CargoRepository cargoRepository,
									UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
	}

	public void inicial(Scanner scanner) {
		Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
		funcionarios.forEach(f -> System.out.println(f));
		system = true;
		while (system) {
			System.out.println("qual ação em funcionarios você quer executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Criar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Remover");
			System.out.println("4 - Visualizar");
			Integer action = scanner.nextInt();

			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				remover(scanner);
				break;
			case 4:
				visualizar(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
	}

	private void visualizar(Scanner scanner) {
		System.out.println("Digite a pagina desejada");
		Integer page = scanner.nextInt();
		
		Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC,"salario"));
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);

		System.out.println(funcionarios);
		System.out.println("Pagina atual: "+funcionarios.getNumber());
		System.out.println("Número de elementos da pagina: "+funcionarios.getNumberOfElements());
		System.out.println("Total de elementos: "+funcionarios.getTotalElements());
		funcionarios.forEach(f -> System.out.println(f));
	}

	private void atualizar(Scanner scanner) {
		Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
		funcionarios.forEach(c -> System.out.println(c.getNome()));
		System.out.println("Digite o funcionario a ser atualizado");
		String nomeFuncionario = scanner.next();
		System.out.println("Digite o novo funcionario");
		String nomeFuncionario2 = scanner.next();
		for (Funcionario funcionario : funcionarios) {
			if (funcionario.getNome().equals(nomeFuncionario)) {
				funcionario.setNome(nomeFuncionario2);
				funcionarioRepository.save(funcionario);
			}
		}
		funcionarios = funcionarioRepository.findAll();
		funcionarios.forEach(c -> System.out.println(c.getNome()));
	}

	private void remover(Scanner scanner) {
		Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
		funcionarios.forEach(c -> System.out.println(c.getNome()));
		System.out.println("Digite o funcionario a ser removido");
		String nomeFuncionario = scanner.next();
		Funcionario funcionario = new Funcionario();
		for (Funcionario funcionario2 : funcionarios) {
			if (funcionario2.getNome().equals(nomeFuncionario))
				funcionario = funcionario2;
		}
		funcionarioRepository.delete(funcionario);
		System.out.println("Funcionario " + funcionario + " removido.");
		funcionarios = funcionarioRepository.findAll();
		funcionarios.forEach(c -> System.out.println(c.getNome()));
	}

	public void salvar(Scanner scanner) {
		Funcionario funcionario = new Funcionario();
		Cargo cargo = new Cargo();
		System.out.println("Digite o nome do novo funcionario");
		String nomeFuncionario = scanner.next();
		System.out.println("Digite o CPF de "+nomeFuncionario);
		String Cpf = scanner.next();
		System.out.println("Qual é o cargo de "+nomeFuncionario+"?");
		cargoRepository.findAll().forEach(System.out::println);
		String nomeCargo = scanner.next();
		System.out.println(nomeFuncionario+" vai trabalhar em qual unidade?");
		unidadeTrabalhoRepository.findAll().forEach(System.out::println);
		String nomeUnidade = scanner.next();
		System.out.println("Qual será o salário de "+nomeFuncionario+"?");
		Double salario = scanner.nextDouble();
		List<Cargo> descricaoCargo = cargoRepository.findByDescricao(nomeCargo);
		cargo = descricaoCargo.get(0);
		List<UnidadeTrabalho> unidadesTrabalho = unidadeTrabalhoRepository.findByDescricao(nomeUnidade);
		funcionario.setNome(nomeFuncionario);
		funcionario.setCargo(cargo);
		funcionario.setUnidadeTrabalhos(unidadesTrabalho);
		funcionario.setDataContratacao(LocalDate.now());
		funcionario.setSalario(salario);
		funcionario.setCpf(Cpf);
		funcionarioRepository.save(funcionario);
		Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
		funcionarios.forEach(System.out::println);
	}
}

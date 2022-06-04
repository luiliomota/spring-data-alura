package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {

	private final CargoRepository cargoRepository;
	private Boolean system;

	public CrudCargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public void inicial(Scanner scanner) {
		Iterable<Cargo> cargos = cargoRepository.findAll();
		cargos.forEach(c -> System.out.println(c));
		system = true;
		while (system) {
			System.out.println("qual ação em cargos você quer executar?");
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
				visualizar();
				break;
			default:
				system = false;
				break;
			}
		}
	}

	private void visualizar() {
		Iterable<Cargo> cargos = cargoRepository.findAll();
		cargos.forEach(c -> System.out.println(c));
	}

	private void atualizar(Scanner scanner) {
		Iterable<Cargo> cargos = cargoRepository.findAll();
		cargos.forEach(c -> System.out.println(c.getDescricao()));
		System.out.println("Digite o cargo a ser atualizado");
		String descricaoCargo = scanner.next();
		System.out.println("Digite a nova descrição");
		String descricaoCargo2 = scanner.next();
		for (Cargo cargo : cargos) {
			if (cargo.getDescricao().equals(descricaoCargo)) {
				cargo.setDescricao(descricaoCargo2);
				cargoRepository.save(cargo);
			}
		}
		cargos = cargoRepository.findAll();
		cargos.forEach(c -> System.out.println(c.getDescricao()));
	}

	private void remover(Scanner scanner) {
		while (system == true) {
			system = true;
			Iterable<Cargo> cargos = cargoRepository.findAll();
			cargos.forEach(System.out::println);
			System.out.println("Remover por nome ou por id?");
			System.out.println("0 - Voltar");
			System.out.println("1 - Nome");
			System.out.println("2 - Id");
			Integer action = scanner.nextInt();
			switch (action) {
			case 1:
				System.out.println("Digite o nome do cargo a ser removido");
				String descricaoCargo = scanner.next();
				Cargo cargo = new Cargo();
				for (Cargo cargo2 : cargos) {
					if (cargo2.getDescricao().equals(descricaoCargo))
						cargo = cargo2;
				}
				cargoRepository.delete(cargo);
				System.out.println("Cargo " + cargo + " removido.");
				cargos = cargoRepository.findAll();
				cargos.forEach(c -> System.out.println(c.getDescricao()));

				break;

			case 2:
				System.out.println("Digite o id do cargo a ser removido");
				Integer idCargo = scanner.nextInt();
				Cargo cargo2 = new Cargo();
				for (Cargo cargo3 : cargos) {
					if (cargo3.getId() == idCargo)
						cargo2 = cargo3;
				}
				cargoRepository.delete(cargo2);
				System.out.println("Cargo " + cargo2 + " removido.");
				cargos = cargoRepository.findAll();
				cargos.forEach(c -> System.out.println(c.getDescricao()));

				break;

			default:
				system = false;
				break;
			}
		}
	}

	public void salvar(Scanner scanner) {
		System.out.println("Descricao do cargo");
		String descricao = scanner.next();
		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);
		System.out.println("Salvo");
		Iterable<Cargo> cargos = cargoRepository.findAll();
		cargos.forEach(c -> System.out.println(c.getDescricao()));
	}
}

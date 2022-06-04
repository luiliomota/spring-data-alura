package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudUnidadeTrabalhoService {

	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
	private Boolean system;

	public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
	}

	public void inicial(Scanner scanner) {
		Iterable<UnidadeTrabalho> unidadesTrabalho = unidadeTrabalhoRepository.findAll();
		unidadesTrabalho.forEach(u -> System.out.println(u));
		system = true;
		while (system) {
			System.out.println("qual ação em unidadeTrabalhos você quer executar?");
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

//		if (action == 1) {
//			salvar(scanner);}
//		else if (action == 2)
//			atualizar(scanner);
//		else if (action == 3)
//			remover(scanner);
	}

	private void visualizar() {
		Iterable<UnidadeTrabalho> unidadesTrabalho = unidadeTrabalhoRepository.findAll();
		unidadesTrabalho.forEach(u -> System.out.println(u));
	}

	private void atualizar(Scanner scanner) {
		Iterable<UnidadeTrabalho> unidadeTrabalhos = unidadeTrabalhoRepository.findAll();
		unidadeTrabalhos.forEach(c -> System.out.println(c.getDescricao()));
		System.out.println("Digite a unidadeTrabalho a ser atualizada");
		String descricaoUnidadeTrabalho = scanner.next();
		System.out.println("Digite a nova descrição");
		String descricaoUnidadeTrabalho2 = scanner.next();
		for (UnidadeTrabalho unidadeTrabalho : unidadeTrabalhos) {
			if (unidadeTrabalho.getDescricao().equals(descricaoUnidadeTrabalho)) {
				unidadeTrabalho.setDescricao(descricaoUnidadeTrabalho2);
				unidadeTrabalhoRepository.save(unidadeTrabalho);
			}
		}
		unidadeTrabalhos = unidadeTrabalhoRepository.findAll();
		unidadeTrabalhos.forEach(c -> System.out.println(c.getDescricao()));
	}

	private void remover(Scanner scanner) {
		Iterable<UnidadeTrabalho> unidadeTrabalhos = unidadeTrabalhoRepository.findAll();
		unidadeTrabalhos.forEach(c -> System.out.println(c.getDescricao()));
		System.out.println("Digite a unidadeTrabalho a ser removida");
		String descricaoUnidadeTrabalho = scanner.next();
		UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
		for (UnidadeTrabalho unidadeTrabalho2 : unidadeTrabalhos) {
			if (unidadeTrabalho2.getDescricao().equals(descricaoUnidadeTrabalho))
				unidadeTrabalho = unidadeTrabalho2;
		}
		unidadeTrabalhoRepository.delete(unidadeTrabalho);
		System.out.println("UnidadeTrabalho " + unidadeTrabalho + " removida.");
		unidadeTrabalhos = unidadeTrabalhoRepository.findAll();
		unidadeTrabalhos.forEach(c -> System.out.println(c.getDescricao()));
	}

	public void salvar(Scanner scanner) {
		System.out.println("Descricao da unidadeTrabalho");
		String descricao = scanner.next();
		UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
		unidadeTrabalho.setDescricao(descricao);
		unidadeTrabalhoRepository.save(unidadeTrabalho);
		System.out.println("Salvo");
		Iterable<UnidadeTrabalho> unidadeTrabalhos = unidadeTrabalhoRepository.findAll();
		unidadeTrabalhos.forEach(c -> System.out.println(c.getDescricao()));
	}
}

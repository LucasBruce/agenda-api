package br.com.bruce.agenda.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.bruce.agenda.domain.exception.EntidadeNaoEncontradaException;
import br.com.bruce.agenda.domain.model.Pessoa;
import br.com.bruce.agenda.domain.repository.PessoaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PessoaService {

	private PessoaRepository pessoaRepository;
	
	public Pessoa pesquisarPessoa(Long id) {
		Optional<Pessoa> pessoa = this.pessoaRepository.findById(id);
		if(!pessoa.isPresent()) {
			throw new EntidadeNaoEncontradaException("Não encontrado");
		}else {
			return pessoa.get();
		}
	}
	
}

package br.com.bruce.agenda.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.bruce.agenda.api.model.PessoaModel;
import br.com.bruce.agenda.api.model.input.PessoaInput;
import br.com.bruce.agenda.domain.model.Pessoa;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AssemblerPessoa {

	private ModelMapper modelMapper;
	
	public PessoaModel toModel(Pessoa pessoa) {
		return modelMapper.map(pessoa, PessoaModel.class);
	}
	
	public List<PessoaModel> toCollectionModel(List<Pessoa> pessoas){
		return pessoas.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Pessoa toEntity(PessoaInput pessoaInput) {
		return modelMapper.map(pessoaInput, Pessoa.class);
	}
}

package br.com.bruce.agenda.domain.repository.pessoa;

import java.util.List;

import br.com.bruce.agenda.domain.model.Pessoa;
import br.com.bruce.agenda.domain.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	public List<Pessoa> filtrar(PessoaFilter pessoaFilter);
}

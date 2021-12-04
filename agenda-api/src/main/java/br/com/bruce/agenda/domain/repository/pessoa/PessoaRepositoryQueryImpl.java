package br.com.bruce.agenda.domain.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.bruce.agenda.domain.model.Pessoa;
import br.com.bruce.agenda.domain.model.Pessoa_;
import br.com.bruce.agenda.domain.repository.filter.PessoaFilter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PessoaRepositoryQueryImpl implements PessoaRepositoryQuery{

	private EntityManager entityManager;
	
	@Override
	public List<Pessoa> filtrar(PessoaFilter pessoaFilter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Pessoa> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder, Root<Pessoa> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if((pessoaFilter.getNome()) != null) {
			predicates.add(builder.like(builder.lower(root.get(Pessoa_.nome)), "%" + pessoaFilter.getNome().toLowerCase() + "%"));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}

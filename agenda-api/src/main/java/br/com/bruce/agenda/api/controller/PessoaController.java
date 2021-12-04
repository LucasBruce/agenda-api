package br.com.bruce.agenda.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruce.agenda.api.assembler.AssemblerPessoa;
import br.com.bruce.agenda.api.event.RecursoCriadoEvent;
import br.com.bruce.agenda.api.model.PessoaModel;
import br.com.bruce.agenda.api.model.input.PessoaInput;
import br.com.bruce.agenda.domain.model.Pessoa;
import br.com.bruce.agenda.domain.repository.PessoaRepository;
import br.com.bruce.agenda.domain.repository.filter.PessoaFilter;
import br.com.bruce.agenda.domain.service.PessoaService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	private PessoaRepository pessoaRepository;
	private PessoaService pessoaService;
    private AssemblerPessoa assemblerPessoa;
    private ApplicationEventPublisher publisher;
    
	@GetMapping
	public List<PessoaModel> listarPesquisa(PessoaFilter pessoaFilter){
		List<Pessoa> pessoas = this.pessoaRepository.filtrar(pessoaFilter);
		return this.assemblerPessoa.toCollectionModel(pessoas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PessoaModel> pesquisarPessoa(@PathVariable Long id) {
		Pessoa pessoaPesquisada = this.pessoaService.pesquisarPessoa(id);
		PessoaModel pessoaModel = this.assemblerPessoa.toModel(pessoaPesquisada);
	    return ResponseEntity.ok(pessoaModel);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PessoaModel salvarPessoa(@Valid @RequestBody PessoaInput pessoaInput, HttpServletResponse response) {
		Pessoa pessoa = this.assemblerPessoa.toEntity(pessoaInput);
		Pessoa pessoaSalva = this.pessoaRepository.save(pessoa);
		this.publisher.publishEvent(new RecursoCriadoEvent(this, pessoaSalva.getId(), response));
     	return this.assemblerPessoa.toModel(pessoaSalva);
	}
	
	@PutMapping("/{id}")
	public PessoaModel atualizarPessoa(@PathVariable Long id, @Valid @RequestBody PessoaInput pessoaInput) {
		Pessoa pessoaOrigem = this.assemblerPessoa.toEntity(pessoaInput);
		Pessoa pessoaAlvo = this.pessoaService.pesquisarPessoa(id);
		BeanUtils.copyProperties(pessoaOrigem, pessoaAlvo, "id");
		Pessoa pessoaSalva = this.pessoaRepository.save(pessoaAlvo);
		return this.assemblerPessoa.toModel(pessoaSalva);
	}
	
	@DeleteMapping("/{id}")
	public void removerPessoa(@PathVariable Long id) {
		this.pessoaRepository.deleteById(id);
	}
}

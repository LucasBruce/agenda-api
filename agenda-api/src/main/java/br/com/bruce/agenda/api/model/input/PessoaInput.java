package br.com.bruce.agenda.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaInput {

	@NotNull
	private String nome;
	@Email
	@NotNull
	private String email;
	@NotNull
	private String telefone;
}

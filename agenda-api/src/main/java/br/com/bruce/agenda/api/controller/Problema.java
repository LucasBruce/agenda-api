package br.com.bruce.agenda.api.controller;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problema {

	private Integer status;
	private OffsetDateTime dataHora;
	private String titulo;
	private List<Campo> campos;

	@AllArgsConstructor
	@Getter
	@Setter
	public static class Campo {
		
		private String nome;
		private String mensagem;
	}
}

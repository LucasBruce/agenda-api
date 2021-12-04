package br.com.bruce.agenda.api.exceptionHandle;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.bruce.agenda.api.controller.Problema;
import br.com.bruce.agenda.domain.exception.EntidadeNaoEncontradaException;
import br.com.bruce.agenda.domain.exception.NegocioException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class AgendaExceptionHandle extends ResponseEntityExceptionHandler{

	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Problema.Campo> campos = new ArrayList<>();
		for(ObjectError error:ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError)error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			campos.add(new Problema.Campo(nome, mensagem));
		}
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Um ou mais campos estão inválidos. preencha e tente novamente!");
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@ExceptionHandler({EntidadeNaoEncontradaException.class})
	public ResponseEntity<Object> EntidadeNaoEncontradaException(NegocioException ex, WebRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Entidade não encontrada!");
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler({NegocioException.class})
	public ResponseEntity<Object> NegocioException(NegocioException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Operação não permitida!");
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
}

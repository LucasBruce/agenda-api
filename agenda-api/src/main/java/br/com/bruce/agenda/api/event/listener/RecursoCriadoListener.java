package br.com.bruce.agenda.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.bruce.agenda.api.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent>{

	@Override
	public void onApplicationEvent(RecursoCriadoEvent event) {
		Long id = event.getId();
		HttpServletResponse response = event.getResponse();
		adicionarHeader(id, response);
	}

	private void adicionarHeader(Long id, HttpServletResponse response) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(id).toUri();
		 response.setHeader("Location", uri.toASCIIString());
		
	}

}

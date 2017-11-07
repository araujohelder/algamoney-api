package com.algaworks.algamoneyapi.resource;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.repository.lancamento.filter.LancamentoFilter;
import com.algaworks.algamoneyapi.repository.projection.ResumoLancamento;
import com.algaworks.algamoneyapi.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO')")
	public ResponseEntity<Lancamento> buscarLancamentoPeloCodigo(@PathVariable Long codigo)  {
		Lancamento lancamentoRetornado = lancamentoService.buscarPeloCodigo(codigo);
		return lancamentoRetornado != null ? ResponseEntity.ok(lancamentoRetornado) : ResponseEntity.notFound().build();
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO')")
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable)  {
		return  lancamentoService.pesquisar(lancamentoFilter, pageable);
	}
	
	@GetMapping(params ="resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO')")
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable)  {
		return  lancamentoService.resumir(lancamentoFilter, pageable);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO')")
	public ResponseEntity<Lancamento> salvarLancamento(@Validated @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoCriado =  lancamentoService.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoCriado.getCodigo()) );
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoCriado);
	}
	
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO')")
	public ResponseEntity<Lancamento> deletar(@PathVariable Long codigo){
		lancamentoService.deletar(codigo);
		return ResponseEntity.noContent().build();
	}
}

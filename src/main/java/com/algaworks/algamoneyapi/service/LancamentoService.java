package com.algaworks.algamoneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import com.algaworks.algamoneyapi.repository.lancamento.filter.LancamentoFilter;
import com.algaworks.algamoneyapi.repository.projection.ResumoLancamento;
import com.algaworks.algamoneyapi.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento buscarPeloCodigo(Long codigo) {
		Lancamento lancamentoRetornado = lancamentoRepository.findOne(codigo);
		if (lancamentoRetornado == null ) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoRetornado;
	}

	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return  lancamentoRepository.filtrar(lancamentoFilter, pageable);
	} 
	
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return  lancamentoRepository.resumir(lancamentoFilter, pageable);
	}
	
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
		if (pessoa == null || pessoa.isInativa()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}

	public void deletar(long codigo) {
		Lancamento lancamento = buscarPeloCodigo(codigo);
		lancamentoRepository.delete(lancamento);;
		
	}

	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo =  buscarPeloCodigo(codigo);
		if (lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validaPessoa(lancamento);
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		return lancamentoRepository.save(lancamentoSalvo);
	}

	private void validaPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if (lancamento.getPessoa().getId() != null) {
			pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
		}
		
		if (pessoa == null || pessoa.isInativa()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
	
}

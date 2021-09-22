package com.mediaflex.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.mediaflex.os.domain.Pessoa;
import com.mediaflex.os.domain.Tecnico;
import com.mediaflex.os.dtos.TecnicoDTO;
import com.mediaflex.os.repositories.PessoaRepository;
import com.mediaflex.os.repositories.TecnicoRepository;
import com.mediaflex.os.resources.exceptions.DataIntegratyViolationException;
import com.mediaflex.os.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoarepository;
	
	@GetMapping(value = "/{id}")
	public Tecnico findById(Integer id) {
		//log.info("SERVICE - BUSCANDO TÉCNICO POR ID");
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}
	
	public Tecnico create(TecnicoDTO objDTO) {
		if(findByCPF(objDTO)!=null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return repository.save( new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()) );
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);
		
		if( findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id ) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if( obj.getList().size() > 0 ) {
			throw new DataIntegratyViolationException("Tecnico possui Ordens de Serviço e não pode ser apagado!");
		}
		repository.deleteById(id);
	}
	
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoarepository.findByCPF(objDTO.getCpf());
		
		if(obj !=null) {
			return obj;
		}
		return null;
	}



}

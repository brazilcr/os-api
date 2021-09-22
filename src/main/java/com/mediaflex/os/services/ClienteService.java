package com.mediaflex.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.mediaflex.os.domain.Cliente;
import com.mediaflex.os.domain.Pessoa;
import com.mediaflex.os.dtos.ClienteDTO;
import com.mediaflex.os.repositories.ClienteRepository;
import com.mediaflex.os.repositories.PessoaRepository;
import com.mediaflex.os.resources.exceptions.DataIntegratyViolationException;
import com.mediaflex.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoarepository;
	
	@GetMapping(value = "/{id}")
	public Cliente findById(Integer id) {
		//log.info("SERVICE - BUSCANDO TÉCNICO POR ID");
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	public Cliente create(ClienteDTO objDTO) {
		if(findByCPF(objDTO)!=null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return repository.save( new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()) );
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);
		
		if( findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id ) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Cliente obj = findById(id);
		if( obj.getList().size() > 0 ) {
			throw new DataIntegratyViolationException("Cliente possui Ordens de Serviço e não pode ser apagado!");
		}
		repository.deleteById(id);
	}
	
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoarepository.findByCPF(objDTO.getCpf());
		
		if(obj !=null) {
			return obj;
		}
		return null;
	}



}

package com.fx.algaapi.api.controller;

import java.util.List;

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

import com.fx.algaapi.domain.model.Cliente;
import com.fx.algaapi.domain.repository.ClienteRepository;
import com.fx.algaapi.domain.service.CatalogoClienteService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private ClienteRepository clienteRepository;
	private CatalogoClienteService catalogoClienteService;

	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return catalogoClienteService.salvar(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @Valid @RequestBody Cliente cliente) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteId);
		cliente = catalogoClienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> excluir(@PathVariable Long clienteId) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		catalogoClienteService.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> obter(@PathVariable Long clienteId) {
		return clienteRepository.findById(clienteId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

}

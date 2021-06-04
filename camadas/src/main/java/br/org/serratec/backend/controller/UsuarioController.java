package br.org.serratec.backend.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.backend.dto.UsuarioDTO;
import br.org.serratec.backend.dto.UsuarioInserirDTO;
import br.org.serratec.backend.exception.EmailException;
import br.org.serratec.backend.model.Usuario;
import br.org.serratec.backend.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioService service;

	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listar() {
		List<UsuarioDTO> usuariosDtos = service.listar();
		return ResponseEntity.ok(usuariosDtos);
	}

	@PostMapping
	public ResponseEntity<Object> inserir(@RequestBody UsuarioInserirDTO usuario) {
		try {
			UsuarioDTO dto = service.inserir(usuario);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId())
					.toUri();
			return ResponseEntity.created(uri).body(dto);
		} catch (EmailException e) {
			return ResponseEntity.unprocessableEntity().body(e.getMessage());
		}

	}
}

package myeshop.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import myeshop.backend.dto.ClienteRegistroDTO;
import myeshop.backend.dto.ClienteRespuestaDTO;
import myeshop.backend.dto.ClienteUpdateDTO;
import myeshop.backend.model.Cliente;
import myeshop.backend.model.InformacionFiscal;
import myeshop.backend.repository.ClienteRepository;

/**
 * Servicio de lógica de negocio para gestionar clientes.
 * 
 * @author Silvia Balmaseda
 */
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	// REGISTRAR CLIENTE
	@Transactional
	public ClienteRespuestaDTO registrarCliente(ClienteRegistroDTO dto) {

		// 1️ REGLAS DE NEGOCIO
		if (clienteRepository.existsById(dto.getNifCif())) {
			throw new RuntimeException("Ya existe un cliente con NIF/CIF: " + dto.getNifCif());
		}

		// 2️ DTO → ENTIDAD Cliente
		Cliente cliente = new Cliente();
		cliente.setNifCif(dto.getNifCif());
		cliente.setNombreCompleto(dto.getNombreCompleto());
		cliente.setEmail(dto.getEmail());

		// 3️ DTO → ENTIDAD InformacionFiscal
		InformacionFiscal infoFiscal = new InformacionFiscal();
		infoFiscal.setTelefono(dto.getTelefono());
		infoFiscal.setDireccionFiscal(dto.getDireccionFiscal());

		// Clave compartida (PK = FK)
		infoFiscal.setCliente(cliente);
		cliente.setInformacionFiscal(infoFiscal);

		// 4️ PERSISTENCIA
		Cliente guardado = clienteRepository.save(cliente);

		// 5️ ENTIDAD → DTO RESPUESTA
		return convertirADTO(guardado);
	}

	// LISTAR TODOS
	@Transactional(readOnly = true)
	public List<ClienteRespuestaDTO> listarTodos() {
		return clienteRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
	}

	// OBTENER POR NIF/CIF
	@Transactional(readOnly = true)
	public ClienteRespuestaDTO obtenerPorNif(String nifCif) {

		Cliente cliente = clienteRepository.findById(nifCif)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado con NIF/CIF: " + nifCif));

		return convertirADTO(cliente);
	}

	// MÉTODO AUXILIAR
	private ClienteRespuestaDTO convertirADTO(Cliente cliente) {
		return new ClienteRespuestaDTO(cliente.getNifCif(), cliente.getNombreCompleto(), cliente.getEmail());
	}

	// OPCIONAL ACTUALIZAR
	@Transactional
	public ClienteRespuestaDTO actualizarCliente(String nifCif, ClienteUpdateDTO dto) {

		Cliente cliente = clienteRepository.findById(nifCif)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado con NIF/CIF: " + nifCif));

		// Actualizar datos simples
		if (dto.getNombreCompleto() != null) {
			cliente.setNombreCompleto(dto.getNombreCompleto());
		}

		if (dto.getEmail() != null) {
			cliente.setEmail(dto.getEmail());
		}

		// Información fiscal
		InformacionFiscal info = cliente.getInformacionFiscal();

		if (info == null && (dto.getTelefono() != null || dto.getDireccionFiscal() != null)) {
		    info = new InformacionFiscal();
		    info.setCliente(cliente);        
		    cliente.setInformacionFiscal(info);
		}

		if (info != null) {
			if (dto.getTelefono() != null) {
				info.setTelefono(dto.getTelefono());
			}
			if (dto.getDireccionFiscal() != null) {
				info.setDireccionFiscal(dto.getDireccionFiscal());
			}
		}

		Cliente actualizado = clienteRepository.save(cliente);

		return new ClienteRespuestaDTO(actualizado.getNifCif(), actualizado.getNombreCompleto(),
				actualizado.getEmail());
	}

}

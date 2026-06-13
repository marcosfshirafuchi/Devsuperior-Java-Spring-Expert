package com.devsuperior.dscatalog.services; // Declara o pacote onde a classe de serviço está localizada

// Importações de classes utilitárias do Java
import java.util.List; // Importa a interface List para coleções de objetos
import java.util.Optional; // Importa a classe Optional para lidar com valores que podem ser nulos

// Importações de classes de projeção e DTOs (Data Transfer Objects)
import com.devsuperior.dscatalog.projections.UserDetailsProjection; // Importa uma interface de projeção para detalhes do usuário e roles
import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação @Autowired para injeção de dependência
import org.springframework.dao.DataIntegrityViolationException; // Importa exceção para violação de integridade de dados
import org.springframework.data.domain.Page; // Importa a interface Page para paginação de resultados
import org.springframework.data.domain.Pageable; // Importa a interface Pageable para informações de paginação
import org.springframework.security.core.userdetails.UserDetails; // Importa a interface UserDetails do Spring Security
import org.springframework.security.core.userdetails.UserDetailsService; // Importa a interface UserDetailsService do Spring Security
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Importa exceção para usuário não encontrado
import org.springframework.security.crypto.password.PasswordEncoder; // Importa a interface PasswordEncoder para codificação de senhas
import org.springframework.stereotype.Service; // Importa a anotação @Service para marcar a classe como um serviço Spring
import org.springframework.transaction.annotation.Propagation; // Importa enumeração para propagação de transações
import org.springframework.transaction.annotation.Transactional; // Importa a anotação @Transactional para controle transacional

import com.devsuperior.dscatalog.dto.RoleDTO; // Importa o DTO para Role
import com.devsuperior.dscatalog.dto.UserDTO; // Importa o DTO para User
import com.devsuperior.dscatalog.dto.UserInsertDTO; // Importa o DTO para inserção de User
import com.devsuperior.dscatalog.dto.UserUpdateDTO; // Importa o DTO para atualização de User
import com.devsuperior.dscatalog.entities.Role; // Importa a entidade Role
import com.devsuperior.dscatalog.entities.User; // Importa a entidade User
import com.devsuperior.dscatalog.repositories.RoleRepository; // Importa o repositório de Role
import com.devsuperior.dscatalog.repositories.UserRepository; // Importa o repositório de User
import com.devsuperior.dscatalog.services.exceptions.DatabaseException; // Importa exceção personalizada para erros de banco de dados
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException; // Importa exceção personalizada para recurso não encontrado

import jakarta.persistence.EntityNotFoundException; // Importa exceção para entidade não encontrada

// Anotação que marca esta classe como um componente de serviço do Spring
@Service
// Declaração da classe UserService, que implementa UserDetailsService para integração com o Spring Security
public class UserService implements UserDetailsService {

	// Injeção de dependência do PasswordEncoder para codificar senhas
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Injeção de dependência do UserRepository para operações de banco de dados com a entidade User
	@Autowired
	private UserRepository repository;

	// Injeção de dependência do RoleRepository para operações de banco de dados com a entidade Role
	@Autowired
	private RoleRepository roleRepository;

	// Anotação que indica que o método é transacional e somente de leitura, otimizando o desempenho
	@Transactional(readOnly = true)
	// Método para buscar todos os usuários de forma paginada
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		// Busca uma página de entidades User do repositório
		Page<User> list = repository.findAll(pageable);
		// Converte a página de entidades User para uma página de UserDTOs
		return list.map(x -> new UserDTO(x));
	}

	// Anotação que indica que o método é transacional e somente de leitura
	@Transactional(readOnly = true)
	// Método para buscar um usuário pelo ID
	public UserDTO findById(Long id) {
		// Busca um usuário pelo ID, retornando um Optional
		Optional<User> obj = repository.findById(id);
		// Obtém a entidade User do Optional ou lança uma exceção se não encontrada
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		// Retorna um UserDTO a partir da entidade encontrada
		return new UserDTO(entity);
	}

	// Anotação que indica que o método é transacional (permite operações de escrita)
	@Transactional
	// Método para inserir um novo usuário
	public UserDTO insert(UserInsertDTO dto) {
		// Cria uma nova instância da entidade User
		User entity = new User();
		// Copia os dados do DTO para a entidade
		copyDtoToEntity(dto, entity);
		// Codifica a senha do DTO e a define na entidade User
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		// Salva a entidade User no repositório
		entity = repository.save(entity);
		// Retorna um UserDTO a partir da entidade salva
		return new UserDTO(entity);
	}

	// Anotação que indica que o método é transacional
	@Transactional
	// Método para atualizar um usuário existente
	public UserDTO update(Long id, UserUpdateDTO dto) {
		try {
			// Obtém uma referência à entidade User pelo ID (não carrega todos os dados imediatamente)
			User entity = repository.getReferenceById(id);
			// Copia os dados do DTO para a entidade
			copyDtoToEntity(dto, entity);
			// Salva a entidade User atualizada no repositório
			entity = repository.save(entity);
			// Retorna um UserDTO a partir da entidade atualizada
			return new UserDTO(entity);
		}
		catch (EntityNotFoundException e) {
			// Captura a exceção se a entidade não for encontrada e lança uma exceção de recurso não encontrado
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	// Anotação que indica que o método é transacional, com propagação SUPPORTS (participa de uma transação existente ou executa sem transação)
	@Transactional(propagation = Propagation.SUPPORTS)
	// Método para deletar um usuário pelo ID
	public void delete(Long id) {
		// Verifica se o usuário existe antes de tentar deletar
		if (!repository.existsById(id)) {
			// Se não existir, lança uma exceção de recurso não encontrado
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			// Tenta deletar o usuário pelo ID
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			// Captura exceções de violação de integridade de dados (ex: usuário com dependências)
			throw new DatabaseException("Falha de integridade referencial");
		}
	}

	// Método auxiliar privado para copiar dados de um DTO para uma entidade User
	private void copyDtoToEntity(UserDTO dto, User entity) {

		// Define o primeiro nome na entidade
		entity.setFirstName(dto.getFirstName());
		// Define o sobrenome na entidade
		entity.setLastName(dto.getLastName());
		// Define o e-mail na entidade
		entity.setEmail(dto.getEmail());

		// Limpa as roles existentes da entidade antes de adicionar as novas
		entity.getRoles().clear();
		// Itera sobre as RoleDTOs do DTO
		for (RoleDTO roleDto : dto.getRoles()) {
			// Obtém uma referência à entidade Role pelo ID
			Role role = roleRepository.getReferenceById(roleDto.getId());
			// Adiciona a Role à coleção de roles da entidade User
			entity.getRoles().add(role);
		}
	}

	// Implementação do método loadUserByUsername da interface UserDetailsService
	// Usado pelo Spring Security para carregar detalhes do usuário para autenticação
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Busca o usuário e suas roles pelo e-mail usando uma projeção
		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
		// Verifica se nenhum resultado foi encontrado
		if (result.size() == 0) {
			// Se o e-mail não for encontrado, lança uma exceção
			throw new UsernameNotFoundException("Email not found");
		}
		// Cria uma nova instância de User
		User user = new User();
		// Define o e-mail do usuário
		user.setEmail(username);
		// Define a senha do usuário (obtida da projeção)
		user.setPassword(result.get(0).getPassword());
		// Itera sobre os resultados da projeção para adicionar as roles ao usuário
		for (UserDetailsProjection projection : result) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}
		// Retorna o objeto User (que implementa UserDetails)
		return user;
	}
}
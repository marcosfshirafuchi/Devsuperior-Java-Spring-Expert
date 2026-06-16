package com.devsuperior.dscatalog.entities;

// Importações necessárias para a classe User
import java.util.*; // Importa classes utilitárias como Set e HashSet

import jakarta.persistence.Column; // Importa anotação para mapear colunas no banco de dados
import jakarta.persistence.Entity; // Importa anotação para marcar a classe como uma entidade JPA
import jakarta.persistence.GeneratedValue; // Importa anotação para geração automática de valores de chave primária
import jakarta.persistence.GenerationType; // Importa enumeração para estratégias de geração de chave primária
import jakarta.persistence.Id; // Importa anotação para marcar o campo como chave primária
import jakarta.persistence.JoinColumn; // Importa anotação para especificar a coluna de junção
import jakarta.persistence.JoinTable; // Importa anotação para especificar a tabela de junção em relacionamentos ManyToMany
import jakarta.persistence.ManyToMany; // Importa anotação para mapear relacionamentos ManyToMany
import jakarta.persistence.Table; // Importa anotação para especificar o nome da tabela no banco de dados
import org.springframework.security.core.GrantedAuthority; // Importa interface do Spring Security para representar uma permissão
import org.springframework.security.core.userdetails.UserDetails; // Importa interface do Spring Security para representar detalhes do usuário

// Anotação que indica que esta classe é uma entidade JPA e será mapeada para uma tabela no banco de dados
@Entity
// Anotação que especifica o nome da tabela no banco de dados para esta entidade
@Table(name = "tb_user")
// Declaração da classe User, que implementa a interface UserDetails do Spring Security
public class User implements UserDetails {

	// Anotação que marca o campo 'id' como a chave primária da entidade
	@Id
	// Anotação que configura a estratégia de geração de valor para a chave primária (IDENTITY = auto-incremento)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // Identificador único do usuário
	private String firstName; // Primeiro nome do usuário
	private String lastName; // Sobrenome do usuário

	// Anotação que especifica que a coluna 'email' deve ter valores únicos no banco de dados
	@Column(unique = true)
	private String email; // Endereço de e-mail do usuário (também usado como nome de usuário para login)
	private String password; // Senha do usuário

	// Anotação que mapeia um relacionamento muitos-para-muitos com a entidade Role
	@ManyToMany
	// Anotação que configura a tabela de junção para o relacionamento ManyToMany
	@JoinTable(name = "tb_user_role", // Nome da tabela de junção
			joinColumns = @JoinColumn(name = "user_id"), // Coluna na tabela de junção que referencia o ID do usuário
			inverseJoinColumns = @JoinColumn(name = "role_id")) // Coluna na tabela de junção que referencia o ID da role
	private Set<Role> roles = new HashSet<>(); // Conjunto de perfis (roles) associados ao usuário

	// Construtor padrão (sem argumentos)
	public User() {
	}

	// Construtor com todos os campos (exceto roles, que é inicializado separadamente)
	public User(Long id, String firstName, String lastName, String email, String password) {
		super(); // Chama o construtor da classe pai (Object)
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	// Método getter para o ID do usuário
	public Long getId() {
		return id;
	}

	// Método setter para o ID do usuário
	public void setId(Long id) {
		this.id = id;
	}

	// Método getter para o primeiro nome do usuário
	public String getFirstName() {
		return firstName;
	}

	// Método setter para o primeiro nome do usuário
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	// Método getter para o sobrenome do usuário
	public String getLastName() {
		return lastName;
	}

	// Método setter para o sobrenome do usuário
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// Método getter para o e-mail do usuário
	public String getEmail() {
		return email;
	}

	// Método setter para o e-mail do usuário
	public void setEmail(String email) {
		this.email = email;
	}

	// Implementação do método getAuthorities() da interface UserDetails
	// Retorna a coleção de GrantedAuthority (neste caso, as roles do usuário)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	// Método getter para a senha do usuário
	public String getPassword() {
		return password;
	}

	// Implementação do método getUsername() da interface UserDetails
	// Retorna o nome de usuário usado para autenticação (neste caso, o e-mail)
	@Override
	public String getUsername() {
		return email;
	}

	// Implementação do método isAccountNonExpired() da interface UserDetails
	// Indica se a conta do usuário não expirou (sempre true neste exemplo)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// Implementação do método isAccountNonLocked() da interface UserDetails
	// Indica se a conta do usuário não está bloqueada (sempre true neste exemplo)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// Implementação do método isCredentialsNonExpired() da interface UserDetails
	// Indica se as credenciais do usuário não expiraram (sempre true neste exemplo)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// Implementação do método isEnabled() da interface UserDetails
	// Indica se o usuário está habilitado (sempre true neste exemplo)
	@Override
	public boolean isEnabled() {
		return true;
	}

	// Método setter para a senha do usuário
	public void setPassword(String password) {
		this.password = password;
	}

	// Método getter para o conjunto de roles do usuário
	public Set<Role> getRoles() {
		return roles;
	}

	// Método para adicionar uma role ao conjunto de roles do usuário
	public void addRole(Role role){
		roles.add(role);
	}

	// Método para verificar se o usuário possui uma determinada role pelo nome
	public boolean hasRoles(String roleName){
		// Itera sobre as roles do usuário
		for(Role role: roles){
			// Compara o nome da role com o nome fornecido
			if(role.getAuthority().equals(roleName)){
				return true; // Retorna true se encontrar a role
			}
		}
		return false; // Retorna false se a role não for encontrada
	}

	// Implementação do método hashCode() para a entidade User
	// Baseado apenas no ID para garantir unicidade e consistência em coleções
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	// Implementação do método equals() para a entidade User
	// Compara objetos User com base no ID para determinar igualdade
	@Override
	public boolean equals(Object obj) {
		// Verifica se é a mesma instância
		if (this == obj)
			return true;
		// Verifica se o objeto é nulo
		if (obj == null)
			return false;
		// Verifica se as classes dos objetos são diferentes
		if (getClass() != obj.getClass())
			return false;
		// Faz um cast do objeto para User
		User other = (User) obj;
		// Compara os IDs dos objetos para determinar a igualdade
		return Objects.equals(id, other.id);
	}
}
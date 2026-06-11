package com.devsuperior.dscatalog.entities;

import java.util.Objects; // Importa a classe Objects para métodos utilitários como hash e equals

import jakarta.persistence.Entity; // Importa anotação para marcar a classe como uma entidade JPA
import jakarta.persistence.GeneratedValue; // Importa anotação para geração automática de valores de chave primária
import jakarta.persistence.GenerationType; // Importa enumeração para estratégias de geração de chave primária
import jakarta.persistence.Id; // Importa anotação para marcar o campo como chave primária
import jakarta.persistence.Table; // Importa anotação para especificar o nome da tabela no banco de dados
import org.springframework.security.core.GrantedAuthority; // Importa interface do Spring Security para representar uma permissão

// Anotação que indica que esta classe é uma entidade JPA e será mapeada para uma tabela no banco de dados
@Entity
// Anotação que especifica o nome da tabela no banco de dados para esta entidade
@Table(name = "tb_role")
// Declaração da classe Role, que implementa a interface GrantedAuthority do Spring Security
public class Role implements GrantedAuthority {

	// Anotação que marca o campo 'id' como a chave primária da entidade
	@Id
	// Anotação que configura a estratégia de geração de valor para a chave primária (IDENTITY = auto-incremento)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // Identificador único da role (perfil de acesso)
	private String authority; // Nome da autoridade/permissão (ex: "ROLE_ADMIN", "ROLE_OPERATOR")

	// Construtor padrão (sem argumentos)
	public Role() {
	}

	// Construtor com todos os campos
	public Role(Long id, String authority) {
		super(); // Chama o construtor da classe pai (Object)
		this.id = id;
		this.authority = authority;
	}

	// Método getter para o ID da role
	public Long getId() {
		return id;
	}

	// Método setter para o ID da role
	public void setId(Long id) {
		this.id = id;
	}

	// Implementação do método getAuthority() da interface GrantedAuthority
	// Retorna o nome da autoridade/permissão
	@Override
	public String getAuthority() {
		return authority;
	}

	// Método setter para o nome da autoridade/permissão
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	// Implementação do método hashCode() para a entidade Role
	// Baseado apenas na 'authority' para garantir unicidade e consistência em coleções
	@Override
	public int hashCode() {
		return Objects.hash(authority);
	}

	// Implementação do método equals() para a entidade Role
	// Compara objetos Role com base na 'authority' para determinar igualdade
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
		// Faz um cast do objeto para Role
		Role other = (Role) obj;
		// Compara as 'authorities' dos objetos para determinar a igualdade
		return Objects.equals(authority, other.authority);
	}
}
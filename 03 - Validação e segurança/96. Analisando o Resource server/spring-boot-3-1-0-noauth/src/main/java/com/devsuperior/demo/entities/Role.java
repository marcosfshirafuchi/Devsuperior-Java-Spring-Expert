package com.devsuperior.demo.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
@Entity // Indica que esta classe é uma entidade JPA, mapeando-a para uma tabela no banco de dados.
@Table(name = "tb_role") // Especifica o nome da tabela no banco de dados para esta entidade como "tb_role".
public class Role implements GrantedAuthority { // Declara a classe 'Role' como uma entidade e indica que ela implementa a interface 'GrantedAuthority'.
    // 'GrantedAuthority' é uma interface do Spring Security que representa uma permissão ou papel concedido a um principal (usuário).
    // Isso significa que objetos 'Role' podem ser usados para definir as autorizações de um usuário.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    public Role() {
    }

    public Role(String authority, Long id) {
        this.authority = authority;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override // Indica que este método está sobrescrevendo um método da interface 'GrantedAuthority'.
    public String getAuthority() {
        // Retorna a string que representa a autoridade (papel/perfil) concedida.
        // No Spring Security, este método é usado para obter o nome do papel
        // (por exemplo, "ROLE_ADMIN", "ROLE_OPERATOR") associado a esta instância de Role.
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Role role)) return false;

        return Objects.equals(authority, role.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(authority);
    }
}

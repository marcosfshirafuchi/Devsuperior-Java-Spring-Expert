package com.devsuperior.demo.entities;

import java.time.LocalDate;
import java.util.*;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tb_user")
public class User implements UserDetails {
    // Declara a classe 'User' como uma classe pública.
    // O 'implements UserDetails' indica que esta classe está fornecendo
    // uma implementação para a interface 'UserDetails' do Spring Security.
    // Isso significa que a classe 'User' será usada para representar
    // os detalhes de um usuário autenticado no sistema,
    // fornecendo informações como nome de usuário, senha, autoridades,
    // e status da conta (habilitada, não expirada, etc.).

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    @ManyToMany
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Long id, String name, String email, String phone, LocalDate birthDate, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna a coleção de autoridades (papéis/perfis) concedidas ao usuário.
        // No Spring Security, isso é usado para verificar as permissões do usuário.
        return roles;
    }

    public String getPassword() {
        // Retorna a senha usada para autenticar o usuário.
        // O Spring Security usa isso para comparar com a senha fornecida durante o login.
        return password;
    }

    @Override
    public String getUsername() {
        // Retorna o nome de usuário usado para autenticar o usuário.
        // Neste caso, o email está sendo usado como nome de usuário.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Indica se a conta do usuário expirou.
        // 'true' significa que a conta é válida e não expirou.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Indica se o usuário está bloqueado ou desbloqueado.
        // 'true' significa que o usuário não está bloqueado e pode ser autenticado.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Indica se as credenciais (senha) do usuário expiraram.
        // 'true' significa que as credenciais são válidas e não expiraram.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Indica se o usuário está habilitado ou desabilitado.
        // 'true' significa que o usuário está habilitado e pode ser autenticado.
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addRole(Role role){
        roles.add(role);
    }

    public boolean hasRoles(String roleName){
        for(Role role: roles){
            if(role.getAuthority().equals(roleName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

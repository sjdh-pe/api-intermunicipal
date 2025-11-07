package br.gov.pe.sjdh.apiIntermunicipal.infra.security;

import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.model.Usuario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Considera "username" como email por padrão
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseGet(() -> usuarioRepository.findByCpf(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username)));

        if (!usuario.isAtivo()) {
            throw new UsernameNotFoundException("Usuário inativo: " + username);
        }

        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return User
                .withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!usuario.isAtivo())
                .build();
    }
}

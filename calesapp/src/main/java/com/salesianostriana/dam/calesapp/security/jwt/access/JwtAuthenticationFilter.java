package com.salesianostriana.dam.calesapp.security.jwt.access;

import com.salesianostriana.dam.calesapp.user.model.Usuario;
import com.salesianostriana.dam.calesapp.user.repository.UsuarioRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // private final UserService userService;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJwtAccessTokenFromRequest(request);

        // Validar el token
        // Si es válido, autenticar al usuario
        try {
            if (StringUtils.hasText(token) && jwtService.validateAccessToken(token)) {
                // Obtener el sub del token, que es el ID del usuario
                // Buscar el usuario por id
                // Colocar el usuario autenticado en el contexto de seguridad

                UUID id = jwtService.getUserIdFromAccessToken(token);

                Optional<Usuario> result = usuarioRepository.findById(id);

                if (result.isPresent()) {
                    Usuario user = result.get();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }

            }
        } catch (JwtException ex) {
            resolver.resolveException(request, response, null, ex);
        }

        filterChain.doFilter(request, response);

    }

    private String getJwtAccessTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtService.TOKEN_HEADER);
        // Bearer asfkñaldsjfslk.asñklfdjadlsñfajs.asñkjdfaksdñlfjal
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtService.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtService.TOKEN_PREFIX.length());
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.equals("/swagger-ui.html") ||
                path.startsWith("/webjars");
    }

}

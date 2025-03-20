package org.example.demoaws.config.security;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class AwsCongnitoJwtGrandtedAuthoritiesConverter
    implements Converter<Jwt, Collection<GrantedAuthority>> {

  @Override
  public Collection<GrantedAuthority> convert(Jwt source) {
    JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
    var defaultScopeAuthorities = authoritiesConverter.convert(source);
    var authorities = new ArrayList<>(defaultScopeAuthorities);
    authorities.addAll(getCognitoRoles(source));
    authorities.addAll(getUserPermissions(source));
    return authorities;
  }

  private List<SimpleGrantedAuthority> getCognitoRoles(Jwt source) {
    return extractAuthorities(source, "cognito:groups", "ROLE_");
  }

  private List<SimpleGrantedAuthority> getUserPermissions(Jwt source) {
    return extractAuthorities(source, "custom:permissions", "SCOPE_");
  }

  private List<SimpleGrantedAuthority> extractAuthorities(Jwt source, String claim, String prefix) {
    List<String> groupClaims = source.getClaimAsStringList(claim);
    if (isNull(groupClaims)) {
      return List.of();
    }
    return groupClaims
        .stream()
        .map(group -> prefix + group)
        .map(SimpleGrantedAuthority::new)
        .toList();
  }

}

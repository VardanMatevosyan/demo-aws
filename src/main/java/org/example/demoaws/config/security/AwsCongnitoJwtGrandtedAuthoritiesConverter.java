package org.example.demoaws.config.security;

import static java.util.Objects.isNull;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

import java.util.ArrayList;
import java.util.Arrays;
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
    return extractAuthorities(source, "custom:permissions", "PERMISSION_");
  }

  private List<SimpleGrantedAuthority> extractAuthorities(Jwt source, String claim, String prefix) {
    List<String> groupClaims = getClaimValues(source, claim);
    if (isNull(groupClaims)) {
      return List.of();
    }
    return groupClaims
        .stream()
        .map(group -> prefix + group)
        .map(SimpleGrantedAuthority::new)
        .toList();
  }

  private static List<String> getClaimValues(Jwt source, String claim) {
    List<String> groupClaims = source.getClaimAsStringList(claim);
    if (isCustomPermissionsClaim(groupClaims)) {
      String[] permissionsArray = getPermissionsArray(groupClaims);
      return Arrays.asList(permissionsArray);
    } else {
      return groupClaims;
    }
  }

  private static String[] getPermissionsArray(List<String> groupClaims) {
    String permissions = groupClaims.getFirst();
    return permissions.split(",");
  }

  private static boolean isCustomPermissionsClaim(List<String> groupClaims) {
    return groupClaims.size() == 1 && groupClaims.getFirst().contains(",");
  }

}

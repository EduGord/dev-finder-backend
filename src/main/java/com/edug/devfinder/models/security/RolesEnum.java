package com.edug.devfinder.models.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@AllArgsConstructor
@Getter
public enum RolesEnum {
    USER(new HashSet<>()),
    STAFF(Stream.of(PermissionEnum.READ, PermissionEnum.WRITE)
            .collect(Collectors.toCollection(HashSet::new))),
    ADMIN(Stream.of(PermissionEnum.READ,
                    PermissionEnum.WRITE,
                    PermissionEnum.CHANGE_PASSWORD)
            .collect(Collectors.toCollection(HashSet::new)));

    public static RolesEnum parse(String roleName) {
        return RolesEnum.valueOf(roleName.toUpperCase());
    }
    private final Set<PermissionEnum> permissions;
}

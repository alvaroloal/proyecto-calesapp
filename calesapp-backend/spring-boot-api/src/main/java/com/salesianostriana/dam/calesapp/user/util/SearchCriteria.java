package com.salesianostriana.dam.calesapp.user.util;

public record SearchCriteria(
        String key,
        String operation,
        Object value
) {
}

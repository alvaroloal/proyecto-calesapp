package com.salesianostriana.dam.calesapp.user.query;

import com.salesianostriana.dam.calesapp.model.Parada;
import com.salesianostriana.dam.calesapp.user.util.SearchCriteria;

import java.util.List;

public class ParadaSpecificationBuilder
        extends GenericSpecificationBuilder<Parada>{
    public ParadaSpecificationBuilder(List<SearchCriteria> params) {
        super(params);
    }
}

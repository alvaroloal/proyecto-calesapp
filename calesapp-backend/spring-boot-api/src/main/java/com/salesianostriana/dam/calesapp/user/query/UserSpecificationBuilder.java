package com.salesianostriana.dam.calesapp.user.query;



import com.salesianostriana.dam.calesapp.user.model.Usuario;
import com.salesianostriana.dam.calesapp.user.util.SearchCriteria;

import java.util.List;

public class UserSpecificationBuilder
    extends GenericSpecificationBuilder<Usuario>{
    public UserSpecificationBuilder(List<SearchCriteria> params) {
        super(params);
    }
}

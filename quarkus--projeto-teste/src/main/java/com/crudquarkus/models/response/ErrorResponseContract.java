package com.crudquarkus.models.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ErrorResponseContract(String layer, String message, String localizedMessage) {

}

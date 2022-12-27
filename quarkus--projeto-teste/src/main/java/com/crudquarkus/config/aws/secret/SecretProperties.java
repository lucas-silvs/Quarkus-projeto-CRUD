package com.crudquarkus.config.aws.secret;

import io.smallrye.config.ConfigMapping;


@ConfigMapping(prefix = "aws")
public interface SecretProperties {
    String secretName();
}

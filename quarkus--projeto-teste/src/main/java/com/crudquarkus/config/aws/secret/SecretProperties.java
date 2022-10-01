package com.crudquarkus.config.aws.secret;

import io.smallrye.config.ConfigMapping;


@ConfigMapping(prefix = "aws")
public interface SecretProperties {

    String secretName();
//    String url(); apenas caso utilize teste local com LocalStack
    String region();



}

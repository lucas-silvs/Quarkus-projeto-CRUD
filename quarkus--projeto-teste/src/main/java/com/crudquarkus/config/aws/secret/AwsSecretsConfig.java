package com.crudquarkus.config.aws.secret;



import io.quarkus.arc.DefaultBean;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import java.net.URI;
import java.net.URISyntaxException;

/*
Para definir uma classe bean ser iniciada durante a inicialização, se utiliza a anotação @Startup em conjunto com a
anotação do @ApplicationScoped para tornar a classe injetavel

 */
@Startup
@ApplicationScoped
public class AwsSecretsConfig {


    SecretsManagerClient secretsManagerClient;

    public AwsSecretsConfig() throws URISyntaxException {
        this.secretsManagerClient = SecretsManagerClient.builder()
                .endpointOverride(new URI("http://localhost:4566"))
                .region(Region.of("us-east-1"))
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

    }

    void startup(@Observes StartupEvent event) {
        GetSecretValueRequest secretValueRequest = GetSecretValueRequest.builder()
                .secretId("mysql/secret-teste")
                .build();
        var secretTeste = secretsManagerClient.getSecretValue(secretValueRequest);
        System.out.println(secretTeste);
    }
}

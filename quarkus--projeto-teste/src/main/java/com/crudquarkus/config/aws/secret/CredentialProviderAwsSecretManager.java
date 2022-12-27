package com.crudquarkus.config.aws.secret;


import com.google.gson.Gson;
import io.quarkus.arc.Unremovable;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.credentials.CredentialsProvider;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

/*
ao implementar a interface CredentialProvider, o datasource só inicializará ao executar
a busca das credencias
 */
@ApplicationScoped
@Unremovable
@Named("aws-secret-manager") //nomeando o credential provider para especifica-lo no application-secret.yaml
@IfBuildProfile("secret")
public class CredentialProviderAwsSecretManager implements CredentialsProvider {

    private  final SecretsManagerClient secretsManagerClient;


    private final SecretProperties secretProperties;

    public CredentialProviderAwsSecretManager(SecretsManagerClient secretsManagerClient, SecretProperties secretProperties) {
        this.secretsManagerClient = secretsManagerClient;
        this.secretProperties = secretProperties;
    }

    @Override
    public Map<String, String> getCredentials(String credentialsProviderName) {
        GetSecretValueRequest secretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretProperties.secretName())
                .build();

        //realiza a busca das credenciais na aws
        GetSecretValueResponse secretResponse = secretsManagerClient.getSecretValue(secretValueRequest);

        //converter o json retornado para um para um Map
        Map<String, String> jsonSecretValue = new Gson().fromJson(secretResponse.secretString(), Map.class);

        //seta as propriedades de credenciais nas properties e ao retorna-lo, instancia o DataSource
        Map<String, String> properties = new HashMap<>();
        properties.put(USER_PROPERTY_NAME, jsonSecretValue.get("username"));
        properties.put(PASSWORD_PROPERTY_NAME, jsonSecretValue.get("password"));
        return properties;
    }
}

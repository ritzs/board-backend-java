package com.example.boardback;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class JasyptTest {

    @Value("${jasypt.encryptor.password}") // 환경변수 사용 시 주석 처리된 부분 이용
    private String encryptKey;


    @Test
    public void jasypt_test() {
        String plainText = "hello";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(encryptKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);


        System.out.println("boptop : " + "ENC(" + encryptor.encrypt("boptop")+")");
        System.out.println("soakadlek1! : " + "ENC(" + encryptor.encrypt("soakadlek1!")+")");

        String encryptText = encryptor.encrypt(plainText);
        String decryptText = encryptor.decrypt(encryptText);
        System.out.println(decryptText);

        assertThat(plainText).isEqualTo(decryptText);

    }

}
package com.bxjh.rsa;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSASecrteKey {

    public static final String KEY_ALGORITHM = "RSA";
    
    public static final int KEY_SIZE = 1024;
    
    /**
     * 随机生成秘钥公钥对
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static RSAKeys generateKeyPair() throws NoSuchAlgorithmException{
    	KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKeys keys = new RSAKeys();
        keys.setPrivateKey(Base64.encode(privateKey.getEncoded()));
        keys.setPublicKey(Base64.encode(publicKey.getEncoded()));
        return keys;
    }
}
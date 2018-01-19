package com.xgd.boss.core.codec;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xgd.boss.core.codec.AES;
import com.xgd.boss.core.codec.SHA2;


@Component
public class EncryptUtil {
  
  public static String PRIVATE_KEY_PATH="E://";
  public static String PUBLIC_KEY_PATH="E://";
  
  @PostConstruct
  void init(){
    PRIVATE_KEY_PATH = privateKeyPath;
    PUBLIC_KEY_PATH = publicKeyPath;
  }
  
  @Value("${jlzf.privateKey.path}")
  private String privateKeyPath;
  
  @Value("${jlzf.publicKey.path}")
  private String publicKeyPath;
  
  

  public String getPrivateKeyPath() {
    return privateKeyPath;
  }

  public void setPrivateKeyPath(String privateKeyPath) {
    this.privateKeyPath = privateKeyPath;
  }

  public String getPublicKeyPath() {
    return publicKeyPath;
  }

  public void setPublicKeyPath(String publicKeyPath) {
    this.publicKeyPath = publicKeyPath;
  }
  public static String encrypt(String data, String key, String charsetName) throws Exception {
    return AES.encrypt(data, key, charsetName);
  }
  
  public static String getSHA256ofStr(String inputStr, String charsetName) throws Exception {
    return SHA2.getSHA256ofStr(inputStr,charsetName);
  }
}

package com.akfu.common.network.security

import java.security.KeyFactory
import java.security.spec.RSAKeyGenParameterSpec
import javax.crypto.Cipher
import java.security.spec.X509EncodedKeySpec
import java.security.KeyPairGenerator
import java.security.cert.X509Certificate
import java.security.spec.PKCS8EncodedKeySpec

object ConnectionEncryptionManager {
  private val keyPairGen = KeyPairGenerator.getInstance("RSA")
  keyPairGen.initialize(new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4))
  
  private val keyPair = keyPairGen.generateKeyPair
  private val publicKey = keyPair.getPublic.getEncoded
  private val privateKey = keyPair.getPrivate.getEncoded
  
  private val keyFactory = KeyFactory.getInstance("RSA")
  
  private val crypter = Cipher.getInstance("RSA")
  crypter.init(Cipher.ENCRYPT_MODE, keyFactory.generatePublic(getEncodedKeySpec(publicKey)))
  
  private val decrypter = Cipher.getInstance("RSA")
  decrypter.init(Cipher.DECRYPT_MODE, keyFactory.generatePrivate(getDecodedKeyspec(privateKey)))
  
  def getEncodedKeySpec(encodedKey: Array[Byte]) =
    new X509EncodedKeySpec(encodedKey) 
    
  def getDecodedKeyspec(encodedKey: Array[Byte]) =
    new PKCS8EncodedKeySpec(encodedKey)
  
  def getPublicKey = publicKey
  
  def getPrivateKey = privateKey
  
  def crypt(raw: Array[Byte]) = 
    crypter.doFinal(raw)
    
  def decrypt(crypted: Array[Byte]) =
    decrypter.doFinal(crypted)
}
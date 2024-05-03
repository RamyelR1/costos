/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;



import com.lowagie.text.pdf.codec.Base64;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author alfonsoc7905
 */
public class Cripto {
        
        
        private static String llave = "qttios";
        private static SecretKeySpec secretKey;
        private static byte[] key;
        public String deco = "";

        public static void setKey(String myKey) {
            MessageDigest sha = null;
            try {
                key = myKey.getBytes("UTF-8");
                sha = MessageDigest.getInstance("SHA-1");
                key = sha.digest(key);
                key = Arrays.copyOf(key, 16);
                secretKey = new SecretKeySpec(key, "AES");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        public String encrypt(String strToEncrypt, String secret) {
            try {
                //setKey(secret);
                setKey(llave);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                return Base64.encodeBytes(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            } catch (Exception e) {
                System.out.println("Error while encrypting: " + e.toString());
            }
            return null;
        }

        public String decrypt(String strToDecrypt, String secret) {
            try {
                //setKey(secret);
                setKey(llave);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return new String(cipher.doFinal(Base64.decode(strToDecrypt)));

            } catch (Exception e) {
                System.out.println("Error while decrypting: " + e.toString());
                e.printStackTrace();
            }
            return null;
        }
        
        public String reset (){
            String resp="";
            return resp = encrypt("citasepq*21", llave);
        }
        
        /*
        public String decodificar (String code){
            String resp = "";
            
            return resp;
        }*/
        
        public boolean decodificar (String code){
            String[] spliteado = code.split(",");
            deco = "0";
            for (int y = 0; y < spliteado.length; y++) {
                if(spliteado[y].equals("34")){deco = "1"; return true;}
            }
            return false;
        }


}
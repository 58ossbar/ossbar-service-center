package com.ossbar.platform.core.utils;

import com.ossbar.utils.tool.Escape;
import com.ossbar.utils.tool.HexToBytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 * 
 */
@Component
public class RSAUtil {

	@Value("${com.creatorblue.key-file-upload-path:}")
	private String RSA_KEY_TXT;

	//private final static String RSA_KEY_TXT = ResourcesUtils.getByName("com.creatorblue.file-upload-path") + "/RSAKey.txt";

	// 本地测试时，放开这个注释
	//private static final String RSA_KEY_TXT = "D:/uploads/RSAKey.txt";

    /**
     * 生成密钥对 *
	 * @return
     * @throws Exception
	 */
	public KeyPair generateKeyPair() throws Exception {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
			final int KEY_SIZE = 512;
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			saveKeyPair(keyPair);
			return keyPair;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public KeyPair getKeyPair() throws Exception {
		System.out.println("文件地址：" + RSA_KEY_TXT);
		File file = new File(RSA_KEY_TXT);
		if (!file.exists()) {
			generateKeyPair();
		}
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream oos = new ObjectInputStream(fis);
		KeyPair kp = (KeyPair) oos.readObject();
		oos.close();
		fis.close();
		return kp;
	}

	public void saveKeyPair(KeyPair kp) throws Exception {

		FileOutputStream fos = new FileOutputStream(RSA_KEY_TXT);
		PrintStream file = new PrintStream(fos, false, "UTF-8");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥

		oos.writeObject(kp);
		// oos.writeUTF(kp.toString());
		oos.close();
		fos.close();
	}

	/**
	 * * 生成公钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param publicExponent
	 *            *
	 * @return RSAPublicKey *
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus,
			byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
				modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 生成私钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param privateExponent
	 *            *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
			byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(
				modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 加密 *
	 * 
	 * @param pk
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
					: data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i
							* outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i
							* blockSize, raw, i * outputSize);
				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
				// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
				// OutputSize所以只好用dofinal方法。

				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 解密 *
	 * 
	 * @param pk
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(cipher.DECRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			bout.flush();
			bout.close();
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 还原密文
	 * 
	 * @param string
	 * @param charset
	 *            客户端编码
	 * @return
	 * @throws Exception
	 */
	public String decryptString(String string, String charset)
			throws Exception {
		if (string == "" || string == null) {
			return string;
		}
		byte[] en_result = HexToBytes.HexString2Bytes(string);
		byte[] de_test = RSAUtil.decrypt(getKeyPair().getPrivate(),
				en_result);
		String e = new String(de_test, charset);
		StringBuffer sb = new StringBuffer();
		sb.append(e);
		String str = sb.reverse().toString();
		str = Escape.unescape(str);
		return str;
	}

	/**
	 * 生成公钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] getPublicKeyString() throws Exception {
		RSAPublicKey rsap = (RSAPublicKey) getKeyPair().getPublic();
		String module = rsap.getModulus().toString(16);
		String empoent = rsap.getPublicExponent().toString(16);
		String[] strings = { module, empoent };
		return strings;
	}

	/**
	 * * *
	 * 
	 * @param args
	 *            *
	 * @throws Exception
	 */
/*	public static void main(String[] args) throws Exception {
		// 获取公钥
		String[] publicKeyString = RSAUtil.getPublicKeyString();
		System.out.println(publicKeyString[0]);
		System.out.println(publicKeyString[1]);
		RSAPublicKey rsap = (RSAPublicKey) RSAUtil.generateKeyPair().getPublic();
		String test = "admin1";
		byte[] en_test = encrypt(getKeyPair().getPublic(), test.getBytes());
		byte[] de_test = decrypt(getKeyPair().getPrivate(), en_test);
		System.out.println(new String(en_test));
		System.out.println(new String(de_test));
	}*/
}
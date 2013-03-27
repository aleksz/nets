package ee.ignite.nets;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

public class PemKeyRepository implements KeyRepository {

	private static final String EXT = ".pem";

	private String privateKeyName;
	private String publicKeyName;
	private String keyFolder;

	public PemKeyRepository(String keyFolder, String privateKeyName, String publicKeyName) {
		this.keyFolder = keyFolder;
		this.privateKeyName = privateKeyName;
		this.publicKeyName = publicKeyName;
	}

	@Override
	public PrivateKey loadPrivateKey() {

		try {
			String key = IOUtils.toString(getClass().getResourceAsStream(
					keyFolder + privateKeyName + EXT));

			Security.addProvider(new BouncyCastleProvider());
			JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
			return converter.getKeyPair((PEMKeyPair) readPEM(key)).getPrivate();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Object readPEM(String key) throws IOException {
		try (Reader reader = new StringReader(key)) {
			return new PEMParser(reader).readObject();
		}
	}

	@Override
	public PublicKey loadPublicKey() {

		try {
			String key = IOUtils.toString(getClass().getResourceAsStream(
					keyFolder + publicKeyName + EXT));

			String pem = key.replace("-----BEGIN PUBLIC KEY-----\n", "");
			pem = pem.replace("-----END PUBLIC KEY-----", "");

			byte[] encoded = Base64.decodeBase64(pem);

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);

			return KeyFactory.getInstance("RSA").generatePublic(keySpec);

		} catch (NoSuchAlgorithmException
				| IOException
				| InvalidKeySpecException e) {

			throw new RuntimeException(e);
		}
	}
}

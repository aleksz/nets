package ee.ignite.nets;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class JksKeyRepository implements KeyRepository {

	private String keyStoreToUse;
	private String keyStorePass;
	private String passForKeyToUse;
	private String keyToUse;
	private String bankCertName;

	public JksKeyRepository(String keyStoreToUse, String keyStorePass,
			String passForKeyToUse, String keyToUse, String bankCertName) {

		this.keyStoreToUse = keyStoreToUse;
		this.keyStorePass = keyStorePass;
		this.passForKeyToUse = passForKeyToUse;
		this.keyToUse = keyToUse;
		this.bankCertName = bankCertName;
	}

	public PrivateKey loadPrivateKey() {

		try {

			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(getClass().getResourceAsStream(keyStoreToUse),
					keyStorePass.toCharArray());

			return (PrivateKey) keystore.getKey(keyToUse, passForKeyToUse.toCharArray());

		} catch(KeyStoreException |
				NoSuchAlgorithmException |
				CertificateException |
				IOException |
				UnrecoverableKeyException e) {

			throw new RuntimeException(e);
		}
	}

	public PublicKey loadPublicKey() {

		try {

			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(getClass().getResourceAsStream(keyStoreToUse),
					keyStorePass.toCharArray());

			return (PublicKey) keystore.getCertificate(bankCertName).getPublicKey();

		} catch(KeyStoreException |
				NoSuchAlgorithmException |
				CertificateException |
				IOException e) {

			throw new RuntimeException(e);
		}
	}
}

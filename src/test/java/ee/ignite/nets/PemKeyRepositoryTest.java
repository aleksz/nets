package ee.ignite.nets;


import static org.fest.assertions.Assertions.assertThat;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Test;

public class PemKeyRepositoryTest {

	private String keyFolder = getClass().getResource("/pem/localhost_private.pem") != null
			? "/pem/"
			: "/resources/pem/";

	private KeyRepository repository = new PemKeyRepository(
			keyFolder,
			"localhost_private",
			"ecomtestpublic");

	@Test
	public void loadPrivateKey() {
		PrivateKey key = repository.loadPrivateKey();
		assertThat(key).isNotNull();
	}

	@Test
	public void loadPublicKey() {
		PublicKey key = repository.loadPublicKey();
		assertThat(key).isNotNull();
	}
}

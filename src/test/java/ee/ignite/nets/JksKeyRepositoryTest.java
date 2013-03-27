package ee.ignite.nets;


import static org.fest.assertions.Assertions.assertThat;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Test;

public class JksKeyRepositoryTest {

	private String keyName = "unittest";
	private String keyStoreLocation = getClass().getResource("/keystore.jks") != null
			? "/keystore.jks"
			: "/resources/keystore.jks";

	private KeyRepository repository = new JksKeyRepository(
			keyStoreLocation,
			"test1test1",
			"test1test1",
			keyName,
			keyName);

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

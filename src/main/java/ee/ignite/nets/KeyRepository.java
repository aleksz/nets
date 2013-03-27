package ee.ignite.nets;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyRepository {

	PrivateKey loadPrivateKey();

	PublicKey loadPublicKey();

}
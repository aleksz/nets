package ee.ignite.nets;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SignatureServiceTest {

	@Mock private KeyRepository keyRepository;
	@Mock private SignaturePayloadService signaturePayloadService;
	private SignatureService signatureService;
	private KeyPair keys;

	@Before
	public void initKeys() throws Exception {
		keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
	}

	@Before
	public void initService() {
		MockitoAnnotations.initMocks(this);

		signatureService = new SignatureService(
				Charset.defaultCharset().name(),
				signaturePayloadService,
				keyRepository);
	}

	@Test
	public void precondition() throws Exception {
		String signature = createTestSignature("äüõ");
		assertThat(isSignatureValid(signature, "äüõ")).isTrue();
	}

	@Test
	public void createSignature() throws Exception {
		CardPayment payment = new CardPayment(1.2, "EEK", "en", "uid1", "http://", "002");
		when(keyRepository.loadPrivateKey()).thenReturn(keys.getPrivate());
		when(signaturePayloadService.generatePayload(payment)).thenReturn("äüõ");
		String signature = signatureService.createSignature(payment);

		assertThat(signature).isNotEmpty();
		assertThat(isSignatureValid(signature, "äüõ")).isTrue();
	}

	@Test
	public void isSignatureValid() throws Exception {
		CardReceipt receipt = new CardReceipt();
		when(keyRepository.loadPublicKey()).thenReturn(keys.getPublic());
		when(signaturePayloadService.generatePayload(receipt)).thenReturn("a");
		assertThat(signatureService.isSignatureValid(receipt, createTestSignature("a"))).isTrue();
	}

	private boolean isSignatureValid(String signature, String payload) throws Exception {
		Signature signatureVerifier = Signature.getInstance("SHA1withRSA");
		signatureVerifier.initVerify(keys.getPublic());
		signatureVerifier.update(payload.getBytes());
		return signatureVerifier.verify(Hex.decodeHex(signature.toCharArray()));
	}

	private String createTestSignature(String payload) throws Exception {
		Signature signer = Signature.getInstance("SHA1withRSA");
		signer.initSign(keys.getPrivate());
		signer.update(payload.getBytes());
		return new String(Hex.encodeHex(signer.sign()));
	}
}

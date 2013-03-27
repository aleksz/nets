package ee.ignite.nets;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import org.apache.commons.codec.binary.Hex;

public class SignatureService {

	private String encoding;
	private SignaturePayloadService signaturePayloadService;
	private KeyRepository keyRepository;

	public SignatureService(String encoding,
			SignaturePayloadService signaturePayloadService,
			KeyRepository keyRepository) {

		this.encoding = encoding;
		this.signaturePayloadService = signaturePayloadService;
		this.keyRepository = keyRepository;
	}

	public String createSignature(CardPayment payment) {

		try {

			PrivateKey privateKey = keyRepository.loadPrivateKey();

			Signature signer = Signature.getInstance("SHA1withRSA");
			signer.initSign(privateKey);
			String payload = signaturePayloadService.generatePayload(payment);
			signer.update(payload.getBytes(encoding));
			String signature = new String(Hex.encodeHex(signer.sign()));

			return signature;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isSignatureValid(CardReceipt receipt, String signature) {

		try {

			PublicKey publicKey = keyRepository.loadPublicKey();

			Signature signatureVerifier = Signature.getInstance("SHA1withRSA");
			signatureVerifier.initVerify(publicKey);
			signatureVerifier.update(signaturePayloadService.generatePayload(receipt).getBytes(encoding));

			return signatureVerifier.verify(Hex.decodeHex(signature.toCharArray()));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

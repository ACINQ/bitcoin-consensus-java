#include <vector>
#include <string>
using namespace std;

#include "fr_acinq_bitcoin_ConsensusWrapper.h"
#include "bitcoinconsensus.h"

vector<unsigned char> convert(JNIEnv& jni, const jbyteArray& input)
{
	// treat a null buffer like an empty buffer
	if (!input)
		return vector<unsigned char>(0);

	jsize size = jni.GetArrayLength(input);
	vector<unsigned char> output(size);
	if (size > 0) {
		jni.GetByteArrayRegion(input, 0, size, (jbyte*)&(output[0]));
	}
	return output;
}

/*
 * Class:     fr_acinq_bitcoin_ConsensusWrapper
 * Method:    VerifyScript
 * Signature: ([B[BII)I
 */
JNIEXPORT jint JNICALL Java_fr_acinq_bitcoin_ConsensusWrapper_VerifyScript
  (JNIEnv *penv, jclass clazz, jbyteArray jpubkeyScript, jbyteArray jtx, jint indexIn, jint flags)
{
	vector<unsigned char> script = convert(*penv, jpubkeyScript);
	vector<unsigned char> tx = convert(*penv, jtx);
	bitcoinconsensus_error error;
	int result = bitcoinconsensus_verify_script(&script[0], static_cast<unsigned int>(script.size()), &tx[0], static_cast<unsigned int>(tx.size()), indexIn, flags, &error);
	if (error != bitcoinconsensus_ERR_OK) {
		jclass cla = penv->FindClass("fr/acinq/bitcoin/ConsensusWrapperException");
		jmethodID constructor = penv->GetMethodID(cla, "<init>", "(I)V");
		jthrowable exception = (jthrowable) penv->NewObject(cla, constructor, error);
		penv->Throw(exception);
		return 0;
	}
	return result;
}
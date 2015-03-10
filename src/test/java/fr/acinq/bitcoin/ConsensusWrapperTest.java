package fr.acinq.bitcoin;

import org.junit.Test;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by fabrice on 3/10/2015.
 */
public class ConsensusWrapperTest {
    @Test
    public void goodTx() throws Exception {
        byte[] scriptPubKey = DatatypeConverter.parseHexBinary("5121037953dbf08030f67352134992643d033417eaa6fcfb770c038f364ff40d761588210054cb15b5434e6778a7e4a7ddf3399c285bee2a4da9512ceb0ac6955e79d41b0552ae");
        byte[] tx = DatatypeConverter.parseHexBinary("01000000019757c385da5a7c42ff88e26c8f6968ddfaf48ec6c38beda81e77527847287d31000000004a00483045022100fd2ce3b0d1c0b4a5062a87a0efaa9c54e28fb36d733aef671461e5f2722b76c002202f343cef5f12da73e721c160a5c75dc6ff603d9b1e21d852ec721cab9b83cb1901ffffffff013da1b20200000000475121037953dbf08030f67352134992643d033417eaa6fcfb770c038f364ff40d7615882100d9d8d6e07d65b322be4c8f74f4856aa3c0a6162548931b7a84edbed8a14df5b252ae4e870300");
        int flags = 0;
        int result = ConsensusWrapper.VerifyScript(scriptPubKey, tx, 0, flags);
        assert(result == 1);
    }

    @Test(expected= ConsensusWrapperException.class)
    public void badTx() throws Exception {
        byte[] scriptPubKey = DatatypeConverter.parseHexBinary("5121037953dbf08030f67352134992643d033417eaa6fcfb770c038f364ff40d761588210054cb15b5434e6778a7e4a7ddf3399c285bee2a4da9512ceb0ac6955e79d41b0552ae");
        byte[] tx = DatatypeConverter.parseHexBinary("9999");
        int flags = 0;
        int result = ConsensusWrapper.VerifyScript(scriptPubKey, tx, 0, flags);
        assert(result == 1);
    }
}

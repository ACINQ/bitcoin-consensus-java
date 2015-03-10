package fr.acinq.bitcoin;

/**
 * Created by fabrice on 3/10/2015.
 */
public class ConsensusWrapperException extends Exception {
    final private int error;

    public ConsensusWrapperException(int error) {
        this.error = error;
    }

    int getError() {
        return error;
    }
}

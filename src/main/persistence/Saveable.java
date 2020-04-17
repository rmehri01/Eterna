package persistence;

import java.io.IOException;

/**
 * Represents any data that can be saved to a file.
 */

public interface Saveable {

    // EFFECTS: saves this object to the data folder
    void save() throws IOException;
}

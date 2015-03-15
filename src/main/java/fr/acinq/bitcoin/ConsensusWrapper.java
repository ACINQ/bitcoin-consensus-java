package fr.acinq.bitcoin;

import java.io.*;
import java.util.UUID;

public class ConsensusWrapper {
    public static native int VerifyScript(final byte[] scriptPubKey, final byte[] txTo, int nIn, int flags) throws Exception;

    private static String libraryName() {
        StringBuilder builder = new StringBuilder();
        String name = System.getProperty("fr.acinq.bitcoin.consensuswrapper", "bitcoin-consensus-wrapper");
        builder.append(name);
        switch (Integer.decode(System.getProperty("sun.arch.data.model"))) {
            case 32:
                builder.append("32");
                break;
            case 64:
                builder.append("64");
                break;
            default:
                throw new RuntimeException("cannot find out what architecture (32 or 64 bits) is running");
        }
        return System.mapLibraryName(builder.toString());
    }

    private static String osName() {
        String name = System.getProperty("os.name");
        if (name.contains("Windows")) {
            return "windows";
        } else if (name.contains("Linux")) {
            return "linux";
        }  else if (name.contains("Mac OS X")) {
            return "osx";
        } else {
            return name.replaceAll("\\W", "");
        }
    }

    private static String ressourceName() {
        String libname = libraryName();
        return osName() + "//" + libraryName();
    }

    private static void loadLibrary() {
        String libName = libraryName();
        try {
            String ressourcePath = "/" + osName() + "//" + libName;
            String uuid = UUID.randomUUID().toString();
            String extractedLibFileName = String.format("btc-%s-%s", uuid, libName);
            String targetFolder = System.getProperty("java.io.tmpdir");
            File extractedLibFile = new File(targetFolder, extractedLibFileName);
            // Extract a native library file into the target directory
            InputStream reader = ConsensusWrapper.class.getResourceAsStream(ressourcePath);
            FileOutputStream writer = new FileOutputStream(extractedLibFile);
            try {
                byte[] buffer = new byte[8192];
                int bytesRead = 0;
                while ((bytesRead = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, bytesRead);
                }
            } finally {
                // Delete the extracted lib file on JVM exit.
                extractedLibFile.deleteOnExit();
                if (writer != null)
                    writer.close();
                if (reader != null)
                    reader.close();
            }
            // Set executable (x) flag to enable Java to load the native library
            extractedLibFile.setReadable(true);
            extractedLibFile.setWritable(true, true);
            extractedLibFile.setExecutable(true);
            System.load(extractedLibFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            System.loadLibrary(libraryName());
        }
    }

    static {
        loadLibrary();
    }
}

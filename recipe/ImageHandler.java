package recipe;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;

public class ImageHandler {
    // Allowed image file exts.
    private static final String[] ALLOWED_EXTS = { "jpg", "jpeg", "png" };

    /**
     * Reads an image from the specified source directory and filename.
     *
     * @param sourceDirectory The directory containing the image.
     * @param filename        The name of the image file.
     * @return A BufferedImage if successful.
     * @throws IOException If the image cannot be read.
     * @throws IllegalArgumentException If arguments are null.
     */
    public BufferedImage readImage(File sourceDirectory, String filename) throws IOException {
        if (sourceDirectory == null || filename == null) {
            throw new IllegalArgumentException("Source directory and filename must not be null.");
        }
        File sourceFile = new File(sourceDirectory, filename);
        BufferedImage image = ImageIO.read(sourceFile);
        if (image == null) {
            throw new IOException("Failed to read image from: " + sourceFile.getAbsolutePath());
        }
        return image;
    }

    /**
     * Extracts and returns the file ext from a filename.
     *
     * @param filename The name of the file.
     * @return The ext in lowercase (without the dot), or an empty string if no ext is found.
     */
    public String getFileExt(String filename) {
        if (filename == null) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex >= 0 && lastDotIndex < filename.length() - 1) {
            return filename.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    /**
     * Checks whether a given file ext is allowed.
     *
     * @param ext The file ext to check.
     * @return True if the ext is allowed; otherwise false.
     */
    public boolean isAllowedExt(String ext) {
        for (String allowed : ALLOWED_EXTS) {
            if (allowed.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reads the image from the specified source directory and writes it to the destination file.
     * This method uses a buffered read and write.
     *
     * @param sourceDirectory The directory containing the source image.
     * @param filename        The name of the image file.
     * @param destination     The destination file where the image will be written.
     * @throws IOException If reading or writing fails.
     * @throws IllegalArgumentException If the file ext is unsupported.
     */
    public void storeBufferedImage(File sourceDirectory, String filename, File destination) throws IOException {
        String ext = getFileExt(filename);
        if (!isAllowedExt(ext)) {
            throw new IllegalArgumentException("Unsupported ext: " + ext);
        }

        File sourceFile = new File(sourceDirectory, filename);
        BufferedImage image = ImageIO.read(sourceFile);
        if (image == null) {
            throw new IOException("Not a valid image: " + sourceFile.getAbsolutePath());
        }
        if (!ImageIO.write(image, ext, destination)) {
            throw new IOException("Failed to write image in format " + ext);
        }
    }

    /**
     * Copies an image file from the source to the destination.
     *
     * @param sourceFile  The source file.
     * @param filename    The image file's name.
     * @param destination The destination file.
     * @throws IOException If the copy fails.
     * @throws IllegalArgumentException If the file ext is unsupported.
     */
    public void copyImageFile(File sourceFile, String filename, File destination) throws IOException {
        String ext = getFileExt(filename);
        if (!isAllowedExt(ext)) {
            throw new IllegalArgumentException("Unsupported ext: " + ext);
        }
        Files.copy(sourceFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Finds an image file by attempting different allowed exts.
     *
     * @param basePath  The directory where the image files are stored.
     * @param imageName The base name of the image file (without ext).
     * @return The File object if found; otherwise null.
     */
    public File findImageFile(String basePath, String imageName) {
        for (String ext : ALLOWED_EXTS) {
            File file = new File(basePath, imageName + "." + ext);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }
}

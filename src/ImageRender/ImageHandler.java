package ImageRender;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;

public class ImageHandler {
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png"};

    public BufferedImage readImage(File sourceFile, String filename) throws IOException {
        if (sourceFile == null || filename == null) {
            throw new IllegalArgumentException("Source file cannot be null.");
        }

        File fullSourceFile = new File(sourceFile, filename);
        BufferedImage image = ImageIO.read(fullSourceFile);

        if (image == null) {
            throw new IOException("Failed to read image from: " + fullSourceFile.getAbsolutePath());
        }

        return image;
    }

    public String getFileExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index >= 0 && index < filename.length() - 1) {
            return filename.substring(index + 1).toLowerCase();
        }
        return "";
    }

    public boolean isAllowed(String extension) {
        for (String allowed : ALLOWED_EXTENSIONS) {
            if (allowed.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    public void storeBuffered(File sourceFile, String filename, File destination) throws IOException {
        String ext = getFileExtension(filename);
        if (!isAllowed(ext)) {
            throw new IllegalArgumentException("Unsupported Extension");
        }

        BufferedImage image = ImageIO.read(new File(sourceFile, filename));
        if (image == null) {
            throw new IOException("Not a valid image: " + sourceFile.getAbsolutePath());
        }

        if (!ImageIO.write(image, ext, destination)) {
            throw new IOException("Failed to write image in " + ext);
        }
    }

    public void copyFile(File sourceFile, String filename, File destination) throws IOException {
        if (!isAllowed(getFileExtension(filename))) {
            throw new IllegalArgumentException("Unsupported extension");
        }
        Files.copy(sourceFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Finds the image file with the correct extension.
     *
     * @param basePath The base path where images are stored.
     * @param imageName The name of the image file without extension.
     * @return The File object representing the image with the correct extension, or null if not found.
     */
    public File getImageFile(String basePath, String imageName) {
        for (String ext : ALLOWED_EXTENSIONS) {
            File file = new File(basePath + imageName + "." + ext);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }
}

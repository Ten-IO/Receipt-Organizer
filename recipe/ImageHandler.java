package recipe;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

public class ImageHandler {
    private static final String[] allowed_EXTENSION = { "jpg",
            "jpeg", "png" };

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

    public String getFileExt(String filename) {
        int index = filename.lastIndexOf('.');
        if (index >= 0 && index < filename.length() - 1) {
            return filename.substring(index + 1).toLowerCase();
        }
        return "";
    }

    public boolean isAllowed(String extension) {
        for (String allowed : allowed_EXTENSION) {
            if (allowed.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    public void storeBuffered(File sourceFile, String filename, File destination) throws IOException {
        String ext = getFileExt(filename);
        if (!isAllowed(ext)) {
            throw new IllegalArgumentException("Unsupported Extension");
        }

        BufferedImage image = ImageIO.read(new File(sourceFile, filename));
        if (image == null) {
            throw new IOException("Not valid image" + sourceFile.getAbsolutePath());
        }
        if (!ImageIO.write(image, ext, destination)) {
            throw new IOException("Failed to write image in" + ext);
        }
    }

    public void copyFile(File sourceFile, String filename, File destination) throws IOException {
        if (!isAllowed(getFileExt(filename))) {
            throw new IllegalArgumentException("Unsupported extension");
        }
        Files.copy(sourceFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}

package ImageRender;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils {

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();

        // Apply rendering hints for better image quality
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return resizedImage;
    }

    public static ImageIcon getScaledImageIcon(String basePath, String imageName, int width, int height) {
        ImageHandler imgHandler = new ImageHandler();
        File imageFile = imgHandler.getImageFile(basePath, imageName);
        
        if (imageFile != null) {
            try {
                BufferedImage originalImage = ImageIO.read(imageFile);
                BufferedImage resizedImage = resizeImage(originalImage, width, height);
                return new ImageIcon(resizedImage);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle error, possibly return a default icon
            }
        }
        
        // Return a default icon if no file was found
        return new ImageIcon(new ImageIcon("src/images/default.jpg").getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
}

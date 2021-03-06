/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package blurfiltercg;

/**
 *
 * @author Guilherme
 */
import Util.ArrayData;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
 
public class ImageConvolution
{

  private static int bound(int value, int endIndex)
  {
    if (value < 0)
      return 0;
    if (value < endIndex)
      return value;
    return endIndex - 1;
  }
 
  public static ArrayData convolute(ArrayData inputData, ArrayData filter, int normalizer)
  {
    int inputWidth = inputData.width;
    int inputHeight = inputData.height;
    int filterSize = filter.width;
    if ((filterSize <= 0) || ((filterSize & 1) != 1))
      throw new IllegalArgumentException("Filtro precisa ter tamanho impar");
    int filterRadius = filterSize >>> 1;
    
    ArrayData outputData = new ArrayData(inputWidth, inputHeight);
    
    for (int i = inputWidth - 1; i >= 0; i--)
    {
      for (int j = inputHeight - 1; j >= 0; j--)
      {
        double newValue = 0.0;
        for (int kw = filterSize - 1; kw >= 0; kw--)
          for (int kh = filterSize - 1; kh >= 0; kh--)
            newValue += filter.get(kw, kh) * inputData.get(
                          bound(i + kw - filterRadius, inputWidth),
                          bound(j + kh - filterRadius, inputHeight));
        outputData.set(i, j, (int)Math.round(newValue / normalizer));
      }
    }
    return outputData;
  }
 
  public static ArrayData[] getArrayDatasFromImage(String filename) throws IOException
  {
    BufferedImage inputImage = ImageIO.read(new File(filename));
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    int[] rgbData = inputImage.getRGB(0, 0, width, height, null, 0, width);
    
    ArrayData reds = new ArrayData(width, height);
    ArrayData greens = new ArrayData(width, height);
    ArrayData blues = new ArrayData(width, height);
    for (int y = 0; y < height; y++)
    {
      for (int x = 0; x < width; x++)
      {
        int rgbValue = rgbData[y * width + x];
        reds.set(x, y, (rgbValue >>> 16) & 0xFF);
        greens.set(x, y, (rgbValue >>> 8) & 0xFF);
        blues.set(x, y, rgbValue & 0xFF);
      }
    }
    return new ArrayData[] { reds, greens, blues };
  }
 
  public static void writeOutputImage(String filename, ArrayData[] redGreenBlue) throws IOException
  {
    ArrayData reds = redGreenBlue[0];
    ArrayData greens = redGreenBlue[1];
    ArrayData blues = redGreenBlue[2];
    BufferedImage outputImage = new BufferedImage(reds.width, reds.height,
                                                  BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < reds.height; y++)
    {
      for (int x = 0; x < reds.width; x++)
      {
        int red = bound(reds.get(x, y), 256);
        int green = bound(greens.get(x, y), 256);
        int blue = bound(blues.get(x, y), 256);
        outputImage.setRGB(x, y, (red << 16) | (green << 8) | blue | -0x01000000);
      }
    }
    ImageIO.write(outputImage, "PNG", new File(filename));
    return;
  }
 
  public static void main(String[] args) throws IOException
  {
      
    String imgInput = "cgExample.png"; 
    String imgOutput = "cgExampleOut.png"; 
    int filterSize = 5;
    int filterNormalizer = filterSize*filterSize;
    int filterIterations = 1;
    
    System.out.println("Filter size: " + filterSize + "x" + filterSize +
                       ", Normalizer=" + filterNormalizer);
    
    ArrayData filter = createFilter(filterSize);
    
    // Cria um arrayData a partir da Imagem.
    ArrayData[] dataArrays = getArrayDatasFromImage(imgInput);

    // Realiza Iterações do Filtro.
    for (int n = 0; n < filterIterations; n++)
        for (int i = 0; i < dataArrays.length; i++)
            dataArrays[i] = convolute(dataArrays[i], filter, filterNormalizer);
    
    // Escreve a imagem final no arquivo.
    writeOutputImage(imgOutput, dataArrays);
    
    System.out.println("Done...");
 }
 public static ArrayData createFilter(int size)
 {
    ArrayData filterArray = new ArrayData(size,size);
    int value = 1;
    for (int i = 0; i < size; i++)
    {
      System.out.print("[");
      for (int j = 0; j < size; j++)
      {
        filterArray.set(j, i, value);
        System.out.print(" " + filterArray.get(j, i) + " ");
      }
      System.out.println("]");
    }
     return filterArray;
 }
}
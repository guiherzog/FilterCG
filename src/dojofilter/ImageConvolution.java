/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dojofilter;

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
    // Code Here
      return 0;
  }
 
  public static ArrayData convolute(ArrayData inputData, ArrayData filter, int normalizer)
  {
    // Code Here
    return null;//outputData;
  }
 
  public static ArrayData[] getArrayDatasFromImage(String filename) throws IOException
  {
    // Code Here
    return null;//
  }
  // Salva a imagem no Disco recebendo um ArrayData
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
    
    // Code Here;
    return filterArray;
 }
}
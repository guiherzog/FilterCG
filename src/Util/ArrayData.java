/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

/**
 *
 * @author Guilherme Herzog
 */
  public class ArrayData
  {
    public final int[] dataArray;
    public final int width;
    public final int height;
 
    public ArrayData(int width, int height)
    {
      this(new int[width * height], width, height);
    }
 
    public ArrayData(int[] dataArray, int width, int height)
    {
      this.dataArray = dataArray;
      this.width = width;
      this.height = height;
    }
 
    public int get(int x, int y)
    {  return dataArray[y * width + x];  }
 
    public void set(int x, int y, int value)
    {  dataArray[y * width + x] = value;  }
  }
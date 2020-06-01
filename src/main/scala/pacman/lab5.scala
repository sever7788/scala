package pacman
import java.io._
import java.util
import java.util.{ArrayList, Map}

import javafx.stage.Stage

object lab5 {
      def lab5(){}
      var size : File = null
      var len : Int = 0
      var index1 : Int = 0
      var index2 : Int = 0
      def main(sizes : Array[File], len:Int ):Array[File]={
            this.len = len
            for(index1 <- 0 to len-1) {
                  size = sizes(index1)
                  sizes(index1) = sort(index1, sizes)
            }
            sizes
      }
      def sort(i: Int, sizes: Array[File]): File ={
            var s: File = null
            if(i==len) size else
            if (sizes(i).length()> size.length()) {
                  s = size
                  size = sizes(i);
                  sizes(i) = s
                  sort(i+1, sizes)
            }
                  else sort(i+1, sizes);
      }
}
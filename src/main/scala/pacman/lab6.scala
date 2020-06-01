package pacman
object lab6 {

  def lab6(){}
  def main(data:Array[ObjectData]): Array[Int] = {
    var x_max: Int = 0
    var x_max_i: Int = 0
    var y_max: Int = 0
    var y_max_i: Int = 0
    var dataList : List[ObjectData] = data.toList
    var len : Int = dataList.length
    var fieldsX = collection.mutable.Map[Int, Int]()
    for (i <- 0 until 28){
      fieldsX.update(i,0)
    }
    var fieldsY = collection.mutable.Map[Int, Int]()
    for (i <- 0 until 20){
      fieldsY.update(i,0)
    }
    var index:Int = 0
    for (i <- 1 until len){
      if(i%6 == 0){
        index = ((dataList(i).x)/32).toInt
        fieldsX(index) +=1
        index = ((dataList(i).y+32)/32).toInt
        println(index)
        fieldsY(index) +=1

      }
    }

    for (i <- 0 until 28){
      if(fieldsX(i)>x_max){
        x_max_i = i;
        x_max = fieldsX(i)
      }
    }
    for (i <- 0 until 20){
      if(fieldsY(i)>y_max){
        y_max_i = i;
        y_max = fieldsY(i)
      }
    }
    println(x_max_i) 
    println(y_max_i)
    var m : Array[Int] = new Array[Int](2)
    m(0) = x_max_i;
    m(1) = y_max_i;
    m
  }
}

package com.example.dsa.Array_Hashing

//https://neetcode.io/problems/concatenation-of-array?list=neetcode250
fun main(){
  var array = intArrayOf(1,2,3,4)
    var output = concatArray(array)
    println(output.joinToString())
}

fun concatArray(intArray: IntArray): IntArray{
    var n = intArray.size
    var outputArray = IntArray(2*n)

    for(i in 0 until n){
        outputArray[i]= intArray[i]
        outputArray[i+n]= intArray[i]
    }

    return outputArray
}
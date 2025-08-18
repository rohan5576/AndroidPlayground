package com.example.dsa.Array_Hashing

fun main(){
 var string1= "racecar"
  var isAnagram = findIsStringAnagram(string1)
  println(isAnagram)
}

fun findIsStringAnagram(mString: String){
    var reverseString = mString.reversed()
    if(mString == reverseString){
        println("String is anagram")
    }
}

fun findWithTwoStrings(mString: String,mString1:String) : Boolean{
    if(mString.length!=mString1.length) return false

    var mFreq = mString.groupingBy { it }.eachCount()
    var tFreq = mString1.groupingBy { it }.eachCount()

    return mFreq==tFreq

}
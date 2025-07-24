package com.example.dsa.Array_Hashing

//https://neetcode.io/problems/duplicate-integer?list=neetcode250
fun main(){

}

fun duplicateItem(intArray: IntArray): Boolean{
    var n = intArray.size
    var setValue = mutableSetOf<Int>()

    for(num in intArray){
        if(!setValue.contains(num)){
          return true
        }
    }
    return false
}

fun withoutSet(intArray: IntArray): Boolean{
    if(intArray.isEmpty()){
        return false
    }

    var n = intArray.size

    for (i in 0 until n-1){
        if(intArray[i]== intArray[i+1]){
            return  true
        }
    }
    return false
}

fun findDuplicates(nums: IntArray): List<Int> {
    if (nums.isEmpty()) return emptyList()

    val duplicates = mutableListOf<Int>()
    val sorted = nums.sorted()

    for (i in 0 until sorted.size - 1) {
        if (sorted[i] == sorted[i + 1]) {
            // Avoid adding same duplicate more than once
            if (duplicates.isEmpty() || duplicates.last() != sorted[i]) {
                duplicates.add(sorted[i])
            }
        }
    }

    return duplicates
}
package com.example.dsa.Array_Hashing

fun main() {
    var array = intArrayOf(4, 5, 6)
    var target = 10
    var index = twoSum(array, target)
    println(index.joinToString())
}

fun twoSum(intArray: IntArray, target: Int): IntArray {
    var n = intArray.size


    for (i in 0 until n - 1) {
        for(j in i+1 until n ) {
            if (intArray[i] + intArray[j] == target) {
                return intArrayOf(i, j)
            }
        }
    }
    return intArrayOf()
}

fun twoSumCompliment(nums: IntArray, target: Int): IntArray {
  var map = mutableMapOf<Int,Int>()

    for(i in 0 until nums.size-1){
        var compliment = target - nums[i]
        if(map.containsKey(compliment)){
            return intArrayOf(map[compliment]!!,i)
        }
        map[nums[i]] = i
    }
 return intArrayOf()
}
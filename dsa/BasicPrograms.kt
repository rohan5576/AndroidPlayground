package com.example.dsa

fun main() {
    var palindrome = isPalindrome("Madam")
    println(palindrome)

    var count =10
    println("fibonacci $count is ${fibonacci(count)}")

    val s1 = "listen"
    val s2 = "silent"
    println("$s1 and $s2 are anagrams? ${areAnagrams(s1, s2)}")

    val number = 29
    println("$number is prime? ${isPrimeNumber(number)}")

    val num = 5
    println("Factorial of $num is ${isFactorial(num)}")

    val original = "Kotlin"
    println("Reversed: ${reverseString(original)}")

    //swap to number without third var

    var a= 5
    var b= 10
    a = a+b
    b= a -b
    a =a -b
    println("After swap  $a , $b")


    val arr = intArrayOf(10, 40, 20, 30, 50)
    println("Second largest: ${secondLargest(arr)}")


    var str = "I am word best coder"
    println("Smallest word ${getSmallestWord(str)}")

    println("Last Smallest word ${getLastSmallestWord(str)}")

}


//Palindrome
fun isPalindrome(str: String): Boolean {
    val cleaned = str.lowercase().filter { it.isLetterOrDigit() }
    return cleaned == cleaned.reversed()
}

//fibonacci
fun fibonacci(n : Int): List<Int>{
    val list = mutableListOf(0,1)
    for(i in 2 until n){
        list.add(list[i-1]+ list[i-2])
    }
    return list
}

//anagram
fun areAnagrams(str1 : String, str2:String):Boolean{
    return str1.lowercase().toCharArray().sorted() == str2.lowercase().toCharArray().sorted()
}

//isPrimeNumber
fun isPrimeNumber(n :Int) :Boolean{
    if(n <=1) return false
    for(i in 2 until n/2){
        if((n%2)==0) return false
    }
    return true
}

fun isFactorial(n :Int): Long{
    return if(n==0) 1 else n* isFactorial(n-1)
}

//reverse
fun reverseString(str: String):String{
    var reversed =""
    for (i in str.length-1 downTo 0){
        reversed += str[i]
    }
    return reversed
}

//secondlargest
fun secondLargest(nums:IntArray):Int?{
    if(nums.size<2) return null
    var first = Int.MIN_VALUE
    var second = Int.MIN_VALUE

    for (num in nums){
        if(num>first){
            second =first
            first =num

        } else if (num>second && num!=first){
            second = num
        }
    }
    return if (second == Int.MIN_VALUE) null else second
}


//smallest word and get last smallest word
fun getSmallestWord(string:String):String {
    var words= string.split(" ")
    var smallestword = words[0]
    for(word in words){
        if(word.length < smallestword.length){
            smallestword = word
        }
    }

    return smallestword
}

fun getLastSmallestWord(input: String): String {
    val words = input.split(" ").filter { it.isNotEmpty() }
    if (words.isEmpty()) return ""

    var smallestWord = words[0]
    for (word in words) {
        if (word.length <= smallestWord.length) {
            smallestWord = word
        }
    }
    return smallestWord
}
package com.study



fun Day01() {
    println("- Day01 - ")

    println("=== 정렬 프로그램 시작 ===")
    print("숫자(띄워쓰기로 구분) : ")
    var num = readLine()!!.split(" ").map { it.toInt() }.toMutableList()

    print("오름차순 정렬 결과 : ")
    sort(num)

    print("내림차순 정렬 결과 : ")
    reserve(num)
}




fun sort(num:MutableList<Int>){
    for(i in num.indices){
        for(x in 0 until num.size){
            if(num[i]<num[x]){
                var idx = num[i]
                num[i] = num[x]
                num[x] = idx
            }
        }
    }

    for(i in num.indices){
        print("${num[i]} ")
    }
    println()
}

fun reserve(num:MutableList<Int>){
    for(i in num.indices){
        for(x in 0 until num.size){
            if(num[i]>num[x]){
                var idx = num[i]
                num[i] = num[x]
                num[x] = idx
            }
        }
    }


    for(i in num.indices){
        print("${num[i]} ")
    }
    println()
}
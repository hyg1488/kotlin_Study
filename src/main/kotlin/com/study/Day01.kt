package com.study

fun Day01(){
    println("======================= Day 01 ========================== ")
    println("학습 요약: 입력 - readLine()!!.trim().toInt() , \n 배열 - var numbers = IntArray(크기){내용}")
    println("""         출력 - println("$ {변수} 를 출력"), """.trimMargin())
    println("배열 사용법 : numbers.sum() 배열 값들의 합 numbers.average() 평균")
    println()
    println("1번 : 나가기 , 2번 : 해당 날짜의 프로그램 보기 ")
    println("=========================================================")
    print("입력 : ")
    var chk = readLine()!!.trim().toInt()
    println()
    if(chk == 1){
        println("메인 페이지로 이동")
    }else{
        day01_P()
    }
    println()
}

fun day01_P() {
    println("===== 프로그램 시작 =====")
    print("숫자 개수 : ")
    val numCount = readLine()!!.trim().toInt()
    val numbers = IntArray(numCount) { 0 }
    println("${numCount}개의 숫자를 입력 받습니다.")

    for (i in 0 until numCount) {
        print("${i + 1}번째 숫자 : ")
        numbers[i] = readLine()!!.trim().toInt()
    }

    println("입력하신 숫자의 합은 ${numbers.sum()} 입니다.")
    println("입력하신 숫자의 평균은 ${numbers.average().toInt()} 입니다.")
    println("== 프로그램 끝 ==")
}


fun StudyList(){
    println("================== 학습 목록 ==========================")
    println("Day 학습일차는 01 ~ 06 까지 존재함")
    println("-1 번은 GitTest 로 Git 으로 올리는 프로그램")
    println("-2 번은 GitTest 와 같은 내용이지만 선생님이 작성한 코드")
    println("-99 번은 Baekjoon 프로그램으로 백준 알고리즘 문제 모음")
    println("======================================================")
}